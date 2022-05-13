package cn.yz.clothManagement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.config.redis.RedisUtil;
import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmCategoryDao;
import cn.yz.clothManagement.dao.IOmClothDao;
import cn.yz.clothManagement.dao.IOmKeywordDao;
import cn.yz.clothManagement.dao.IOmSysLogDao;
import cn.yz.clothManagement.entity.*;
import cn.yz.clothManagement.entity.dto.OmClothDto;
import cn.yz.clothManagement.entity.enums.LogType;
import cn.yz.clothManagement.service.IOmClothService;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmClothServiceImpl
 * @date 2022/1/11 17:08
 */
@Service
public class OmClothServiceImpl implements IOmClothService {

    @Resource
    private IOmClothDao omClothDao;
    @Resource
    private IOmKeywordDao omKeywordDao;
    @Resource
    private IOmCategoryDao omCategoryDao;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private IOmSysLogDao omSysLogDao;

    @Override
    public List<OmCloth> getAllClothByUser(int userId) {
        List<OmCloth> clothByUser = omClothDao.getClothByUser(userId);

//        for(OmCloth cloth:clothByUser){
//            int clothId = cloth.getClothId();
//        }

        return clothByUser;
    }

    @Override
    public List<OmCloth> getClothByUserAndCate(int userId, int categoryId) {
        List<OmCloth> clothByUserAndCate = omClothDao.getClothByUserAndCate(categoryId, userId);
        return clothByUserAndCate;
    }

    @Transactional
    @Override
    public boolean deleteClothById(int clothId) {
        OmCloth clothById = omClothDao.getClothById(clothId);
        if (CommonUtil.isEmpty(clothById)) {
            return false;
        }
        String clothUri = clothById.getClothUri();
        //删除数据库，删除缓存，删除文件
        int delete1 = omClothDao.delete(clothId);
        if(delete1 <= 0){
            return false;
        }
        //删除缓存
        int userId = clothById.getUserId();
        redisUtil.del(String.valueOf(userId));
        //文件删除图简单不用校验的
        File file = new File(CommonConstant.CLOTH_PIC_PATH + clothUri);
        if(file.exists()){
            boolean delete = file.delete();
        }
        return true;
    }

    @Transactional
    @Override
    public CommonResult<String> insertCloth(OmClothDto omClothDto) {
        //前端校验，不用判空
//        String categoryAccName = omCloth.getCategoryAccName();
//        int cateIdByAccName = omCategoryDao.getCateIdByAccName(categoryAccName);
//        omCloth.setCategoryId(cateIdByAccName);
        OmCloth omCloth = new OmCloth();
        BeanUtil.copyProperties(omClothDto,omCloth);
        int userId = ShiroUtil.getUserIdBySubject();
        omCloth.setUserId(userId);
        omCloth.setCreateTime(new DateTime());

        Map<String,List<Integer>> map = omClothDto.getKeywords();
        List<Integer> styleKeyword = map.get(CommonConstant.STYLE_KEYWORD);
        List<Integer> bodyKeyword = map.get(CommonConstant.BODY_KEYWORD);
        List<Integer> colorKeyword = map.get(CommonConstant.COLOR_KEYWORD);
        try{
            //存储到数据库
            omClothDao.insert(omCloth);
            int clothId = omCloth.getClothId();
            omKeywordDao.insertClothKeyword(clothId,styleKeyword,0);
            omKeywordDao.insertClothKeyword(clothId,bodyKeyword,1);
            omKeywordDao.insertClothKeyword(clothId,colorKeyword,2);
        }catch (SQLException e){
            return new CommonResult<>(StatusCode.EMPTY_ERROR,"存储失败，可能存在空值异常！");
        }
        //数据有变动，删除缓存
        redisUtil.del(String.valueOf(userId));

        return new CommonResult<>(StatusCode.SUCCESS,String.valueOf(omCloth.getClothId()));
    }

    @Override
    public List<String> getClothFromRedis(int userId) {
        List<Object> clothList = redisUtil.lGet(String.valueOf(userId),0,-1);
        //缓存中不存在，已经被超时清除或更新删除
        if(CommonUtil.isEmpty(clothList)||clothList.size()==0){
            //从数据库中获取并写入缓存
            List<OmCloth> clothByUser = omClothDao.getClothByUser(userId);
            clothList = new ArrayList<>();
            for(OmCloth omCloth:clothByUser){
                String clothUri = omCloth.getClothUri();
                byte[] imgDate = null;
                try {
                    InputStream in = null;
                    in = new FileInputStream(CommonConstant.CLOTH_PIC_PATH + clothUri);
                    imgDate = new byte[in.available()];
                    in.read(imgDate);
                    in.close();
                }catch (IOException e) {
                    //
                }
                BASE64Encoder encoder = new BASE64Encoder();
                String encode = encoder.encode(imgDate);
                OmClothRedis omClothRedis = new OmClothRedis(omCloth.getClothId(), encode);
                clothList.add(JSONObject.toJSONString(omClothRedis));
            }
            //放入缓存，设置超时时间为60分钟
            boolean b = redisUtil.lSet(String.valueOf(userId), clothList,CommonConstant.CLOTH_REDIS_EXPIRE_TIME);
            //日志记录一下
            OmSysLog sysLog = new OmSysLog(LogType.RECORD, "服装写入缓存", userId, "");
            omSysLogDao.insert(sysLog);
        }

        return (List<String>)(List)clothList;
    }

    @Override
    public OmClothDto getClothById(int clothId) {
        OmCloth clothById = omClothDao.getClothById(clothId);
        int categoryId = clothById.getCategoryId();
        String cateNameById = omCategoryDao.getCateNameById(categoryId);
        clothById.setCategoryName(cateNameById);
        clothById.setClothUri(CommonConstant.PIC_PATH+clothById.getClothUri());
        OmClothDto omClothDto = new OmClothDto();
        BeanUtil.copyProperties(clothById,omClothDto);
        List<Integer> styleKeywords = omKeywordDao.getKeywordsByCloth(clothId, 0);
        List<Integer> bodyKeywords = omKeywordDao.getKeywordsByCloth(clothId, 1);
        List<Integer> colorKeywords = omKeywordDao.getKeywordsByCloth(clothId, 2);
        Map<String,List<Integer>> map = new HashMap(){{
            put(CommonConstant.STYLE_KEYWORD,styleKeywords);
            put(CommonConstant.BODY_KEYWORD,bodyKeywords);
            put(CommonConstant.COLOR_KEYWORD,colorKeywords);
        }};
        omClothDto.setKeywords(map);
        return omClothDto;
    }

    @Override
    public List<OmClothDto> getClothByCate(String categoryAccName) {
        int userId = ShiroUtil.getUserIdBySubject();
        int categoryId = omCategoryDao.getCateIdByAccName(categoryAccName);
        List<OmCloth> clothByCate = getClothByUserAndCate(userId,categoryId);
        List<OmClothDto> omClothDtoList = new ArrayList<>();
        for(OmCloth omCloth:clothByCate){
            omCloth.setClothUri(CommonConstant.PIC_PATH+omCloth.getClothUri());
            int clothId = omCloth.getClothId();
            OmClothDto omClothDto = new OmClothDto();
            BeanUtil.copyProperties(omCloth,omClothDto);
            Map<String,List<Integer>> keywordMap = new HashMap<>();
            List<Integer> styleKeyword = omKeywordDao.getKeywordsByCloth(clothId,0);
            keywordMap.put(CommonConstant.STYLE_KEYWORD,styleKeyword);
            List<Integer> bodyKeyword = omKeywordDao.getKeywordsByCloth(clothId,0);
            keywordMap.put(CommonConstant.BODY_KEYWORD,bodyKeyword);
            List<Integer> colorKeyword = omKeywordDao.getKeywordsByCloth(clothId,0);
            keywordMap.put(CommonConstant.COLOR_KEYWORD,colorKeyword);
            omClothDtoList.add(omClothDto);
        }
        return omClothDtoList;
    }

    @Transactional
    @Override
    public int update(int clothId,OmClothDto omClothDto) {
        OmCloth omCloth = new OmCloth();
        BeanUtil.copyProperties(omClothDto,omCloth);
        omCloth.setClothId(clothId);
        Map<String, List<Integer>> keywords = omClothDto.getKeywords();
        List<Integer> styleKeyword = keywords.get(CommonConstant.STYLE_KEYWORD);
        List<Integer> bodyKeyword = keywords.get(CommonConstant.BODY_KEYWORD);
        List<Integer> colorKeyword = keywords.get(CommonConstant.COLOR_KEYWORD);
        try{
            omClothDao.update(omCloth);
            omKeywordDao.deleteClothKeyword(clothId);
            omKeywordDao.insertClothKeyword(clothId,styleKeyword,0);
            omKeywordDao.insertClothKeyword(clothId,bodyKeyword,1);
            omKeywordDao.insertClothKeyword(clothId,colorKeyword,2);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
}

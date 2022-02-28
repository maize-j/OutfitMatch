package cn.yz.clothManagement.service.impl;

import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.config.redis.RedisUtil;
import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmCategoryDao;
import cn.yz.clothManagement.dao.IOmClothDao;
import cn.yz.clothManagement.dao.IOmKeywordDao;
import cn.yz.clothManagement.dao.IOmSysLogDao;
import cn.yz.clothManagement.entity.*;
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
import java.util.List;
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

    @Override
    public CommonResult<String> insertCloth(OmCloth omCloth) {
        //前端校验，不用判空
//        String categoryAccName = omCloth.getCategoryAccName();
//        int cateIdByAccName = omCategoryDao.getCateIdByAccName(categoryAccName);
//        omCloth.setCategoryId(cateIdByAccName);
        int userId = ShiroUtil.getUserIdBySubject();
        omCloth.setUserId(userId);
        omCloth.setCreateTime(new DateTime());

        try{
            //存储到数据库
            omClothDao.insert(omCloth);
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
    public OmCloth getClothById(int clothId) {
        OmCloth clothById = omClothDao.getClothById(clothId);
        int categoryId = clothById.getCategoryId();
        String cateNameById = omCategoryDao.getCateNameById(categoryId);
        clothById.setCategoryName(cateNameById);
        clothById.setClothUri(CommonConstant.PIC_PATH+clothById.getClothUri());
        return clothById;
    }

    @Override
    public List<OmCloth> getClothByCate(String categoryAccName) {
        int userId = ShiroUtil.getUserIdBySubject();
        int categoryId = omCategoryDao.getCateIdByAccName(categoryAccName);
        List<OmCloth> clothByCate = getClothByUserAndCate(userId,categoryId);
        for(OmCloth omCloth:clothByCate){
            omCloth.setClothUri(CommonConstant.PIC_PATH+omCloth.getClothUri());
            int clothId = omCloth.getClothId();
            List<String> keywordsByCloth = omKeywordDao.getKeywordsByCloth(clothId);
            String collect = keywordsByCloth.stream().collect(Collectors.joining(","));
            omCloth.setClothKeyword(collect);
        }
        return clothByCate;
    }
}

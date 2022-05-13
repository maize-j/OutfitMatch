package cn.yz.clothManagement.service.impl;

import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmKeywordDao;
import cn.yz.clothManagement.entity.OmKeyword;
import cn.yz.clothManagement.service.IOmKeyWordService;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmKeyWordServiceImpl
 * @date 2022/2/27 15:16
 */
@Service
public class OmKeyWordServiceImpl implements IOmKeyWordService {

    @Resource
    private IOmKeywordDao omKeywordDao;

    @Override
    public Map<String , List<OmKeyword>> getKeywordByUser() {
        Map<String,List<OmKeyword>> map = new HashMap<>();
        int userId = ShiroUtil.getUserIdBySubject();
        List<OmKeyword> styleKeyWord = omKeywordDao.getKeyWordByUser(-1,CommonConstant.STYLE_KEYWORD);
        styleKeyWord.addAll(omKeywordDao.getKeyWordByUser(userId,CommonConstant.STYLE_KEYWORD));
        map.put(CommonConstant.STYLE_KEYWORD,styleKeyWord);
        List<OmKeyword> bodyKeyWord = omKeywordDao.getKeyWordByUser(-1,CommonConstant.BODY_KEYWORD);
        bodyKeyWord.addAll(omKeywordDao.getKeyWordByUser(userId,CommonConstant.BODY_KEYWORD));
        map.put(CommonConstant.BODY_KEYWORD,bodyKeyWord);
        List<OmKeyword> colorKeyWord = omKeywordDao.getKeyWordByUser(-1,CommonConstant.COLOR_KEYWORD);
        colorKeyWord.addAll(omKeywordDao.getKeyWordByUser(userId,CommonConstant.COLOR_KEYWORD));
        map.put(CommonConstant.COLOR_KEYWORD,colorKeyWord);

        return map;
    }
}

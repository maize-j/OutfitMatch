package cn.yz.clothManagement.service.impl;

import cn.hutool.core.date.DateTime;
import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmKeywordDao;
import cn.yz.clothManagement.entity.OmKeyword;
import cn.yz.clothManagement.service.IOmKeyWordService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public List<OmKeyword> getKeywordByUser() {
//        int userId = ShiroUtil.getUserIdBySubject();
        int userId = 5;
        List<OmKeyword> keyWordByUser = omKeywordDao.getKeyWordByUser(userId);
        if(CommonUtil.isEmpty(keyWordByUser) || keyWordByUser.size() == 0){
            //用户没有创建关键字
            keyWordByUser = omKeywordDao.getKeyWordByUser(-1);
            for(OmKeyword omKeyword:keyWordByUser){
                omKeyword.setCreateTime(new DateTime());
                omKeyword.setUserId(userId);
            }
            //将这些关键字赋值给用户
            omKeywordDao.batchInsert(keyWordByUser);
        }
        return keyWordByUser;
    }
}

package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.dao.IOmClothDao;
import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.service.IOmClothService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private IOmClothDao iOmClothDao;

    @Override
    public List<OmCloth> getClothByUser(int user) {
        List<OmCloth> clothByUser = iOmClothDao.getClothByUser(user);
        return clothByUser;
    }

    @Override
    public List<OmCloth> getClothByUserAndCate(int userId, int categoryId) {
        List<OmCloth> clothByUserAndCate = iOmClothDao.getClothByUserAndCate(categoryId, userId);
        return clothByUserAndCate;
    }
}

package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.dao.IOmOutfitDao;
import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.OmOutfit;
import cn.yz.clothManagement.entity.OutfitData;
import cn.yz.clothManagement.service.IOmOutfitService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmOutfitServiceImpl
 * @date 2022/2/12 16:41
 */
@Service
public class OmOutfitServiceImpl implements IOmOutfitService {

    @Resource
    private IOmOutfitDao omOutfitDao;

    @Override
    public List<OmOutfit> getOutfitByUser(int userId) {
        List<Integer> outfitByUserId = omOutfitDao.getOutfitByUserId(userId);
        if(CommonUtil.isEmpty(outfitByUserId)){
            return null;
        }
        List<OmOutfit> outfitList = new ArrayList<>();
        for(Integer num:outfitByUserId){
            List<OmCloth> clothByOutfitId = omOutfitDao.getClothByOutfitId(num);
            OmOutfit omOutfit = new OmOutfit(num,userId,clothByOutfitId);
            outfitList.add(omOutfit);
        }
        return outfitList;
    }
}

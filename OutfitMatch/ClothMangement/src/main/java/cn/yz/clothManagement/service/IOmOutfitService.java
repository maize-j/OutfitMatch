package cn.yz.clothManagement.service;

import cn.yz.clothManagement.entity.OmOutfit;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmOutfitService
 * @date 2022/2/12 16:39
 */
public interface IOmOutfitService {

    List<OmOutfit> getOutfitByUser(int userId);
}

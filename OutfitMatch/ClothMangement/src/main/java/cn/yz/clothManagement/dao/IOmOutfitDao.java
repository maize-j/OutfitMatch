package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmCloth;
import cn.yz.clothManagement.entity.OmOutfit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmOutfitDao
 * @date 2022/2/10 16:20
 */
@Mapper
public interface IOmOutfitDao {

    List<Integer> getOutfitByUserId(@Param("userId") int userId);

    List<OmCloth> getClothByOutfitId(@Param("outfitId") int outfitId);

    int deleteOutfitId(@Param("outfitId")int outfitId);

}

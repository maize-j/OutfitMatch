package cn.yz.clothManagement.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OutfitDao
 * @date 2022/1/1 22:56
 */
@Mapper
public interface IOutfitDataDao {
    void delByOutfitId(@Param("outfitId") String outfitId);

    List<String> getOutfitData(@Param("start") int start,@Param("limit") int limit);

    List<String> getItemIds(@Param("start") int start,@Param("limit") int limit);

    LinkedList<String> getUnDownloadOutfitIds(@Param("flag") int flag);

    void updateDownloadByOutfitId(@Param("outfitId")String outfitId, @Param("download")int download);

    int getCountByOutfitId(@Param("outfitId") String outfitId);

    List<String> getOutfitByFlag(@Param("flag") int flag);

    List<String> getOutfitByCount(@Param("flag") int flag, @Param("count") int count);
}

package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.ItemData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IItemDataDao
 * @date 2022/1/2 13:59
 */
@Mapper
public interface IItemDataDao {

    void updateSeasonById(@Param("id") Integer id, @Param("season") String season);

    List<ItemData> getItemDataBySeason(@Param("limit") int limit);

    List<ItemData> getItemDataByIds(@Param("itemIds") List<String> itemIds);

    List<ItemData> getItemDataByOutfit(@Param("outfitId") String outfitId);

    void updateDescById(@Param("id") Integer id, @Param("desc") String desc);
}

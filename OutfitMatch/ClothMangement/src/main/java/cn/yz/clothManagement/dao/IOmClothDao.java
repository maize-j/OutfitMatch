package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmCloth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmClothDao
 * @date 2022/1/10 21:53
 */
@Mapper
public interface IOmClothDao {

    /**新增图片*/
    int insert(OmCloth omCloth) throws SQLException;;
    /**查询用户的所有服装*/
    List<OmCloth> getClothByUser(@Param("userId") int userId);
    /**根据类别查询用户的服装*/
    List<OmCloth> getClothByUserAndCate(@Param("categoryId")int categoryId,@Param("userId")int userId);
    /**删除一件衣服*/
    int delete(@Param("clothId") int clothId);
    /**根据服装id查询服装*/
    OmCloth getClothById(@Param("clothId")int clothId);
    /**根据服装id修改服装*/
    int update(OmCloth omCloth);
}

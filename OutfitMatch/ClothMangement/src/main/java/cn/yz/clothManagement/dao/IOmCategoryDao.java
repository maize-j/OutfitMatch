package cn.yz.clothManagement.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmCloth
 * @date 2022/1/10 19:55
 */
@Mapper
public interface IOmCategoryDao {

    /**获取所有的服装大类*/
    List<String> getFullCateName();
    /**根据服装大类获取详细分类*/
    List<String> getAccCateNameByCateName(@Param("categoryName") String categoryName);

    int getCateIdByAccName(@Param("categoryAccName") String categoryAccName);

}

package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmKeywordDao
 * @date 2022/1/10 20:30
 */
@Mapper
public interface IOmKeywordDao {

    /**添加关键字*/
    void insert(OmKeyword omKeyword);
    /**根据用户和关键字更新使用数量*/
    void updateCount(@Param("userId") int userId,@Param("keyword") String keyword, @Param("count") int count);
    /**根据用户和关键字查询关键字*/
    OmKeyword getOmKeywordByUserIdAndKeyword(@Param("userId") int userId,@Param("keyword")String keyword);
    /**根据用户Id查询使用最多的topN关键字*/
    List<OmKeyword> getTopNKeyWord(@Param("userId") int userId,@Param("num") int num);
    /**删除关键字*/
    void delete(@Param("userId")int userId, @Param("keyword") String keyword);
    /**根据服装id获取使用的关键字*/
    List<String> getKeywordsByCloth(@Param("clothId") Integer clothId);

    List<OmKeyword> getKeyWordByUser(@Param("userId") int userId);

    /**批量插入*/
    int batchInsert(@Param("omKeywords")List<OmKeyword> omKeywords);
}

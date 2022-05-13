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

    /**删除关键字*/
    void delete(@Param("keywordId") String keywordId);

    /**根据用户获取关键字*/
    List<OmKeyword> getKeyWordByUser(@Param("userId") int userId,@Param("classification")String classification);

    /**批量插入*/
    int batchInsert(@Param("omKeywords")List<OmKeyword> omKeywords);

    /**根据服装id获取使用的关键字，type为哪种类型的关键字，0为风格，1为质感，2为颜色*/
    List<Integer> getKeywordsByCloth(@Param("clothId") Integer clothId,@Param("type")int type);

    /**添加服装关联的关键字*/
    void insertClothKeyword(@Param("clothId")int clothId,@Param("keywordList")List<Integer> keywordList,@Param("type")int type);

    /**删除服装相关关键字*/
    void deleteClothKeyword(@Param("clothId")int clothId);
}

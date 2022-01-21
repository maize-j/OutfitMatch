package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.Keywords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IKeywords
 * @date 2022/1/6 19:56
 */
@Mapper
public interface IKeywordsDao {

    List<Keywords> getKeywords();

    void updataCountById(@Param("id")int id,@Param("count")int count);
}

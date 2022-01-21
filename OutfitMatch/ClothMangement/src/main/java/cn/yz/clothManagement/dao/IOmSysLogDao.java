package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmSysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmSysLogDao
 * @date 2022/1/14 16:43
 */
@Mapper
public interface IOmSysLogDao {

    public void insert(OmSysLog omSysLog);

    OmSysLog getById(@Param("logId") int logId);
}

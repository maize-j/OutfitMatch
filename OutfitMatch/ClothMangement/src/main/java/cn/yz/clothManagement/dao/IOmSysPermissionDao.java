package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmSysPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmSysPermission
 * @date 2022/1/12 16:03
 */
@Mapper
public interface IOmSysPermissionDao {

    /**添加权限*/
    void insert(OmSysPermission omSysPermission);

}

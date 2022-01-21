package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmSysPermission;
import cn.yz.clothManagement.entity.OmSysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmSysRole
 * @date 2022/1/12 15:41
 */
@Mapper
public interface IOmSysRoleDao {

    /**获取所有的角色*/
    List<OmSysRole> getFullRole();
    /**根据角色获得权限*/
    List<OmSysPermission> getPermissionByRole(@Param("roleId") int roleId);
    /**为角色添加权限*/
    void insert(int roleId,int permissionId);
    /**删除角色权限*/
    void delete(int roleId,int permissionId);

}

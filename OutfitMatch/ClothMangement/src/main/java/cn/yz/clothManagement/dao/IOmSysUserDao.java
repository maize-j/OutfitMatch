package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmSysRole;
import cn.yz.clothManagement.entity.OmSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmSysUser
 * @date 2022/1/12 14:41
 */
@Mapper
public interface IOmSysUserDao {

    /**新增*/
    void insert(OmSysUser user);
    /**根据用户名获取用户*/
    OmSysUser getUserByName(@Param("username") String username);
    /**根据用户Id获得角色*/
    List<OmSysRole> getRoleByUser(@Param("userId") int userId);
    /**为用户添加角色*/
    void insertRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);
    /**删除用户的角色*/
    void deleteRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);
}


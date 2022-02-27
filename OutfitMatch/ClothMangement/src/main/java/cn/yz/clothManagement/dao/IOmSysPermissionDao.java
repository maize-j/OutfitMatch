package cn.yz.clothManagement.dao;

import cn.yz.clothManagement.entity.OmSysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    /**根据类别获取权限*/
    List<Integer> getPermisseionByCate(@Param("categoryName") String categoryName);
    /**根据角色和类别获取权限*/
    List<Integer> getPermisseionByRole(@Param("roleId") int roleId, @Param("permissions") List<Integer> permissions);

    List<OmSysPermission> getPermisseionByIds(@Param("permissions") List<Integer> permissions);
}

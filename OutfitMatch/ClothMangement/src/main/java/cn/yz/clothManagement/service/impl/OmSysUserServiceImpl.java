package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.dao.IOmSysPermissionDao;
import cn.yz.clothManagement.dao.IOmSysRoleDao;
import cn.yz.clothManagement.dao.IOmSysUserDao;
import cn.yz.clothManagement.entity.*;
import cn.yz.clothManagement.service.IOmSysUserService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysUserServiceImpl
 * @date 2022/1/12 15:55
 */
@Service
public class OmSysUserServiceImpl implements IOmSysUserService {

    @Resource
    private IOmSysUserDao omSysUserDao;

    @Resource
    private IOmSysRoleDao omSysRoleDao;

    @Resource
    private IOmSysPermissionDao omSysPermissionDao;

    @Override
    public OmSysUser getUserByName(String username) {
        OmSysUser userByName = omSysUserDao.getUserByName(username);
        if(CommonUtil.isEmpty(userByName)){
            throw new AuthenticationException("token有误或用户名不存在！");
        }
        int userId = userByName.getUserId();
        List<OmSysRole> roleByUser = omSysUserDao.getRoleByUser(userId);
        for(OmSysRole omSysRole:roleByUser){
            int roleId = omSysRole.getRoleId();
            List<OmSysPermission> permissionByRole = omSysRoleDao.getParentPermissionByRole(roleId);
            for(OmSysPermission omSysPermission:permissionByRole){

            }
            omSysRole.setPermissions(permissionByRole);
        }
        userByName.setRoles(roleByUser);
        return userByName;
    }

    @Override
    public CommonResult<Object> getPermissionByUser(String username) {
        OmSysUser userByName = omSysUserDao.getUserByName(username);
        if(CommonUtil.isEmpty(userByName)){
            return new CommonResult<>(StatusCode.ERROR,"用户不存在");
        }
        int userId = userByName.getUserId();
        List<OmSysRole> roleByUser = omSysUserDao.getRoleByUser(userId);
        if(CommonUtil.isEmpty(roleByUser)){
            return new CommonResult<>(StatusCode.ERROR,"用户无任何权限，请联系管理员");
        }
        //父权限
        Map<String,Map<String, String>> parentPermission = new HashMap();
        roleByUser.forEach(omSysRole->{
            int roleId = omSysRole.getRoleId();
            //根据父权限获取子权限
            List<OmSysPermission> permissionByRole = omSysRoleDao.getParentPermissionByRole(roleId);
            //父权限下的子权限
            Map<String, String> permissions = new HashMap<>();
            permissionByRole.forEach(omSysPermission->{
                int permissionId = omSysPermission.getPermissionId();
                List<OmSysPermission> permissionByRole1 = omSysRoleDao.getPermissionByRole(roleId, permissionId);
                //将子权限添加进父权限
                permissionByRole1.forEach(permission->{
                    permissions.put(permission.getName(),permission.getRoute());
                });
                parentPermission.put(omSysPermission.getName(),permissions);
            });
        });
        return new CommonResult<>(StatusCode.SUCCESS,userByName);
    }

    @Override
    public List<ChildrenRouterEntity> getPermessionByCate(String categoryName) {
        int userId = ShiroUtil.getUserIdBySubject();
        int roleByUser = omSysRoleDao.getRoleByUser(userId);
        List<Integer> permisseionByCate = omSysPermissionDao.getPermisseionByCate(categoryName);
        List<Integer> permisseionByRole = omSysPermissionDao.getPermisseionByRole(roleByUser, permisseionByCate);
        List<OmSysPermission> permisseionByIds = omSysPermissionDao.getPermisseionByIds(permisseionByRole);
        List<ChildrenRouterEntity> res = new ArrayList<>();
        for(OmSysPermission omSysPermission:permisseionByIds){
            ChildrenRouterEntity child = new ChildrenRouterEntity(omSysPermission.getName(),"view",null,omSysPermission.getRoute(),omSysPermission.getPermission(),true,"iconfont icon-tushuguanli");
            res.add(child);
        }
        return res;
    }
}

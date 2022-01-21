package cn.yz.clothManagement.service.impl;

import cn.yz.clothManagement.dao.IOmSysRoleDao;
import cn.yz.clothManagement.dao.IOmSysUserDao;
import cn.yz.clothManagement.entity.OmSysPermission;
import cn.yz.clothManagement.entity.OmSysRole;
import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.service.IOmSysUserService;
import cn.yz.clothManagement.utils.CommonUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
            List<OmSysPermission> permissionByRole = omSysRoleDao.getPermissionByRole(roleId);
            omSysRole.setPermissions(permissionByRole);
        }
        userByName.setRoles(roleByUser);
        return userByName;
    }
}

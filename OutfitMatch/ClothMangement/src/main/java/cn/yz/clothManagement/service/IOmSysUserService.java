package cn.yz.clothManagement.service;

import cn.yz.clothManagement.entity.ChildrenRouterEntity;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmSysUser;

import java.util.List;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName IOmSysUserService
 * @date 2022/1/12 15:46
 */
public interface IOmSysUserService {
    OmSysUser getUserByName(String username);

    CommonResult<Object> getPermissionByUser(String username);

    List<ChildrenRouterEntity> getPermessionByCate(String categoryName);
}

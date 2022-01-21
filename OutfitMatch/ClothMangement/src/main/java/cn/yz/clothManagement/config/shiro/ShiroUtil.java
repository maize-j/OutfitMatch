package cn.yz.clothManagement.config.shiro;

import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.utils.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ShiroUtil
 * @date 2022/1/16 16:50
 */
public class ShiroUtil {

    public static int getUserIdBySubject(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        OmSysUser omSysUser = null;
        if(CommonUtil.isNotEmpty(principals)){
            omSysUser = (OmSysUser)principals.getPrimaryPrincipal();
        }
        int userId = 0;
        if(CommonUtil.isNotEmpty(omSysUser)){
            userId = omSysUser.getUserId();
        }
        return userId;
    }
}

package cn.yz.clothManagement.config.shiro;

import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName CustomCredentialsMatcher
 * @date 2022/1/16 10:56
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        OmSysUser user = (OmSysUser)info.getPrincipals().getPrimaryPrincipal();
        String salt = user.getSalt();
        //加密类型，密码，盐值，迭代次数
        Object tokenCredentials = PasswordUtil.encrypt(username,password,salt);
        //数据库存储密码
        Object accountCredentials = user.getPassword();
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }
}

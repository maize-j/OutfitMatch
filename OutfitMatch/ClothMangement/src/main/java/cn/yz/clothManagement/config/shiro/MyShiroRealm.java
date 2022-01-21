package cn.yz.clothManagement.config.shiro;

import cn.yz.clothManagement.config.redis.RedisUtil;
import cn.yz.clothManagement.dao.IOmSysLogDao;
import cn.yz.clothManagement.entity.OmSysLog;
import cn.yz.clothManagement.entity.OmSysPermission;
import cn.yz.clothManagement.entity.OmSysRole;
import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.service.IOmSysUserService;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import cn.yz.clothManagement.utils.JWTUtil;
import lombok.SneakyThrows;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName MyShiroRealm
 * @date 2022/1/11 22:48
 */
public class MyShiroRealm extends AuthorizingRealm {

    //创建用户表和权限表

    @Resource
    private IOmSysUserService omSysUserService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IOmSysLogDao omSysLogDao;

    /**
     * 设置自定义认证加密方式
     *
     * @param credentialsMatcher 默认加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //自定义认证加密方式
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        // 设置自定义认证加密方式
        super.setCredentialsMatcher(customCredentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("MyShiroRealm->doGetAuthorizationInfo");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        OmSysUser userInfo = (OmSysUser) principalCollection.getPrimaryPrincipal();
        for (OmSysRole role : userInfo.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for (OmSysPermission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyShiroRealm->doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //用户名
        String username = (String) token.getPrincipal();

        String credentials = String.valueOf(token.getCredentials());
        //获取用户信息
        OmSysUser userInfo = checkUser(username,credentials);

        if (userInfo == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo,
                credentials,ByteSource.Util.bytes(userInfo.getSalt()),
                getName());
        return authenticationInfo;
    }

    public OmSysUser checkUser(String username,String password) throws AuthenticationException, ParseException {
        OmSysUser user = omSysUserService.getUserByName(username);
        if (username == null) {
            throw new AuthenticationException("用户不存在!");
        }

        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!tokenRefresh(username,password,user.getPassword())) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }
        return user;
    }

    public boolean tokenRefresh(String username,String password, String encryptPassword) {
        String token = String.valueOf(redisUtil.get(CommonConstant.REDIS_USER_TOKEN + username));
        if (CommonUtil.isNotEmpty(token)) {
            //校验token有效性
            if (!JWTUtil.verify(token, username, password,encryptPassword)) {
                String encrypt = JWTUtil.sign(username, password, encryptPassword);
                // 设置超时时间
                redisUtil.set(CommonConstant.REDIS_USER_TOKEN + username, encrypt);
                redisUtil.expire(CommonConstant.REDIS_USER_TOKEN + username, CommonConstant.USER_TOKEN_EXPIRE_TIME);
                omSysLogDao.insert(new OmSysLog(1,"——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— ",0,username));
//            log.debug("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— "+ token);
            }

            return true;
        }
        return false;

    }
}

package cn.yz.clothManagement.config.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ShiroConfig
 * @date 2022/1/11 22:50
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器  从上到下执行
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        /**
         * anon 无需认证就能访问
         * authc 必须认证了才能访问
         * user 必须拥有记住我功能才能访问
         * perms 拥有对某个资源的权限才能访问
         * role 拥有某个角色权限才能访问
         */
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/getIP", "anon");
//        filterChainDefinitionMap.put("/hello", "anon");
        filterChainDefinitionMap.put("/images", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/register", "anon");
        //配置退出 shiro已经帮我们实现退出的代码
        filterChainDefinitionMap.put("/logout", "logout");
        //需要权限验证
//        filterChainDefinitionMap.put("/**", "authc");
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwtFilter", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        filterChainDefinitionMap.put("/**", "jwtFilter");
        //登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登陆成功跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器（（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMather = new HashedCredentialsMatcher();
//        hashedCredentialsMather.setHashAlgorithmName("md5"); //散列算法:这里使用MD5算法;
//        hashedCredentialsMather.setHashIterations(1); //散列的次数，如果散列两次，相当于 md5(md5(""));
//        return hashedCredentialsMather;
//    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "403");
        r.setExceptionMappings(mappings);
        r.setDefaultErrorView("error");
        r.setExceptionAttribute("ex");
        return r;
    }

//    @Bean
//    public ShiroFilter shiroFilter(){
//        ShiroFilter shiroFilter = new ShiroFilter();
//        return shiroFilter;
//    }

}

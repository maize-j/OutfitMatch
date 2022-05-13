package cn.yz.clothManagement.config.shiro;

import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import cn.yz.clothManagement.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ShiroFilter
 * @date 2022/1/14 16:58
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException("Token失效，请重新登录", e);
        }
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String userToken = httpServletRequest.getHeader(CommonConstant.USER_ACCESS_TOKEN);
        if(CommonUtil.isEmpty(userToken)){
            userToken = httpServletRequest.getParameter("token");
        }
        if(CommonUtil.isEmpty(userToken)){
            throw new AuthenticationException("token为空");
        }
        // 添加token验证，获取token参数
        String username = JWTUtil.getByKey(userToken,"username");
        String password = JWTUtil.getByKey(userToken,"password");
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

}

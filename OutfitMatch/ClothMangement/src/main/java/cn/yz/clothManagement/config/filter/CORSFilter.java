package cn.yz.clothManagement.config.filter;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName CORSFilter
 * @date 2022/2/6 20:49
 */
@Component
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("CORSFilter->doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //解决跨域问题
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Token, Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

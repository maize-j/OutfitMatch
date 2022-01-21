package cn.yz.clothManagement.config.shiro;

import cn.yz.clothManagement.dao.IOmSysUserDao;
import cn.yz.clothManagement.entity.CommonResult;
import cn.yz.clothManagement.entity.OmSysUser;
import cn.yz.clothManagement.entity.StatusCode;
import cn.yz.clothManagement.utils.JWTUtil;
import cn.yz.clothManagement.utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ShiroController
 * @date 2022/1/13 15:46
 */
@RestController
public class ShiroController {

    @Resource
    private IOmSysUserDao omSysUserDao;

    @PostMapping("/login")
    public CommonResult login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);//执行登录的方法
            return new CommonResult(StatusCode.SUCCESS,"登录成功");
        }catch(UnknownAccountException e){
            return new CommonResult(StatusCode.SUCCESS,"用户名错误");
        }catch (IncorrectCredentialsException e){
            return new CommonResult(StatusCode.SUCCESS,"密码错误");
        }

    }
}

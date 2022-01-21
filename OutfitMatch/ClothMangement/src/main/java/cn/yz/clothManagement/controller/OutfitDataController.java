package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.config.shiro.ShiroUtil;
import cn.yz.clothManagement.entity.OmSysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OutfitDataController
 * @date 2022/1/1 23:09
 */
@RestController
public class OutfitDataController {

    @GetMapping("/hello")
    public String hello(){
        int userId = ShiroUtil.getUserIdBySubject();
        System.out.println("userIdBySubject:"+userId);
        System.out.println("hello");
        System.out.println(SecurityUtils.getSubject());
        System.out.println(SecurityUtils.getSubject().getPrincipals());
        OmSysUser omSysUser = (OmSysUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        System.out.println(omSysUser);
//        int userIdBySubject = ShiroUtil.getUserIdBySubject();
//        System.out.println("userIdBySubject:"+userIdBySubject);
        return "hello cyj";
    }

}

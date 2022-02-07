package cn.yz.clothManagement.controller;

import cn.yz.clothManagement.config.redis.RedisUtil;
import cn.yz.clothManagement.dao.IOmSysLogDao;
import cn.yz.clothManagement.dao.IOmSysUserDao;
import cn.yz.clothManagement.entity.*;
import cn.yz.clothManagement.utils.CommonConstant;
import cn.yz.clothManagement.utils.CommonUtil;
import cn.yz.clothManagement.utils.JWTUtil;
import cn.yz.clothManagement.utils.PasswordUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName OmSysController
 * @date 2022/1/12 16:23
 */
@RestController
public class OmSysController {

    @Resource
    private IOmSysUserDao omSysUserDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IOmSysLogDao omSysLogDao;

    @PostMapping("/sys/registery")
    public CommonResult<String> registery(@RequestBody OmSysUser omSysUser){

        //不用判空了，前端校验了
        String username = omSysUser.getUsername();
        String password = omSysUser.getPassword();
        String salt = CommonUtil.randomGen(8);

        String encrypt = PasswordUtil.encrypt(username, password, salt);
        OmSysUser user = new OmSysUser(username, encrypt, salt);
        omSysUserDao.insert(user);
        return new CommonResult<>(StatusCode.SUCCESS,"注册成功");
    }

    @PostMapping("/sys/login")
    public CommonResult<String> login(@RequestBody OmSysUser omSysUser){
        String username = omSysUser.getUsername();
        String password = omSysUser.getPassword();
        if(CommonUtil.isNotEmpty(username) && CommonUtil.isNotEmpty(password)){
            OmSysUser userByName = omSysUserDao.getUserByName(username);
            String encryptPassword = null;
            if(CommonUtil.isNotEmpty(userByName)){
                encryptPassword = userByName.getPassword();
                String password1 = PasswordUtil.encrypt(username, password, userByName.getSalt());
                if(!encryptPassword.equals(password1)){
                    return new CommonResult<>(StatusCode.LOGIN_ERROR,"用户名或密码错误");
                }
            }
            String token = JWTUtil.sign(username,password,encryptPassword);
            redisUtil.set(CommonConstant.REDIS_USER_TOKEN + username, token);
            redisUtil.expire(CommonConstant.REDIS_USER_TOKEN + username, CommonConstant.USER_TOKEN_EXPIRE_TIME);
            omSysLogDao.insert(new OmSysLog(0,"登录成功",userByName.getUserId(),userByName.getUsername()));
            return new CommonResult<>(StatusCode.SUCCESS,token);
        }

        //这里前端校验了
        return new CommonResult<>(StatusCode.EMPTY_ERROR,"用户名和密码不能为空");
    }

    @GetMapping("/sys/getPermession")
    public CommonResult<Object> getPermession(@RequestParam("username") String username){

//        if(){
//
//            return new CommonResult<>(StatusCode.SUCCESS,null);
//        }
        return new CommonResult<>(StatusCode.EMPTY_ERROR,"用户名不存在");


    }

}

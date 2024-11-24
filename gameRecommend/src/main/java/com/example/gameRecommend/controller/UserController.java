package com.example.gameRecommend.controller;


import com.example.gameRecommend.utils.Result;
import com.example.gameRecommend.entity.User;
import com.example.gameRecommend.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2023-07-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return Result.success(userService.getById(id));
    }
    @GetMapping("/hello")
    public Result test() {
        return Result.success("hello");
    }

    //登录后可以添加用户
    @PostMapping("/register")
    public Result registerUser(@RequestBody HashMap<String,Object> params){
        //对密码进行加密
        return userService.register(params);
    }
    //登录后可以修改用户信息
    @PostMapping("/updateBasic")
    public Result updateBasic(@RequestBody HashMap<String,Object> params){
        return userService.updateBasic(params);
    }

    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody HashMap<String,Object> params){
        return userService.updatePwd(params);
    }

    @PostMapping("/fupdatePwd")
    public Result fupdatePwd(@RequestBody HashMap<String,Object> params){
        return userService.fupdatePwd(params);
    }

    @PostMapping("/accessUser")
    public Result accessUser(String id){
        return userService.accessUser(id);
    }

    @PostMapping("/demoteUser")
    public Result demoteUser(String id){
        return userService.demoteUser(id);
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(String id){
        return userService.deleteUser(id);
    }

    //给swagger2加注解的
    @GetMapping("/getByPage")
    @ApiOperation("用户分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "want",value = "是否申请"),
    })
    public Result userGetByPage(Long current, Long size, Boolean want){
        return userService.pageUsers(current, size,want);
    }

    @PostMapping("/sendCaptcha")
    public Result sendCaptcha(String phoneNumber){
        Result result = Result.build();
        if(userService.getUserByPhone(phoneNumber).getCode()==200){
            result.setCode(200);
            result.setMsg("发送成功");
        }
        else{
            result.setCode(500);
            result.setMsg("尚未注册");
        }
        return result;
    }

    @PostMapping("/rsendCaptcha")
    public Result rsendCaptcha(String phoneNumber){
        Result result = Result.build();
        if(userService.getUserByPhone(phoneNumber).getCode()==200){
            result.setCode(500);
            result.setMsg("已经注册");
        }
        else{
            //api发送验证码
            //后端做持久化处理
            result.setCode(200);
            result.setMsg("发送成功");

        }
        return result;
    }

    @PostMapping("/verifyCaptcha")
    public Result verifyCaptcha(String phoneNumber,String verificationCode){
        Result result = Result.build();
        //从后端持久化中拿出数据
        if(Objects.equals(verificationCode, "1234")){
            result.setCode(200);
            result.setMsg("验证成功");
            return result;
        }
        else{
            result.setCode(500);
            result.setMsg("验证失败");
        }
        return result;
    }

    @PostMapping("/loginverifyCaptcha")
    public Result loginverifyCaptcha(String phoneNumber,String verificationCode){
        Result result = Result.build();
        if(Objects.equals(verificationCode, "1234")){
            result.setCode(200);
            result.setMsg("验证成功");
            result.setData(userService.getUserByPhone(phoneNumber).getData());
            return result;
        }
        else{
            result.setCode(500);
            result.setMsg("验证失败");
        }
        return result;
    }

    @PostMapping("/rverifyCaptcha")
    public Result rverifyCaptcha(String phoneNumber,String verificationCode){
        Result result = Result.build();
        result.setCode(200);
        result.setMsg("发送成功");
        return result;
    }
}

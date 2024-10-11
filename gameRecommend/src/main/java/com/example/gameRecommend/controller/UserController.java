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
}

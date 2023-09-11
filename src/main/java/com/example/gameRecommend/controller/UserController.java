package com.example.gameRecommend.controller;


import com.example.gameRecommend.utils.Result;
import com.example.gameRecommend.entity.User;
import com.example.gameRecommend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return Result.success(userService.getById(id));
    }
    @GetMapping("/hello")
    public Result test() {
        return Result.success("hello");
    }

    //登录后可以添加用户
    @GetMapping("/register")
    public Result registerUser(@RequestParam String username,@RequestParam String password){
        //对密码进行加密
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setId(null);
        user.setAvatar("NULL");
        user.setEmail(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userService.save(user);
        return Result.success(user);
    }
}

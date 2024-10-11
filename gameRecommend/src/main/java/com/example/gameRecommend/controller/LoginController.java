package com.example.gameRecommend.controller;

import com.example.gameRecommend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/login")
    public Result login(){
        // System.out.println("LoginController login");
        return Result.fail("尚未登录，请先登录！");
    }
}
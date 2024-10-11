package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.User;
import com.example.gameRecommend.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 用于管理登录
@Service
public class CustomUserServiceImpl implements UserDetailsService {
    @Resource
    UserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //System.out.println(s.contains("@"));
        User tUser = new User();
        if(s.contains("@"))
            tUser = tUserMapper.loadUserByUserEmail(s);
        else{
            tUser = tUserMapper.loadUserByUserPhone(s);
        }
        if (s == null){  //如果用户名不存在，就抛出下面的信息
            throw new UsernameNotFoundException("账号不存在！");
        }
        //用户存在的话就返回hr对象
        return tUser;
    }
}
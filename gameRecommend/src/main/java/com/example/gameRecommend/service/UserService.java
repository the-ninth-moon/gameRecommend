package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.utils.Result;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
public interface UserService extends IService<User> {
    Result register(HashMap<String, Object> params);
    Result updateBasic(HashMap<String, Object> params);
    Result updatePwd(HashMap<String, Object> params);
    Result pageUsers(Long current, Long limit, Boolean want);
    Result accessUser(String id);
    Result demoteUser(String id);
    Result deleteUser(String id);
}

package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-07-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User loadUserByUserPhone(String phone);
    User loadUserByUserEmail(String email);
}

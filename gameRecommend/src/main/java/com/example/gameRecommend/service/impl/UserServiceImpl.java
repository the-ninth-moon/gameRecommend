package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.entity.Blog;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.User;
import com.example.gameRecommend.mapper.UserMapper;
import com.example.gameRecommend.service.UserService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Result register(HashMap<String, Object> params) {
        Result result = Result.build();
        User user = new User();
        //tBlog.setId(Long.parseLong(params.get("id").toString()));
        String pe = (String) params.get("phone");
        if(pe.contains("@")){
            user.setEmail(pe);
        }
        else{
            user.setPhone(pe);
        }
        user.setPassword(passwordEncoder.encode((String) params.get("password")));
        user.setUsername((String) params.get("userName"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole(3);
        userMapper.insert(user);
        result.setCode(200);
        result.setMsg("注册成功");
        return result;
    }

    @Override
    public Result updateBasic(HashMap<String, Object> params) {
        Result result = Result.build();
        User user = userMapper.selectById(Long.parseLong(params.get("id").toString()));
        if(user==null){
            result.setCode(500);
            result.setMsg("没有这个用户");
            return result;
        }
        user.setUsername((String)params.get("userName"));
        user.setAvatar((String)params.get("avatar"));
        user.setWant((Boolean) params.get("want"));
        userMapper.updateById(user);
        user.setPassword(null);
        result.setCode(200);
        result.setData(user);
        result.setMsg("修改成功");
        return result;
    }

    @Override
    public Result updatePwd(HashMap<String, Object> params) {
        Result result = Result.build();
        User user = userMapper.selectById(Long.parseLong(params.get("id").toString()));
        if(user==null){
            result.setCode(500);
            result.setMsg("没有这个用户");
            return result;
        }

        if(!passwordEncoder.matches((String) params.get("old"),user.getPassword()))//不相等旧密码错误
        {
            result.setCode(500);
            result.setMsg("旧密码错误");
            return result;
        }
        user.setPassword(passwordEncoder.encode((String)params.get("newPwd")));
        userMapper.updateById(user);
        user.setPassword(null);
        result.setCode(200);
        result.setData(user);
        result.setMsg("修改成功");
        return result;
    }

    @Override
    public Result pageUsers(Long current, Long limit, Boolean want) {
        Result result = Result.build();
        //创建Page对象
        Page<User> tUserPage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //获取传入讲师的条件是否为空
        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        if (want != null){
            //构建条件
            wrapper.eq("want",want);
        }
        //以更新时间排序（降序）
        wrapper.orderByDesc("update_time");

        //调用mybatis plus分页方法进行查询
        userMapper.selectPage(tUserPage,wrapper);

        //通过Page对象获取分页信息
        List<User> tUserList = tUserPage.getRecords(); //每页的数据 list集合
        long size = tUserPage.getSize(); //每页显示的条数
        long total = tUserPage.getTotal(); //总记录数
        long pages = tUserPage.getPages(); //总页数
        for(int i=0;i<tUserList.size();i++){
            tUserList.get(i).setPassword(null);
        }

        result.setCode(200);
        result.setData(tUserPage);
        return result;
    }

    @Override
    public Result accessUser(String id) {
        Result result = Result.build();
        User user = userMapper.selectById(Long.parseLong(id));
        if(user==null){
            result.setCode(500);
            result.setMsg("没有这个用户");
            return result;
        }
        user.setWant(false);
        user.setRole(2);
        userMapper.updateById(user);
        user.setPassword(null);
        result.setCode(200);
        result.setMsg("批准成功");
        return result;
    }
    @Override
    public Result demoteUser(String id) {
        Result result = Result.build();
        User user = userMapper.selectById(Long.parseLong(id));
        if(user==null){
            result.setCode(500);
            result.setMsg("没有这个用户");
            return result;
        }
        user.setWant(false);
        user.setRole(3);
        userMapper.updateById(user);
        user.setPassword(null);
        result.setCode(200);
        result.setMsg("降级成功");
        return result;
    }
    @Override
    public Result deleteUser(String id) {
        Result result = Result.build();
        User user = userMapper.selectById(Long.parseLong(id));
        if(user==null){
            result.setCode(500);
            result.setMsg("没有这个用户");
            return result;
        }
        userMapper.deleteById(user);
        user.setPassword(null);
        result.setCode(200);
        result.setMsg("删除成功");
        return result;
    }
}

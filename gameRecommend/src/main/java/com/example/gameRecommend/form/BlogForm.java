package com.example.gameRecommend.form;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.mapper.BlogMapper;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * 表单校验
 */
public class BlogForm {
    public static Result check(HashMap<String,Object> params) {
        String title = (String) params.get("title");
        String gameName = (String) params.get("gameName");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String content = (String) params.get("content");
//        Boolean published = (Boolean) params.get("published");
        Result info = new Result();
        if (StringUtils.isEmpty(title)) {
            info.setCode(500);
            info.setMsg("title 不能为空");
        }else if (StringUtils.isEmpty(description)) {
            info.setCode(500);
            info.setMsg("description 不能为空");
        }else if (StringUtils.isEmpty(gameName)) {
            info.setCode(500);
            info.setMsg("游戏名 不能为空");
        }else if (StringUtils.isEmpty(first_picture)) {
            info.setCode(500);
            info.setMsg("first_picture 不能为空");
        }else if (StringUtils.isEmpty(content)) {
            info.setCode(500);
            info.setMsg("content 不能为空");
        }else {
            info.setMsg("校验成功");
            info.setCode(200);
        }
        return info;
    }

    public static Result checkTemporaryBlog(HashMap<String,Object> params) {
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String content = (String) params.get("content");
        Result info = new Result();
        if (StringUtils.isEmpty(title)) {
            info.setCode(500);
            info.setMsg("title 不能为空");
        }else if (StringUtils.isEmpty(description)) {
            info.setCode(500);
            info.setMsg("description 不能为空");
        }else if (StringUtils.isEmpty(first_picture)) {
            info.setCode(500);
            info.setMsg("first_picture 不能为空");
        }else if (StringUtils.isEmpty(content)) {
            info.setCode(500);
            info.setMsg("content 不能为空");
        }
        return info;
    }
}
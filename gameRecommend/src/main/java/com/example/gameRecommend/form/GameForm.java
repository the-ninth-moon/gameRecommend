package com.example.gameRecommend.form;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.gameRecommend.entity.Tag;
import com.example.gameRecommend.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * 表单校验
 */
public class GameForm {
    public static Result check(HashMap<String,Object> params) {
        String title = (String) params.get("name");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String gameContent = (String) params.get("gameContent");
        List<Tag> tags = (List) params.get("tGameTags");
        //Long type_id = Long.parseLong(params.get("typeId").toString());
//        Boolean published = (Boolean) params.get("published");
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
        }else if (StringUtils.isEmpty(gameContent)) {
            info.setCode(500);
            info.setMsg("gameContent 不能为空");
        }else {
            info.setMsg("校验成功");
            info.setCode(200);
        }
        return info;
    }

    public static Result checkTemporaryGame(HashMap<String,Object> params) {
        String title = (String) params.get("name");
        String description = (String) params.get("description");
        String first_picture = (String) params.get("first_picture");
        String gameContent = (String) params.get("gameContent");
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
        }else if (StringUtils.isEmpty(gameContent)) {
            info.setCode(500);
            info.setMsg("gameContent 不能为空");
        }
        return info;
    }
}
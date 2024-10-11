package com.example.gameRecommend.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gameRecommend.entity.Tag;
import com.example.gameRecommend.service.TagService;
import com.example.gameRecommend.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tTagService;

    Result result = Result.build();

    @PostMapping("/saveTag")
    public Result saveTag(@RequestBody Tag tTag){
        if (StringUtils.isEmpty(tTag.getName())){
            result.setCode(500);
            result.setMsg("标签名称不能为空！");
            return result;
        }
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<Tag>();
        queryWrapper.eq("name",tTag.getName());
        List<Tag> tagList = tTagService.list(queryWrapper);
        if (tagList.size() > 0){
            result.setCode(500);
            result.setMsg("添加失败，已有该标签名称");
            return result;
        }
        Boolean re = tTagService.save(tTag);
        if (re){
            result.setCode(200);
            result.setMsg("添加标签成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("添加标签失败！");
            return result;
        }

    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping("/getAllTag")
    public Result getAllTag(){
        List<Tag> tagList = tTagService.list();
        return Result.success(200,"查询成功！",tagList);
    }

    /**
     * 标签的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getTagByPage")
    @ApiOperation("标签的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public Result getTagByPage(Long current, Long size){
        return tTagService.pageTags(current, size);
    }

    /**
     * 通过名称查找标签
     * @param name
     * @return
     */
    @GetMapping("/getTagByName")
    public Result getTagByName(String name){
        return tTagService.getTagByName(name);
    }

    /**
     * 更新标签
     * @param tTag
     * @return
     */
    @PutMapping("/updateTag")
    public Result updateTag(@RequestBody Tag tTag){
        if (StringUtils.isEmpty(tTag.getName())){
            result.setMsg("分类名称不能为空");
            return result;
        }
        return tTagService.updateTag(tTag);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("deleteTagById")
    public Result deleteTagById(Long id){
        return tTagService.deleteTagById(id);
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @GetMapping("/getTagById")
    public Result getTagById(Long id){
        return tTagService.getTagById(id);
    }

    @GetMapping("/getTagByGameId")
    public Result getTagByGameId(String id)
    {
        return tTagService.getTagByGameId(id);}
}

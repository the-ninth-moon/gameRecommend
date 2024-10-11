package com.example.gameRecommend.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gameRecommend.entity.Type;
import com.example.gameRecommend.service.TypeService;
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
@RequestMapping("/type")
public class TypeController {

    @Autowired
    TypeService tTypeService;

    Result result = Result.build();

    @PostMapping("/saveType")
    public Result saveType(@RequestBody Type tType){
        if (StringUtils.isEmpty(tType.getName())){
            result.setCode(500);
            result.setMsg("分类专栏名称不能为空！");
            return result;
        }
        QueryWrapper<Type> queryWrapper = new QueryWrapper<Type>();
        queryWrapper.eq("name",tType.getName());
        List<Type> tBlogList = tTypeService.list(queryWrapper);
        if (tBlogList.size() > 0){
            result.setCode(500);
            result.setMsg("添加失败，已有该分类专栏名称");
            return result;
        }
        boolean re = tTypeService.save(tType);
        if (re){
            result.setCode(200);
            result.setMsg("添加成功！");
        }else {
            result.setCode(500);
            result.setMsg("添加失败！");
        }
        return result;

    }

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping("/getAllType")
    public Result getAllType(){
        List<Type> typeList = tTypeService.list();
        return Result.success(200,"查询成功！",typeList);
    }
    /**
     * 分类的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getTypeByPage")
    @ApiOperation("分类的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public Result getByPage(Long current, Long size){
        return tTypeService.pageTypes(current, size);
    }

    /**
     * 通过名称查找分类
     * @param name
     * @return
     */
    @GetMapping("/getTypeByName")
    public Result getTypeByName(String name){
        return tTypeService.getTypeByName(name);
    }

    /**
     * 更新分类
     * @param tType
     * @return
     */
    @PutMapping("/updateType")
    public Result updateType(@RequestBody Type tType){
        if (StringUtils.isEmpty(tType.getName())){
            result.setMsg("分类名称不能为空");
            return result;
        }
        return tTypeService.updateType(tType);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("deleteTypeById")
    public Result deleteTypeById(Long id){
        return tTypeService.deleteTypeById(id);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/getTypeById")
    public Result getTypeById(Long id){
        return tTypeService.getTypeById(id);
    }
}
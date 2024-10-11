package com.example.gameRecommend.controller;


import com.example.gameRecommend.entity.Links;
import com.example.gameRecommend.service.LinksService;
import com.example.gameRecommend.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/links")
public class LinksController {
    @Autowired
    LinksService tLinksService;

    Result result = Result.build();

    /**
     * 新增友链
     * @param tLinks
     * @return
     */
    @PostMapping("/saveLink")
    public Result saveLink(@RequestBody Links tLinks){
        tLinks.setCreateTime(LocalDateTime.now());
        Boolean re = tLinksService.save(tLinks);
        if (re){
            result.setCode(200);
            result.setMsg("添加友链成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("添加友链失败！");
            return result;
        }

    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getAllLink")
    public Result getAllLink(){
        List<Links> linksList = tLinksService.list();
        return Result.success(200,"查询成功！",linksList);
    }

    /**
     * 友链的分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getLinksByPage")
    @ApiOperation("友链的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
    })
    public Result getLinksByPage(Long current, Long size){
        return tLinksService.pageLinks(current, size);
    }

    /**
     * 通过名称查找友链
     * @param name
     * @return
     */
    @GetMapping("/getLinksByName")
    public Result getLinksByName(String name){
        return tLinksService.getLinksByName(name);
    }

    /**
     * 更新友链
     * @param tLinks
     * @return
     */
    @PutMapping("/updateLink")
    public Result updateLink(@RequestBody Links tLinks){
        return tLinksService.updateLink(tLinks);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("deleteLinkById")
    public Result deleteLinkById(Long id){
        return tLinksService.deleteLinkById(id);
    }

    /**
     * 根据id查询友链
     * @param id
     * @return
     */
    @GetMapping("/getLinksById")
    public Result getLinksById(Long id){
        return tLinksService.getLinksById(id);
    }
}

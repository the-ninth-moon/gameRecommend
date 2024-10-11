package com.example.gameRecommend.controller;


import com.example.gameRecommend.entity.Blog;
import com.example.gameRecommend.form.BlogForm;
import com.example.gameRecommend.mapper.BlogMapper;
import com.example.gameRecommend.service.BlogService;
import com.example.gameRecommend.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/blog")
public class BlogController {


    @Autowired
    BlogService tBlogService;

    Result result = Result.build();

    /**
     * 分页查询
     * @param current
     * @param size
     * @param published
     * @param share_statement
     * @param is_delete
     * @return
     */
    //给swagger2加注解的
    @GetMapping("/getByPage")
    @ApiOperation("博客分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "original",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
    })
    public Result getByPage(Long current, Long size, Boolean published,Boolean processed,
                            String original, Boolean share_statement, Boolean is_delete){
        return tBlogService.pageBlogs(current, size,published,processed,original,share_statement,is_delete);
    }
    //给swagger2加注解的
    @GetMapping("/writerGetByPage")
    @ApiOperation("博客分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "original",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
    })
    public Result writerGetByPage(String id,Long current, Long size, Boolean published,Boolean processed,
                            String original, Boolean share_statement, Boolean is_delete){
        return tBlogService.writerPageBlogs(id,current, size,published,processed,original,share_statement,is_delete);
    }
    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    @GetMapping("/getByTitle")
    @ApiOperation("通过文章标题查询")
    @ApiImplicitParam(name = "title",value = "文章的标题")
    public Result findByTitle(String title){
        return tBlogService.getByTitle(title);
    }

    /**
     * 保存文章和标签
     * @param params
     * @return
     */
    @PostMapping("/saveBT")
    public Result saveBlogTag(@RequestBody HashMap<String,Object> params) {
        //对博客需要进行校验
        Result check = BlogForm.check(params);
        if (check.getCode() == 500) {
            return check;
        }else {
            return tBlogService.saveBT(params);
        }
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @PostMapping("/temporarySave")
    public Result temporarySave(@RequestBody HashMap<String,Object> params){
        Result checkTemporaryBlog = BlogForm.checkTemporaryBlog(params);
        if (checkTemporaryBlog.getCode() == 500) {
            return checkTemporaryBlog;
        }
        else {
            return tBlogService.temporarySave(params);
        }
    }

    /**
     * 更新博客和标签
     * @param params
     * @return
     */
    @PutMapping("/updateBlog")
    public Result updateBlog(@RequestBody HashMap<String,Object> params){
        Result checkUpdateBlog = BlogForm.check(params);
        if (checkUpdateBlog.getCode() == 500) {
            return checkUpdateBlog;
        }
        else {
            return tBlogService.updateBlog(params);
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/logicDeleteBlog")
    public Result logicDeleteBlog(String id){
        Blog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.logicDeleteBlog(id);
        }else {
            result.setCode(500);
            result.setMsg("没有数据");
            return result;
        }
    }

    /**
     * 删除博客和对应标签
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBlog")
    public Result deleteBlog(String id){
        Blog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.deleteBT(id);
        }else {
            result.setCode(500);
            result.setMsg("没有数据");
            return result;
        }
    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @GetMapping("/recoveryBlog")
    public Result recoveryBlog(String id){
        return tBlogService.recoveryBlog(id);
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @GetMapping("/getByBlogId")
    @ResponseBody
    public Result getByBlogId(String id){
        result.setCode(200);
        result.setMsg("查询成功");
        result.setData(tBlogService.getByBlogId(id));
        return result;
    }
    @GetMapping("/processBlog")
    @ResponseBody
    public Result processBlog(String id){
        result.setCode(200);
        result.setMsg("查询成功");
        result.setData(tBlogService.processBlog(id));
        return result;
    }
}

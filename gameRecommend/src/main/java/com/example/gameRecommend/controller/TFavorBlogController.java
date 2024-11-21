package com.example.gameRecommend.controller;


import com.example.gameRecommend.mapper.TFavorBlogMapper;
import com.example.gameRecommend.mapper.TLikeBlogMapper;
import com.example.gameRecommend.service.ITFavorBlogService;
import com.example.gameRecommend.service.ITLikeBlogService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/favors-blog")
public class TFavorBlogController {
    @Autowired
    private ITFavorBlogService favorBlogService;
    @Autowired
    private TFavorBlogMapper favorBlogMapper;

    @PostMapping("/blog")//添加
    public Result favorBlog(@RequestParam Long userId, @RequestParam Long blogId) {
        boolean favord = favorBlogService.favorBlog(userId, blogId);
        Result result=Result.build();
        if (!favord) {
            result.setCode(220);
            return result;
        }
        result.setCode(200);
        return result;
    }
    @GetMapping("/getfavor")//添加
    public Result isFavorBlog(@RequestParam Long userId) {
        Result result=Result.build();
        result.setData(favorBlogMapper.GetFavorsById(userId));
        result.setCode(200);
        return result;
    }
    @GetMapping("/isfavor")//添加
    public Result isFavorBlog(@RequestParam Long userId, @RequestParam Long blogId) {
        int favord = favorBlogMapper.countFavors(userId, blogId);
        Result result=Result.build();
        if (favord==0) {
            result.setCode(200);
            result.setData(0);
            return result;
        }
        result.setCode(200);
        result.setData(1);
        return result;
    }

    @GetMapping("/blog/count")
    public Result getFavorCountByBlogId(@RequestParam Long blogId) {
        int favorCount = favorBlogService.getFavorCountByBlogId(blogId);
        Result result=Result.build();
        result.setData(favorCount);
        result.setCode(200);
        return result;
    }
}

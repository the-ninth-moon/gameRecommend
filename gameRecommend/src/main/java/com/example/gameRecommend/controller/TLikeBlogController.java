package com.example.gameRecommend.controller;



import com.example.gameRecommend.mapper.TLikeBlogMapper;
import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITLikeBlogService;
import com.example.gameRecommend.service.ITLikeGameService;
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
@RequestMapping("/likes-blog")
public class TLikeBlogController {
    @Autowired
    private ITLikeBlogService likeBlogService;
    @Autowired
    private TLikeBlogMapper likeBlogMapper;

    @PostMapping("/blog")//添加
    public Result likeBlog(@RequestParam Long userId, @RequestParam Long blogId) {
        boolean liked = likeBlogService.likeBlog(userId, blogId);
        Result result=Result.build();
        if (!liked) {
            result.setCode(220);
            return result;
        }
        result.setCode(200);
        return result;
    }

    @GetMapping("/islike")//添加
    public Result isLikeBlog(@RequestParam Long userId, @RequestParam Long blogId) {
        int liked = likeBlogMapper.countLikes(userId, blogId);
        Result result=Result.build();
        if (liked==0) {
            result.setCode(200);
            result.setData(0);
            return result;
        }
        result.setCode(200);
        result.setData(1);
        return result;
    }

    @GetMapping("/blog/count")
    public Result getLikeCountByBlogId(@RequestParam Long blogId) {
        int likeCount = likeBlogService.getLikeCountByBlogId(blogId);
        Result result=Result.build();
        result.setData(likeCount);
        result.setCode(200);
        return result;
    }
}

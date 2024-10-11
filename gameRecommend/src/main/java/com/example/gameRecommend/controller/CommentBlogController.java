package com.example.gameRecommend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.*;
import com.example.gameRecommend.service.BlogService;
import com.example.gameRecommend.service.CommentBlogService;
import com.example.gameRecommend.service.CommentGameService;
import com.example.gameRecommend.service.GameService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/commentBlog")
public class CommentBlogController {
    @Autowired
    private CommentBlogService commentService;
    @Autowired
    private BlogService tBlogService;

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public Result comments(@PathVariable Long blogId) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.listCommentByBlogId(blogId));
        return result;
    }
    //查询评论列表-分页
    @GetMapping("/blogComments/{blogId}")
    public Result commentsBlogPage(@PathVariable Long blogId,Long current, Long size) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.getBlogCommentByPage(blogId,current, size));
        return result;
    }
    //查询所有评论
    @GetMapping("/getCommentByPage")
    public Result getCommentByPage(Long current, Long size){
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.getCommentByPage(current, size));
        return result;
    }

    //根据评论人姓名查询评论
    @GetMapping("/comments")
    public List<CommentBlog> findCommentByNickname(String nickname){
        return commentService.findCommentByNickname(nickname);
    }


    //新增评论
    @PostMapping("/comments")
    public Result post(@RequestBody CommentBlog tComment, Authentication authentication, HttpServletRequest request) {
        //获取评论人的ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        Long blogId = tComment.getBlogId();
        if (authentication != null){
            User user = (User)authentication.getPrincipal();
        }

        if (tComment.getParentCommentId() != null) {
            tComment.setParentCommentId(tComment.getParentCommentId());
        }
        commentService.saveComment(tComment);
        Blog tBlog = tBlogService.getById(blogId);
        tBlogService.updateByComment(tBlog);
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.getBlogCommentByPage(blogId, 1L, 5L));
        return result;
    }

    //删除评论
    @DeleteMapping("/deleteComment")
    public String delete(Long id){
        commentService.deleteComment(id);

        return "OK";
    }
}

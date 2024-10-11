package com.example.gameRecommend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.CommentGame;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.Comment;
import com.example.gameRecommend.entity.User;
import com.example.gameRecommend.service.GameService;
import com.example.gameRecommend.service.CommentGameService;
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
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/commentGame")
public class CommentGameController {
    @Autowired
    private CommentGameService commentService;
    @Autowired
    private GameService tGameService;

    //查询评论列表
    @GetMapping("/comments/{gameId}")
    public Result comments(@PathVariable Long gameId) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.listCommentByGameId(gameId));
        System.out.println("----------------------------------------------"+result.getData());
        return result;
    }

    //查询评论列表-分页
    @GetMapping("/gameComments/{gameId}")
    public Result commentsGamePage(@PathVariable Long gameId,Long current, Long size) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.getGameCommentByPage(gameId,current, size));
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
    public List<CommentGame> findCommentByNickname(String nickname){
        return commentService.findCommentByNickname(nickname);
    }


    //新增评论
    @PostMapping("/comments")
    public Result post(@RequestBody CommentGame tComment, Authentication authentication, HttpServletRequest request) {
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
        Long gameId = tComment.getGameId();
        if (authentication != null){
            User user = (User)authentication.getPrincipal();
        }

        if (tComment.getparent_comment_id() != null) {
            tComment.setparent_comment_id(tComment.getparent_comment_id());
        }
        //System.out.println("-----------------------"+tComment.toString());
        commentService.saveComment(tComment);
        Game tGame = tGameService.getById(gameId);
        tGameService.updateByComment(tGame);
        Result result = Result.build();
        result.setCode(200);
        result.setData(commentService.getGameCommentByPage(gameId, 1L, 5L));
        return result;
    }

    //删除评论
    @DeleteMapping("/deleteComment")
    public String delete(Long id){
        commentService.deleteComment(id);

        return "OK";
    }

}

package com.example.gameRecommend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Comment;
import com.example.gameRecommend.entity.CommentGame;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-01-27
 */
public interface CommentGameService extends IService<CommentGame> {
    //根据博客id查询评论信息
    List<CommentGame> listCommentByGameId(Long GameId);

    //添加保存评论
    int saveComment(CommentGame comment);

    //删除评论
    void deleteComment(Long id);

    List<CommentGame> findCommentByNickname(String nickname);

    Page<CommentGame> getCommentByPage(Long current, Long size);
    Page<CommentGame> getGameCommentByPage(Long gameId,Long current, Long size);
}

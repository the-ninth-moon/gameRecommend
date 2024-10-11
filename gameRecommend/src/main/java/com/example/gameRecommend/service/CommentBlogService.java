package com.example.gameRecommend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.CommentBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.entity.CommentGame;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-01-27
 */
public interface CommentBlogService extends IService<CommentBlog> {
    List<CommentBlog> listCommentByBlogId(Long BlogId);

    //添加保存评论
    int saveComment(CommentBlog comment);

    //删除评论
    void deleteComment(Long id);

    List<CommentBlog> findCommentByNickname(String nickname);

    Page<CommentBlog> getCommentByPage(Long current, Long size);
    Page<CommentBlog> getBlogCommentByPage(Long blogId,Long current, Long size);
}

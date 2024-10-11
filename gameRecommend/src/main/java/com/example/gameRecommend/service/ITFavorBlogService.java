package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.entity.TFavorBlog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
public interface ITFavorBlogService extends IService<TFavorBlog> {
    public boolean favorBlog(Long userId, Long blogId);

    public int getFavorCountByBlogId(Long blogId);
}

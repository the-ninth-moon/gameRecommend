package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.entity.TLikeBlog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
public interface ITLikeBlogService extends IService<TLikeBlog> {
    public boolean likeBlog(Long userId, Long blogId);

    public int getLikeCountByBlogId(Long blogId);
}

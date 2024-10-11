package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.entity.TLikeBlog;
import com.example.gameRecommend.mapper.TLikeBlogMapper;
import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITLikeBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@Service
public class TLikeBlogServiceImpl extends ServiceImpl<TLikeBlogMapper, TLikeBlog> implements ITLikeBlogService {
    @Autowired
    private TLikeBlogMapper likeBlogMapper;
    public boolean likeBlog(Long userId, Long blogId) {
        // 检查用户是否已点赞
        int likeCount = likeBlogMapper.countLikes(userId, blogId);
        if (likeCount > 0) {
            likeBlogMapper.deleteLike(userId,blogId);//删除点赞
            return false;
        }
        // 添加点赞记录
        likeBlogMapper.insertLike(userId, blogId);
        return true; // 点赞成功
    }

    public int getLikeCountByBlogId(Long blogId) {
        return likeBlogMapper.countLikesByBlogId(blogId);
    }
}

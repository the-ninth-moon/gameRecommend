package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.entity.TFavorBlog;
import com.example.gameRecommend.mapper.TFavorBlogMapper;
import com.example.gameRecommend.mapper.TLikeBlogMapper;
import com.example.gameRecommend.service.ITFavorBlogService;
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
public class TFavorBlogServiceImpl extends ServiceImpl<TFavorBlogMapper, TFavorBlog> implements ITFavorBlogService {
    @Autowired
    private TFavorBlogMapper favorBlogMapper;
    public boolean favorBlog(Long userId, Long blogId) {
        // 检查用户是否已收藏
        int favorCount = favorBlogMapper.countFavors(userId, blogId);
        if (favorCount > 0) {
            favorBlogMapper.deleteFavor(userId,blogId);//删除收藏
            return false;
        }
        // 添加收藏记录
        favorBlogMapper.insertFavor(userId, blogId);
        return true; // 收藏成功
    }

    public int getFavorCountByBlogId(Long blogId) {
        return favorBlogMapper.countFavorsByBlogId(blogId);
    }
}

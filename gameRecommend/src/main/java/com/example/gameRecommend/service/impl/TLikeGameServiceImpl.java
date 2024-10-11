package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.entity.TLikeGame;
import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITLikeGameService;
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
public class TLikeGameServiceImpl extends ServiceImpl<TLikeGameMapper, TLikeGame> implements ITLikeGameService {

    @Autowired
    private TLikeGameMapper likeGameMapper;
    public boolean likeGame(Long userId, Long gameId) {
        // 检查用户是否已点赞
        int likeCount = likeGameMapper.countLikes(userId, gameId);
        if (likeCount > 0) {
            likeGameMapper.deleteLike(userId,gameId);//删除点赞
            return false;
        }
        // 添加点赞记录
        likeGameMapper.insertLike(userId, gameId);
        return true; // 点赞成功
    }

    public int getLikeCountByGameId(Long gameId) {
        return likeGameMapper.countLikesByGameId(gameId);
    }
}

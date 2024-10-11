package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.entity.TFavorGame;
import com.example.gameRecommend.mapper.TFavorGameMapper;
import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITFavorGameService;
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
public class TFavorGameServiceImpl extends ServiceImpl<TFavorGameMapper, TFavorGame> implements ITFavorGameService {

    @Autowired
    private TFavorGameMapper favorGameMapper;
    public boolean favorGame(Long userId, Long gameId) {
        // 检查用户是否已收藏
        int favorCount = favorGameMapper.countFavors(userId, gameId);
        if (favorCount > 0) {
            favorGameMapper.deleteFavor(userId,gameId);//删除点赞
            return false;
        }
        // 添加收藏记录
        favorGameMapper.insertFavor(userId, gameId);
        return true; // 点赞成功
    }

    public int getFavorCountByGameId(Long gameId) {
        return favorGameMapper.countFavorsByGameId(gameId);
    }
}

package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.entity.TFavorGame;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
public interface ITFavorGameService extends IService<TFavorGame> {
    public boolean favorGame(Long userId, Long gameId);

    public int getFavorCountByGameId(Long gameId);
}

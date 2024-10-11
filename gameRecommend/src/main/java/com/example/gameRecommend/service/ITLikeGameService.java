package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.entity.TLikeGame;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
public interface ITLikeGameService extends IService<TLikeGame> {
    public boolean likeGame(Long userId, Long gameId);

    public int getLikeCountByGameId(Long gameId);
}

package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2023-07-28
 */
public interface GameService extends IService<Game> {
    //分页查询
    Result pageGames(Long current, Long limit, Boolean published, String flag,Boolean share_statement,  Boolean is_delete,Long typeId);
    Result getByTitle(String title);

    //添加博客和标签
    Result saveGame(HashMap<String, Object> params);
    //暂时保存博客
    Result temporarySave(HashMap<String, Object> params);
    //更新博客
    Result updateGame(HashMap<String,Object> params);
    Result tempUpdateGame(HashMap<String,Object> params);
    //删除博客（逻辑删除）
    Result logicDeleteGame(String id);
    //删除博客和标签（彻底删除）
    Result deleteBT(String id);
    //还原博客
    Result recoveryGame(String id);
    //根据博客id查询
    Result getByGameId(String id);

    //更新评论总数
    void updateByComment(Game tGame);
    Result getGamesByTypeName(String typeName);
}

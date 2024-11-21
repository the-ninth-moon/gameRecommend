package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.Links;
import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gameRecommend.entity.TFavorGame;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@Mapper
public interface TFavorGameMapper extends BaseMapper<TFavorGame> {
    // 添加收藏记录
    @Insert("INSERT INTO t_favor_game (userId, gameId) VALUES (#{userId}, #{gameId})")
    void insertFavor(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Delete("DELETE FROM t_favor_game WHERE (userId, gameId)=(#{userId}, #{gameId})")
    void deleteFavor(@Param("userId") Long userId, @Param("gameId") Long gameId);

    // 检查用户是否已收藏
    @Select("SELECT COUNT(*) FROM t_favor_game WHERE userId = #{userId} AND gameId = #{gameId}")
    int countFavors(@Param("userId") Long userId, @Param("gameId") Long gameId);

    // 检查用户的收藏
    @Select("SELECT gameId FROM t_favor_game WHERE userId = #{userId}")
    List<Integer> GetFavorsById(@Param("userId") Long userId);

    // 查询游戏的收藏数量
    @Select("SELECT COUNT(*) FROM t_favor_game WHERE gameId = #{gameId}")
    int countFavorsByGameId(@Param("gameId") Long gameId);
}

package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gameRecommend.entity.TLikeGame;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@Mapper
public interface TLikeGameMapper extends BaseMapper<TLikeGame> {
    // 添加点赞记录
    @Insert("INSERT INTO t_like_game (userId, gameId) VALUES (#{userId}, #{gameId})")
    void insertLike(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Delete("DELETE FROM t_like_game WHERE (userId, gameId)=(#{userId}, #{gameId})")
    void deleteLike(@Param("userId") Long userId, @Param("gameId") Long gameId);

    // 检查用户是否已点赞
    @Select("SELECT COUNT(*) FROM t_like_game WHERE userId = #{userId} AND gameId = #{gameId}")
    int countLikes(@Param("userId") Long userId, @Param("gameId") Long gameId);

    // 查询游戏的点赞数量
    @Select("SELECT COUNT(*) FROM t_like_game WHERE gameId = #{gameId}")
    int countLikesByGameId(@Param("gameId") Long gameId);
}

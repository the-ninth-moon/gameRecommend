package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.TGameImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-10-05
 */
@Mapper
public interface TGameImageMapper extends BaseMapper<TGameImage> {


    @Select("SELECT imageUrl FROM t_game_image WHERE gameId = #{gameId}")
    List<String> getGameImage(@Param("gameId") Long gameId);

    @Delete("Delete FROM t_game_image WHERE gameId = #{gameId}")
    void deleteGameImage(@Param("gameId") Long gameId);
}

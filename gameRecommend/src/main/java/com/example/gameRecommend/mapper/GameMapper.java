package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.Blog;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.Game;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-01-24
 */
@Mapper
public interface GameMapper extends BaseMapper<Game> {
    Game getByGameId(@Param("id") String id);

    Game getGameIdByName(@Param("name") String name);
    @Select("SELECT * FROM t_game WHERE typeId = #{typeId}")
    List<Game> getGamesByTypeId(@Param("typeId") Long typeId);
}

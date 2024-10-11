package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gameRecommend.entity.TFavorBlog;
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
public interface TFavorBlogMapper extends BaseMapper<TFavorBlog> {
    // 添加收藏记录
    @Insert("INSERT INTO t_favor_blog (userId, blogId) VALUES (#{userId}, #{blogId})")
    void insertFavor(@Param("userId") Long userId, @Param("blogId") Long blogId);

    @Delete("DELETE FROM t_favor_blog WHERE (userId, blogId)=(#{userId}, #{blogId})")
    void deleteFavor(@Param("userId") Long userId, @Param("blogId") Long blogId);

    // 检查用户是否已收藏
    @Select("SELECT COUNT(*) FROM t_favor_blog WHERE userId = #{userId} AND blogId = #{blogId}")
    int countFavors(@Param("userId") Long userId, @Param("blogId") Long blogId);

    // 查询游戏的收藏数量
    @Select("SELECT COUNT(*) FROM t_favor_blog WHERE blogId = #{blogId}")
    int countFavorsByBlogId(@Param("blogId") Long blogId);
}

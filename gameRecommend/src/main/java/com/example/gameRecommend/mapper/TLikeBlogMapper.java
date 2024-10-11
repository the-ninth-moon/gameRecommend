package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.TCommentBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gameRecommend.entity.TLikeBlog;
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
public interface TLikeBlogMapper extends BaseMapper<TLikeBlog> {
    // 添加点赞记录
    @Insert("INSERT INTO t_like_blog (userId, blogId) VALUES (#{userId}, #{blogId})")
    void insertLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    @Delete("DELETE FROM t_like_blog WHERE (userId, blogId)=(#{userId}, #{blogId})")
    void deleteLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    // 检查用户是否已点赞
    @Select("SELECT COUNT(*) FROM t_like_blog WHERE userId = #{userId} AND blogId = #{blogId}")
    int countLikes(@Param("userId") Long userId, @Param("blogId") Long blogId);

    // 查询游戏的点赞数量
    @Select("SELECT COUNT(*) FROM t_like_blog WHERE blogId = #{blogId}")
    int countLikesByBlogId(@Param("blogId") Long blogId);
}

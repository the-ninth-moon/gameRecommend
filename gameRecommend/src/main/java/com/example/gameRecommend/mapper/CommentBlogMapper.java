package com.example.gameRecommend.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.CommentBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.gameRecommend.entity.CommentGame;
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
 * @since 2024-01-27
 */
@Mapper
public interface CommentBlogMapper extends BaseMapper<CommentBlog> {
    //查询父级评论
    List<CommentBlog> findByBlogIdParentIdNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);

    //查询一级回复
    List<CommentBlog> findByBlogIdParentIdNotNull(@Param("blogId") Long blogId, @Param("id") Long id);

    //查询二级回复
    List<CommentBlog> findByBlogIdAndReplayId(@Param("blogId") Long blogId,@Param("childId") Long childId);

    //添加一个评论
    int saveComment(CommentBlog comment);

    //删除评论
    void deleteComment(Long id);

    @Select("""
        SELECT cg.*, u.userName, u.avatar
        FROM t_comment_blog cg
        LEFT JOIN t_user u ON cg.userId = u.id
        WHERE cg.blogId = #{blogId}
        ORDER BY cg.create_time DESC
    """)
    Page<CommentBlog> selectBlogCommentByPage(Page<CommentBlog> page, @Param("blogId") Long blogId);
}

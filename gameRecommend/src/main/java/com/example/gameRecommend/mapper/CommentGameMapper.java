package com.example.gameRecommend.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Comment;
import com.example.gameRecommend.entity.CommentGame;
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
 * @since 2024-01-27
 */
@Mapper
public interface CommentGameMapper extends BaseMapper<CommentGame> {
    //查询父级评论
    List<CommentGame> findByGameIdParentIdNull(@Param("gameId") Long gameId);

    //查询一级回复
    List<CommentGame> findByGameIdParentIdNotNull(@Param("gameId") Long gameId, @Param("id") Long id);

    //查询二级回复
    List<CommentGame> findByGameIdAndReplayId(@Param("gameId") Long gameId,@Param("childId") Long childId);

    @Select("""
        SELECT cg.*, u.userName, u.avatar
        FROM t_comment_game cg
        LEFT JOIN t_user u ON cg.userId = u.id
        WHERE cg.gameId = #{gameId}
        ORDER BY cg.create_time DESC
    """)
    Page<CommentGame> selectGameCommentByPage(Page<CommentGame> page, @Param("gameId") Long gameId);
    //添加一个评论
    int saveComment(CommentGame comment);

    //删除评论
    void deleteComment(Long id);
}

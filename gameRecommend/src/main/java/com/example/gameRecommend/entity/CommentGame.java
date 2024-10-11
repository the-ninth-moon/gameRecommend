package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2024-01-27
 */
@TableName("t_comment_game")
public class CommentGame implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对象id
     */
    @TableField("gameId")
    private Long gameId;

    /**
     * 评论用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
    /**
     * 头像
     */
    @TableField(exist = false) //查询时过滤非数据库字段
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @TableField(exist = false) //查询时过滤非数据库字段
    private String userName;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 父评论id
     */
    @TableField("parent_comment_id")
    private Long parent_comment_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getcreateTime() {
        return createTime;
    }

    public void setcreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getparent_comment_id() {
        return parent_comment_id;
    }

    public void setparent_comment_id(Long parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    @Override
    public String toString() {
        return "TCommentGame{" +
            "id=" + id +
            ", gameId=" + gameId +
            ", userId=" + userId +
            ", content=" + content +
            ", createTime=" + createTime +
            ", parent_comment_id=" + parent_comment_id +
        "}";
    }

    //回复评论
    @TableField(exist = false) //查询时过滤非数据库字段
    private List<CommentGame> replyComments = new ArrayList<>();
    @TableField(exist = false) //查询时过滤非数据库字段
    private Comment parentComment;
    @TableField(exist = false) //查询时过滤非数据库字段
    private String parentNickname;

    public List<CommentGame> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<CommentGame> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public String getParentNickname() {
        return parentNickname;
    }

    public void setParentNickname(String parentNickname) {
        this.parentNickname = parentNickname;
    }
}

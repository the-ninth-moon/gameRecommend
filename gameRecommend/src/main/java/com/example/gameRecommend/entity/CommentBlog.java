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
@TableName("t_comment_blog")
public class CommentBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * blog评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 博客id
     */
    @TableField("blogId")
    private Long blogId;
    @TableField(exist = false) //查询时过滤非数据库字段
    private String userName;
    @TableField(exist = false) //查询时过滤非数据库字段
    private String avatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 父评论id
     */
    @TableField("parent_comment_id")
    private Long parentCommentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "TCommentBlog{" +
            "id=" + id +
            ", blogId=" + blogId +
            ", userId=" + userId +
            ", content=" + content +
            ", createTime=" + createTime +
            ", parentCommentId=" + parentCommentId +
        "}";
    }
    @TableField(exist = false) //查询时过滤非数据库字段
    private List<CommentBlog> replyComments = new ArrayList<>();
    @TableField(exist = false) //查询时过滤非数据库字段
    private Comment parentComment;

    public List<CommentBlog> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<CommentBlog> replyComments) {
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

    @TableField(exist = false) //查询时过滤非数据库字段
    private String parentNickname;
}

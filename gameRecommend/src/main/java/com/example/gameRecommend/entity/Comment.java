package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ????id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ?Ƿ?Ϊ????Ա???
     */
    @TableField("admin_comment")
    private Boolean adminComment;

    /**
     * ͷ?
     */
    @TableField("avatar")
    private String avatar;

    /**
     * ???????
     */
    @TableField("content")
    private String content;

    /**
     * ????ʱ?
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * ???
     */
    @TableField("email")
    private String email;

    /**
     * ip??ַ
     */
    @TableField("ip")
    private String ip;

    /**
     * ?ǳ
     */
    @TableField("nickname")
    private String nickname;

    /**
     * ????id
     */
    @TableField("blog_id")
    private Long blogId;

    /**
     * ??????id
     */
    @TableField("parent_comment_id")
    private Long parentCommentId;

    //回复评论
    @TableField(exist = false) //查询时过滤非数据库字段
    private List<Comment> replyComments = new ArrayList<>();
    @TableField(exist = false) //查询时过滤非数据库字段
    private Comment parentComment;
    @TableField(exist = false) //查询时过滤非数据库字段
    private String parentNickname;

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Boolean adminComment) {
        this.adminComment = adminComment;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "TComment{" +
            "id=" + id +
            ", adminComment=" + adminComment +
            ", avatar=" + avatar +
            ", content=" + content +
            ", createTime=" + createTime +
            ", email=" + email +
            ", ip=" + ip +
            ", nickname=" + nickname +
            ", blogId=" + blogId +
            ", parentCommentId=" + parentCommentId +
        "}";
    }
}

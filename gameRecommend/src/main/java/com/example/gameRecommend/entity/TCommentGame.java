package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@TableName("t_comment_game")
public class TCommentGame implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * game评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏id
     */
    @TableField("gameId")
    private Long gameId;

    /**
     * 评论用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * ip地址
     */
    @TableField("ip")
    private String ip;

    @TableField(exist = false) //查询时过滤非数据库字段
    private User tUser;

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
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public String toString() {
        return "TCommentGame{" +
            "id=" + id +
            ", gameId=" + gameId +
            ", userId=" + userId +
            ", avatar=" + avatar +
            ", content=" + content +
            ", createTime=" + createTime +
            ", ip=" + ip +
            ", parentCommentId=" + parentCommentId +
        "}";
    }
}

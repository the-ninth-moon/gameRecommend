package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ????id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type=IdType.AUTO,value = "id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "game_id")
    private Long gameId;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "userId")
    private Long userId;
    /**
     * ???
     */
    @TableField("title")
    private String title;

    /**
     * ???
     */
    @TableField("description")
    private String description;

    /**
     * ??ͼ??ַ
     */
    @TableField("first_picture")
    private String firstPicture;

    /**
     * ???????
     */
    @TableField("content")
    private String content;

    /**
     * ԭ??
     */
    @TableField("processed")
    private Boolean processed;

    /**
     * ?Ƿ??
     */
    @TableField("published")
    private Boolean published;

    /**
     * ?Ƿ????????
     */
    @TableField("original")
    private Boolean original;

    /**
     * ?Ƿ????????
     */
    @TableField("share_statement")
    private Boolean shareStatement;

    /**
     * ?Ƿ????Իظ?
     */
    @TableField("recommend")
    private Boolean recommend;

    /**
     * ?Ƿ??ѷ???
     */

    public Blog() {
        this.updateTime = LocalDateTime.now();
    }

    /**
     * ????ʱ?
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * ?߼?ɾ?
     */
    @TableField("isDelete")
    private Boolean isDelete;


    @TableField(exist = false) //查询时过滤非数据库字段
    @ApiModelProperty(value = "用户对象")
    private User tUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public User gettUser() {
        return tUser;
    }

    public void settUser(User tUser) {
        this.tUser = tUser;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", game_id=" + gameId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", content='" + content + '\'' +
                ", processed='" + processed + '\'' +
                ", published=" + published +
                ", original=" + original +
                ", share_statement=" + shareStatement +
                ", recommend=" + recommend +
                ", shareStatement=" + shareStatement +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", tUser=" + tUser +
                '}';
    }
}

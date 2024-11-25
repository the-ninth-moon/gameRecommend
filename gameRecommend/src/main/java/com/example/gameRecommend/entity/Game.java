package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-01-24
 */
@TableName("t_game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type=IdType.AUTO,value = "id")
    private Long id;

    /**
     * 游戏名字
     */
    @TableField("name")
    private String name;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @TableField("views")
    private int views;

    /**
     * 游戏分类id
     */
    @TableField("typeId")
    private Long typeId;

    /**
     * 游戏开发商
     */
    @TableField("developer")
    private String developer;

    /**
     * 发售时间
     */
    @TableField("sell_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime sellTime;

    /**
     * 收录时间
     */
    @TableField("collect_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime collectTime;

    /**
     * 游戏平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 官方网站
     */
    @TableField("official_web")
    private String officialWeb;

    /**
     * 游戏评分
     */
    @TableField("score")
    private BigDecimal score;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 介绍内容
     */
    @TableField("game_content")
    private String gameContent;

    /**
     * 配置信息
     */
    @TableField("config_require")
    private String configRequire;

    /**
     * 首图地址
     */
    @TableField("first_picture")
    private String firstPicture;

    /**
     * 图片列表
     */

    /**
     * 是否公开
     */
    @TableField("published")
    private Boolean published;

    /**
     * 是否已发布
     */
    @TableField("share_statement")
    private Boolean shareStatement;

    /**
     * 逻辑删除
     */
    @TableField("is_delete")
    private Boolean isDelete;

    /**
     * 是否可以回复
     */
    @TableField("recommend")
    private Boolean recommend;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
    public LocalDateTime getSellTime() {
        return sellTime;
    }

    public void setSellTime(LocalDateTime sellTime) {
        this.sellTime = sellTime;
    }
    public LocalDateTime getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(LocalDateTime collectTime) {
        this.collectTime = collectTime;
    }
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getOfficialWeb() {
        return officialWeb;
    }

    public void setOfficialWeb(String officialWeb) {
        this.officialWeb = officialWeb;
    }
    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getGameContent() {
        return gameContent;
    }

    public void setGameContent(String gameContent) {
        this.gameContent = gameContent;
    }
    public String getConfigRequire() {
        return configRequire;
    }

    public void setConfigRequire(String configRequire) {
        this.configRequire = configRequire;
    }
    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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


    @Override
    public String toString() {
        return "TGame{" +
            "id=" + id +
            ", name=" + name +
            ", typeId=" + typeId +
            ", developer=" + developer +
            ", sellTime=" + sellTime +
            ", collectTime=" + collectTime +
            ", platform=" + platform +
            ", officialWeb=" + officialWeb +
            ", score=" + score +
            ", description=" + description +
            ", gameContent=" + gameContent +
            ", configRequire=" + configRequire +
            ", firstPicture=" + firstPicture +
            ", published=" + published +
            ", shareStatement=" + shareStatement +
            ", isDelete=" + isDelete +
            ", recommend=" + recommend +
            ", updateTime=" + updateTime +
        "}";
    }
}

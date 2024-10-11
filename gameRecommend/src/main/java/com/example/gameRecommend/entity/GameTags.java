package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_game_tags")
public class GameTags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ????id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("gamesId")
    private Long GamesId;

    /**
     * ??ǩid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("tagsId")
    private Long tagsId;
    @TableField(exist = false)
    @ApiModelProperty(value = "游戏对象")
    private Game tGame;

    @TableField(exist = false)
    @ApiModelProperty(value = "标签对象")
    private Tag tTag;

    public Game gettGame() {
        return tGame;
    }

    public void settGame(Game tGame) {
        this.tGame = tGame;
    }

    public Tag gettTag() {
        return tTag;
    }

    public void settTag(Tag tTag) {
        this.tTag = tTag;
    }


    public Long getGamesId() {
        return GamesId;
    }

    public void setGamesId(Long GamesId) {
        this.GamesId = GamesId;
    }
    public Long getTagsId() {
        return tagsId;
    }

    public void setTagsId(Long tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "TGameTags{" +
            "GamesId=" + GamesId +
            ", tagsId=" + tagsId +
        "}";
    }
}

package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-10-05
 */
@TableName("t_game_image")
public class TGameImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("gameId")
    private Long gameId;

    @TableField("imageUrl")
    private String imageUrl;

    public TGameImage(Long gameId, String s) {
        this.gameId=gameId;
        this.imageUrl = s;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "TGameImage{" +
            "gameId=" + gameId +
            ", imageUrl=" + imageUrl +
        "}";
    }
}

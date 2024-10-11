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
 * @since 2024-09-30
 */
@TableName("t_favor_game")
public class TFavorGame implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 游戏id
     */
    @TableField("gameId")
    private Long gameId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "TFavorGame{" +
            "userId=" + userId +
            ", gameId=" + gameId +
        "}";
    }
}

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
@TableName("t_like_blog")
public class TLikeBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 博客id
     */
    @TableField("blogId")
    private Long blogId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "TLikeBlog{" +
            "userId=" + userId +
            ", blogId=" + blogId +
        "}";
    }
}

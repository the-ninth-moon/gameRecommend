package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("t_links")
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ???
     */
    @TableId(type= IdType.AUTO,value = "id")
    private Long id;

    /**
     * ???͵?ַ
     */
    @TableField("blog_address")
    private String blogAddress;

    /**
     * ?????
     */
    @TableField("blog_name")
    private String blogName;

    /**
     * ??ͼ
     */
    @TableField("picture_address")
    private String pictureAddress;

    /**
     * ????ʱ?
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getBlogAddress() {
        return blogAddress;
    }

    public void setBlogAddress(String blogAddress) {
        this.blogAddress = blogAddress;
    }
    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        this.pictureAddress = pictureAddress;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TLinks{" +
            "id=" + id +
            ", blogAddress=" + blogAddress +
            ", blogName=" + blogName +
            ", pictureAddress=" + pictureAddress +
            ", createTime=" + createTime +
        "}";
    }
}

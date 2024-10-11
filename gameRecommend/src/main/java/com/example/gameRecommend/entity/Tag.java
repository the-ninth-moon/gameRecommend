package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ???
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type= IdType.AUTO,value = "id")
    private Long id;

    /**
     * ??ɫ
     */
    @TableField("color")
    private String color;

    /**
     * ?
     */
    @TableField("name")
    private String name;

    @TableField(exist = false)  //查询时过滤非数据库字段
    @ApiModelProperty(value = "博客标签集合")
    private List<GameTags> tGameTags;

    @TableField(exist = false)  //查询时过滤非数据库字段
    private Integer countTag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TTag{" +
            "id=" + id +
            ", color=" + color +
            ", name=" + name +
        "}";
    }
}

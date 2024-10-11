package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ????id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type= IdType.AUTO,value = "id")
    private Long id;

    /**
     * ?????
     */
    @TableField("name")
    private String name;

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

    @Override
    public String toString() {
        return "TType{" +
            "id=" + id +
            ", name=" + name +
        "}";
    }
}

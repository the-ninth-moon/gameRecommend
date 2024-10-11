package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ??ɫid
     */
    @TableId("id")
    private Long id;

    /**
     * ??ɫ?
     */
    @TableField("name")
    private String name;

    /**
     * ??ɫ?????
     */
    @TableField("nameZh")
    private String nameZh;

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
    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    @Override
    public String toString() {
        return "TRole{" +
            "id=" + id +
            ", name=" + name +
            ", nameZh=" + nameZh +
        "}";
    }
}

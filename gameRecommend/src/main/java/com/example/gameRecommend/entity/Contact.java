package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ???
     */
    @TableId(type= IdType.AUTO,value = "id")
    private Long id;

    /**
     * ???
     */
    @TableField("email")
    private String email;

    /**
     * ΢??֧????ά?
     */
    @TableField("pay_by_wechat")
    private String payByWechat;

    /**
     * ֧??????ά?
     */
    @TableField("pay_by_zhi")
    private String payByZhi;

    /**
     * qq?˺
     */
    @TableField("qq")
    private String qq;

    /**
     * ΢?ź
     */
    @TableField("wechat")
    private String wechat;

    /**
     * ΢?Ŷ?ά?
     */
    @TableField("wechat_code")
    private String wechatCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPayByWechat() {
        return payByWechat;
    }

    public void setPayByWechat(String payByWechat) {
        this.payByWechat = payByWechat;
    }
    public String getPayByZhi() {
        return payByZhi;
    }

    public void setPayByZhi(String payByZhi) {
        this.payByZhi = payByZhi;
    }
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    @Override
    public String toString() {
        return "TContact{" +
            "id=" + id +
            ", email=" + email +
            ", payByWechat=" + payByWechat +
            ", payByZhi=" + payByZhi +
            ", qq=" + qq +
            ", wechat=" + wechat +
            ", wechatCode=" + wechatCode +
        "}";
    }
}

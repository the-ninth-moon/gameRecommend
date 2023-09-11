package com.example.gameRecommend.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@TableName("t_user")
public class User implements Serializable , UserDetails{

    private static final long serialVersionUID = 1L;

    /**
     * ?û?id
     */
    @TableId(type= IdType.AUTO,value = "id")
    private Long id;

    /**
     * ͷ?
     */
    @TableField("avatar")
    private String avatar;

    /**
     * ????ʱ?
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * ???
     */
    @TableField("email")
    private String email;

    /**
     * ?ǳ
     */
    @TableField("nickname")
    private String nickname;

    @TableField("phone")
    private String phone;

    /**
     * ???
     */
    @TableField("password")
    private String password;

    /**
     * ??ɫ
     */
    @TableField("type")
    private Integer type;

    /**
     * ????ʱ?
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * ?û??
     */
    @TableField("username")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", updateTime=" + updateTime +
                ", username='" + username + '\'' +
                '}';
    }
}

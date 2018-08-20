package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User  implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true)
    private String sex;

    @Column(nullable = true)
    private String highScore = "0";

    public User(String nickName, String password){
        this.nickname = nickName;
        this.password = password;
    }

    public User(){}

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

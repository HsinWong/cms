package com.hsinwong.cms.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "sys_user")
public class User extends Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String nickname;

    private String email;

    private String mobile;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum Sex {
        MALE("男"), FEMALE("女");

        @JsonValue
        private String value;

        Sex(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum State {
        NORMAL("正常"), DELETED("已删除"), DISABLED("禁用"), FROZEN("冻结");

        @JsonValue
        private String value;

        State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

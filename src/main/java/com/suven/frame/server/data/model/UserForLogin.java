package com.suven.frame.server.data.model;


import com.suven.frame.server.JsonParse;

/**
 * Created by lipingfa on 2017/6/16.
 */
public class UserForLogin extends JsonParse {
    private String name;
    private String password;

    public UserForLogin() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password=" + password +
                '}';
    }
}

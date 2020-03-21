package com.hsun.myapplication.model;

import java.io.Serializable;

public class User implements Serializable {

    private String name,
            url = "https://stickershop.line-scdn.net/stickershop/v1/product/718/LINEStorePC/main.png;compress=true";
    private int totalUser;

    public User(String name) {
        this.name = name;
    }

    public User(String name, int totalUser) {
        this.name = name;
        this.totalUser = totalUser;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }
}

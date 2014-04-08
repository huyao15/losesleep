package com.xianglanqi.losesleep.model;

import org.json.JSONObject;

public class User {

    private long id;
    private String name;
    private Boolean gender;
    private String avatar;
    private String deviceId;

    public static User parse(JSONObject jo) {
        User user = new User();
        user.setId(jo.optLong("id"));
        user.setName(jo.optString("name"));
        user.setAvatar(jo.optString("avatar"));
        user.setGender(jo.optBoolean("gender"));
        user.setDeviceId(jo.optString("deviceId"));
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", avatar=" + avatar + ", gender=" + gender + ", deviceId=" + deviceId;
    }
}

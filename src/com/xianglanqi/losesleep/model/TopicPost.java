package com.xianglanqi.losesleep.model;

import java.util.Date;

import org.json.JSONObject;

public class TopicPost {
    private long id;
    private long userId;
    private String userName;
    private String userAvatar;
    private long topicId;
    private String post;
    private Date addTime;

    public static TopicPost parse(JSONObject jo) {
        TopicPost tp = new TopicPost();
        tp.setId(jo.optLong("id"));
        tp.setUserId(jo.optLong("userId"));
        tp.setUserName(jo.optString("userName"));
        tp.setUserAvatar(jo.optString("userAvatar"));
        if ("".endsWith(tp.getUserAvatar())) {
            tp.setUserAvatar("http://head.xiaonei.com/photos/0/0/men_head.gif");
        }
        tp.setTopicId(jo.optLong("topicId"));
        tp.setPost(jo.optString("post"));
        tp.setAddTime(new Date(jo.optLong("addTime")));
        return tp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

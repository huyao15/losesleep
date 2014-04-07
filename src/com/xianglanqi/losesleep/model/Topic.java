package com.xianglanqi.losesleep.model;

import java.util.Date;

import org.json.JSONObject;

public class Topic {

    private long id;
    private String subject;
    private Date addTime;
    private Date openDate;
    private int status;
    private int viewCount;
    private int commentCount;

    public static Topic parse(JSONObject jo) {
        Topic topic = new Topic();
        topic.setId(jo.optLong("id"));
        topic.setSubject(jo.optString("subject"));
        topic.setAddTime(new Date(jo.optLong("addTime")));
        topic.setOpenDate(new Date(jo.optLong("openDate")));
        topic.setStatus(jo.optInt("status"));
        topic.setViewCount(jo.optInt("viewCount"));
        topic.setCommentCount(jo.optInt("commentCount"));
        return topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}

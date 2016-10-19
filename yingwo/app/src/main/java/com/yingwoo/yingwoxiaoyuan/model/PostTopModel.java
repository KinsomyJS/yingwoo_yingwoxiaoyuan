package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 9/1/16.
 */

public class PostTopModel {
    private String topic;
    private String userHead;
    private String userId;
    private String realseTime;
    private String content;
    private int likeNum;
    private int commentNum;

    public PostTopModel(){

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealseTime() {
        return realseTime;
    }

    public void setRealseTime(String realseTime) {
        this.realseTime = realseTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}

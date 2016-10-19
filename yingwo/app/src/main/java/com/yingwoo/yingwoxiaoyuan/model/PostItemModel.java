package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 9/1/16.
 */

public class PostItemModel {
    private String userHead;
    private String userId;
    private String realseTime;
    private String content;
    private int likeNum;
    private int commentNum;
    private Boolean commandFlag;

    public PostItemModel() {

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

    public Boolean getCommandFlag() {
        return commandFlag;
    }

    public void setCommandFlag(Boolean commandFlag) {
        this.commandFlag = commandFlag;
    }
}

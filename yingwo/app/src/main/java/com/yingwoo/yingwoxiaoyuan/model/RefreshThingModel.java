package com.yingwoo.yingwoxiaoyuan.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class RefreshThingModel {
    private Bitmap userhead;
    private String userId;
    private Date releaseTime;
    private String content;
    private List<Integer> photos;
    private int likeNum;
    private int commentNum;

    public RefreshThingModel() {
    }

    public RefreshThingModel(Date releaseTime, String content, int likeNum, int commentNum) {
        this.releaseTime = releaseTime;
        this.content = content;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public Bitmap getUserhead() {
        return userhead;
    }

    public void setUserhead(Bitmap userhead) {
        this.userhead = userhead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Integer> photos) {
        this.photos = photos;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}

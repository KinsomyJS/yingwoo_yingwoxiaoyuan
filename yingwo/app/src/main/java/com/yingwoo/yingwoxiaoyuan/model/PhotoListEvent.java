package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by FJS0420 on 2016/8/13.
 */

public class PhotoListEvent {

    private List<PhotoInfo> mPhotoList;

    public PhotoListEvent(List<PhotoInfo> mPhotoList){
        this.mPhotoList = mPhotoList;
    }

    public List<PhotoInfo> getPhotos(){
        return mPhotoList;
    }
}

package com.yingwoo.yingwoxiaoyuan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FJS0420 on 2016/9/15.
 */

public class FieldModel {


    /**
     * info : [{"id":"1","title":"校园生活","description":null,"status":"1","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","top":"0"},{"id":"2","title":"兴趣爱好","description":null,"status":"1","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","top":"0"},{"id":"3","title":"知识技能","description":null,"status":"1","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","top":"0"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 1
     * title : 校园生活
     * description : null
     * status : 1
     * img : http://obabu2buy.bkt.clouddn.com/icon/icon.png
     * top : 0
     */

    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable {
        private String id;
        private String title;
        private Object description;
        private String status;
        private String img;
        private String top;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }
    }
}

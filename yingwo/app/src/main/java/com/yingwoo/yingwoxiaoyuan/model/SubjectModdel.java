package com.yingwoo.yingwoxiaoyuan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FJS0420 on 2016/9/15.
 */

public class SubjectModdel {

    /**
     * info : [{"id":"1","field_id":"1","title":"情感","description":null,"status":"1","top":"0"},{"id":"2","field_id":"1","title":"学习","description":null,"status":"1","top":"0"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 1
     * field_id : 1
     * title : 情感
     * description : null
     * status : 1
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
        private String field_id;
        private String title;
        private Object description;
        private String status;
        private String top;
        private String img;
        private String del;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getField_id() {
            return field_id;
        }

        public void setField_id(String field_id) {
            this.field_id = field_id;
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

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }
    }
}

package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 2016/9/22.
 */

public class TopicBaseInfo {

    /**
     * id : 1
     * subject_id : 1
     * school_id : 1
     * title : 大学排名 南工有多强
     * img : http://obabu2buy.bkt.clouddn.com/icon/icon.png
     * description : null
     * post_cnt : 0
     * like_cnt : 2
     * status : 1
     * top : 1
     * hot : 0
     * del : 0
     * like : 0
     */

    private InfoBean info;
    /**
     * info : {"id":"1","subject_id":"1","school_id":"1","title":"大学排名 南工有多强","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","description":null,"post_cnt":"0","like_cnt":"2","status":"1","top":"1","hot":"0","del":"0","like":0}
     * status : 1
     * url :
     */

    private int status;
    private String url;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

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

    public static class InfoBean {
        private String id;
        private String subject_id;
        private String school_id;
        private String title;
        private String img;
        private Object description;
        private String post_cnt;
        private String like_cnt;
        private String status;
        private String top;
        private String hot;
        private String del;
        private int like;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getPost_cnt() {
            return post_cnt;
        }

        public void setPost_cnt(String post_cnt) {
            this.post_cnt = post_cnt;
        }

        public String getLike_cnt() {
            return like_cnt;
        }

        public void setLike_cnt(String like_cnt) {
            this.like_cnt = like_cnt;
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

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }
    }
}

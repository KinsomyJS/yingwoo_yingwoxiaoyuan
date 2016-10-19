package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by FJS0420 on 2016/10/9.
 */

public class HotlistModel {

    /**
     * info : [{"id":"2","subject_id":"1","school_id":"1","title":"百团大战","img":"http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png","description":"","post_cnt":"0","like_cnt":"3","status":"1","top":"0","hot":"1","del":"0","sort":"8","big_img":"http://obabu2buy.bkt.clouddn.com/Topic_big_img/2_1476000060265","create_time":"0"},{"id":"5","subject_id":"2","school_id":"1","title":"学长们撩你的套路","img":"http://image.zhibaizhi.com/top/png/xuezhang.png","description":"","post_cnt":"1","like_cnt":"10","status":"1","top":"0","hot":"1","del":"0","sort":"7","big_img":"http://obabu2buy.bkt.clouddn.com/Topic_big_img/5_1475998812076","create_time":"0"},{"id":"6","subject_id":"2","school_id":"1","title":"我的直男癌男友","img":"http://obabu2buy.bkt.clouddn.com/topic/img2.png","description":"","post_cnt":"0","like_cnt":"6","status":"1","top":"0","hot":"1","del":"0","sort":"2","big_img":"http://obabu2buy.bkt.clouddn.com/Topic_big_img/6_1475999228715","create_time":"0"},{"id":"46","subject_id":"6","school_id":"1","title":"迎新晚会一起嗨","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1475938106904","description":"热点3的描述","post_cnt":"0","like_cnt":"0","status":"1","top":"0","hot":"1","del":"0","sort":"46","big_img":"http://obabu2buy.bkt.clouddn.com/Topic_big_img/46_1475998998429","create_time":"1475938156"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 2
     * subject_id : 1
     * school_id : 1
     * title : 百团大战
     * img : http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png
     * description :
     * post_cnt : 0
     * like_cnt : 3
     * status : 1
     * top : 0
     * hot : 1
     * del : 0
     * sort : 8
     * big_img : http://obabu2buy.bkt.clouddn.com/Topic_big_img/2_1476000060265
     * create_time : 0
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

    public static class InfoBean {
        private String id;
        private String subject_id;
        private String school_id;
        private String title;
        private String img;
        private String description;
        private String post_cnt;
        private String like_cnt;
        private String status;
        private String top;
        private String hot;
        private String del;
        private String sort;
        private String big_img;
        private String create_time;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getBig_img() {
            return big_img;
        }

        public void setBig_img(String big_img) {
            this.big_img = big_img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}

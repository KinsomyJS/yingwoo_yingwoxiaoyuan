package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by FJS0420 on 2016/9/15.
 */

public class TopicListModel {


    /**
     * info : [{"id":"16","subject_id":"1","school_id":"1","title":"应我等你来","img":"http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png","description":null,"post_cnt":"0","like_cnt":"0","status":"1","top":"1","hot":"1","del":"0","sort":"16","big_img":"http://imgmini.eastday.com/pushimg/20161006/1475710048841549.jpg","create_time":"0","user_topic_like":"0"},{"id":"12","subject_id":"1","school_id":"1","title":"一句话证明你是南工人","img":"http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png","description":null,"post_cnt":"0","like_cnt":"0","status":"1","top":"1","hot":"0","del":"0","sort":"12","big_img":"","create_time":"0","user_topic_like":"0"},{"id":"3","subject_id":"1","school_id":"1","title":"开学典礼大家秀","img":"http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png","description":null,"post_cnt":"0","like_cnt":"0","status":"1","top":"1","hot":"0","del":"0","sort":"3","big_img":"","create_time":"0","user_topic_like":"0"},{"id":"2","subject_id":"1","school_id":"1","title":"百团大战","img":"http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png","description":null,"post_cnt":"0","like_cnt":"3","status":"1","top":"1","hot":"1","del":"0","sort":"2","big_img":"http://obabu2buy.bkt.clouddn.com/topic/big_img/hot_topic.png","create_time":"0","user_topic_like":"0"},{"id":"1","subject_id":"1","school_id":"1","title":"南工3050","img":"http://image.zhibaizhi.com/top/png/nangong.png","description":null,"post_cnt":"0","like_cnt":"4","status":"1","top":"1","hot":"0","del":"0","sort":"1","big_img":"","create_time":"0","user_topic_like":"0"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 16
     * subject_id : 1
     * school_id : 1
     * title : 应我等你来
     * img : http://obabu2buy.bkt.clouddn.com/topic/img/LOGO.png
     * description : null
     * post_cnt : 0
     * like_cnt : 0
     * status : 1
     * top : 1
     * hot : 1
     * del : 0
     * sort : 16
     * big_img : http://imgmini.eastday.com/pushimg/20161006/1475710048841549.jpg
     * create_time : 0
     * user_topic_like : 0
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
        private Object description;
        private String post_cnt;
        private String like_cnt;
        private String status;
        private String top;
        private String hot;
        private String del;
        private String sort;
        private String big_img;
        private String create_time;
        private String user_topic_like;

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

        public String getUser_topic_like() {
            return user_topic_like;
        }

        public void setUser_topic_like(String user_topic_like) {
            this.user_topic_like = user_topic_like;
        }
    }
}

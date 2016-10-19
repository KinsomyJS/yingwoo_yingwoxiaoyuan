package com.yingwoo.yingwoxiaoyuan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FJS0420 on 2016/9/2.
 */

public class PostModel {

    /**
     * info : [{"id":"503","user_id":"114","topic_id":"0","school_id":"3","content":"存储过程","img":"","create_time":"1476002938","like_cnt":"1","reply_cnt":"0","del":"0","top":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","topic_title":null,"user_post_like":"1"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 503
     * user_id : 114
     * topic_id : 0
     * school_id : 3
     * content : 存储过程
     * img :
     * create_time : 1476002938
     * like_cnt : 1
     * reply_cnt : 0
     * del : 0
     * top : 0
     * user_name : 人不如故
     * user_face_img : http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR
     * topic_title : null
     * user_post_like : 1
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
        private String user_id;
        private String topic_id;
        private String school_id;
        private String content;
        private String img;
        private String create_time;
        private String like_cnt;
        private String reply_cnt;
        private String del;
        private String top;
        private String user_name;
        private String user_face_img;
        private String topic_title;
        private String user_post_like;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLike_cnt() {
            return like_cnt;
        }

        public void setLike_cnt(String like_cnt) {
            this.like_cnt = like_cnt;
        }

        public String getReply_cnt() {
            return reply_cnt;
        }

        public void setReply_cnt(String reply_cnt) {
            this.reply_cnt = reply_cnt;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_face_img() {
            return user_face_img;
        }

        public void setUser_face_img(String user_face_img) {
            this.user_face_img = user_face_img;
        }

        public String getTopic_title() {
            return topic_title;
        }

        public void setTopic_title(String topic_title) {
            this.topic_title = topic_title;
        }

        public String getUser_post_like() {
            return user_post_like;
        }

        public void setUser_post_like(String user_post_like) {
            this.user_post_like = user_post_like;
        }
    }
}

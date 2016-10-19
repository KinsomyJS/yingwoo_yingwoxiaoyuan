package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by wangyu on 9/3/16.
 */

public class PostListEntity {

    /**
     * info : [{"id":"322","user_id":"114","post_id":"164","create_time":"1473505707","content":"哈哈女","img":"","like_cnt":"0","comment_cnt":"13","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR"},{"id":"323","user_id":"114","post_id":"164","create_time":"1473505749","content":"敏敏","img":"","like_cnt":"0","comment_cnt":"7","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR"},{"id":"324","user_id":"114","post_id":"164","create_time":"1473506478","content":"明年","img":"","like_cnt":"0","comment_cnt":"0","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 322
     * user_id : 114
     * post_id : 164
     * create_time : 1473505707
     * content : 哈哈女
     * img :
     * like_cnt : 0
     * comment_cnt : 13
     * del : 0
     * user_name : 人不如故
     * user_face_img : http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR
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
        private String user_id;
        private String post_id;
        private String create_time;
        private String content;
        private String img;
        private String like_cnt;
        private String comment_cnt;
        private String del;
        private String user_name;
        private String user_face_img;

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

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getLike_cnt() {
            return like_cnt;
        }

        public void setLike_cnt(String like_cnt) {
            this.like_cnt = like_cnt;
        }

        public String getComment_cnt() {
            return comment_cnt;
        }

        public void setComment_cnt(String comment_cnt) {
            this.comment_cnt = comment_cnt;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
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
    }
}

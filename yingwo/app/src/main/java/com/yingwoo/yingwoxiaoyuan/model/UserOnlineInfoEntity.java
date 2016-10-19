package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 12/10/2016.
 */

public class UserOnlineInfoEntity {
    /**
     * id : 114
     * name : 人不如故777
     * sex : 1
     * mobile : 15850755223
     * signature : 2017
     * face_img : http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR
     * school_id : 1
     * academy_id : 23
     * register_status : 1
     * grade : 2017
     * create_time : 1472977714
     * like_cnt : 0
     * liked_cnt : 0
     * school_name : 南京工业大学
     * academy_name : 计算机科学与技术学院
     */

    private InfoBean info;
    /**
     * info : {"id":"114","name":"人不如故777","sex":"1","mobile":"15850755223","signature":"2017","face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","school_id":"1","academy_id":"23","register_status":"1","grade":"2017","create_time":"1472977714","like_cnt":"0","liked_cnt":"0","school_name":"南京工业大学","academy_name":"计算机科学与技术学院"}
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
        private String name;
        private String sex;
        private String mobile;
        private String signature;
        private String face_img;
        private String school_id;
        private String academy_id;
        private String register_status;
        private String grade;
        private String create_time;
        private String like_cnt;
        private String liked_cnt;
        private String school_name;
        private String academy_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getFace_img() {
            return face_img;
        }

        public void setFace_img(String face_img) {
            this.face_img = face_img;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getAcademy_id() {
            return academy_id;
        }

        public void setAcademy_id(String academy_id) {
            this.academy_id = academy_id;
        }

        public String getRegister_status() {
            return register_status;
        }

        public void setRegister_status(String register_status) {
            this.register_status = register_status;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
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

        public String getLiked_cnt() {
            return liked_cnt;
        }

        public void setLiked_cnt(String liked_cnt) {
            this.liked_cnt = liked_cnt;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getAcademy_name() {
            return academy_name;
        }

        public void setAcademy_name(String academy_name) {
            this.academy_name = academy_name;
        }
    }
}

package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 9/3/16.
 */

public class LoginEntity {
    /**
     * id : 26
     * name : null
     * sex : 0
     * mobile : 15850755223
     * signature : null
     * face_img : null
     * school_id : null
     * academy_id : null
     * register_status : 0
     * grade : null
     * create_time : 1472786340
     */

    private InfoBean info;
    /**
     * info : {"id":"26","name":null,"sex":"0","mobile":"15850755223","signature":null,"face_img":null,"school_id":null,"academy_id":null,"register_status":"0","grade":null,"create_time":"1472786340"}
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
        private Object name;
        private String sex;
        private String mobile;
        private Object signature;
        private Object face_img;
        private Object school_id;
        private Object academy_id;
        private String register_status;
        private Object grade;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
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

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public Object getFace_img() {
            return face_img;
        }

        public void setFace_img(Object face_img) {
            this.face_img = face_img;
        }

        public Object getSchool_id() {
            return school_id;
        }

        public void setSchool_id(Object school_id) {
            this.school_id = school_id;
        }

        public Object getAcademy_id() {
            return academy_id;
        }

        public void setAcademy_id(Object academy_id) {
            this.academy_id = academy_id;
        }

        public String getRegister_status() {
            return register_status;
        }

        public void setRegister_status(String register_status) {
            this.register_status = register_status;
        }

        public Object getGrade() {
            return grade;
        }

        public void setGrade(Object grade) {
            this.grade = grade;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

}

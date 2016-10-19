package com.yingwoo.yingwoxiaoyuan.model;

/**
 * Created by wangyu on 26/09/2016.
 */

public class UserInfoBean {
    private int id;
    private String name;
    private int sex;
    private String mobile;
    private String signature;
    private String face_img;
    private String school_id;
    private String academy_id;
    private String grade;
    private String school_name;
    private String academy_name;

    public String getAcademy_name() {
        return academy_name;
    }

    public void setAcademy_name(String academy_name) {
        this.academy_name = academy_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public UserInfoBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public UserInfoBean(LoginEntity.InfoBean infoBean) {
        id = Integer.parseInt(infoBean.getId());
        name = (String) infoBean.getName();
        sex = Integer.parseInt(infoBean.getSex());
        mobile = infoBean.getMobile();
        signature = (String) infoBean.getSignature();
        face_img = (String) infoBean.getFace_img();
        school_id = (String) infoBean.getSchool_id();
        academy_id = (String) infoBean.getAcademy_id();
        grade = (String) infoBean.getGrade();
    }
}

package com.yingwoo.yingwoxiaoyuan.model;

import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.MyApplication;

/**
 * Created by wangyu on 9/2/16.
 */

public class UserInfoEntity {
    private String name = "";
    private String password = "";
    private String face_img = "";
    private String grade = "";
    private String signature = "";
    private String school_id = "";
    private String academy_id = "";
    private int sex = 0;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public UserInfoEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFace_img() {
        return face_img;
    }

    public void setFace_img(String face_img) {
        this.face_img = face_img;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public boolean rightFlag() {

        if (name.equals("") || sex==0 ||school_id.equals("")) {
            if (name.equals(""))
                Toast.makeText(MyApplication.getGlobalContext(), "请填写昵称", Toast.LENGTH_SHORT).show();
//            else if (grade.equals(""))
//                Toast.makeText(MyApplication.getGlobalContext(), "请填写所在年级", Toast.LENGTH_SHORT).show();
//            else if (signature.equals(""))
//                Toast.makeText(MyApplication.getGlobalContext(), "请填写个性签名", Toast.LENGTH_SHORT).show();
            else if (sex==0)
                Toast.makeText(MyApplication.getGlobalContext(), "请填写性别", Toast.LENGTH_SHORT).show();
            else if (school_id.equals(""))
                Toast.makeText(MyApplication.getGlobalContext(), "请填写所在学校", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        String content = "用户名:" + name + " 年级:" + grade + " 签名:" + signature + " 性别:" + sex + " 头像:" + face_img + " 学校id:" + school_id + " 专业id:" + academy_id;
        return content;
    }

    public static boolean isUpDateInfo(LoginEntity.InfoBean infoBean){
        if (infoBean.getName()==null||infoBean.getSex().equals("0")||infoBean.getSchool_id()==null){
            return false;
        }else {
            return true;
        }
    }
}

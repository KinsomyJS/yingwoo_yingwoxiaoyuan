package com.yingwoo.yingwoxiaoyuan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.model.LoginEntity;
import com.yingwoo.yingwoxiaoyuan.model.UserInfoBean;

/**
 * Created by wangyu on 26/09/2016.
 */

public class UserInsertHelper {

    public static final String ID = "id";
    public static final String NAME = "user_name";
    public static final String SEX = "sex";
    public static final String MOBILE = "mobile";
    public static final String SIGNATURE = "signature";
    public static final String FACE_IMG = "face_img";
    public static final String SCHOOL_ID = "school_id";
    public static final String ACADEMY_ID = "academy_id";
    public static final String GRADE = "grade";
    public static final String SCHOOL_NAME = "school_name";
    public static final String ACADEMY_NAME = "academy_name";


    public static void insertUser(Context context, LoginEntity.InfoBean infoBean) {
        UserInfoBean userInfoBean = new UserInfoBean(infoBean);
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putInt(ID, userInfoBean.getId());
        editor.putString(NAME, userInfoBean.getName());
        editor.putInt(SEX, userInfoBean.getSex());
        editor.putString(MOBILE, userInfoBean.getMobile());
        if (userInfoBean.getSignature() != null && !userInfoBean.getSignature().equals(""))
            editor.putString(SIGNATURE, userInfoBean.getSignature());
        if (userInfoBean.getFace_img() != null && !userInfoBean.getFace_img().equals(""))
            editor.putString(FACE_IMG, userInfoBean.getFace_img());
        editor.putInt(SCHOOL_ID, Integer.parseInt(userInfoBean.getSchool_id()));
        if (userInfoBean.getAcademy_id() != null && !userInfoBean.getAcademy_id().equals(""))
            editor.putInt(ACADEMY_ID, Integer.parseInt(userInfoBean.getAcademy_id()));
        if (userInfoBean.getGrade() != null && !userInfoBean.getGrade().equals(""))
            editor.putInt(GRADE, Integer.parseInt(userInfoBean.getGrade()));
        editor.commit();
    }

    public static void insertSchoolName(Context context, String School_name) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putString(SCHOOL_NAME, School_name);
        editor.commit();
    }

    public static void insertAcademyName(Context context, String Academy_name) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putString(ACADEMY_NAME, Academy_name);
        editor.commit();
    }

    public static void updateUser(Context context, UserInfoBean userInfoBean) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putInt(ID, userInfoBean.getId());
        editor.putString(NAME, userInfoBean.getName());
        editor.putInt(SEX, userInfoBean.getSex());
        editor.putString(MOBILE, userInfoBean.getMobile());
        if (userInfoBean.getSignature() != null && !userInfoBean.getSignature().equals(""))
            editor.putString(SIGNATURE, userInfoBean.getSignature());
        if (userInfoBean.getFace_img() != null && !userInfoBean.getFace_img().equals(""))
            editor.putString(FACE_IMG, userInfoBean.getFace_img());
        editor.putInt(SCHOOL_ID, Integer.parseInt(userInfoBean.getSchool_id()));
        if (userInfoBean.getAcademy_id() != null && !userInfoBean.getAcademy_id().equals(""))
            editor.putInt(ACADEMY_ID, Integer.parseInt(userInfoBean.getAcademy_id()));
        if (userInfoBean.getGrade() != null && !userInfoBean.getGrade().equals(""))
            editor.putInt(GRADE, Integer.parseInt(userInfoBean.getGrade()));
        editor.commit();
    }

    public static UserInfoBean getUserInfo(Context context) {
        SharedPreferences sharedPreferences = MyApplication.getSharedPreferences(context);
        if (sharedPreferences.getInt(ID, -1) != -1) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setId(sharedPreferences.getInt(ID, -1));
            userInfoBean.setName(sharedPreferences.getString(NAME, ""));
            userInfoBean.setSex(sharedPreferences.getInt(SEX, -1));
            userInfoBean.setMobile(sharedPreferences.getString(MOBILE, ""));
            userInfoBean.setSignature(sharedPreferences.getString(SIGNATURE, ""));
            userInfoBean.setFace_img(sharedPreferences.getString(FACE_IMG, ""));
            userInfoBean.setSchool_id(String.valueOf(sharedPreferences.getInt(SCHOOL_ID, -1)));
            userInfoBean.setAcademy_id(String.valueOf(sharedPreferences.getInt(ACADEMY_ID, -1)));
            userInfoBean.setGrade(String.valueOf(sharedPreferences.getInt(GRADE, -1)));
            userInfoBean.setSchool_name(sharedPreferences.getString(SCHOOL_NAME, ""));
            userInfoBean.setAcademy_name(sharedPreferences.getString(ACADEMY_NAME, ""));
            return userInfoBean;
        }

        return null;
    }

    public static boolean isUserId(Context context, int id) {
        SharedPreferences sharedPreferences = MyApplication.getSharedPreferences(context);
        int userId = sharedPreferences.getInt(ID, -1);
        return id == userId;
    }

    public static void removeUser(Context context) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.remove(ID);
        editor.remove(NAME);
        editor.remove(SEX);
        editor.remove(MOBILE);
        editor.remove(SIGNATURE);
        editor.remove(FACE_IMG);
        editor.remove(SCHOOL_ID);
        editor.remove(ACADEMY_ID);
        editor.remove(GRADE);
        editor.remove(SCHOOL_NAME);
        editor.remove(ACADEMY_NAME);
        editor.commit();
    }


}

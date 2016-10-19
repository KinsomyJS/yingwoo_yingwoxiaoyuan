package com.yingwoo.yingwoxiaoyuan.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * eventBus事件类
 * Created by FJS0420 on 2016/8/13.
 */


public class TokenEvent {

    private String mMsg;
    public TokenEvent(String msg){
        mMsg = msg;
    }

    public String getMsg(){
        String info = "";
        try {
            JSONObject jsonObject = new JSONObject(mMsg);
            info  = (String) jsonObject.get("info");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

}

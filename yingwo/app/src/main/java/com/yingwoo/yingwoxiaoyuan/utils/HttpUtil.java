package com.yingwoo.yingwoxiaoyuan.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by FJS0420 on 2016/8/13.
 */

public class HttpUtil {

    private static OkHttpClient client = HttpControl.getInstance().getClient();


    //获取七牛上传凭证
    public static String getToken(String url) throws IOException {
        MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        String postBody = "Hello com.yingwoo.yingwoo";
        String token = "";

        Request request = new Request.Builder()
                .url(url)
                .header("X-Requested-With","XMLHttpRequest")
                .post(RequestBody.create(null,postBody))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        String jsonstr = response.body().string();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonstr);
            token = (String) jsonObject.get("info");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    /*
    发 布 帖 子
    传 入 参 数  user_id,cat_id,content 可 选 , img 可 选
    post
    http://yw.zhibaizhi.com/yingwophp/post/post_add
     */
    public static void releasePost(int topic_id,  String content, String imgurls) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("topic_id", Integer.toString(topic_id))
                .add("content", content)
                .add("img",imgurls)
                .build();
        Request request = new Request.Builder()
                .url("http://yw.zhibaizhi.com/yingwophp/api/v1/Post/add_new")
                .header("X-Requested-With","XMLHttpRequest")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("qiniushangchuan", str );
            }
        });
    }

    //获取短信验证码
    public static void getSms(String mobile){

        RequestBody formBody = new FormBody.Builder()
                .add("mobile", mobile)
                .build();
        Request request = new Request.Builder()
                .url("http://yw.zhibaizhi.com/yingwophp/api/v1/Sms/Send")
                .header("X-Requested-With","XMLHttpRequest")
                .post(formBody)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("register","false");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("register", str );
            }
        });
    }

    //验证短信验证码
    public static void verifySms(String mobile,String code){
        OkHttpClient client = HttpControl.getInstance().getClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("code",code)
                .build();
        Request request = new Request.Builder()
                .url("http://yw.zhibaizhi.com/yingwophp/api/v1/Sms/Check")
                .header("X-Requested-With","XMLHttpRequest")
                .post(formBody)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("register","false");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("register", str );
            }
        });
    }


}

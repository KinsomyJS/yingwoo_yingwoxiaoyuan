package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;

/**
 * 启动页
 * 用handler.postdelay方法延迟1.5秒启动
 * Created by FJS0420 on 2016/5/8.
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_splash);
        toNextPage();
    }

    private void toNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserInsertHelper.getUserInfo(SplashActivity.this) != null) {
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                    finish();
                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 800);
    }
}

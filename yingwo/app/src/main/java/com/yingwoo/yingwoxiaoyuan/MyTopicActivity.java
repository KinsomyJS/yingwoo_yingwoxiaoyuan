package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yingwoo.yingwoxiaoyuan.fragment.MyTopicMainFragment;

/**
 * Created by wangyu on 25/09/2016.
 */

public class MyTopicActivity extends AppCompatActivity {
    private MyTopicMainFragment myTopicMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        AppManager.getAppManager().addActivity(this);


        myTopicMainFragment = new MyTopicMainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, myTopicMainFragment).commit();
    }

}

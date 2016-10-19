package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yingwoo.yingwoxiaoyuan.fragment.TopicMainFragment;

/**
 * Created by wangyu on 25/09/2016.
 */

public class TopicActivity extends AppCompatActivity {
    private TopicMainFragment topicMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_general);
        Bundle bundle = new Bundle();
        bundle.putInt("topic_id", getTopic_id());
        bundle.putString("topic_name", getTopic_name());
        topicMainFragment = TopicMainFragment.newInstance(
                this, bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, topicMainFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private int getTopic_id() {
        return Integer.parseInt(getIntent().getStringExtra("topic_id"));
    }

    private String getTopic_name(){
        return getIntent().getStringExtra("topic_name");
    }

}

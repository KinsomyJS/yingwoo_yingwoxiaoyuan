package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.model.UserInfoBean;
import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/23.
 */

public class PersonCenterActivity extends AppCompatActivity {
    private SimpleDraweeView action_head;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.iv_usersex)
    ImageView ivUsersex;
    @BindView(R.id.tv_userdesc)
    TextView tvUserdesc;
    @BindView(R.id.action_home)
    ImageView action_home;
    @BindView(R.id.action_find)
    ImageView action_find;
    @BindView(R.id.action_add)
    ImageView action_add;
    @BindView(R.id.action_bub)
    ImageView action_bub;
    @BindView(R.id.block_topic)
    LinearLayout block_topic;
    @BindView(R.id.action_head)
    ImageView actionHead;
    @BindView(R.id.person_info_layout)
    RelativeLayout personInfoLayout;
    @BindView(R.id.block_post)
    LinearLayout blockPost;
    @BindView(R.id.block_comment)
    LinearLayout blockcomment;
    @BindView(R.id.block_like)
    LinearLayout blocklike;

    private Toolbar toolbar = null;
    private UserInfoBean infoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personcenter);
        ButterKnife.bind(this);


        AppManager.getAppManager().addActivity(this);
        init();

    }

    public void init() {
        infoBean = UserInsertHelper.getUserInfo(PersonCenterActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initView();
        actionHead.setImageResource(R.mipmap.head_g);
    }

    @Override
    protected void onResume() {
        super.onResume();
        infoBean = UserInsertHelper.getUserInfo(PersonCenterActivity.this);
        initView();
    }

    public void initView() {
        if (infoBean.getFace_img() != null && !infoBean.getFace_img().equals("")) {
            action_head = (SimpleDraweeView) findViewById(R.id.iv_head);
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
            roundingParams.setRoundAsCircle(true);
            action_head.getHierarchy().setRoundingParams(roundingParams);
            action_head.setImageURI(Uri.parse(infoBean.getFace_img()));
        }
        if (infoBean.getName() != null && !infoBean.getName().equals("")) {
            tvUserid.setText(infoBean.getName());
        }
        if (infoBean.getSignature() != null && !infoBean.getSignature().equals("")) {
            tvUserdesc.setText(infoBean.getSignature());
        }
        if (infoBean.getSex() == 1)
            ivUsersex.setBackgroundResource(R.mipmap.man);
        else if (infoBean.getSex() == 2)
            ivUsersex.setBackgroundResource(R.mipmap.woman);
        else
            ivUsersex.setVisibility(View.GONE);
    }

//    Intent intent = new Intent(this, MyTopicActivity.class);
//    startActivity(intent);

    @OnClick(R.id.block_topic)
    public void toMyTopic() {
        startActivity(new Intent(this, MyTopicActivity.class));
    }

    @OnClick(R.id.layout_action_find)
    public void tofind() {
        startActivity(new Intent(this, FindActivity.class));
    }

    @OnClick(R.id.layout_action_bub)
    public void toPost() {
        Toast.makeText(this,"正在开发中~~",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_action_home)
    public void toHome() {
        startActivity(new Intent(this, HomePageActivity.class));
    }

    @OnClick(R.id.action_add)
    public void toRefreshthing() {
        Intent addPost = new Intent(this, RefreshThingActivity.class);
        startActivity(addPost);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personcenter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            startActivity(new Intent(this, SettingsActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.person_info_layout)
    public void onClick() {
        startActivity(new Intent(PersonCenterActivity.this, ChangeinfoActivity.class));
    }

    @OnClick(R.id.block_post)
    public void toMyPost() {
        startActivity(new Intent(PersonCenterActivity.this, MyPostActivity.class));
    }

    @OnClick(R.id.block_comment)
    public void toMyComment(){
        Toast.makeText(this,"正在开发中~~",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.block_like)
    public void toMyLike(){
        Toast.makeText(this,"正在开发中~~",Toast.LENGTH_SHORT).show();
    }
}

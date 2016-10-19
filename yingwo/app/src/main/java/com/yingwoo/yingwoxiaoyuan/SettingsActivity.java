package com.yingwoo.yingwoxiaoyuan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/22.
 */

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.block_changepd)
    RelativeLayout blockChangepd;
    @BindView(R.id.block_aboutus)
    RelativeLayout blockAboutus;
    @BindView(R.id.block_protocol)
    RelativeLayout blockprotocol;
    @BindView(R.id.tv_bindPhone)
    TextView tv_bindPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AppManager.getAppManager().addActivity(this);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_bindPhone.setText(UserInsertHelper.getUserInfo(this).getMobile());
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {

        new AlertDialog.Builder(SettingsActivity.this).setTitle("登出提示")
                .setMessage("确定登出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserInsertHelper.removeUser(SettingsActivity.this);
                        MyApplication.restartApp();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();

    }

    @OnClick(R.id.block_changepd)
    public void change_passwd() {
        startActivity(new Intent(this,ResetPasswdActivity1.class));
    }

    @OnClick(R.id.block_aboutus)
    public void aboutus(){
        startActivity(new Intent(this,AboutUsActivity.class));
    }

    @OnClick(R.id.block_protocol)
    public void protocol(){
        startActivity(new Intent(this,UserProtocolActivity.class));
    }
}

package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.utils.ValidateUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/13.
 */

public class ResetPasswdActivity1 extends AppCompatActivity {

    @BindView(R.id.btn_next_step)
    Button btn_next_step;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_code)
    EditText editCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd1);
        ButterKnife.bind(this);
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
    }

    @OnClick(R.id.btn_next_step)
    public void nextstep() {
        String phoneNums = editCode.getText().toString();
        if (!ValidateUserInfo.judgePhoneNums(phoneNums)) {
            Toast.makeText(getApplicationContext(),"手机号填写错误",Toast.LENGTH_LONG).show();
            return;
        } else{
            Intent intent = new Intent(this,ResetPasswdActivity2.class);
            intent.putExtra("phone",phoneNums);
            startActivity(intent);
        }
    }
}

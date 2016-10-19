package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.utils.ValidateUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/10.
 */

public class RegisterActivity1 extends AppCompatActivity {

    @BindView(R.id.edit_phone) EditText edit_phone;
    @BindView(R.id.check_agree) CheckBox check_agree;
    @BindView(R.id.btn_next_step) Button btn_next_step;
    @BindView(R.id.tv_haveaccount) TextView tv_haveaccount;
    @BindView(R.id.tv_article) TextView tv_protocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_next_step)
    public void nextStep(){
        String phoneNums = edit_phone.getText().toString();
        if (!ValidateUserInfo.judgePhoneNums(phoneNums)) {
            Toast.makeText(getApplicationContext(),"手机号填写错误",Toast.LENGTH_LONG).show();
            return;
        } else if(!check_agree.isChecked()){
            Toast.makeText(getApplicationContext(),"请认真阅读协议并同意",Toast.LENGTH_LONG).show();
        } else{
            Intent intent = new Intent(this,RegisterActivity2.class);
            intent.putExtra("phone",phoneNums);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_haveaccount)
    public void jumpToLogin(){
        String phoneNums = edit_phone.getText().toString();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("phone",phoneNums);
        startActivity(intent);
    }

    @OnClick(R.id.tv_article)
    public void jumpToProtocol(){
        startActivity(new Intent(this,UserProtocolActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

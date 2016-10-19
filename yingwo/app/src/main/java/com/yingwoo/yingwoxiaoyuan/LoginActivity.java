package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.model.LoginEntity;
import com.yingwoo.yingwoxiaoyuan.model.UserInfoEntity;
import com.yingwoo.yingwoxiaoyuan.model.UserOnlineInfoEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/7/13.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_forgetpd)
    TextView tv_forgetpd;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_passwd)
    EditText edit_passwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    private static final int InsertUser = 167;
    private LoginEntity.InfoBean infoBean;

    private boolean loginFlag;
    private boolean getInfoFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        loginFlag = false;
        getInfoFlag =false;
        if (UserInsertHelper.getUserInfo(LoginActivity.this) != null) {
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }
        Intent intent = this.getIntent();
        String phone = intent.getStringExtra("phone");
        edit_phone.setText(phone);
        tv_forgetpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswdActivity1.class));
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity1.class));
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void login() {
        final String phone = edit_phone.getText().toString();
        final String passwd = edit_passwd.getText().toString();

        UserinfoService userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        userinfoService.login(phone, passwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEntity>() {
                    @Override
                    public void onCompleted() {
                        if (loginFlag) {
                            getInfo();
                            loginFlag = false;
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        if (!UserInfoEntity.isUpDateInfo(loginEntity.getInfo())) {
                            Toast.makeText(LoginActivity.this, "请完善个人信息", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MakeupinfoActivity.class));
                        } else {
                            if (loginEntity.getStatus() == 1) {
                                loginFlag = true;
                                infoBean = loginEntity.getInfo();
                            }
                        }
                    }
                });
    }

    public void getInfo() {
        final UserinfoService userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        userinfoService.getUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserOnlineInfoEntity>() {
                    @Override
                    public void onCompleted() {
                        if (getInfoFlag) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HomePageActivity", "Error");
                    }

                    @Override
                    public void onNext(UserOnlineInfoEntity userOnlineInfoEntity) {
                        if (userOnlineInfoEntity.getStatus() == 1) {
                            getInfoFlag = true;
                            Message message = handler.obtainMessage();
                            message.obj = userOnlineInfoEntity;
                            message.what = InsertUser;
                            message.sendToTarget();
                        }
                    }
                });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == InsertUser) {
                UserOnlineInfoEntity userOnlineInfoEntity = (UserOnlineInfoEntity) msg.obj;
                UserInsertHelper.insertUser(LoginActivity.this, infoBean);
                UserInsertHelper.insertSchoolName(LoginActivity.this, userOnlineInfoEntity.getInfo().getSchool_name());
                UserInsertHelper.insertAcademyName(LoginActivity.this, userOnlineInfoEntity.getInfo().getAcademy_name());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

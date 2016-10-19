package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.model.LoginEntity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;
import com.yingwoo.yingwoxiaoyuan.utils.ValidateUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/7/10.
 */

public class RegisterActivity2 extends AppCompatActivity {

    @BindView(R.id.edit_passwd)
    EditText edit_passwd;
    @BindView(R.id.edit_code)
    EditText edit_code;
    @BindView(R.id.btn_lookpd)
    Button btn_lookpd;
    @BindView(R.id.btn_resend)
    Button btn_resend;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_done_reg)
    Button btn_done_reg;
    private boolean isvisible = false;
    private String phone;
    private String code;
    private String passwd;
    private boolean sendFlag;
    private boolean smsCheck;
    private boolean registerFlag;
    private boolean loginFlag;
    int i = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        btn_resend.setClickable(false);
        btn_resend.setText(i + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
        sendFlag = false;
        smsCheck = false;
        registerFlag = false;
        loginFlag = false;
        phone = intent.getStringExtra("phone");
        SmsSend(phone);
        tv_phone.setText(phone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_lookpd)
    public void lookpd(Button btn_lookpd) {
        if (!isvisible) {
            edit_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isvisible = true;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_close);
        } else {
            edit_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isvisible = false;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_open);
        }
    }

    @OnClick(R.id.btn_done_reg)
    public void done(Button btn_done_reg) {
        code = edit_code.getText().toString().trim();
        passwd = edit_passwd.getText().toString();
        if(passwd.length() < 6){
            Toast.makeText(RegisterActivity2.this,"输入至少六位密码",Toast.LENGTH_SHORT).show();
        }else {
            SmsCheck(phone, code);
        }
    }

    public void SmsSend(String number) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService api = retrofit.create(UserinfoService.class);
        api.smsSend(number)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        if (sendFlag) {
                            Log.d("RegisterActivity2", "发送短信成功");
                            sendFlag = false;
                        } else {
                            Log.d("RegisterActivity2", "发送短信失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        if (registerEntity.getStatus() == 1) {
                            sendFlag = true;
                        }
                    }
                });
    }

    public void SmsCheck(String number, String code) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService api = retrofit.create(UserinfoService.class);
        api.verifySms("register", number, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        if (smsCheck) {
                            Log.d("RegisterActivity2", "验证成功");
                            register(passwd, phone);
                            smsCheck = false;
                        } else {
                            Log.d("RegisterActivity2", "验证失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        if (registerEntity.getStatus() == 1) {
                            smsCheck = true;
                        }
                    }
                });

    }

    public void register(final String passwd, String mobile) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.register(passwd, mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        if (registerFlag) {
                            Toast.makeText(RegisterActivity2.this, "注册成功,正在登录", Toast.LENGTH_SHORT).show();
                            login(phone, passwd);
                            registerFlag = false;
                        } else {
                            Toast.makeText(RegisterActivity2.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        if (registerEntity.getStatus() == 1) {
                            registerFlag = true;
                        }
                    }
                });
    }

    public void login(String phone, String passwd) {
        UserinfoService userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        userinfoService.login(phone, passwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEntity>() {
                    @Override
                    public void onCompleted() {
                        if (loginFlag) {
                            UserInsertHelper.removeUser(RegisterActivity2.this);
                            Toast.makeText(RegisterActivity2.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity2.this, MakeupinfoActivity.class));
                            loginFlag = false;
                        } else {
                            Toast.makeText(RegisterActivity2.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        if (loginEntity.getStatus() == 1) {
                            loginFlag = true;
                        }
                    }
                });
    }

    @OnClick(R.id.btn_resend)
    public void resend() {
        SmsSend(phone);

        // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
        btn_resend.setClickable(false);
        btn_resend.setText(i + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            ValidateUserInfo validate = new ValidateUserInfo();
            if (msg.what == -9) {
                btn_resend.setText(i + "");
            } else if (msg.what == -8) {
                btn_resend.setText("重发");
                btn_resend.setClickable(true);
                i = 60;
            }
        }

    };

}

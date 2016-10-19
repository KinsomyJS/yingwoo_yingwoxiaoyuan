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

import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;
import com.yingwoo.yingwoxiaoyuan.utils.ValidateUserInfo;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yingwoo.yingwoxiaoyuan.R.id.btn_resend;
import static com.yingwoo.yingwoxiaoyuan.R.id.tv_phone;


/**
 * Created by FJS0420 on 2016/7/13.
 */

public class ResetPasswdActivity2 extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(tv_phone)
    TextView tvPhone;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(btn_resend)
    Button btnResend;
    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.btn_lookpd)
    Button btnLookpd;
    @BindView(R.id.btn_done_reg)
    Button btnDoneReg;
    private boolean isvisible = false;
    private String phone;
    private String code;
    private String passwd;
    private boolean sendFlag;
    private boolean smsCheck;
    private boolean resetFlag;
    private boolean loginFlag;
    int i = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd2);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        sendFlag = false;
        smsCheck = false;
        resetFlag = false;
        loginFlag = false;
        phone = intent.getStringExtra("phone");
        SmsSend(phone);
        tvPhone.setText(phone);
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
            editPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isvisible = true;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_close);
        } else {
            editPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isvisible = false;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_open);
        }
    }

    @OnClick(R.id.btn_done_reg)
    public void done(Button btn_done_reg) {
        code = editCode.getText().toString().trim();
        passwd = editPasswd.getText().toString();
        SmsCheck(phone, code);
    }

    public void SmsSend(String number) {
        UserinfoService api = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
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
        UserinfoService api = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        api.verifySms("register", number, code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        if (smsCheck) {
                            Log.d("RegisterActivity2", "验证成功");
                            register(passwd);
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

    public void register(String passwd) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("password", passwd);
        UserinfoService service = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        service.update_info(updateMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        if (resetFlag) {
                            UserInsertHelper.removeUser(ResetPasswdActivity2.this);
                            Toast.makeText(ResetPasswdActivity2.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                            MyApplication.restartApp();
                            resetFlag = false;
                        } else {
                            Toast.makeText(ResetPasswdActivity2.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterActivity2", "Error");
                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        if (registerEntity.getStatus() == 1) {
                            resetFlag = true;
                        }
                    }
                });
    }


    @OnClick(btn_resend)
    public void resend() {
        SmsSend(phone);

        // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
        btnResend.setClickable(false);
        btnResend.setText(i + "");
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
                btnResend.setText(i + "");
            } else if (msg.what == -8) {
                btnResend.setText("重发");
                btnResend.setClickable(true);
                i = 60;
            }
        }

    };

}

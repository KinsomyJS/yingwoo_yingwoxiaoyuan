package com.yingwoo.yingwoxiaoyuan;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Grade_PopUp;
import com.yingwoo.yingwoxiaoyuan.clip.ClipHeaderActivity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.model.UserInfoEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.HttpUtil;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/7/20.
 */

public class MakeupinfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.school_select)
    LinearLayout schoolSelect;
    @BindView(R.id.school_name)
    TextView tv_schoolName;
    @BindView(R.id.academy_name)
    TextView tv_academyName;
    @BindView(R.id.academy_select)
    LinearLayout academySelect;
    @BindView(R.id.grade_select)
    TextView gradeSelect;

    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    public static final int ACADEMY_REQ = 103;
    public static final int SCHOOL_REQ = 104;
    public static final int ACADEMY_RES = 105;
    public static final int SCHOOL_RES = 106;
    public static final int NICK_RES = 107;
    public static final int NICK_REQ = 108;
    public static final int DISPLAY_RES = 109;
    public static final int DISPLAY_REQ = 110;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.edit_name)
    TextView editName;
    @BindView(R.id.ibtn_male)
    ImageButton ibtnMale;
    @BindView(R.id.ibtn_female)
    ImageButton ibtnFemale;
    private int HEADER_FALG = 107;

    @BindView(R.id.tv_signature)
    TextView tvSignature;

    private File tempFile;
    private Uri headerUri;
    private UserInfoEntity userInfo;
    private String School_id;
    private String School_name;
    private String Academy_id;
    private String Academy_name;
    private Grade_PopUp grade_popUp;
    private String gradeinfo;
    private String nickName;
    private String diaplay;

    ImageView iv_head_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeupinfo);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init();
        initData(savedInstanceState);
    }


    private void init() {
        userInfo = new UserInfoEntity();
        iv_head_icon = (ImageView) findViewById(R.id.iv_headpic);
        iv_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseDialog();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        updateBaseInfoProgress.setVisibility(View.VISIBLE);
        String signature = tvSignature.getText().toString();
        if (nickName == null)
            nickName = "";
        userInfo.setSignature(signature);
        updateBaseInfo();
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/com.yingwoo.yingwoo/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    starCropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();

                    starCropPhoto(uri);
                }

                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {

                    if (intent != null) {
                        setPicToView(intent);
                    }
                }
                break;
            case SCHOOL_REQ:
                if (resultCode == SCHOOL_RES) {
                    School_id = intent.getStringExtra("school_id");
                    School_name = intent.getStringExtra("school_name");
                    userInfo.setSchool_id(School_id);
                    tv_schoolName.setText(School_name);
                    tv_academyName.setText("选填");
                }
                break;
            case ACADEMY_REQ:
                if (resultCode == ACADEMY_RES) {
                    Academy_id = intent.getStringExtra("academy_id");
                    Academy_name = intent.getStringExtra("academy_name");
                    userInfo.setAcademy_id(Academy_id);
                    tv_academyName.setText(Academy_name);
                }
                break;
            case NICK_REQ:
                if (resultCode == NICK_RES) {
                    nickName = intent.getStringExtra("nickName");
                    editName.setText(nickName);
                    userInfo.setName(nickName);
//                    Toast.makeText(this, nickName, Toast.LENGTH_SHORT).show();
                }
                break;
            case DISPLAY_REQ:
                if (resultCode == DISPLAY_RES) {
                    diaplay = intent.getStringExtra("signature");
                    tvSignature.setText(diaplay);
                    userInfo.setSignature(diaplay);
                }
                break;
            default:
                break;
        }
    }


    private void showChooseDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                            startActivityForResult(intent, RESULT_CAPTURE);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);
                        }
                    }
                }).show();
    }

    /**
     * 打开截图界面
     *
     * @param uri 原图的Uri
     */
    public void starCropPhoto(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipHeaderActivity.class);
        intent.setData(uri);
        intent.putExtra("side_length", 200);//裁剪图片宽高
        startActivityForResult(intent, CROP_PHOTO);

    }

    private void setPicToView(Intent picdata) {
        Uri uri = picdata.getData();

        if (uri == null) {
            return;
        }

        iv_head_icon.setImageURI(uri);
        headerUri = uri;

    }


    /**
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param uri
     * @return the file path or null
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void updateBaseInfo() {
        if (headerUri != null) {
            updateHeader(headerUri);
        } else {
            updateHttp();
        }
    }

    @OnClick(R.id.school_select)
    public void schoolSelect() {
        startActivityForResult(new Intent(this, SchoolSelectActivity.class), SCHOOL_REQ);
    }

    @OnClick(R.id.academy_select)
    public void academySelect() {
        if (School_name != null) {
            Intent academyIntent = new Intent(this, AcademySelectActivity.class);
            academyIntent.putExtra("school_id", School_id);
            academyIntent.putExtra("school_name", School_name);
            startActivityForResult(academyIntent, ACADEMY_REQ);
        }
    }

    @OnClick(R.id.grade_select)
    public void gradeSelect() {
        grade_popUp = new Grade_PopUp(MakeupinfoActivity.this, new gradePopClick(), new valueChangeListener());
        gradeinfo = Grade_PopUp.data[Grade_PopUp.DEFAULT_VALUE];
        grade_popUp.showAtLocation(this.findViewById(R.id.makeupinfo), Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.tv_signature)
    public void setSignature() {
        Intent intent = new Intent(this, SignatureActivity.class);
        startActivityForResult(intent, DISPLAY_REQ);
    }

    @OnClick(R.id.edit_name)
    public void changeNickName() {
        startActivityForResult(new Intent(this, ChangeNickNameActivity.class), NICK_REQ);
    }

    @OnClick({R.id.ibtn_male, R.id.ibtn_female})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_male:
                ibtnFemale.setBackgroundResource(R.mipmap.femalehide);
                ibtnMale.setBackgroundResource(R.mipmap.maleshow);
                userInfo.setSex(1);
                break;
            case R.id.ibtn_female:
                ibtnMale.setBackgroundResource(R.mipmap.malehide);
                ibtnFemale.setBackgroundResource(R.mipmap.femaleshow);
                userInfo.setSex(2);
                break;
        }
    }

    public class gradePopClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    grade_popUp.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (gradeinfo != null) {
                        grade_popUp.dismiss();
                        gradeSelect.setText(gradeinfo);
                        userInfo.setGrade(gradeinfo);
                    }
                    break;
            }
        }
    }

    public class valueChangeListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            gradeinfo = Grade_PopUp.data[picker.getValue()];
        }
    }

    public void updateHeader(final Uri uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = null;
                try {
                    token = HttpUtil.getToken("http://yw.zhibaizhi.com/yingwophp/qiniu/getAT");
                    String headerPath = getRealFilePath(uri);
                    Log.i("json", token);
                    UploadManager uploadManager = new UploadManager();
                    String data = headerPath;
                    String key = null;
                    uploadManager.put(data, key, token,
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    try {
                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res.get("key"));
                                        //上传后返回url
                                        String url = "http://obabu2buy.bkt.clouddn.com/" + res.get("key");
                                        userInfo.setFace_img(url);
                                        Message message = handler.obtainMessage();
                                        message.what = HEADER_FALG;
                                        message.sendToTarget();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MakeupinfoActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HEADER_FALG) {
                updateHttp();
            }
        }
    };


    public void updateHttp() {
        if (userInfo.rightFlag()) {
            Retrofit retrofit = HttpControl.getInstance().getRetrofit();
            UserinfoService userinfoService = retrofit.create(UserinfoService.class);
            userinfoService.updateBaseInfo(userInfo.getName(), userInfo.getGrade(), userInfo.getSignature(), userInfo.getSex(), userInfo.getFace_img(), userInfo.getSchool_id(), userInfo.getAcademy_id())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RegisterEntity>() {
                        @Override
                        public void onCompleted() {
                            Toast.makeText(MakeupinfoActivity.this, "信息更新成功,请重新登录", Toast.LENGTH_SHORT).show();
                            updateBaseInfoProgress.setVisibility(View.GONE);
                            MyApplication.restartApp();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MakeupinfoActivity.this, "更新信息失败", Toast.LENGTH_SHORT).show();
                            updateBaseInfoProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNext(RegisterEntity registerEntity) {
                            if (registerEntity.getStatus() != 1) {
                                onError(new Exception());
                            }
                        }
                    });
        } else {
            updateBaseInfoProgress.setVisibility(View.GONE);
        }
    }


}

package com.yingwoo.yingwoxiaoyuan.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.R;

/**
 * Created by FJS0420 on 2016/9/5.
 */

public class ImageShower extends Activity {
    private String imgurl;
    SimpleDraweeView simpleDraweeView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);
        Intent intent = this.getIntent();
        imgurl = intent.getStringExtra("imgurl");

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.bigimg);
        simpleDraweeView.setImageURI(Uri.parse(imgurl));
        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }

}
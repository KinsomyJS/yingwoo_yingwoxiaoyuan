package com.yingwoo.yingwoxiaoyuan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FJS0420 on 2016/10/12.
 */

public class UserProtocolActivity extends Activity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocoltext);
        ButterKnife.bind(this);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

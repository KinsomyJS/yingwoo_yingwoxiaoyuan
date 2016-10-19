package com.yingwoo.yingwoxiaoyuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by FJS0420 on 2016/8/13.
 */

public class SecondActivity extends Activity {
    private Button btn_FirstEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        AppManager.getAppManager().addActivity(this);
        btn_FirstEvent = (Button) findViewById(R.id.btn_first_event);

        btn_FirstEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EventBus.getDefault().post(
                        "FirstEvent btn clicked");
            }
        });
    }
}

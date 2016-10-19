package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.View.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyu on 9/3/16.
 */

public class SignatureActivity extends AppCompatActivity {
    @BindView(R.id.tv_nick)
    ClearEditText editContent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Intent displayData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        AppManager.getAppManager().addActivity(this);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("个性签名");
        tvTitle.setHint("请输入个性签名");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_changenick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_changeNick) {
            String signature = editContent.getText().toString().trim();
            if (signature.equals("")) {
                Toast.makeText(this, "个性签名不为空,请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                displayData = new Intent();
                displayData.putExtra("signature", signature);
                setResult(MakeupinfoActivity.DISPLAY_RES, displayData);
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
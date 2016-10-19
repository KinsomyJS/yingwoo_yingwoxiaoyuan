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
 * Created by wangyu on 9/10/16.
 */

public class ChangeNickNameActivity extends AppCompatActivity  {

    @BindView(R.id.tv_nick)
    ClearEditText tvNick;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Intent nickData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        AppManager.getAppManager().addActivity(this);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("昵称");
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
            String nickName = tvNick.getText().toString().trim();
            if (nickName.equals("")){
                Toast.makeText(this, "昵称不为空,请重新输入", Toast.LENGTH_SHORT).show();
            }else {
                nickData = new Intent();
                nickData.putExtra("nickName",nickName);
                setResult(MakeupinfoActivity.NICK_RES,nickData);
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}

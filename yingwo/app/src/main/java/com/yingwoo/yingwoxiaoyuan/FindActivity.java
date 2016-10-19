package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yingwoo.yingwoxiaoyuan.fragment.FindMainFragment;

public class FindActivity extends AppCompatActivity {

	private FindMainFragment findMainFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general);
		AppManager.getAppManager().addActivity(this);


		findMainFragment =new FindMainFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.content_frame, findMainFragment).commit();
	}

}

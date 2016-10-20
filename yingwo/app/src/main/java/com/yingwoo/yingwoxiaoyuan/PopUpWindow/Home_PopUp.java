package com.yingwoo.yingwoxiaoyuan.PopUpWindow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.R;

import static com.yingwoo.yingwoxiaoyuan.R.color.app_primary;
import static com.yingwoo.yingwoxiaoyuan.R.color.textcolor1;


/**
 * Created by wangyu on 8/27/16.
 */

public class Home_PopUp extends PopupWindow {
    private View mMenuView;
    protected TextView all_btn, new_btn, attention_btn, friend_btn;

    public Home_PopUp(final Activity mcontext, View.OnClickListener itemsOnClick) {
        mMenuView = LayoutInflater.from(mcontext).inflate(R.layout.home_popupwindow, null);
        all_btn = (TextView) mMenuView.findViewById(R.id.tv_all);
        new_btn = (TextView) mMenuView.findViewById(R.id.tv_new);
        attention_btn = (TextView) mMenuView.findViewById(R.id.tv_attention);
        friend_btn = (TextView) mMenuView.findViewById(R.id.tv_friendthing);
        all_btn.setOnClickListener(itemsOnClick);
        new_btn.setOnClickListener(itemsOnClick);
        attention_btn.setOnClickListener(itemsOnClick);
        friend_btn.setOnClickListener(itemsOnClick);
        this.setOutsideTouchable(true);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float Right = mMenuView.findViewById(R.id.layout_selector).getRight();
                float bottom = mMenuView.findViewById(R.id.layout_selector).getBottom();
                float y = event.getY();
                float x = event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > bottom || x > Right) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setContentView(mMenuView);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.homepopwindow_anim_style);
    }


    public void changeTextColor(int ResId) {
        all_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(textcolor1));
        new_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(textcolor1));
        attention_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(textcolor1));
        friend_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(textcolor1));
        switch (ResId) {
            case R.id.tv_all:
                all_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(app_primary));
                break;
            case R.id.tv_new:
                new_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(app_primary));
                break;
            case R.id.tv_attention:
                attention_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(app_primary));
                break;
            case R.id.tv_friendthing:
                friend_btn.setTextColor(MyApplication.getGlobalContext().getResources().getColor(app_primary));
                break;
        }
    }

}

package com.yingwoo.yingwoxiaoyuan.PopUpWindow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yingwoo.yingwoxiaoyuan.R;


/**
 * Created by wangyu on 8/27/16.
 */

public class Comfirm_PopUp extends PopupWindow {
    private Activity mContext;
    private View mMenuView;
    protected Button comfirm_btn, cancel_btn;

    public Comfirm_PopUp(final Activity mcontext, View.OnClickListener itemsOnClick) {
        this.mContext = mcontext;
        backgroundMengban(mcontext, 0.4f);
        mMenuView = LayoutInflater.from(mcontext).inflate(R.layout.confirm_popup, null);
        cancel_btn = (Button) mMenuView.findViewById(R.id.cancel_btn);
        comfirm_btn = (Button) mMenuView.findViewById(R.id.comfirm_btn);
        comfirm_btn.setOnClickListener(itemsOnClick);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setOutsideTouchable(true);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float height = mMenuView.findViewById(R.id.command_popup).getTop();
                float y = event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setContentView(mMenuView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundMengban(mcontext, 1f);
            }
        });
    }

    public void backgroundMengban(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}

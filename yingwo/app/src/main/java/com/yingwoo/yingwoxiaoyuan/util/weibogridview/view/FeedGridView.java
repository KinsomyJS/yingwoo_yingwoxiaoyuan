package com.yingwoo.yingwoxiaoyuan.util.weibogridview.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.PhotoViewActivity;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.util.weibogridview.adapter.FeedPhotoAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuz on 16/6/3.
 */
public class FeedGridView extends BaseGridView implements AdapterView.OnItemClickListener {

    private static final String TAG = "FeedGridView";

    private List<String> mDatas = new ArrayList<>();

    private OnItemClickListerner onItemClickListerner;

    private Context context;


    private int mHorizontalSpacing,mVerticalSpacing, mPaddingSpacing;

    public FeedGridView(Context context) {
        super(context);
    }

    public FeedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = getContext().obtainStyledAttributes(attrs,
                R.styleable.FeedGridView);
        mHorizontalSpacing = array.getDimensionPixelSize(R.styleable.FeedGridView_hSpacing, 0);
        mVerticalSpacing = array.getDimensionPixelSize(R.styleable.FeedGridView_vSpacing, 0);
        mPaddingSpacing = array.getDimensionPixelSize(R.styleable.FeedGridView_pSpacing, 0);
        array.recycle();
    }

    public FeedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private FeedPhotoAdapter photoAdapter;
    private int mColumnNum;




    public void setPhotoAdapter(Context context,List<String> imgs) {
        this.context = context;
        mDatas.clear();
        mDatas.addAll(imgs);
        int count = mDatas.size();
        //一张图片的时候也是占1/2
        if (count == 1 || count == 2 || count == 4) {
            mColumnNum = 2;
            setNumColumns(2);
        } else {
            mColumnNum = 3;
            setNumColumns(3);
        }
        Log.d("zhe","mHorizontalSpacing:"+mHorizontalSpacing);
        Log.d("zhe","mVerticalSpacing:"+mVerticalSpacing);
        setHorizontalSpacing(mHorizontalSpacing);
        setVerticalSpacing(mVerticalSpacing);
        int width = calculateColumnWidth();
        setColumnWidth(width);
        photoAdapter = new FeedPhotoAdapter((Activity) context, mDatas, width);
        this.setAdapter(photoAdapter);
        this.setOnItemClickListener(this);
        photoAdapter.notifyDataSetChanged();
        setGridViewWidthBasedOnChildren(this, mDatas.size());
    }

    private int calculateColumnWidth() {
        int width = MyApplication.getWidth();
        if (mColumnNum == 2) {
            width = (width - mPaddingSpacing * 2 - mHorizontalSpacing) / mColumnNum;
        } else if (mColumnNum == 3) {
            width = (width - mPaddingSpacing * 2 - mHorizontalSpacing * 2) / mColumnNum;
        }
        return width;
    }

    /**
     * 动态计算gridview的宽度
     *
     * @param gridView
     * @param count
     */
    public void setGridViewWidthBasedOnChildren(GridView gridView, int count) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = count;
        if (listAdapter.getCount() < count) {
            col = listAdapter.getCount();
        }
        if (count == 4) {
            col = 2;
        }
        int totalWidth = 0;
        for (int i = 0; i < col; i++) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的宽度和
            totalWidth += listItem.getMeasuredWidth() + mHorizontalSpacing * 2;
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置宽度
        params.width = totalWidth;
        // 设置参数
        gridView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(context, PhotoViewActivity.class);
        intent.putExtra("page", position);
        intent.putExtra("photo_list", (Serializable) mDatas);
        context.startActivity(intent);
    }

    public void setOnItemClickListener(OnItemClickListerner onItemClickListerner) {
        this.onItemClickListerner = onItemClickListerner;
    }



    public interface OnItemClickListerner {
        public void onItemClick(View view, int position);
    }
}

package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.View.photoview.PhotoView;
import com.yingwoo.yingwoxiaoyuan.View.photoview.PhotoViewAttacher;
import com.yingwoo.yingwoxiaoyuan.util.HackyViewPager;

import java.util.List;


/**
 * Created by FJS0420 on 2016/9/28.
 */

public class PhotoViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    static final String PHOTO_LIST = "photo_list";
    private static final String ISLOCKED_ARG = "isLocked";
    private ViewPager mViewPager;
    private int position;
    private List<String> datas;
    private TextView mTvIndicator;
    private GestureDetector gestureScanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        mViewPager = (HackyViewPager) findViewById(R.id.viewpager);
        mTvIndicator = (TextView) findViewById(cn.finalteam.galleryfinal.R.id.tv_indicator);


        if (getIntent() != null) {
            int page = getIntent().getIntExtra("page",0);
            datas = (List<String>) getIntent().getSerializableExtra(PHOTO_LIST);
            mViewPager.setAdapter(new SamplePagerAdapter(datas));
            mViewPager.setCurrentItem(page);
        }


        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
        setListener();

    }
    private void setListener() {
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTvIndicator.setText((position + 1) + "/" + datas.size());
    }

    public boolean onTouchEvent(MotionEvent me) {
        return gestureScanner.onTouchEvent(me);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class SamplePagerAdapter extends PagerAdapter {

        private List<String> datas;
        public SamplePagerAdapter(List<String> datas) {
            this.datas = datas;
        }


        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
//            CustomGifImageView gifView = new CustomGifImageView(PhotoViewActivity.this);
//            gifView.setImageUrl(datas.get(position));
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });
            photoView.setImageUri(datas.get(position));

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }
}


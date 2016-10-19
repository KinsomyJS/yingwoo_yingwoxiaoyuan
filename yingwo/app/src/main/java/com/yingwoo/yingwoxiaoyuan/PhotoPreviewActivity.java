package com.yingwoo.yingwoxiaoyuan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.Adapter.PhotoPreviewAdapter;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PhotoBaseActivity;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFViewPager;

/**
 * Created by FJS0420 on 2016/9/5.
 */

public class PhotoPreviewActivity extends PhotoBaseActivity implements ViewPager.OnPageChangeListener{

    static final String PHOTO_LIST = "photo_list";

//    private RelativeLayout mTitleBar;
//    private ImageView mIvBack;
//    private TextView mTvTitle;
    private TextView mTvIndicator;

    private GFViewPager mVpPager;
    private List<String> mPhotoList;
    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    private ThemeConfig mThemeConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThemeConfig = GalleryFinal.getGalleryTheme();

        if ( mThemeConfig == null) {
            resultFailureDelayed(getString(cn.finalteam.galleryfinal.R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.gf_activity_photo_preview);
            AppManager.getAppManager().addActivity(this);
            findViews();
            setListener();
            setTheme();

            int page = getIntent().getIntExtra("page",0);
            Log.i("page",page + "===");
            mPhotoList = (List<String>) getIntent().getSerializableExtra(PHOTO_LIST);
            mPhotoPreviewAdapter = new PhotoPreviewAdapter(this, mPhotoList);
            mVpPager.setAdapter(mPhotoPreviewAdapter);
            mVpPager.setCurrentItem(page);
        }
    }

    private void findViews() {
//        mTitleBar = (RelativeLayout) findViewById(cn.finalteam.galleryfinal.R.id.titlebar);
//        mIvBack = (ImageView) findViewById(cn.finalteam.galleryfinal.R.id.iv_back);
//        mTvTitle = (TextView) findViewById(cn.finalteam.galleryfinal.R.id.tv_title);
        mTvIndicator = (TextView) findViewById(cn.finalteam.galleryfinal.R.id.tv_indicator);

        mVpPager = (GFViewPager) findViewById(cn.finalteam.galleryfinal.R.id.vp_pager);
    }

    private void setListener() {
        mVpPager.addOnPageChangeListener(this);
//        mIvBack.setOnClickListener(mBackListener);
    }

    private void setTheme() {
//        mIvBack.setImageResource(mThemeConfig.getIconBack());
//        if (mThemeConfig.getIconBack() == cn.finalteam.galleryfinal.R.drawable.ic_gf_back) {
//            mIvBack.setColorFilter(mThemeConfig.getTitleBarIconColor());
//        }

//        mTitleBar.setBackgroundColor(mThemeConfig.getTitleBarBgColor());
//        mTvTitle.setTextColor(mThemeConfig.getTitleBarTextColor());
        if(mThemeConfig.getPreviewBg() != null) {
            mVpPager.setBackgroundDrawable(mThemeConfig.getPreviewBg());
        }
    }

    @Override
    protected void takeResult(PhotoInfo info) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTvIndicator.setText((position + 1) + "/" + mPhotoList.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    private View.OnClickListener mBackListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            finish();
//        }
//    };

}


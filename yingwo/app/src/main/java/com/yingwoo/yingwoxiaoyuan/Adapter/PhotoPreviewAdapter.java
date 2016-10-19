package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yingwoo.yingwoxiaoyuan.View.ZoomableDraweeView;
import com.yingwoo.yingwoxiaoyuan.utils.ImageUtil;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.List;

import cn.finalteam.galleryfinal.adapter.ViewHolderRecyclingPagerAdapter;

/**
 * 图片预览适配器 viewpager
 * Created by FJS0420 on 2016/9/5.
 */

public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<com.yingwoo.yingwoxiaoyuan.Adapter.PhotoPreviewAdapter.PreviewViewHolder, String> {

    private Activity mActivity;
    private List<String> urllist;
    private float screenWidth,screenHeight;
    WindowManager wm ;

    public PhotoPreviewAdapter(Activity activity, List<String> list) {
        super(activity,list);
        this.mActivity = activity;
        urllist = list;
        wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = (float)wm.getDefaultDisplay().getWidth();
        screenHeight = (float)wm.getDefaultDisplay().getHeight();
    }

    @Override
    public com.yingwoo.yingwoxiaoyuan.Adapter.PhotoPreviewAdapter.PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        PreviewViewHolder holder = new PreviewViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.bigpic_preview_viewpage_item, parent, false));
//        View view = getLayoutInflater().inflate(R.layout.bigpic_preview_viewpage_item, null);
//        ZoomableDraweeView zoomableDraweeView = (ZoomableDraweeView) view.findViewById(R.id.imgview);
        return holder;
    }

    @Override
    public void onBindViewHolder(com.yingwoo.yingwoxiaoyuan.Adapter.PhotoPreviewAdapter.PreviewViewHolder holder, int position) {

        String[] urls = urllist.get(position).split(",");
        Object[] imgInfo = ImageUtil.downloadImageFormat(urls[0]);
        float picheight = (float) imgInfo[2];
        float picwidth = (float) imgInfo[1];
        Log.d("pichw",position + "height" + picheight + "  width" + picwidth);

        ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
//        layoutParams.height = 400;
        if(picwidth >= picheight && picwidth >= screenWidth){
            float scale = picwidth / screenWidth;
            layoutParams.width = (int)screenWidth;
            layoutParams.height = (int)(picheight / scale);
            Log.d("picbijiao",position +"picheight <= picwidth" + layoutParams.height + " " +layoutParams.width);
        }else if(picheight >= picwidth && picheight >= screenHeight){
            float scale = picheight / screenHeight;
            layoutParams.height = (int)screenHeight;
            layoutParams.width = (int)(picwidth / scale);
            Log.d("picbijiao",position +"picheight >= picwidth" + layoutParams.height + " " +layoutParams.width);
        }else{
            layoutParams.height = (int) picheight;
            layoutParams.width = (int) picwidth;
        }

        holder.mImageView.setLayoutParams(layoutParams);
        holder.mImageView.setImageURI(Uri.parse(urls[0]));
    }

    static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder{
        ZoomableDraweeView mImageView;
        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (ZoomableDraweeView) view.findViewById(R.id.imgview);
        }
    }
}

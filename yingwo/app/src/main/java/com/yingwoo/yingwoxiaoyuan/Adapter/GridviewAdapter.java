package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.w4lle.library.NineGridAdapter;
import com.yingwoo.yingwoxiaoyuan.utils.ImageLoadUtil;

import java.util.List;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class GridviewAdapter extends NineGridAdapter {
    Context context = null;
    private List<String> urls;
    private ViewGroup.LayoutParams params;
    public GridviewAdapter(Context context,List<String> urls) {
        super(context, urls);
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return (urls == null) ? 0 : urls.size();
    }

    @Override
    public String getUrl(int positopn) {
        return urls.get(positopn);
    }

    @Override
    public Object getItem(int position) {
        return (urls == null) ? null : urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int i, View convertView) {
        SimpleDraweeView iv = new SimpleDraweeView(context);
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources()).build();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        iv.setHierarchy(hierarchy);
//        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        iv.setBackgroundColor(Color.parseColor("#f5f5f5"));
        ImageLoadUtil.showThumb(Uri.parse(urls.get(i)),iv,context);
        return iv;
    }


}

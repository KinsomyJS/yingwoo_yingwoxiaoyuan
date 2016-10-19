package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.SimpleAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FJS0420 on 2016/9/2.
 */

public class PictureAdapter extends SimpleAdapter {

    Context context = null;
    int colnum = 1;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public PictureAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.pic_item,null);
        HashMap<String, String> urls = (HashMap<String, String>)getItem(position);
        String imgurl = urls.get("url");
        Uri uri = Uri.parse(imgurl);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.iv_pic);
        simpleDraweeView.setImageURI(uri);
        // Calculate the item width by the column number to let total width fill the screen width
        // 根据列数计算项目宽度，以使总宽度尽量填充屏幕
        int itemWidth = (int)(context.getResources().getDisplayMetrics().widthPixels -  colnum * 10)  / colnum;
        // Calculate the height by your scale rate, I just use itemWidth here
        // 下面根据比例计算您的item的高度，此处只是使用itemWidth
        int itemHeight = itemWidth;

        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                itemWidth,
                itemHeight);
        ViewGroup.LayoutParams para;
        para = simpleDraweeView.getLayoutParams();
        para.height = itemHeight;
        para.width = itemWidth;
        simpleDraweeView.setLayoutParams(para);
        convertView.setLayoutParams(param);
        return convertView;
    }
}

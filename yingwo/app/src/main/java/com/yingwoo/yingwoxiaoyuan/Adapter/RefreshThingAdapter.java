package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.model.RefreshThingModel;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.List;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class RefreshThingAdapter extends BaseAdapter {

    private List<RefreshThingModel> refreshThinglist;
    Context context;
    private LayoutInflater mInflater = null;

    public RefreshThingAdapter(Context context,List<RefreshThingModel> refreshThinglist){
        this.mInflater = LayoutInflater.from(context);
        this.refreshThinglist = refreshThinglist;
    }
    @Override
    public int getCount() {
        return refreshThinglist.size();
    }

    @Override
    public Object getItem(int position) {
        return refreshThinglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RefreshthingListViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null){
            holder = new RefreshthingListViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.list_refreshthing_item,null);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.gridView = (GridView) convertView.findViewById(R.id.gridview);
            holder.iv_userhead = (ImageView) convertView.findViewById(R.id.iv_userhead);
            holder.tv_userId = (TextView) convertView.findViewById(R.id.tv_userId);
            holder.tv_releasetime = (TextView) convertView.findViewById(R.id.tv_releasetime);
            holder.tv_likeNum = (TextView) convertView.findViewById(R.id.tv_likenum);
            holder.tv_commentNum = (TextView) convertView.findViewById(R.id.tv_commentnum);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else{
            holder = (RefreshthingListViewHolder) convertView.getTag();
        }
        holder.iv_userhead.setImageBitmap(refreshThinglist.get(position).getUserhead());
        holder.tv_content.setText(refreshThinglist.get(position).getContent());
        holder.tv_releasetime.setText(refreshThinglist.get(position).getReleaseTime().toString());
        holder.tv_likeNum.setText(refreshThinglist.get(position).getLikeNum());
        holder.tv_commentNum.setText(refreshThinglist.get(position).getCommentNum());
        holder.tv_userId.setText(refreshThinglist.get(position).getUserId());
//        SimpleAdapter simpleAdapter = new SimpleAdapter(context,refreshThinglist.get(position).getPhotos(),R.layout.grid_viewitem,new )
        return convertView;
    }
}
//ViewHolder静态类
class RefreshthingListViewHolder
{
    public TextView tv_content;
    public GridView gridView;
    public ImageView iv_userhead;
    public TextView tv_userId;
    public TextView tv_releasetime;
    public TextView tv_likeNum;
    public TextView tv_commentNum;
}
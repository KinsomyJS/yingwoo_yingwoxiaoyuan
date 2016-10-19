package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.List;

public class PostImageItemAdapter extends BaseAdapter {

    private List<String> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public PostImageItemAdapter(Context context, List<String> objects) {
        this.objects = objects;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.post_image_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder) {
        //TODO implement
        holder.ivPostPic.setImageURI(Uri.parse(object));
    }

    protected class ViewHolder {
        private SimpleDraweeView ivPostPic;

        public ViewHolder(View view) {
            ivPostPic = (SimpleDraweeView) view.findViewById(R.id.iv_postPic);
        }
    }
}

package com.yingwoo.yingwoxiaoyuan.util.weibogridview.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.util.weibogridview.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by liuz on 16/6/3.
 */
public class FeedPhotoAdapter extends BaseWeiBoAdapter<String> {

    private int mColumnWidth;

    private LayoutInflater mInflater;

    public FeedPhotoAdapter(Activity context, List<String> mEntities, int mColumnWidth) {
        super(context, mEntities);
        this.mColumnWidth = mColumnWidth;
        this.mInflater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getDatas().get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_feed_photo, null);
        }
        SimpleDraweeView photo = ViewHolder.get(convertView, R.id.iv_photo);
        ViewGroup.LayoutParams params = photo.getLayoutParams();
        params.width = mColumnWidth;
        params.height = mColumnWidth;
        photo.setLayoutParams(params);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setAutoRotateEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        photo.setController(controller);

        return convertView;
    }
}

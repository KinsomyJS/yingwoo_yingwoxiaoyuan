/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yingwoo.yingwoxiaoyuan.R;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;
import cn.finalteam.toolsfinal.adapter.ViewHolderAdapter;

/**
 * Desction:
 */
public class PhotoEditListAdapter extends ViewHolderAdapter<PhotoEditListAdapter.ViewHolder, PhotoInfo> {

    private AppCompatActivity mActivity;
    private int mRowWidth;
    private List<PhotoInfo> list;
    private View.OnClickListener addPhotoListener;

    public PhotoEditListAdapter(AppCompatActivity activity, List<PhotoInfo> list) {
        super(activity, list);
        mActivity = activity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.gf_adapter_edit_list, parent);
        return new ViewHolder(view);
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == list.size()) {
            if (list.size() < 15) {
                holder.mIvPhoto.setImageResource(R.mipmap.add_photo);
                holder.mIvDelete.setVisibility(View.GONE);
                if (addPhotoListener!=null)
                holder.mIvPhoto.setOnClickListener(addPhotoListener);
            }
        } else {
            String path = "";
            PhotoInfo photoInfo = getDatas().get(position);
            if (photoInfo != null) {
                path = photoInfo.getPhotoPath();
            }
            holder.mIvPhoto.setImageResource(R.drawable.ic_gf_default_photo);
            holder.mIvDelete.setImageResource(GalleryFinal.getGalleryTheme().getIconDelete());
            Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
            GalleryFinal.getCoreConfig().getImageLoader().displayImage(mActivity, path, holder.mIvPhoto, defaultDrawable, 100, 100);
            if (!GalleryFinal.getFunctionConfig().isMutiSelect()) {
                holder.mIvDelete.setVisibility(View.GONE);
            } else {
                holder.mIvDelete.setVisibility(View.VISIBLE);
            }
            holder.mIvDelete.setOnClickListener(new OnDeletePhotoClickListener(position));
        }
    }

    public class ViewHolder extends ViewHolderAdapter.ViewHolder {
        GFImageView mIvPhoto;
        ImageView mIvDelete;

        public ViewHolder(View view) {
            super(view);
            mIvPhoto = (GFImageView) view.findViewById(R.id.iv_photo);
            mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
        }
    }

    private class OnDeletePhotoClickListener implements View.OnClickListener {

        private int position;

        public OnDeletePhotoClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            getDatas().remove(position);
            notifyDataSetChanged();
//            mActivity.deleteIndex(position, photoInfo);
        }
    }

    public void setAddPhotoListener(View.OnClickListener onClickListener){
        addPhotoListener = onClickListener;
    }
}

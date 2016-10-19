package com.yingwoo.yingwoxiaoyuan.utils;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by FJS0420 on 2016/9/3.
 */

public class ImageLoadUtil {

    public static void showThumb(Uri uri, SimpleDraweeView draweeView, Context context){

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtil.dp2px(context,3), DensityUtil.dp2px(context,3)))
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//请求经过缓存级别  BITMAP_MEMORY_CACHE，ENCODED_MEMORY_CACHE，DISK_CACHE，FULL_FETCH
                .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
                .setProgressiveRenderingEnabled(true)//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }
}

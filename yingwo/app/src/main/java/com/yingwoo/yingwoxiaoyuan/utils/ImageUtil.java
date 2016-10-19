package com.yingwoo.yingwoxiaoyuan.utils;

import android.content.Context;
import android.net.Uri;

/**
 * img url解析
 * Created by wangyu on 9/6/16.
 */

public class ImageUtil {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private static final String spitFlag1 = "&";
    private static final String spitFlag2 = "?";
    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public static Object[] downloadImageFormat(String url) {
        String materialUrl;
        float width;
        float height;
        String spitFlag;
        String w_hStirng;
        if (!url.contains(spitFlag1)&&!url.contains(spitFlag2))
            return new Object[]{url, 50, 50};

        if (url.contains(spitFlag1)){
            spitFlag = spitFlag1;
            w_hStirng = url.substring(url.indexOf(";") + 1, url.length());
            height = Float.parseFloat(w_hStirng.substring(w_hStirng.indexOf(";") + 1, w_hStirng.length()));
        }else {
            spitFlag = spitFlag2;
            w_hStirng = url.substring(url.indexOf(spitFlag2) + 1, url.length());
            height = Float.parseFloat(w_hStirng.substring(w_hStirng.indexOf(spitFlag2) + 1, w_hStirng.length()));
        }
        materialUrl = url.substring(0, url.indexOf(spitFlag));
        width = Float.parseFloat(w_hStirng.substring(0, w_hStirng.indexOf(spitFlag)));
        return new Object[]{materialUrl, width, height};
    }

    public static String uploadImageFormat(String materialUrl, int... args) {
        return materialUrl + "?" + args[0] + "?" + args[1];
    }

}

package com.yingwoo.yingwoxiaoyuan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.yingwoo.yingwoxiaoyuan.Listener.UILPauseOnScrollListener;
import com.yingwoo.yingwoxiaoyuan.loader.UILImageLoader;
import com.yingwoo.yingwoxiaoyuan.utils.ClearableCookieJar;
import com.yingwoo.yingwoxiaoyuan.utils.PersistentCookieJar;
import com.yingwoo.yingwoxiaoyuan.utils.cache.SetCookieCache;
import com.yingwoo.yingwoxiaoyuan.utils.persistence.SharedPrefsCookiePersistor;

import java.io.File;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by FJS0420 on 2016/8/4.
 */

public class MyApplication extends Application{
    static Context context;
    FunctionConfig functionConfig;
    CoreConfig coreConfig;
    ThemeConfig theme;
    static ClearableCookieJar cookieJar;
    static int screenwidth = 1080;

    @Override
    public void onCreate() {
        super.onCreate();
//        ConfigConstants.init(getResources());// 初始化默认图片（占位图，错误图）
//        Fresco.initialize(this, ConfigConstants.getImagePipelineConfig(this));// 图片缓存初始化配置

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"yingwoxy"))
                .setBaseDirectoryName("com.yingwoo.yingwoo")
                .setMaxCacheSize(200*1024*1024)//200MB
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
        context =getApplicationContext();
        //设置主题
        theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(29, 210, 166))
                .setIconBack(R.mipmap.nva_con)
                .setCheckSelectedColor(Color.rgb(244, 206, 33))
                .setFabNornalColor(Color.rgb(244, 206, 33))
                .setFabPressedColor(Color.rgb(200, 165, 2))
                .build();
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(false)
                .build();
        coreConfig = new CoreConfig.Builder(this, new UILImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(null)  // recorder分片上传时，已上传片记录器。默认null
                .recorder(null, null)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);

    }

    public static void restartApp(){
        AppManager.getAppManager().AppExit(getGlobalContext());
        Intent i = getGlobalContext().getPackageManager()
                .getLaunchIntentForPackage(getGlobalContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getGlobalContext().startActivity(i);
    }

    public static  Context getGlobalContext(){
        return context;
    }

    public static ClearableCookieJar getCookieJar() {
        if (cookieJar == null)
            return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        else
            return cookieJar;
    }

    public static int getWidth(){
        return screenwidth;
    }

    public static  void setWidth(int width){
        screenwidth = width;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("login", MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

}

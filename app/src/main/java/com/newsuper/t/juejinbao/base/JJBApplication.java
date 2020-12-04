package com.newsuper.t.juejinbao.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.danikula.videocache.HttpProxyCacheServer;
import com.newsuper.t.inittask.SmartRefreshLayoutTask;
import com.newsuper.t.inittask.TaskDispatcher;
import com.newsuper.t.inittask.X5WebTask;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.utils.SP;

import io.paperdb.Paper;

public class JJBApplication extends Application {
    private static Context context;
    private static JJBApplication instance;
    public static JunjinBaoMainActivity mainActivity;
    public static String WebViewTag = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (context == null) {
            context = this;
        }
        Paper.init(getInstance());
        //请求地址配置
        // if(!RetrofitManager.RELEASE){
        //debug接口
        String jk = SP.getAccount(this).getString(RetrofitManager.SP_APP_URL_DOMAIN, RetrofitManager.APP_URL_DOMAIN0);
        RetrofitManager.APP_URL_DOMAIN = jk;

        //debug资源地址
//            String zy = SP.getAccount(this).getString(RetrofitManager.SP_WEB_URL_COMMON , RetrofitManager.WEB_URL_COMMON0);
//            RetrofitManager.WEB_URL_COMMON = zy;

        //debug注入地址
        String zr = SP.getAccount(this).getString(RetrofitManager.SP_VIP_JS_URL, RetrofitManager.VIP_JS_URL0);
        RetrofitManager.VIP_JS_URL = zr;
        //    }

        //程序入口处异步初始化的方案
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new SmartRefreshLayoutTask())
                .addTask(new X5WebTask())
                .start();
    }


    public static JJBApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    //视频加载
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        JJBApplication app = (JJBApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }

    // 获取渠道号
    public static String getChannel() {
        String channel = "";
        try {
            ApplicationInfo info = instance.getPackageManager().
                    getApplicationInfo(instance.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                String metaData = info.metaData.getString("UMENG_CHANNEL");
                if (!metaData.isEmpty()) {
                    channel = metaData;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }
}

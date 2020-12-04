package com.newsuper.t.markert.base;

import android.app.Application;
import android.content.Context;
import android.os.RemoteException;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.newsuper.t.markert.utils.CrashUtils;
import com.newsuper.t.markert.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;
import com.trans.network.HttpManger;

import java.util.ArrayList;
import java.util.List;


public class BaseApp extends Application {

    private static Application mContext;
    private static List<BaseActivity> mAllActivitys = new ArrayList<>();
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        HttpManger.getSingleton().init(this);
        Tools.init(this);
        CrashUtils.getInstance().init(this);//自定义异常信息捕捉
        CrashReport.initCrashReport(getApplicationContext(), "e22a5d5433", false);
      //  initWxFace();
    }

    public static Application getApplication(){
        return mContext;
    }

    public static List<BaseActivity> getmAllActivitys() {
        return mAllActivitys;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
//        TPUtils.remove(this, KeyConstrant.KEY_WORKER);
    }
}

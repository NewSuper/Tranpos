package com.newsuper.t.sale.base;

import android.app.Application;
import android.content.Context;
import android.os.RemoteException;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.newsuper.t.sale.utils.Tools;
import com.newsuper.t.sale.utils.WxConstant;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;
import com.tencent.wxpayface.WxfacePayCommonCode;
import com.trans.network.HttpManger;
import com.transpos.tools.tputils.TPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        CrashReport.initCrashReport(getApplicationContext(), "88c581ae76", false);
        initWxFace();
    }

    private void initWxFace() {
        Map<String, String> agencyMap = new HashMap<>();
        WxPayFace.getInstance().initWxpayface(this, agencyMap, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                if (map != null) {
                    String code = (String) map.get(WxConstant.RETURN_CODE);
                    String msg = (String) map.get(WxConstant.RETURN_MSG);
                    if (!(code != null && code.equals(WxfacePayCommonCode.VAL_RSP_PARAMS_SUCCESS))) {
                        Log.e("debug", "response: "+"初始化失败" +msg);
                    }

                }
            }
        });
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

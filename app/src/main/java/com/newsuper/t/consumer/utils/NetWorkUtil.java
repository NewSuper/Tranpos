package com.newsuper.t.consumer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.newsuper.t.consumer.application.BaseApplication;


/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class NetWorkUtil {
    /**
     * 判断网络是否连接.
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }
}

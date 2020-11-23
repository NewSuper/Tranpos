package com.newsuper.t.juejinbao.ui.movie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class NetUtils {

    //判断网络是否连接
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }
    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getNetState(Context context){
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connMgr==null)
            return "";
        //获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        //用于存放网络连接信息
        StringBuilder sb = new StringBuilder();
        //通过循环将网络信息逐个取出来

        boolean wifi = false;
        boolean mobile = false;
        boolean fourg = false;
        for (int i=0; i < networks.length; i++){
            //获取ConnectivityManager对象对应的NetworkInfo对象
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            if(networkInfo==null)
                return "";
            if(networkInfo.getTypeName().equals("WIFI") && networkInfo.isConnected()){
                wifi = true;
            }
            if(networkInfo.getTypeName().equals("MOBILE") && networkInfo.isConnected()){
                mobile = true;
            }
            if(networkInfo.getSubtypeName().equals("LTE") &&networkInfo.getTypeName().equals("MOBILE") && networkInfo.isConnected()){
                fourg = true;
            }
        }

        if(mobile || wifi){
            if(wifi) {
                Log.e("zy" , "wifi");

                return "WIFI";
            }else if(fourg){
                Log.e("zy" , "4G");
                return "4G";
            }else{
                Log.e("zy" , "移动网络");
                return "移动网络";
            }
        }else{
            Log.e("zy" , "无网络");
            return "无网络";
        }
    }

}

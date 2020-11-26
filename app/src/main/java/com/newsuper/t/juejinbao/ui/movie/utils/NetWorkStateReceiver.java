package com.newsuper.t.juejinbao.ui.movie.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;


import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.ui.movie.player.EventNetChange;

import org.greenrobot.eventbus.EventBus;


public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Log.e("zy", "WIFI已连接,移动数据已连接");
//                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                Log.e("zy", "WIFI已连接,移动数据已断开");
//                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Log.e("zy", "WIFI已断开,移动数据已连接");
//                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("zy", "WIFI已断开,移动数据已断开");
//                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            }
//API大于23时使用下面的方式进行网络监听
        }else {

            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
//            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            //获取所有网络连接的信息
//            Network[] networks = connMgr.getAllNetworks();
//            //用于存放网络连接信息
//            StringBuilder sb = new StringBuilder();
//            //通过循环将网络信息逐个取出来
//
//            boolean wifi = false;
//            boolean net = false;
//            for (int i=0; i < networks.length; i++){
//                //获取ConnectivityManager对象对应的NetworkInfo对象
//                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                if(networkInfo.getTypeName().equals("WIFI") && networkInfo.isConnected()){
//                    wifi = true;
//                }
//                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
//                net = true;
//            }
//
//            if(net){
//                if(!wifi) {
//                    Log.e("zy" , "wifi");
//                }else{
//                    Log.e("zy" , "流量");
//                }
//            }else{
//                Log.e("zy" , "无网络");
//            }

//            Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
//            Log.e("zy", sb.toString());
            BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_PLAYER_NETSTATE,  NetUtils.getNetState(context)));

            EventBus.getDefault().post(new EventNetChange(NetUtils.getNetState(context)));

        }
    }
}
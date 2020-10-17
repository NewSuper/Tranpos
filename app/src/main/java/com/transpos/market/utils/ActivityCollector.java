package com.transpos.market.utils;


import android.app.ActivityManager;
import android.content.Context;

import com.transpos.market.base.BaseApp;
import org.apache.commons.collections4.CollectionUtils;
import java.util.List;

public class ActivityCollector {
    /**
     * 判断服务是否正在运行
     * @param className
     * @return
     */
    public static boolean isServiceRunning(String className){
        ActivityManager activityManager = (ActivityManager) BaseApp.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infoList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if(CollectionUtils.isEmpty(infoList)) return false;
        for(ActivityManager.RunningServiceInfo info : infoList){
            if(className.equals(info.service.getClassName())){
                return  true;
            }
        }
        return false;
    }
    /**
     * 判断service是否在运行
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        try {
            ActivityManager activityManager = (ActivityManager)
                    mContext.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList
                    = activityManager.getRunningServices(30);

            if (!(serviceList.size() > 0)) {
                return false;
            }

            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).service.getClassName().equalsIgnoreCase(className) ) {
                    isRunning = true;
                    break;
                }
            }
        }catch (Exception e){

        }
        return isRunning;
    }
}

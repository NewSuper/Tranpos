package com.transpos.sale.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ActivityCollector {
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

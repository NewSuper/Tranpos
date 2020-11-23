package com.juejinchain.steplibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by flt on 2016/8/6.
 */

public class NimPreferences {
    //上一次系统总步数
    public static final String STEP_NUM_LAST = "long_step_last";
    //当前总步数
    public static final String STEP_NUM_NOW = "long_step_now";
    //当前时间
    public static final String STEP_TIME = "long_step_time";


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("jjb", Context.MODE_PRIVATE);
    }


    public static String getString(Context context ,String flag, String defaultValue) {
        return getSharedPreferences(context).getString(flag, defaultValue);
    }

    public static boolean getBoolean(Context context ,String flag, boolean defValue) {
        return getSharedPreferences(context).getBoolean(flag, defValue);
    }

    public static int getInt(Context context ,String flag, int defValue) {
        return getSharedPreferences(context).getInt(flag, defValue);
    }

    public static long getLong(Context context ,String flag, long defValue) {
        return getSharedPreferences(context).getLong(flag, defValue);
    }

    public static void setString(Context context ,String flag, String value) {
        getSharedPreferences(context).edit().putString(flag, value).commit();
    }

    public static void setBoolean(Context context ,String flag, boolean value) {
        getSharedPreferences(context).edit().putBoolean(flag, value).commit();
    }

    public static int setInt(Context context ,String flag, int value) {
        getSharedPreferences(context).edit().putInt(flag, value).commit();
        return value;
    }
    public static long setLong(Context context ,String flag, long value) {
        getSharedPreferences(context).edit().putLong(flag, value).commit();
        return value;
    }


}

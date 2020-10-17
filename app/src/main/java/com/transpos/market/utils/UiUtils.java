package com.transpos.market.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.widget.Toast;

import com.transpos.market.base.BaseApp;


public class UiUtils {

    public static void showToastLong(String content) {
        Toast.makeText(BaseApp.getApplication(), content, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("ResourceType")
    public static void showToastLong(@IdRes int content) {
        Toast.makeText(BaseApp.getApplication(), content, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(String content) {
        Toast.makeText(BaseApp.getApplication(), content, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceType")
    public static void showToastShort(@IdRes int content) {
        Toast.makeText(BaseApp.getApplication(), content, Toast.LENGTH_SHORT).show();
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * convert sp to its equivalent px
     */
    public static int sp2px(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
    public static String hidePhoneNum(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }
}

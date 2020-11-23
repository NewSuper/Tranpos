package com.newsuper.t.juejinbao.utils;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;


public class ToastUtils {

    //在自己内部定义自己的一个实例，只供内部调用

    private Toast mToast=null;
    private static ToastUtils instance;

    private ToastUtils() {
    }

    //这里提供了一个供外部访问本class的静态方法，可以直接访问
    public static ToastUtils getInstance() {
        if(instance == null){
            instance = new ToastUtils();
        }
        return instance;
    }

    /**
     * Android 默认 Toast
     * @param context
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void show(Context context, String msg) {
        try {
            // TODO: 2019/4/9 防止多次弹出
            if(mToast==null){
                mToast = Toast.makeText(context.getApplicationContext(), null, Toast.LENGTH_SHORT);
            }
            mToast.setText(msg);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 完全自定义
     * @param context
     * @param resID  布局ID
     */
    @SuppressLint("ShowToast")
    public void showCustonViewToast(Context context,int resID ) {
        try {
            //获取布局xml
            View tempView = LayoutInflater.from(context.getApplicationContext()).inflate(resID, null);

            if(mToast==null){
                mToast = new Toast(context);
            }
            //显示在屏幕的中间
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(tempView);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 警告toast
     * @param context
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void showWarning(Context context,String msg ) {
        try {
            //获取布局xml
            View tempView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_toast_custom_warning, null);
            TextView msgTv = tempView.findViewById(R.id.tv_msg);
            msgTv.setText(msg==null?"":msg);
            if(mToast==null){
                mToast = new Toast(context.getApplicationContext());
            }
            //显示在屏幕的中间
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(tempView);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 错误(红色)
     * @param context
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void showError(Context context,String msg ) {
        try {
            //获取布局xml
            View tempView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_toast_custom_error, null);
            TextView msgTv = tempView.findViewById(R.id.tv_msg);
            msgTv.setText(msg==null?"":msg);

            if(mToast==null){
                mToast = new Toast(context.getApplicationContext());
            }
            //显示在屏幕的中间
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(tempView);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成功(绿色)
     * @param context
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void showSuccess(Context context,String msg) {
        try {
            //获取布局xml
            View tempView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_toast_custom_success, null);
            TextView msgTv = tempView.findViewById(R.id.tv_msg);
            msgTv.setText(msg==null?"":msg);
            if(mToast==null){
                mToast = new Toast(context.getApplicationContext());
            }
            //显示在屏幕的中间
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(tempView);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

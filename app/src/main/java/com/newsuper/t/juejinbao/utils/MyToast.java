package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.widget.Toast;

import com.newsuper.t.juejinbao.base.JJBApplication;

/**
 * Toast 工具类
 *
 * @author Seny
 */
public class MyToast {
    private static Toast mToast;

    public static void showToast(String massage) {
        if (mToast == null) {
            mToast = Toast.makeText(JJBApplication.getContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(massage);
        mToast.show();
    }

    /**
     * show a toast
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }

    /**
     * show a long toast
     *
     * @param context
     * @param text
     */
    public static void showLong(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void showLong(Context context, int resId) {
        showLong(context, context.getString(resId));
    }
}

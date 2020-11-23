package com.newsuper.t.juejinbao.utils.androidUtils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.Window;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarTextColor {
    @IntDef({TYPE_MIUI,
            TYPE_FLYME,
            TYPE_M})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }

    public final static int TYPE_MIUI = 0;
    public final static int TYPE_FLYME = 1;
    public final static int TYPE_M = 3;//6.0
    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public static void setImmersiveStatusBar(Activity activity, boolean fontIconDark) {
        if (fontIconDark) {
            if (Rom.isMiui()) {
                setStatusBarFontIconDark(activity,TYPE_MIUI);
            }
            else if (Rom.isFlyme()) {
                setStatusBarFontIconDark(activity,TYPE_FLYME);
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarFontIconDark(activity,TYPE_M);
            }
            else {

            }
        }
    }



    /**
     * 设置文字颜色
     */
    public static void setStatusBarFontIconDark(Activity activity, @ViewType int type) {
        switch (type) {
            case TYPE_MIUI:
                setMiuiUI(activity,true);
                break;
            case TYPE_M:
                setCommonUI(activity);
                break;
        }
    }

    //设置6.0的字体
    public static void setCommonUI(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    //设置MIUI字体
    public static void setMiuiUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            Class clazz = activity.getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

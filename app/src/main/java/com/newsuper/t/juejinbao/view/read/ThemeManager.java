
package com.newsuper.t.juejinbao.view.read;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.utils.ScreenUtils;

public class ThemeManager {

    public static final int THEME1 = 0;
    public static final int THEME2 = 1;
    public static final int THEME3 = 2;
    public static final int THEME4 = 3;
    public static final int THEME5 = 4;
    public static final int NIGHT = 5;

    public static void setReaderTheme(int theme, View view) {
        switch (theme) {
            case THEME1:
                view.setBackgroundResource(R.color.theme1);
                break;
            case THEME2:
                view.setBackgroundResource(R.color.theme2);
                break;
            case THEME3:
                view.setBackgroundResource(R.color.theme3);
                break;
            case THEME4:
                view.setBackgroundResource(R.color.theme4);
                break;
            case THEME5:
                view.setBackgroundResource(R.color.theme5);
                break;
            case NIGHT:
                view.setBackgroundResource(R.color.c_1d1d1f);
                break;
            default:
                break;
        }
    }

    public static Bitmap getThemeDrawable(int theme) {
        Bitmap bmp = Bitmap.createBitmap(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), Bitmap.Config.ARGB_8888);
        switch (theme) {
            case THEME1:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.theme1));
                break;
            case THEME2:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.theme2));
                break;
            case THEME3:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.theme3));
                break;
            case THEME4:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.theme4));
                break;
            case THEME5:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.theme5));
                break;
            case NIGHT:
                bmp.eraseColor(ContextCompat.getColor(JJBApplication.getContext(), R.color.c_1d1d1f));
                break;
            default:
                break;
        }
        return bmp;
    }
}

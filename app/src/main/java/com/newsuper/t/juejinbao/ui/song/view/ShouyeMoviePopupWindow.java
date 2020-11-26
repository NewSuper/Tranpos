package com.newsuper.t.juejinbao.ui.song.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ShouyeMoviePopupWindow {
    private Context context;
    final PopupWindow popupWindow2;

    private Handler handler = new Handler();

    public ShouyeMoviePopupWindow(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_shouyemovie, null);
        popupWindow2 = new PopupWindow(view,
                GridView.MarginLayoutParams.WRAP_CONTENT, GridView.MarginLayoutParams.WRAP_CONTENT);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setTouchable(true);


    }

    public void show(Activity activity){
//        int screenHeight = Utils.getScreenHeight(context) + Utils.getStateBarHeight(context) - Utils.dip2px(context , 20);
////        int[] location = new int[2];
////        v.getLocationOnScreen(location);
//        //在控件上方显示    向上移动y轴是负数
//        int width = Utils.getScreenWidth(context) / 10 * 3 - popupWindow2.getContentView().getMeasuredWidth() / 2;
//        int height = screenHeight - popupWindow2.getContentView().getMeasuredHeight() - Utils.dip2px(context , 50);

        int screenHeight = Utils.dip2px(context , 50) + Utils.getNavigationBarHeightIfRoom(context);
//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
        //在控件上方显示    向上移动y轴是负数
        int width = Utils.getScreenWidth(context) / 10 * 3 - popupWindow2.getContentView().getMeasuredWidth() / 2;
        int height = screenHeight;
        popupWindow2.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM| Gravity.LEFT  , width , height);

        delayDismiss();

    }

    private void delayDismiss(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        if(timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow2.dismiss();
                    }
                });

            }
        };

        timer.schedule(timerTask , 3000);


    }

    Timer timer;
    TimerTask timerTask;

}

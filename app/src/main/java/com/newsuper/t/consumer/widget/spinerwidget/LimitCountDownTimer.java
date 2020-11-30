package com.newsuper.t.consumer.widget.spinerwidget;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class LimitCountDownTimer extends CountDownTimer{
    /**
     * @param millisInFuture
     *      表示以「 毫秒 」为单位倒计时的总数
     *      例如 millisInFuture = 1000 表示1秒
     *
     * @param countDownInterval
     *      表示 间隔 毫秒 调用一次 onTick()
     *      例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
     */
     private TextView textViewHour;
     private TextView textViewMiniute;
     private TextView textViewSecond;
     public LimitCountDownTimer(long millisInFuture, long countDownInterval, TextView textViewHour,TextView textViewMiniute,TextView textViewSecond) {
        super(millisInFuture, countDownInterval);
        this.textViewHour=textViewHour;
        this.textViewMiniute=textViewMiniute;
        this.textViewSecond=textViewSecond;
     }


     public void onFinish() {
         textViewHour.setText("00");
         textViewMiniute.setText("00");
         textViewSecond.setText("00");
     }

     public void onTick(long millisUntilFinished) {
         SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
         String showTime=timeFormat.format(new Date(millisUntilFinished));
         long seconds=millisUntilFinished/1000;
         textViewHour.setText(jugdeNum(seconds/3600));
         textViewMiniute.setText(jugdeNum(seconds%3600/60));
         textViewSecond.setText(jugdeNum(seconds%3600%60));
     }

     public String jugdeNum(long num){
         String numStr=String.valueOf(num);
         if(numStr.length()==1){
             numStr="0"+numStr;
         }
         return numStr;

     }

}


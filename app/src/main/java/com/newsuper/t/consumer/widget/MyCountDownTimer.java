package com.newsuper.t.consumer.widget;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class MyCountDownTimer extends CountDownTimer{
    /**
     * @param millisInFuture
     *      表示以「 毫秒 」为单位倒计时的总数
     *      例如 millisInFuture = 1000 表示1秒
     *
     * @param countDownInterval
     *      表示 间隔 多少微秒 调用一次 onTick()
     *      例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
     */
     private TextView mCountDownTextView;
     public MyCountDownTimer(long millisInFuture, long countDownInterval,TextView mCountDownTextView) {
        super(millisInFuture, countDownInterval);
        this.mCountDownTextView=mCountDownTextView;
     }


     public void onFinish() {
         mCountDownTextView.setText("0秒");
     }

     public void onTick(long millisUntilFinished) {
         SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
         Date countTime=new Date(millisUntilFinished);
         String showTime=timeFormat.format(countTime);
         Log.e("showTime.....", millisUntilFinished+"...."+showTime );
         String min="";
         if(showTime.split(":")[0].startsWith("0")){
             min=showTime.split(":")[0].substring(1);
         }else{
             min=showTime.split(":")[0];
         }
         String second="";
         if(showTime.split(":")[1].startsWith("0")){
             second=showTime.split(":")[1].substring(1);
         }else{
             second=showTime.split(":")[1];
         }
         if(Integer.parseInt(min)==0){
             mCountDownTextView.setText(second+"秒");
         }else{
             mCountDownTextView.setText(min+"分"+second+"秒");
         }

     }

}


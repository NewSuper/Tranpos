package com.qx.imlib.netty;

import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.event.HeartBeatEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class HeartBeatHolder {
    static class Holder {
        static final HeartBeatHolder instance = new HeartBeatHolder();
    }

    public static HeartBeatHolder getInstance(){
return Holder.instance;
    }
    public final long EXP_TIME= 60000;//超出指定时间无tcp通信则认定需要发送心跳包
    private long lastestTime;//记录tcp通信的最后一次时间
    private boolean isSending;
    static final int INTERVAL_CHECK = 1000;
    Timer timer;
    Timer replenishTimer;

    public void updateLatestTime(){
        lastestTime = System.currentTimeMillis();
    }
    public boolean isNeedSendHeatBeat(){
        long idleTime = System.currentTimeMillis() - lastestTime;
        boolean value = !isSending && idleTime > EXP_TIME;
        return value;
    }

    public boolean isSending() {
        return isSending;
    }

    public void setSending(boolean sending) {
        isSending = sending;
    }
    public void startHeartBeatService(){
//        Context context = GlobalContextManager.getInstance().getContext();
//        Intent intent = new Intent(context, HeartBeatService.class);
//        if (Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.O) {
//            context.startForegroundService(intent);
//        } else {
//            context.startService(intent);
//        }
//        context.startService(new Intent(context, HeartBeatService.class));

        if (timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    EventBus.getDefault().post(HeartBeatEvent.EVENT_CHECK_NORMALLY);
                }
            };
        }

    }
    public void stopHeartBeatService(){
//        Context context = GlobalContextManager.getInstance().getContext();
//        context.stopService(new Intent(context, HeartBeatService.class));
        if (timer!= null){
            timer.cancel();
            timer = null;
            QLog.i("HeartBeatHolder",  "取消心跳检测定时器");
        }
    }


    public synchronized void replenishPing(boolean isForeground) {
        if (isForeground) {
            startReplenishHeartBeat();
        } else {
            stopReplenishHeartBeat();
        }
    }

    public synchronized void startReplenishHeartBeat() {
        if (replenishTimer == null) {
            replenishTimer = new Timer();
            TimerTask task  = new TimerTask() {
                @Override
                public void run() {
                    QLog.i("HeartBeatHolder",  "发送补充心跳");
                    EventBus.getDefault().post(HeartBeatEvent.EVENT_CHECK_IMMEDIATELY);
                }
            };
            this.replenishTimer.schedule(task,2000L,15000L);
        }
    }

    public synchronized void stopReplenishHeartBeat() {
        if (replenishTimer != null) {
            QLog.i("HeartBeatHolder",  "结束补充心跳");
            replenishTimer.cancel();
            replenishTimer = null;
        }
    }











}

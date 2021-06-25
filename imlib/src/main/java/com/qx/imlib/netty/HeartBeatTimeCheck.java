package com.qx.imlib.netty;


import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.event.ReconnectEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 心跳回复定时监听
 */
public class HeartBeatTimeCheck {

    private static final String TAG ="HeartBeatTimeCheck";

    private volatile long sendTime;
    private final long EXP_TIME = 60000;
    static final int INTERVAL_CHECK = 1000;

    Timer timer;
    TimerTask task;

    public static HeartBeatTimeCheck getInstance() {
        return Holder.instance;
    }

    public synchronized void startTimer() {
        QLog.d(TAG, "启动心跳超时检测定时器");

        HeartBeatHolder.getInstance().setSending(true);
        updateSendTime();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(isTimeOut()) {
                    QLog.d(TAG, "心跳回复超时");
                    //发送重连请求
                    EventBus.getDefault().post(ReconnectEvent.EVENT_RECONNECT);
                    cancelTimer();
                }
            }
        };
        timer.schedule(task,0, INTERVAL_CHECK);
    }

    public void updateSendTime() {
        sendTime = System.currentTimeMillis();
    }

    public synchronized void cancelTimer() {
        if(timer!=null) {
            HeartBeatHolder.getInstance().setSending(false);
            task.cancel();
            timer.cancel();
            timer = null;
            task = null;
            QLog.d(TAG, "关闭心跳超时定时器");
        }
    }

    /**
     * 服务器回复心跳包是否超时
     * @return
     */
    private boolean isTimeOut() {

        return System.currentTimeMillis() - sendTime > EXP_TIME;
    }

    static class Holder {
        static final HeartBeatTimeCheck instance = new HeartBeatTimeCheck();
    }
}

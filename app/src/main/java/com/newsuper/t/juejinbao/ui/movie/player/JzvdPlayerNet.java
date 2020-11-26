package com.newsuper.t.juejinbao.ui.movie.player;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZMediaSystem;

/**
 * 播放器-用于监听播放卡顿时的转圈动画
 */
public class JzvdPlayerNet extends JzvdPlayerTouch {

    private Handler mHandler = new Handler();

    //播放进度
    private int current;
    private Timer loadMonitorTimer;
    private TimerTask loadMonitorTimerTask;


    public JzvdPlayerNet(Context context) {
        super(context);
    }

    public JzvdPlayerNet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        setLoadMonitor();
        Glide.with(this).load(R.drawable.bainian).into(iv_loading);

    }

    //更改网络状态
    public void changeNetState(String stateStr) {
        tv_net.setText(stateStr);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoadMonitor();

    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        ll_loading.setVisibility(View.VISIBLE);
        tv_network_speed.setText("");
        setLoadMonitor();

        pb_loading.setVisibility(View.GONE);
        iv_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        ll_loading.setVisibility(View.GONE);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        ll_loading.setVisibility(View.GONE);

        pb_loading.setVisibility(View.VISIBLE);
        iv_loading.setVisibility(View.GONE);
    }

    @Override
    public void gotoScreenNormal() {
        super.gotoScreenNormal();
        setLoadMonitor();
    }

    @Override
    public void gotoScreenFullscreen() {
        super.gotoScreenFullscreen();
        setLoadMonitor();
    }

    @Override
    protected void clickPlay() {
        if (state == STATE_PLAYING) {
            stopLoadMonitor();
            ll_loading.setVisibility(View.GONE);
        } else if (state == STATE_PAUSE) {
            setLoadMonitor();
        }
        super.clickPlay();

    }

    //loading监听循环计数
    private int loadingCount = 0;
    //监听间隔（必须能被1000整除）
    private static final int INTERVAL = 200;

    private void setLoadMonitor() {
        stopLoadMonitor();

        loadMonitorTimer = new Timer();
        loadMonitorTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mediaInterface == null || mHandler == null) {
                    return;
                }

                int currentPosition = 0;
                if(mediaInterface instanceof JZMediaSystem) {
                    if (mediaInterface == null || ((JZMediaSystem) mediaInterface).mediaPlayer == null || mHandler == null) {
                        return;
                    }

                    currentPosition = (int) ((JZMediaSystem) mediaInterface).mediaPlayer.getCurrentPosition();


                }

//                if (mediaInterface == null || ((JZMediaExo) mediaInterface).simpleExoPlayer == null || mHandler == null) {
//                    return;
//                }
//
//                int currentPosition = (int) ((JZMediaExo) mediaInterface).simpleExoPlayer.getCurrentPosition();

                if (current == currentPosition) {
                    if (state == STATE_PLAYING || state == STATE_PREPARED || state == STATE_PREPARING) {

                        //更新网速
                        if (loadingCount % (1000 / INTERVAL) == 0) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ll_loading.setVisibility(View.VISIBLE);
                                    tv_network_speed.setText(getNetworkSpeed(System.currentTimeMillis()));
                                }
                            });
                        }else{
                            if (ll_loading.getVisibility() != View.VISIBLE) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ll_loading.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }




                    }
                } else {
                    if (ll_loading.getVisibility() != View.GONE) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ll_loading.setVisibility(View.GONE);
                            }
                        });
                    }
                }
                current = currentPosition;
                loadingCount++;
            }
        };
        //200间隔
        loadMonitorTimer.schedule(loadMonitorTimerTask, 0, INTERVAL);
    }

    protected void stopLoadMonitor() {
        if (loadMonitorTimer != null) {
            loadMonitorTimer.cancel();
            loadMonitorTimer = null;
        }
        if (loadMonitorTimerTask != null) {
            loadMonitorTimerTask.cancel();
            loadMonitorTimerTask = null;
        }
    }

    public void destory() {
        stopLoadMonitor();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    //网速检测
    private long rxtxTotal =0;
    //上一次测速时间
    private long lastNetSpeedTime = 0;
    private String getNetworkSpeed(long time) {

        long tempSum = TrafficStats.getTotalRxBytes()

                + TrafficStats.getTotalTxBytes();

        long rxtxLast = tempSum - rxtxTotal;

        double totalSpeed= rxtxLast *1000 /2000d;
        rxtxTotal = tempSum;


        //不计入网速
        if(time - lastNetSpeedTime > 5000){
            lastNetSpeedTime = time;
            return "0KB/s";
        }else{
            lastNetSpeedTime = time;

            double kb = totalSpeed / 1024d;
            if(kb > 1024){
                return getDecimalFormat().format(kb / 1024d) + "MB/s";
            }else{
                return getDecimalFormat().format(kb) + "KB/s";
            }
        }

    }

    DecimalFormat df;
    private DecimalFormat getDecimalFormat(){
        if(df == null){
            df = new DecimalFormat();
            String style = "0.0";
            df.applyPattern(style);
        }
        return df;
    }
}

package com.newsuper.t.juejinbao.ui.movie.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.utils.ClickUtil;
import com.juejinchain.android.utils.MyToast;
import com.ys.network.base.BaseApplication;
import com.ys.network.base.PagerCons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZConstants;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static io.paperdb.Paper.book;


/**
 * 能够视频横竖屏UI的播放器界面
 */
public class JzvdPlayer extends Jzvd {
    //当前电量百分比
    private int battery_now = 70;
    //当前是否全屏
    public boolean isFull = false;



    //中间播放按钮
    private ImageButton ib_play;
    //横屏底部布局
    private LinearLayout ll_bottom;
    //小播放按钮
    private ImageButton ib_start;
    //全屏按钮
    protected ImageButton iv_full;
    //总时长
    private TextView tv_time_total;
    //当前时长
    private TextView tv_time_now;
    //进度条
    private SeekBar sb;
    //全屏顶部布局
    private LinearLayout ll_top;
    //返回
    private ImageButton ib_back;
    //标题
    protected TextView tv_title;
    //引导蒙层
    protected RelativeLayout rl_guide;

    //中间播放按钮
    private ImageButton ib_play2;
    //竖屏底部布局
    private LinearLayout ll_bottom2;
    private ImageButton ib_start2;
    private ImageButton iv_full2;
    private TextView tv_time_total2;
    private TextView tv_time_now2;
    private SeekBar sb2;
    private LinearLayout ll_top2;
    protected ImageButton ib_back2;
    protected TextView tv_title2;

    //当前时间
    private TextView tv_time;
    //当前时间
    private ImageView iv_battery;
    //加载框
    protected LinearLayout ll_loading;
    //网络状态
    protected TextView tv_net;
    //网速
    protected TextView tv_network_speed;
    //锁屏按钮
    private ImageButton ib_lock;
    //右边操作栏
    private RelativeLayout rl_operate;
    //播放失败按钮
    protected RelativeLayout rl_playerror;
    protected TextView tv_playerror;

    //复制视频链接
    protected RelativeLayout rl_copylink;


    //加载图案
    protected ProgressBar pb_loading;
    protected ImageView iv_loading;

    protected RelativeLayout rl_top;
    protected RelativeLayout rl_playershare;

    protected View v_xiaomi;

    int lastSeekBar = 0;

    protected DismissControlViewTimerTask mDismissControlViewTimerTask;

    public JzvdPlayer(Context context) {
        super(context);
    }

    public JzvdPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_jz_player;
    }


    @Override
    public void init(Context context) {
        super.init(context);

        ib_play = findViewById(R.id.ib_play);
        ll_bottom = findViewById(R.id.ll_bottom);
        sb = findViewById(R.id.sb);
        ib_start = findViewById(R.id.ib_start);
        iv_full = findViewById(R.id.iv_full);
        tv_time_total = findViewById(R.id.tv_time_total);
        tv_time_now = findViewById(R.id.tv_time_now);
        ll_top = findViewById(R.id.ll_top);
        ib_back = findViewById(R.id.ib_back);
        tv_title = findViewById(R.id.tv_title);

        ib_play2 = findViewById(R.id.ib_play2);
        ll_bottom2 = findViewById(R.id.ll_bottom2);
        sb2= findViewById(R.id.sb2);
        ib_start2 = findViewById(R.id.ib_start2);
        iv_full2 = findViewById(R.id.iv_full2);
        tv_time_total2 = findViewById(R.id.tv_time_total2);
        tv_time_now2 = findViewById(R.id.tv_time_now2);
        ll_top2 = findViewById(R.id.ll_top2);
        ib_back2 = findViewById(R.id.ib_back2);
        tv_title2 = findViewById(R.id.tv_title2);

        tv_time = findViewById(R.id.tv_time);
        iv_battery = findViewById(R.id.iv_battery);
        ll_loading = findViewById(R.id.ll_loading);
        tv_net = findViewById(R.id.tv_net);
        tv_network_speed = findViewById(R.id.tv_network_speed);
        ib_lock = findViewById(R.id.ib_lock);
        rl_operate = findViewById(R.id.rl_operate);
        rl_guide = findViewById(R.id.rl_guide);
        rl_playerror = findViewById(R.id.rl_playerror);
        tv_playerror = findViewById(R.id.tv_playerror);

        pb_loading = findViewById(R.id.pb_loading);
        iv_loading = findViewById(R.id.iv_loading);

        rl_top = findViewById(R.id.rl_top);
        rl_playershare = findViewById(R.id.rl_playershare);
        rl_copylink = findViewById(R.id.rl_copylink);

        v_xiaomi = findViewById(R.id.v_xiaomi);

        ib_play.setOnClickListener(this);
        ib_start.setOnClickListener(this);
        iv_full.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        ib_lock.setOnClickListener(this);

        ib_play2.setOnClickListener(this);
        ib_start2.setOnClickListener(this);
        iv_full2.setOnClickListener(this);
        ib_back2.setOnClickListener(this);



        if(BaseApplication.getChannel().equals(JZConstants.CHANNEL_XIAOMI)) {
            JzvdStd.setViewMargin(ib_back, 150, 0, 0, 0);
            JzvdStd.setViewMargin(ib_back2, 150, 0, 0, 0);
        }

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //设置这个progres对应的时间，给textview
                    long duration = getDuration();
                    currentTimeTextView.setText(JZUtils.stringForTime(progress * duration / 100));

                    //显示进度弹窗
                    long totalTimeDuration = getDuration();
                    mSeekTimePosition = (long) (progress / 100f * totalTimeDuration);
                    if (mSeekTimePosition > totalTimeDuration)
                        mSeekTimePosition = totalTimeDuration;
                    String seekTime = JZUtils.stringForTime(mSeekTimePosition);
                    tv_time_now.setText(seekTime);


                    String totalTime = JZUtils.stringForTime(totalTimeDuration);
                    if(progress >lastSeekBar) {
                        showProgressDialog(10000, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                    }else{
                        showProgressDialog(-10000, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);

                    }


                }
                lastSeekBar = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showOperateUI();

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long time = seekBar.getProgress() * getDuration() / 100;
                seekToManulPosition = seekBar.getProgress();
                mediaInterface.seekTo(time);
                sb2.setProgress(seekBar.getProgress());


                dismissProgressDialog();
            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    //显示进度弹窗
                    long totalTimeDuration = getDuration();
                    mSeekTimePosition = (long) (progress / 100f * totalTimeDuration);
                    if (mSeekTimePosition > totalTimeDuration)
                        mSeekTimePosition = totalTimeDuration;
                    String seekTime = JZUtils.stringForTime(mSeekTimePosition);
                    tv_time_now2.setText(seekTime);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long time = seekBar.getProgress() * getDuration() / 100;
                seekToManulPosition = seekBar.getProgress();
                mediaInterface.seekTo(time);
                sb.setProgress(seekBar.getProgress());
            }
        });

        rl_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_guide.setVisibility(View.GONE);
            }
        });
    }

    //根据当前交互操作通知子类显示或隐藏
    protected void showUI(boolean show){

    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        //数据初始化
        sb.setSecondaryProgress(0);
        sb2.setSecondaryProgress(0);
        tv_time_now.setText("00:00");
        tv_time_now2.setText("00:00");
        tv_time_total.setText("00:00");
        tv_time_total2.setText("00:00");
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();;

        ib_play.setBackgroundResource(cn.jzvd.R.drawable.jz_pause_normal);
        ib_play2.setBackgroundResource(cn.jzvd.R.drawable.jz_pause_normal);
        ib_start.setBackgroundResource(R.mipmap.player_zanting);
        ib_start2.setBackgroundResource(R.mipmap.player_zanting);
        showOperateUI();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        ib_play.setBackgroundResource(cn.jzvd.R.drawable.jz_play_normal);
        ib_play2.setBackgroundResource(cn.jzvd.R.drawable.jz_play_normal);
        ib_start.setBackgroundResource(R.mipmap.player_bofang);
        ib_start2.setBackgroundResource(R.mipmap.player_bofang);
        showOperateUI();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    protected void dragProgress(int progress) {
        super.dragProgress(progress);
        sb.setProgress(progress);
        sb2.setProgress(progress);

    }

    @Override
    public void onProgress(int progress, long position, long duration) {
        super.onProgress(progress, position, duration);
        sb.setProgress(progress);
        sb2.setProgress(progress);
        if (position != 0) {
            tv_time_now.setText(JZUtils.stringForTime(position));
            tv_time_now2.setText(JZUtils.stringForTime(position));
        }
        tv_time_total.setText(JZUtils.stringForTime(duration));
        tv_time_total2.setText(JZUtils.stringForTime(duration));
    }

    @Override
    public void setBufferProgress(int bufferProgress) {
        if (bufferProgress != 0){
            sb.setSecondaryProgress(bufferProgress);
            sb2.setSecondaryProgress(bufferProgress);
        }
    }

    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        DISMISS_CONTROL_VIEW_TIMER = new Timer();
        mDismissControlViewTimerTask = new DismissControlViewTimerTask();
        DISMISS_CONTROL_VIEW_TIMER.schedule(mDismissControlViewTimerTask, 3000);
    }

    public void cancelDismissControlViewTimer() {
        if (DISMISS_CONTROL_VIEW_TIMER != null) {
            DISMISS_CONTROL_VIEW_TIMER.cancel();
        }
        if (mDismissControlViewTimerTask != null) {
            mDismissControlViewTimerTask.cancel();
        }

    }

    public class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
            post(() -> {
                hideOperateUI();
            });
        }
    }

    protected void hideOperateUI() {

        if(isFull) {
            ib_play.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
            ll_top.setVisibility(View.GONE);
            ib_lock.setVisibility(View.GONE);
            rl_operate.setVisibility(View.GONE);
            showUI(false);
        }else{
            ib_play2.setVisibility(View.GONE);
            ll_bottom2.setVisibility(View.GONE);
            ll_top2.setVisibility(View.GONE);
        }

    }

    protected void showOperateUI() {
        startDismissControlViewTimer();
        setSystemTimeAndBattery();
        if(isFull) {

            if(isLock){

            }else {
                ib_play.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.VISIBLE);
                ll_top.setVisibility(View.VISIBLE);
                rl_operate.setVisibility(View.VISIBLE);
                showUI(true);
            }
            ib_lock.setVisibility(View.VISIBLE);
        }else {
            ib_play2.setVisibility(View.VISIBLE);
            ll_bottom2.setVisibility(View.VISIBLE);
            ll_top2.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if(state == STATE_PLAYING || state == STATE_PAUSE || state == STATE_ERROR || state == STATE_AUTO_COMPLETE) {

                        if(isFull){
                            if(ib_lock.getVisibility() == View.VISIBLE){
                                cancelDismissControlViewTimer();
                                hideOperateUI();
                            }else{
                                showOperateUI();
                            }
                        }else{
                            if(ib_play2.getVisibility() == View.VISIBLE){
                                cancelDismissControlViewTimer();
                                hideOperateUI();
                            }else{
                                showOperateUI();
                            }
                        }


                    }
                    break;
            }
        } else if (id == R.id.sb) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                    break;
                case MotionEvent.ACTION_UP:
                    showOperateUI();
                    break;
            }
        }
        return super.onTouch(v, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        //点击全屏
        if (id == R.id.iv_full || id == R.id.iv_full2) {
            showOperateUI();
            clickFull();
        }
        //点击播放
        else if (id == R.id.ib_start || id == R.id.ib_start2 || id == R.id.ib_play || id == R.id.ib_play2) {
            showOperateUI();
            clickPlay();
        }
        //点击返回
        else if (id == R.id.ib_back || id == R.id.ib_back2) {
            showOperateUI();
            if(isFull){
                clickFull();
            }else{

            }
        }
        //点击锁屏按钮
        else if(id == R.id.ib_lock){
            if(isLock){
                isLock = false;
                ib_lock.setBackgroundResource(R.drawable.player_unlock);
                //解锁
                showOperateUI();
                //方向不锁定
                JZUtils.setRequestedOrientation(getContext(), FULLSCREEN_ORIENTATION);
            }else{
                isLock = true;
                ib_lock.setBackgroundResource(R.drawable.player_lock);
                //锁屏，隐藏界面
                hideOperateUI();
                //方向锁定
                JZUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_LOCKED );
            }
        }


    }

    @Override
    public void gotoScreenNormal() {
        super.gotoScreenNormal();
        setOrientationUI(false);
    }

    @Override
    public void gotoScreenFullscreen() {
        super.gotoScreenFullscreen();
        setOrientationUI(true);

        boolean noFirstInto = book().read(PagerCons.KEY_MOVIEPLAY_NOFIRST, false);
        if (!noFirstInto) {
            book().write(PagerCons.KEY_MOVIEPLAY_NOFIRST, true);
            rl_guide.setVisibility(View.VISIBLE);
        }
    }

    //设置横竖屏UI
    private void setOrientationUI(boolean isFull){
        this.isFull = isFull;
        if(isFull){
            ib_play.setVisibility(View.VISIBLE);
            ib_play2.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.VISIBLE);
            ll_bottom2.setVisibility(View.GONE);
            ll_top.setVisibility(View.VISIBLE);
            ll_top2.setVisibility(View.GONE);

            ib_lock.setVisibility(View.VISIBLE);
            rl_operate.setVisibility(View.VISIBLE);

            rl_top.setVisibility(View.GONE);
            showUI(true);

        }else{
            ib_play.setVisibility(View.GONE);
            ib_play2.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.GONE);
            ll_bottom2.setVisibility(View.VISIBLE);
            ll_top.setVisibility(View.GONE);
            ll_top2.setVisibility(View.VISIBLE);
            rl_operate.setVisibility(View.GONE);
            showUI(false);
            ib_lock.setVisibility(View.GONE);
            rl_top.setVisibility(View.VISIBLE);
        }
    }

    //电池广播
    private BroadcastReceiver battertReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int percent = level * 100 / scale;
                battery_now = percent;
                setBatteryLevel();
                try {
                    getContext().unregisterReceiver(battertReceiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //刷新时间显示和电量
    public void setSystemTimeAndBattery() {
        tv_time.setVisibility(View.VISIBLE);
        iv_battery.setVisibility(View.VISIBLE);

        SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        tv_time.setText(dateFormater.format(date));
        if (ClickUtil.isNotFastClick(30000)) {
            getContext().registerReceiver(
                    battertReceiver,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            );
        } else {
            setBatteryLevel();
        }
    }

    //刷新当前电量
    public void setBatteryLevel() {
        int percent = battery_now;
        if (percent < 15) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_10);
        } else if (percent >= 15 && percent < 40) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_30);
        } else if (percent >= 40 && percent < 60) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_50);
        } else if (percent >= 60 && percent < 80) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_70);
        } else if (percent >= 80 && percent < 95) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_90);
        } else if (percent >= 95 && percent <= 100) {
            iv_battery.setBackgroundResource(cn.jzvd.R.drawable.jz_battery_level_100);
        }
    }





}

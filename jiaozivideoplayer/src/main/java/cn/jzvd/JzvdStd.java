package cn.jzvd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nathen
 * On 2016/04/18 16:15
 */
public class JzvdStd extends Jzvd {

    public static long LAST_GET_BATTERYLEVEL_TIME = 0;
    public static int LAST_GET_BATTERYLEVEL_PERCENT = 70;


    public ImageView backButton;
    public ProgressBar bottomProgressBar, loadingProgressBar;
    public TextView titleTextView;
    public ImageView thumbImageView;
    public ImageView tinyBackImageView;
    public LinearLayout batteryTimeLayout;
    public ImageView batteryLevel;
    public TextView videoCurrentTime;
    public TextView replayTextView;
//    public TextView clarity;
    public PopupWindow clarityPopWindow;
    public TextView mRetryBtn;
    public LinearLayout mRetryLayout;
    protected DismissControlViewTimerTask mDismissControlViewTimerTask;
    protected Dialog mProgressDialog;
    protected ProgressBar mDialogProgressBar;
    protected TextView mDialogSeekTime;
    protected TextView mDialogTotalTime;
    protected ImageView mDialogIcon;
    protected Dialog mVolumeDialog;
    protected ProgressBar mDialogVolumeProgressBar;
    protected TextView mDialogVolumeTextView;
    protected ImageView mDialogVolumeImageView;
    protected Dialog mBrightnessDialog;
    protected ProgressBar mDialogBrightnessProgressBar;
    protected TextView mDialogBrightnessTextView;

    protected RelativeLayout rl_bofang;
    private ImageView iv_bofang;

    protected LinearLayout ll_progress;
    protected TextView tv_progress;
    private RelativeLayout layout_top;


    protected LinearLayout ll_loading;
//    protected TextView tv_cs;

    //网速加载UI
    protected LinearLayout ll_loading_net;
    protected TextView tv_netkb;

    //引导蒙层
    protected RelativeLayout rl_guide;
    //新app功能
//    protected LinearLayout ll_app;
    //视频下载地址
//    private String title = null;
//    private String videoDownloadUrl = null;
//    //app下载地址
//    private String appDownloadUrl = null;
    private TextView tv_videoLength;
    private boolean hasDuration = false;


    public JzvdStd(Context context) {
        super(context);
    }

    public JzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        batteryTimeLayout = findViewById(R.id.battery_time_layout);
        bottomProgressBar = findViewById(R.id.bottom_progress);
        titleTextView = findViewById(R.id.title);
        backButton = findViewById(R.id.back);
        thumbImageView = findViewById(R.id.thumb);
        loadingProgressBar = findViewById(R.id.loading);
        tinyBackImageView = findViewById(R.id.back_tiny);
        batteryLevel = findViewById(R.id.battery_level);
        videoCurrentTime = findViewById(R.id.video_current_time);
        replayTextView = findViewById(R.id.replay_text);
//        clarity = findViewById(R.id.clarity);
        mRetryBtn = findViewById(R.id.retry_btn);
        mRetryLayout = findViewById(R.id.retry_layout);
        ll_progress = findViewById(R.id.ll_progress);
        tv_progress = findViewById(R.id.tv_progress);
        rl_guide = findViewById(R.id.rl_guide);
//        ll_app = findViewById(R.id.ll_app);
        tv_videoLength = findViewById(R.id.tv_videoLength);
        layout_top = findViewById(R.id.layout_top);
        ll_loading = findViewById(R.id.ll_loading);

        rl_bofang = findViewById(cn.jzvd.R.id.rl_bofang);
        iv_bofang = findViewById(cn.jzvd.R.id.iv_bofang);
//        tv_cs = findViewById(R.id.tv_cs);
        tv_netkb = findViewById(cn.jzvd.R.id.tv_netkb);
        ll_loading_net = findViewById(cn.jzvd.R.id.ll_loading_net);

        thumbImageView.setOnClickListener(this);
        backButton.setOnClickListener(this);
        tinyBackImageView.setOnClickListener(this);
//        clarity.setOnClickListener(this);
        mRetryBtn.setOnClickListener(this);
//        layout_top.setOnClickListener(this);
        titleTextView.setOnClickListener(this);
        backButton.setOnClickListener(this);

        rl_bofang.setOnClickListener(this);

        rl_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_guide.setVisibility(View.GONE);
            }
        });

        if(getChannel(context).equals(JZConstants.CHANNEL_XIAOMI)) {
            setViewMargin(backButton, 150, 0, 0, 0);
        }
    }


    public void setUp(JZDataSource jzDataSource, int screen, Class mediaInterfaceClass) {
        super.setUp(jzDataSource, screen, mediaInterfaceClass);
        if(isTextSizeLevel) {
            setTextSize(titleTextView);
        }
        titleTextView.setText(jzDataSource.title);
        setScreen(screen);
    }

    public String getMSTime(long time) {
        String newTime = null;
        String m = (time / 60 < 10) ? ("0" + time / 60) : (time / 60 + "");
        String s = (time % 60 < 10) ? ("0" + time % 60) : (time % 60 + "");
        newTime = m + ":" + s;
        return newTime;
    }

    public void changeStartButtonSize(int size) {
        ViewGroup.LayoutParams lp = startButton.getLayoutParams();
        lp.height = size;
        lp.width = size;
        lp = loadingProgressBar.getLayoutParams();
        lp.height = size;
        lp.width = size;
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_std;
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        changeUiToNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        changeUiToPreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        changeUiToPlayingClear();
        if(stateListener != null){
            stateListener.startPlay();
        }

        if(commenStateListener != null){
            commenStateListener.startPlay(0);
        }

        if(liveStateListener != null){
            liveStateListener.startPlay();
        }

        if(hasDuration) {
            tv_videoLength.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        changeUiToPauseShow();
        cancelDismissControlViewTimer();

        if(commenStateListener != null){
            commenStateListener.pause();
        }

        if(hasDuration) {
            tv_videoLength.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStateError() {
        super.onStateError();
        changeUiToError();
        if(stateListener != null){
            stateListener.error();
        }

        if(commenStateListener != null){
            commenStateListener.error();
        }
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        changeUiToComplete();
        cancelDismissControlViewTimer();
        bottomProgressBar.setProgress(100);
        if(stateListener != null){
            stateListener.endPlay();
        }

        if(commenStateListener != null){
            commenStateListener.endPlay();
        }


    }

    @Override
    public void changeUrl(int urlMapIndex, long seekToInAdvance) {
        super.changeUrl(urlMapIndex, seekToInAdvance);
        startButton.setVisibility(INVISIBLE);
        replayTextView.setVisibility(View.GONE);
        mRetryLayout.setVisibility(View.GONE);
    }

    private boolean isTextSizeLevel = false;
    private String textSizeLevel = "middle";
    public void setIsTextSizeLevel(boolean isTextSizeLevel,String textSizeLevel) {
        this.isTextSizeLevel = isTextSizeLevel;
        this.textSizeLevel = textSizeLevel;
    }

    private void setTextSize(TextView holder) {
        if(holder != null) {
            switch (textSizeLevel) {
                case "small":
                    holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.content_small));
                    break;
                case "middle":
                    holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.content_middle));
                    break;
                case "big":
                    holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.content_big));
                    break;
                case "large":
                    holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.content_lager));
                    break;
            }
        }
    }

    @Override
    public void changeUrl(JZDataSource jzDataSource, long seekToInAdvance) {
        super.changeUrl(jzDataSource, seekToInAdvance);
        if(isTextSizeLevel) {
            setTextSize(titleTextView);
        }
        titleTextView.setText(jzDataSource.title);
        startButton.setVisibility(INVISIBLE);
        replayTextView.setVisibility(View.GONE);
        mRetryLayout.setVisibility(View.GONE);
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
//                    startDismissControlViewTimer();
//                    if (mChangePosition) {
//                        long duration = getDuration();
//                        int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
//                        bottomProgressBar.setProgress(progress);
//                    }
//                    if (!mChangePosition && !mChangeVolume) {
//                        onClickUiToggle();
//                    }
                    showUI();
                    break;
            }
        } else if (id == R.id.bottom_seek_progress) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    break;
            }
        }
        return super.onTouch(v, event);
    }


    //显示UI
    public void showUI(){
        startDismissControlViewTimer();
        if (mChangePosition) {
            long duration = getDuration();
            int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
            bottomProgressBar.setProgress(progress);
        }
        if (!mChangePosition && !mChangeVolume) {
            onClickUiToggle();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(isTextSizeLevel) {
            setTextSize(titleTextView);
        }
        int i = v.getId();
        if (i == R.id.thumb) {
            if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
//                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }

            if (state == STATE_NORMAL) {
                if (!jzDataSource.getCurrentUrl().toString().startsWith("file") &&
                        !jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                        !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    showWifiDialog();
                    return;
                }
                startVideo();
            } else if (state == STATE_AUTO_COMPLETE) {
                onClickUiToggle();
            }
        } else if (i == R.id.surface_container) {
            startDismissControlViewTimer();
        }
        else if(i == R.id.back || i == R.id.title ){
            if(finishListener != null){
                finishListener.finish();
            }

            if(screen == SCREEN_NORMAL){
                if(finishListener != null){
                    finishListener.finish();
                }
            }
            backPress();
        }
        else if (i == R.id.back) {
            if(finishListener != null){
                finishListener.finish();
            }

            if(screen == SCREEN_NORMAL){
                if(finishListener != null){
                    finishListener.finish();
                }
            }
            backPress();
        }
        //点击底部播放按钮
        else if(i == R.id.rl_bofang){
            playState();
        }
        else if (i == R.id.back_tiny) {
            clearFloatScreen();
        }
        //清晰度
//        else if (i == R.id.clarity) {
//            LayoutInflater inflater = (LayoutInflater) getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.jz_layout_clarity, null);
//
//            OnClickListener mQualityListener = v1 -> {
//                int index = (int) v1.getTag();
//                changeUrl(index, getCurrentPositionWhenPlaying());
//                clarity.setText(jzDataSource.getCurrentKey().toString());
//                for (int j = 0; j < layout.getChildCount(); j++) {//设置点击之后的颜色
//                    if (j == jzDataSource.currentUrlIndex) {
//                        ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#fff85959"));
//                    } else {
//                        ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#ffffff"));
//                    }
//                }
//                if (clarityPopWindow != null) {
//                    clarityPopWindow.dismiss();
//                }
//            };
//
//            for (int j = 0; j < jzDataSource.urlsMap.size(); j++) {
//                String key = jzDataSource.getKeyFromDataSource(j);
//                TextView clarityItem = (TextView) View.inflate(getContext(), R.layout.jz_layout_clarity_item, null);
//                clarityItem.setText(key);
//                clarityItem.setTag(j);
//                layout.addView(clarityItem, j);
//                clarityItem.setOnClickListener(mQualityListener);
//                if (j == jzDataSource.currentUrlIndex) {
//                    clarityItem.setTextColor(Color.parseColor("#fff85959"));
//                }
//            }
//
//            clarityPopWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
//            clarityPopWindow.setContentView(layout);
//            clarityPopWindow.showAsDropDown(clarity);
//            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            int offsetX = clarity.getMeasuredWidth() / 3;
//            int offsetY = clarity.getMeasuredHeight() / 3;
//            clarityPopWindow.update(clarity, -offsetX, -offsetY, Math.round(layout.getMeasuredWidth() * 2), layout.getMeasuredHeight());
//        }
        else if (i == R.id.retry_btn) {
            if (jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
//                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                    jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                showWifiDialog();
                return;
            }
            addTextureView();
            onStatePreparing();
        }
    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();
        fullscreenButton.setImageResource(R.drawable.jz_enlarge);
        //正常播放
        if(isLocalVideo){
            backButton.setVisibility(View.VISIBLE);
            tv_mtop.setVisibility(View.VISIBLE);
        }
        //直播播放
        else if(isLiveVideo){
            backButton.setVisibility(View.VISIBLE);
            tv_mtop.setVisibility(View.GONE);
            replayTextView.setVisibility(View.GONE);
        }
        //视频列表播放模式
        else{
            backButton.setVisibility(View.GONE);
            tv_mtop.setVisibility(View.GONE);
        }
        tinyBackImageView.setVisibility(View.INVISIBLE);
        changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_normal));
        batteryTimeLayout.setVisibility(View.GONE);
//        clarity.setVisibility(View.GONE);

//        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(videoDownloadUrl)) {
//            ll_app.setVisibility(View.VISIBLE);
//        }
        rl_lock.setVisibility(View.GONE);

//        AdaptiveXiaomi(false);
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        //进入全屏之后要保证原来的播放状态和ui状态不变，改变个别的ui
        fullscreenButton.setImageResource(R.drawable.jz_shrink);
        backButton.setVisibility(View.VISIBLE);
        tv_mtop.setVisibility(View.GONE);
        tinyBackImageView.setVisibility(View.INVISIBLE);
        batteryTimeLayout.setVisibility(View.VISIBLE);
//        if (jzDataSource.urlsMap.size() == 1) {
//            clarity.setVisibility(GONE);
//        } else {
//            clarity.setText(jzDataSource.getCurrentKey().toString());
//            clarity.setVisibility(View.VISIBLE);
//        }

//        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(videoDownloadUrl)) {
//            ll_app.setVisibility(View.VISIBLE);
//        }

        changeStartButtonSize((int) getResources().getDimension(R.dimen.jz_start_button_w_h_fullscreen));
        setSystemTimeAndBattery();

//        AdaptiveXiaomi(true);

    }


    public void AdaptiveXiaomi(boolean isShow){

        if(isShow){
            //显示
            findViewById(R.id.v_xiaomi).setVisibility(VISIBLE);
        }else {
            //隐藏
            findViewById(R.id.v_xiaomi).setVisibility(GONE);
        }
    }

    @Override
    public void setScreenTiny() {
        super.setScreenTiny();
        tinyBackImageView.setVisibility(View.VISIBLE);
        setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
        batteryTimeLayout.setVisibility(View.GONE);
//        clarity.setVisibility(View.GONE);
    }

    @Override
    public void showWifiDialog() {
        super.showWifiDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.tips_not_wifi));
        builder.setPositiveButton(getResources().getString(R.string.tips_not_wifi_confirm), (dialog, which) -> {
            dialog.dismiss();
            startVideo();
            WIFI_TIP_DIALOG_SHOWED = true;
        });
        builder.setNegativeButton(getResources().getString(R.string.tips_not_wifi_cancel), (dialog, which) -> {
            dialog.dismiss();
            clearFloatScreen();
        });
        builder.setOnCancelListener(DialogInterface::dismiss);
        builder.create().show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        cancelDismissControlViewTimer();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        startDismissControlViewTimer();
    }

    public void onClickUiToggle() {//这是事件
        if (bottomContainer.getVisibility() != View.VISIBLE) {
            setSystemTimeAndBattery();
//            clarity.setText(jzDataSource.getCurrentKey().toString());
        }
        if (state == STATE_PREPARING) {
            changeUiToPreparing();
            if (bottomContainer.getVisibility() == View.VISIBLE) {
            } else {
                setSystemTimeAndBattery();
            }
        } else if (state == STATE_PLAYING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingClear();
            } else {
                changeUiToPlayingShow();
            }
        } else if (state == STATE_PAUSE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        }
    }

    public void setSystemTimeAndBattery() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        videoCurrentTime.setText(dateFormater.format(date));
        if ((System.currentTimeMillis() - LAST_GET_BATTERYLEVEL_TIME) > 30000) {
            LAST_GET_BATTERYLEVEL_TIME = System.currentTimeMillis();
            getContext().registerReceiver(
                    battertReceiver,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            );
        } else {
            setBatteryLevel();
        }
    }

    public void setBatteryLevel() {
        int percent = LAST_GET_BATTERYLEVEL_PERCENT;
        if (percent < 15) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_10);
        } else if (percent >= 15 && percent < 40) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_30);
        } else if (percent >= 40 && percent < 60) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_50);
        } else if (percent >= 60 && percent < 80) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_70);
        } else if (percent >= 80 && percent < 95) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_90);
        } else if (percent >= 95 && percent <= 100) {
            batteryLevel.setBackgroundResource(R.drawable.jz_battery_level_100);
        }
    }

    public void onCLickUiToggleToClear() {
        if (state == STATE_PREPARING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPreparing();
            } else {
            }
        } else if (state == STATE_PLAYING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingClear();
            } else {
            }
        } else if (state == STATE_PAUSE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPauseClear();
            } else {
            }
        } else if (state == STATE_AUTO_COMPLETE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToComplete();
            } else {
            }
        }
    }


    @Override
    public void onProgress(int progress, long position, long duration) {
        super.onProgress(progress, position, duration);
        if (progress != 0) {
            bottomProgressBar.setProgress(progress);
        }
        if(stateListener != null){
            stateListener.progress(progress);
        }
    }

    @Override
    public void setBufferProgress(int bufferProgress) {
        super.setBufferProgress(bufferProgress);
        if (bufferProgress != 0) bottomProgressBar.setSecondaryProgress(bufferProgress);
    }

    @Override
    public void resetProgressAndTime() {
        super.resetProgressAndTime();
        bottomProgressBar.setProgress(0);
        bottomProgressBar.setSecondaryProgress(0);
    }

    public void changeUiToNormal() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }
    }

    public void changeUiToPreparing() {
        switch (screen) {
            case SCREEN_NORMAL:
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void changeUiToPlayingShow() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void changeUiToPlayingClear() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void changeUiToPauseShow() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }
    }

    public void changeUiToPauseClear() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void changeUiToComplete() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void changeUiToError() {
        switch (screen) {
            case SCREEN_NORMAL:
                setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_FULLSCREEN:
                setAllControlsVisiblity(View.VISIBLE, View.INVISIBLE, View.VISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                updateStartImage();
                break;
            case SCREEN_TINY:
                break;
        }

    }

    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int thumbImg, int bottomPro, int retryLayout) {
        if(isLock){

        }else {
        topContainer.setVisibility(topCon);
        bottomContainer.setVisibility(bottomCon);

            //zhangye
            if (bottomCon == View.VISIBLE) {
                bottomProgressBar.setVisibility(View.INVISIBLE);
            }

            startButton.setVisibility(startBtn);
//            loadingProgressBar.setVisibility(loadingPro);
            ll_loading.setVisibility(loadingPro);
            thumbImageView.setVisibility(thumbImg);
            //   bottomProgressBar.setVisibility(bottomPro);
            mRetryLayout.setVisibility(retryLayout);
        }
        if(isMovie && screen == SCREEN_FULLSCREEN) {
            rl_lock.setVisibility(startBtn);
        }
    }

    public void updateStartImage() {
        if(isLock){
            if(isMovie && screen == SCREEN_FULLSCREEN) {
                rl_lock.setVisibility(View.VISIBLE);
            }
        }else {
            if (state == STATE_PLAYING) {
                startButton.setVisibility(VISIBLE);
                startButton.setImageResource(R.drawable.jz_click_pause_selector);
                replayTextView.setVisibility(GONE);
            } else if (state == STATE_ERROR) {
                startButton.setVisibility(INVISIBLE);
                replayTextView.setVisibility(GONE);
            } else if (state == STATE_AUTO_COMPLETE) {
                startButton.setVisibility(VISIBLE);
                startButton.setImageResource(R.drawable.jz_click_replay_selector);
                replayTextView.setVisibility(VISIBLE);
            } else {
                startButton.setImageResource(R.drawable.jz_click_play_selector);
                replayTextView.setVisibility(GONE);
            }

            if (state == STATE_PLAYING) {
                iv_bofang.setBackgroundResource(cn.jzvd.R.drawable.dianjizanting);
            } else if (state == STATE_ERROR) {
            } else if (state == STATE_AUTO_COMPLETE) {
                iv_bofang.setBackgroundResource(cn.jzvd.R.drawable.dianjibofang);
            } else {
                iv_bofang.setBackgroundResource(cn.jzvd.R.drawable.dianjibofang);
            }
        }
    }

    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (mProgressDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_progress, null);
            mDialogProgressBar = localView.findViewById(R.id.duration_progressbar);
            mDialogSeekTime = localView.findViewById(R.id.tv_current);
            mDialogTotalTime = localView.findViewById(R.id.tv_duration);
            mDialogIcon = localView.findViewById(R.id.duration_image_tip);
            mProgressDialog = createDialogWithView(localView);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        mDialogSeekTime.setText(seekTime);
        mDialogTotalTime.setText(" / " + totalTime);
        mDialogProgressBar.setProgress(totalTimeDuration <= 0 ? 0 : (int) (seekTimePosition * 100 / totalTimeDuration));
        if (deltaX > 0) {
            mDialogIcon.setBackgroundResource(R.drawable.jz_forward_icon);
        } else {
            mDialogIcon.setBackgroundResource(R.drawable.jz_backward_icon);
        }

        if(deltaX == 10000 || deltaX == -10000){

        }else{
            onCLickUiToggleToClear();
        }

    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_volume, null);
            mDialogVolumeImageView = localView.findViewById(R.id.volume_image_tip);
            mDialogVolumeTextView = localView.findViewById(R.id.tv_volume);
            mDialogVolumeProgressBar = localView.findViewById(R.id.volume_progressbar);
            mVolumeDialog = createDialogWithView(localView);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (volumePercent <= 0) {
            mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_close_volume);
        } else {
            mDialogVolumeImageView.setBackgroundResource(R.drawable.jz_add_volume);
        }
        if (volumePercent > 100) {
            volumePercent = 100;
        } else if (volumePercent < 0) {
            volumePercent = 0;
        }
        mDialogVolumeTextView.setText(volumePercent + "%");
        mDialogVolumeProgressBar.setProgress(volumePercent);
        onCLickUiToggleToClear();
    }

    @Override
    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
        }
    }

    @Override
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
        if (mBrightnessDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.jz_dialog_brightness, null);
            mDialogBrightnessTextView = localView.findViewById(R.id.tv_brightness);
            mDialogBrightnessProgressBar = localView.findViewById(R.id.brightness_progressbar);
            mBrightnessDialog = createDialogWithView(localView);
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (brightnessPercent > 100) {
            brightnessPercent = 100;
        } else if (brightnessPercent < 0) {
            brightnessPercent = 0;
        }
        mDialogBrightnessTextView.setText(brightnessPercent + "%");
        mDialogBrightnessProgressBar.setProgress(brightnessPercent);
        onCLickUiToggleToClear();
    }

    @Override
    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
        if (mBrightnessDialog != null) {
            mBrightnessDialog.dismiss();
        }
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(getContext(), R.style.jz_style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(Window.FEATURE_ACTION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.setLayout(-2, -2);
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(localLayoutParams);
        return dialog;
    }

    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        DISMISS_CONTROL_VIEW_TIMER = new Timer();
        mDismissControlViewTimerTask = new DismissControlViewTimerTask();
        DISMISS_CONTROL_VIEW_TIMER.schedule(mDismissControlViewTimerTask, 2500);
    }

    public void cancelDismissControlViewTimer() {
        if (DISMISS_CONTROL_VIEW_TIMER != null) {
            DISMISS_CONTROL_VIEW_TIMER.cancel();
        }
        if (mDismissControlViewTimerTask != null) {
            mDismissControlViewTimerTask.cancel();
        }

    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        cancelDismissControlViewTimer();
    }

    @Override
    public void reset() {
        super.reset();
        cancelDismissControlViewTimer();
        if (clarityPopWindow != null) {
            clarityPopWindow.dismiss();
        }
    }

    public void dissmissControlView() {
        if(dismissListener != null){
            dismissListener.dismiss();
        }

        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                if(isMovie && screen == SCREEN_FULLSCREEN) {
                    rl_lock.setVisibility(View.GONE);
                }
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss();
                }
                if (screen != SCREEN_TINY) {
                    bottomProgressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void setVideoDuration(long video_duration) {
        hasDuration = true;
        tv_videoLength.setVisibility(View.VISIBLE);
        tv_videoLength.setText(getMSTime(video_duration));
    }

    public class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
          dissmissControlView();
        }
    }

    private BroadcastReceiver battertReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int percent = level * 100 / scale;
                LAST_GET_BATTERYLEVEL_PERCENT = percent;
                setBatteryLevel();
                try {
                    getContext().unregisterReceiver(battertReceiver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


//    public void setProgress(int num){
//        progressBar.setProgress(num);
//    }


    //张野:进度提示
    public void showLLProgress(final Activity activity , boolean isShow , String content , boolean eternal){
        if(isShow){
            ll_progress.setVisibility(View.VISIBLE);
            tv_progress.setText(content);

            if(!eternal) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);

                        } catch (Exception e) {
                        }

                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ll_progress.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }).start();
            }else{

            }

        }else{
            ll_progress.setVisibility(View.GONE);
        }
    }

    //显示倒计时
    public void showCs(TextView textView , boolean isShow){
        if(isShow){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
    }

    //倒计时计数
    public void showCsNum(TextView textView , int num){
        //在直接播放视频倒计时为0后请求接口没有及时返回导致界面没及时关闭，倒计时负数显示
        if(num < 0){
            return;
        }
        textView.setText(num + "s");
    }

    //开启蒙层
    public void showGuide(){
        rl_guide.setVisibility(View.VISIBLE);
    }

//    //启动下载
//    public static interface LightDownloadListener{
//        public void download(String url);
//    }
//
//    //判断是否安装APK
//    public static boolean isAPKInstalled(Context context , String pkgName) {
//        final PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String pn = pinfo.get(i).packageName;
//                if (pn.equals(pkgName)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    //显示下载按钮
//    public void showDownloadButton(){
//        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(videoDownloadUrl)) {
//            ll_app.setVisibility(View.VISIBLE);
//        }
//    }

    //张野：设置为正常UI，非视频列表UI
    public void setLocalVideoUI(){
        isLocalVideo = true;
    }

    //张野：设置为直播UI
    public void setLiveVideoUI(){
        isLiveVideo = true;
    }

    //点击播放或暂停按钮
    protected void playState(){
        Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
        if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
//            Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (state == STATE_NORMAL) {
            if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                    jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {//这个可以放到std中
                showWifiDialog();
                return;
            }
            startVideo();
        } else if (state == STATE_PLAYING) {
            Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
            mediaInterface.pause();
            onStatePause();
        } else if (state == STATE_PAUSE) {
            mediaInterface.start();
            onStatePlaying();
        } else if (state == STATE_AUTO_COMPLETE) {
            startVideo();
        }
    }


    public void showNetkb(){
        ll_loading_net.setVisibility(View.VISIBLE);
    }
    public void setNetkb(int kbs){
        if(kbs < 1000) {
            tv_netkb.setText(kbs + " KB/s");
        }else{
            tv_netkb.setText(float1(kbs / 1000f)  + " MB/s");
        }
    }

    public void hideNetkb(){
        ll_loading_net.setVisibility(View.INVISIBLE);
    }

    //保留2位小数
    public static String float1(float number){

        DecimalFormat df = new DecimalFormat();
        String style = "0.0";

        df.applyPattern(style);
        return df.format(number);
    }


    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(View view,int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }

    // 获取渠道号
    public static String getChannel(Context context){
        String channel = "";
        try{
            ApplicationInfo info = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(info != null && info.metaData != null){
                String metaData = info.metaData.getString("UMENG_CHANNEL");
                if(!metaData.isEmpty()){
                    channel = metaData;
                }
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return channel;
    }


}

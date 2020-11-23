package com.newsuper.t.juejinbao.ui.movie.player;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 播放器的手势触摸感知
 */
public class JzvdPlayerTouch extends JzvdPlayer{

    //拖动进度dialog
    protected Dialog mProgressDialog;
    //音量大小dialog
    protected Dialog mVolumeDialog;
    //亮度大小dialog
    protected Dialog mBrightnessDialog;


    public JzvdPlayerTouch(Context context) {
        super(context);
    }

    public JzvdPlayerTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //显示触摸进度弹框
    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        View localView = null;
        if (mProgressDialog == null) {
            localView = LayoutInflater.from(getContext()).inflate(cn.jzvd.R.layout.jz_dialog_progress, null);
            mProgressDialog = createDialogWithView(localView);
        }else{
            localView = mProgressDialog.getWindow().getDecorView();
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        ((TextView)localView.findViewById(cn.jzvd.R.id.tv_current)).setText(seekTime);
        ((TextView)localView.findViewById(cn.jzvd.R.id.tv_duration)).setText(" / " + totalTime);
        ((ProgressBar)localView.findViewById(cn.jzvd.R.id.duration_progressbar)).setProgress(totalTimeDuration <= 0 ? 0 : (int) (seekTimePosition * 100 / totalTimeDuration));
        if (deltaX > 0) {
            ((ImageView)localView.findViewById(cn.jzvd.R.id.duration_image_tip)).setBackgroundResource(cn.jzvd.R.drawable.jz_forward_icon);
        } else {
            ((ImageView)localView.findViewById(cn.jzvd.R.id.duration_image_tip)).setBackgroundResource(cn.jzvd.R.drawable.jz_backward_icon);
        }

        if(deltaX == 10000 || deltaX == -10000){

        }else{
            hideOperateUI();
        }

    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    //显示音量大小弹框
    @Override
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
        View localView = null;
        if (mVolumeDialog == null) {
            localView = LayoutInflater.from(getContext()).inflate(cn.jzvd.R.layout.jz_dialog_volume, null);
            mVolumeDialog = createDialogWithView(localView);
        }else{
            localView = mVolumeDialog.getWindow().getDecorView();
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (volumePercent <= 0) {
            ((ImageView)localView.findViewById(cn.jzvd.R.id.volume_image_tip)).setBackgroundResource(cn.jzvd.R.drawable.jz_close_volume);
        } else {
            ((ImageView)localView.findViewById(cn.jzvd.R.id.volume_image_tip)).setBackgroundResource(cn.jzvd.R.drawable.jz_add_volume);
        }
        if (volumePercent > 100) {
            volumePercent = 100;
        } else if (volumePercent < 0) {
            volumePercent = 0;
        }
        ((TextView)localView.findViewById(cn.jzvd.R.id.tv_volume)).setText(volumePercent + "%");
        ((ProgressBar)localView.findViewById(cn.jzvd.R.id.volume_progressbar)).setProgress(volumePercent);
        hideOperateUI();

        startDismissVoiceViewTimer();
    }

    @Override
    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
        }
    }

    //显示亮度大小弹框
    @Override
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
        View localView = null;
        if (mBrightnessDialog == null) {
            localView = LayoutInflater.from(getContext()).inflate(cn.jzvd.R.layout.jz_dialog_brightness, null);
            mBrightnessDialog = createDialogWithView(localView);
        }else{
            localView = mBrightnessDialog.getWindow().getDecorView();
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (brightnessPercent > 100) {
            brightnessPercent = 100;
        } else if (brightnessPercent < 0) {
            brightnessPercent = 0;
        }
        ((TextView)localView.findViewById(cn.jzvd.R.id.tv_brightness)).setText(brightnessPercent + "%");
        ((ProgressBar)localView.findViewById(cn.jzvd.R.id.brightness_progressbar)).setProgress(brightnessPercent);
        hideOperateUI();


    }

    @Override
    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
        if (mBrightnessDialog != null) {
            mBrightnessDialog.dismiss();
        }
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(getContext(), cn.jzvd.R.style.jz_style_dialog_progress);
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

    //音乐弹窗定时关闭
    protected static Timer timer;
    protected DismissVoiceTimerTask dismissVoiceTimerTask;
    private void startDismissVoiceViewTimer() {
        cancelDismissVoiceViewTimer();
        timer = new Timer();
        dismissVoiceTimerTask = new DismissVoiceTimerTask();
        timer.schedule(dismissVoiceTimerTask, 3000);
    }

    private void cancelDismissVoiceViewTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (dismissVoiceTimerTask != null) {
            dismissVoiceTimerTask.cancel();
        }
    }

    private class DismissVoiceTimerTask extends TimerTask {

        @Override
        public void run() {
            post(() -> {
                dismissVolumeDialog();
            });
        }
    }

}

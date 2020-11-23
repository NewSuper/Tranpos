package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.juejinchain.android.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class JzvdPlayer extends Jzvd {

    private SeekBar sb;

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
        sb = findViewById(R.id.sb);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long time = seekBar.getProgress() * getDuration() / 100;
                seekToManulPosition = seekBar.getProgress();
                mediaInterface.seekTo(time);

            }
        });
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        startButton.setBackgroundResource(cn.jzvd.R.drawable.jz_pause_normal);
        startDismissControlViewTimer();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        startButton.setBackgroundResource(cn.jzvd.R.drawable.jz_play_normal);
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    protected void dragProgress(int progress) {
        super.dragProgress(progress);
        sb.setProgress(progress);
    }

    @Override
    public void onProgress(int progress, long position, long duration) {
        super.onProgress(progress, position, duration);
        sb.setProgress(progress);
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

    public class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
            post(() -> {
                hideOperate();
            });
        }
    }

    private void hideOperate(){
        startButton.setVisibility(View.GONE);
    }

    private void showOperate(){
        startButton.setVisibility(View.VISIBLE);
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
                    showOperate();
                    break;
            }
        } else if (id == R.id.sb) {
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

}

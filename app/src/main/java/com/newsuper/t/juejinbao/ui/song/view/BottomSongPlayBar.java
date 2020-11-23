package com.newsuper.t.juejinbao.ui.song.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Log;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.view.CircleImageView;
import com.newsuper.t.juejinbao.ui.song.Activity.SongActivity;
import com.newsuper.t.juejinbao.ui.song.Activity.SongListActivity;
import com.newsuper.t.juejinbao.ui.song.Event.MusicCancelEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicPauseEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicStartEvent;
import com.newsuper.t.juejinbao.ui.song.Event.StopMusicEvent;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.view.CirclePercentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import io.paperdb.Paper;

public class BottomSongPlayBar extends RelativeLayout {
    private static final String TAG = "BottomSongPlayBar";

    private Context mContext;

    private RelativeLayout rlSongController;
    private CircleImageView ivCover;
    private ImageView ivPlay, ivController;
    private TextView tvSongName, tvSongSinger;
    private LinearLayout llSongInfo;
    private CirclePercentView circlePercentProgress;
    private SongInfo currentSongInfo;

    private TimerTaskManager mTimerTask;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayMusicEvent(MusicStartEvent event) {
        Log.d(TAG, "MusicStartEvent :" + event);
        setSongBean(event.getSongInfo());
        ivPlay.setImageResource(R.mipmap.ic_bottom_controller_pause);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopMusicEvent(StopMusicEvent event) {
        Log.d(TAG, "onStopMusicEvent");
        setSongBean(event.getSongInfo());
        ivPlay.setImageResource(R.mipmap.ic_bottom_controller_play);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPauseMusicEvent(MusicPauseEvent event) {
        Log.d(TAG, "onPauseMusicEvent");
        ivPlay.setImageResource(R.mipmap.ic_bottom_controller_play);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicCancelEveMusicCancelEventnt(MusicCancelEvent event) {
        ivPlay.setImageResource(R.mipmap.ic_bottom_controller_play);
    }

    public BottomSongPlayBar(Context context) {
        this(context, null);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        mContext = context;
        initView();
        initListener();
        initSongInfo();
//        EventBus.getDefault().register(this);
//        circlePercentProgress.setPercentage(0);
    }

    private void initView() {
        rlSongController = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_songplay_control, this, true);
        ivCover = rlSongController.findViewById(R.id.iv_cover);
        ivPlay = rlSongController.findViewById(R.id.iv_bottom_play);
        ivController = rlSongController.findViewById(R.id.iv_bottom_controller);
        tvSongName = rlSongController.findViewById(R.id.tv_songname);
        tvSongSinger = rlSongController.findViewById(R.id.tv_singer);
        llSongInfo = rlSongController.findViewById(R.id.ll_songinfo);
        circlePercentProgress = rlSongController.findViewById(R.id.circle_percent_progress);
    }

    private void initListener() {
        ivCover.setOnClickListener(v -> {
            if(currentSongInfo==null){
                return;
            }

            Intent intent = new Intent(mContext, SongActivity.class);
            intent.putExtra(SongActivity.SONG_INFO, currentSongInfo);
            mContext.startActivity(intent);
        });

        llSongInfo.setOnClickListener(v -> {
            if(currentSongInfo==null){
                return;
            }
            Intent intent = new Intent(mContext, SongActivity.class);
            intent.putExtra(SongActivity.SONG_INFO, currentSongInfo);
            mContext.startActivity(intent);
        });

        ivPlay.setOnClickListener(v -> {
            if(currentSongInfo==null){
                MyToast.show(mContext , "未选择播放歌曲，快找一首听吧~");
                return;
            }

            if(!LoginEntity.getIsLogin()){
                GuideLoginActivity.start(mContext,false,"");
                return;
            }


            Log.d(TAG, "ivPlay OnClick");
            if (!SongPlayManager.getInstance().isPlaying()) {
                Log.d(TAG, "playMusic");
                SongPlayManager.getInstance().clickBottomContrllerPlay(currentSongInfo);
            } else {
                Log.d(TAG, "pauseMusic");
                SongPlayManager.getInstance().pauseMusic();
            }
        });

        ivController.setOnClickListener(v -> {

            if(!ClickUtil.isNotFastClick()){
                return;
            }

            if(currentSongInfo==null){
                MyToast.show(mContext , "暂无歌曲播放");
                return;
            }
            Intent intent = new Intent(mContext, SongListActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        });
    }

    private void initSongInfo() {
        //初始化的时候，要从本地拿最近一次听的歌曲
//        currentSongInfo = SharePreferenceUtil.getInstance(App.getContext()).getLatestSong();
        currentSongInfo = Paper.book().read(PagerCons.KEY_CURRENT_SONG_INDEX);
        if (currentSongInfo != null) {
            setSongBean(currentSongInfo);
            Log.d(TAG, "isPlaying " + SongPlayManager.getInstance().isPlaying());
            if (SongPlayManager.getInstance().isPlaying()) {
                ivPlay.setImageResource(R.mipmap.ic_bottom_controller_pause);
            }
        }

    }



    public void setSongBean(SongInfo bean) {
        currentSongInfo = bean;
        tvSongName.setText(bean.getSongName());
        tvSongSinger.setText(bean.getArtist());
        if (!TextUtils.isEmpty(bean.getSongCover())) {
            Glide.with(JJBApplication.getContext()).load(bean.getSongCover()).into(ivCover);
        }


        circlePercentProgress.setPercentage(0);
        circlePercentProgress.setVisibility(View.VISIBLE);
        if(mTimerTask != null){
            mTimerTask.removeUpdateProgressTask();
            mTimerTask = null;
        }
        mTimerTask = new TimerTaskManager();
        mTimerTask.setUpdateProgressTask(() -> {
            long position = StarrySky.with().getPlayingPosition();
            long total = currentSongInfo.getDuration();
            if(total <= 0){
                return;
            }
            Log.e("zy" , "circlePercentProgress : " + (position * 100f / total));
            circlePercentProgress.setPercentage( position * 100f / total );
        });
        mTimerTask.startToUpdateProgress();

    }


    @Override
    protected void onDetachedFromWindow() {
        if(mTimerTask != null) {
            mTimerTask.removeUpdateProgressTask();
            mTimerTask = null;
        }
//        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMusicStartEvent(MusicStartEvent event) {
//        SongInfo songInfo = event.getSongInfo();
//
//    }

}
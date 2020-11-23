package com.newsuper.t.juejinbao.ui.song.Activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.util.Log;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.newsuper.t.databinding.ActivitySongBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.song.Event.MusicCancelEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicErrorEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicPauseEvent;
import com.newsuper.t.juejinbao.ui.song.Event.MusicStartEvent;
import com.newsuper.t.juejinbao.ui.song.Event.StopMusicEvent;
import com.newsuper.t.juejinbao.ui.song.Util.MusicTimeUtil;
import com.newsuper.t.juejinbao.ui.song.entity.LyricBean;
import com.newsuper.t.juejinbao.ui.song.entity.SongDetailBean;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.SongPresenterImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.NetUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.OnClick;
import io.paperdb.Paper;


public class SongActivity extends BaseActivity<SongPresenterImpl, ActivitySongBinding> implements SongPresenterImpl.MvpView {

    public static final String SONG_INFO = "songInfo";
    private static final String TAG = "SongActivity";
    private SongInfo currentSongInfo;
    private long ids;
    private SongDetailBean songDetail;
    private TimerTaskManager mTimerTask;
    private boolean isLike = false;
    private int playMode;
    private ObjectAnimator rotateAnimator;
    private ObjectAnimator alphaAnimator;
    private RotateAnimation rotate;
    private boolean isShowLyrics = false;
    private LyricBean lyricBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicStartEvent(MusicStartEvent event) {
        Log.d(TAG, "onMusicStartEvent");
        SongInfo songInfo = event.getSongInfo();
        updateDuration(songInfo);
        if (mViewBinding.ivRecord.getVisibility() == View.VISIBLE)
            pointerAnim(0f, 35f);
        if (!songInfo.getSongId().equals(currentSongInfo.getSongId())) {
            //说明该界面下，切歌了，则要重新设置一遍
            currentSongInfo = songInfo;
            if (lyricBean != null) {
                lyricBean = null;
            }
            checkMusicState();


        } else {
            //如果没有没有切歌，则check一下就行了
            checkMusicPlaying();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicPauseEvent(MusicPauseEvent event) {
        checkMusicPlaying();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicStopEvent(StopMusicEvent event) {
        checkMusicPlaying();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicCancelEveMusicCancelEventnt(MusicCancelEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicCancelEveMusicCancelEventnt(MusicErrorEvent event) {
//        if(event.getErrorCode() == 0){
//            ToastUtils.showLong("当前网络较慢，请检查您的网络");
//        }else {
        if (NetUtil.isNetworkAvailable(mActivity)) {

            if(!SongPlayManager.getInstance().isErrorAutoNext()) {

                mPresenter.crawMusicList(mActivity, currentSongInfo.getSongName());
            }
        } else {
            MyToast.show(mActivity,"当前网络较慢，请检查您的网络");
        }
//        }

//            new android.support.v7.app.AlertDialog.Builder(mActivity)
//                    .setCancelable(true)
//                    .setMessage("该音乐播放地址走丢了，点击搜一搜查找更多播放地址。")
//                    .setPositiveButton("搜一搜", (dialog, which) -> {
//                        dialog.dismiss();
//                        if(currentSongInfo != null) {
//                            MusicSearchActivity.intentMe(mActivity, currentSongInfo.getSongName());
//                        }
//                        finish();
//                    })
//                    .setNegativeButton("取消", (dialog, which) -> {
//                        finish();
//                    })
//                    .create()
//                    .show();

//            ToastUtils.showLong("播放错误");
//        }
    }


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_song;
    }

    @Override
    public void initView() {

        if (SongPlayManager.getInstance().isPlaying())
            pointerAnim(0f, 35f);

//        SongInfo songInfo = new SongInfo();
//        songInfo.setSongUrl("https://sharefs.yun.kugou.com/201912041122/0b44542868fe8ee9d9c8fd6a16d3783c/G125/M08/02/09/HYcBAFw1ZpmAIqGpAEWwLDHL8Eg086.mp3");
//        songInfo.setSongId("1376577855");
//
//
//
//        SongPlayManager.getInstance().clickASong(songInfo);


        mViewBinding.title.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void initData() {
        getIntentData();
    }

    private void pointerAnim(float from, float to) {
        rotate = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1000);//设置动画持续周期
        rotate.setRepeatCount(0);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(0);//执行前的等待时间
        mViewBinding.ivPointer.setAnimation(rotate);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        currentSongInfo = intent.getParcelableExtra(SONG_INFO);
        playMode = SongPlayManager.getInstance().getMode();
        mTimerTask = new TimerTaskManager();

        initTimerTaskWork();

        checkMusicState();


    }


    private void initTimerTaskWork() {
        mTimerTask.setUpdateProgressTask(() -> {
            long position = StarrySky.with().getPlayingPosition();
            //SeekBar 设置 Max
            mViewBinding.seekBar.setProgress((int) position);
            mViewBinding.tvPastTime.setText(MusicTimeUtil.getTimeNoYMDH(position));
            mViewBinding.lrc.updateTime(position);
        });

        mViewBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SongPlayManager.getInstance().seekTo(seekBar.getProgress());
                SongActivity.this.mViewBinding.seekBar.setProgress(seekBar.getProgress());
                mViewBinding.lrc.updateTime(seekBar.getProgress());
            }
        });
    }

    private void checkMusicState() {
        resetCollect();

        Log.d(TAG, "currentSongInfo : " + currentSongInfo);
        setSongInfo(currentSongInfo.getSongName(), currentSongInfo.getArtist());

        if (judgeContainsStr(currentSongInfo.getSongId())) {
            mViewBinding.llInfo.setVisibility(View.GONE);
        } else {

            if (!TextUtils.isEmpty(currentSongInfo.getLanguage())) {
                mViewBinding.lrc.loadLrc(currentSongInfo.getLanguage(), "");
            }
            initLrcListener();
            mViewBinding.llInfo.setVisibility(View.VISIBLE);
            ids = Long.parseLong(currentSongInfo.getSongId());
            String songId = currentSongInfo.getSongId();
//            List<String> likeList = SharePreferenceUtil.getInstance(this).getLikeList();
            List<String> likeList = Paper.book().read(PagerCons.KEY_MUSIC_LIKE_LIST);

            if (likeList == null) {
                likeList = new ArrayList<>();
            }

            Log.d(TAG, "likeList :" + likeList);
            if (likeList.contains(songId)) {
                isLike = true;
                mViewBinding.ivLike.setImageResource(R.mipmap.ic_music_like_check);
            } else {
                isLike = false;
            }
            setSongDetailBean(currentSongInfo);
//            if (SongPlayManager.getInstance().getSongDetail(ids) == null) {
//                mPresenter.getSongDetail(ids);
//            } else {
//                songDetail = SongPlayManager.getInstance().getSongDetail(ids);
//                setSongDetailBean(songDetail);
//            }
        }

        long duration = currentSongInfo.getDuration() < 0 ? 0 : currentSongInfo.getDuration();
        if (mViewBinding.seekBar.getMax() != duration) {
            mViewBinding.seekBar.setMax((int) duration);
        }

        switch (playMode) {
            case SongPlayManager.MODE_LIST_LOOP_PLAY:
                mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_play_in_order);
                break;
            case SongPlayManager.MODE_RANDOM:
                mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_music_random_play);
                break;
            case SongPlayManager.MODE_SINGLE_LOOP_PLAY:
                mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_music_single_cycle);
                break;
        }
        mViewBinding.totalTime.setText(MusicTimeUtil.getTimeNoYMDH(duration));
        checkMusicPlaying();
    }


    private void checkMusicPlaying() {


        mTimerTask.startToUpdateProgress();
        if (SongPlayManager.getInstance().isPlaying()) {
            Log.d("zzz", "播放中");
            if (getRotateAnimator().isPaused()) {
                getRotateAnimator().resume();
            } else if (getRotateAnimator().isRunning()) {
            } else {
                getRotateAnimator().start();
            }
            mViewBinding.ivPlay.setImageResource(R.mipmap.ic_music_pause);
        } else {
            Log.d("zzz", "非播放");
            getRotateAnimator().pause();
            mViewBinding.ivPlay.setImageResource(R.mipmap.ic_music_play);
//            mTimerTask.stopToUpdateProgress();
        }


    }

    private ObjectAnimator getRotateAnimator() {
        if (rotateAnimator == null) {
            rotateAnimator = ObjectAnimator.ofFloat(mViewBinding.ivRecord, "rotation", 360f);
            rotateAnimator.setDuration(25 * 1000);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            rotateAnimator.setRepeatCount(100000);
            rotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        }
        return rotateAnimator;
    }

    public void setSongInfo(String songName, String singerName) {
//        RelativeLayout rlSong = findViewById(R.id.rl_song_info);
//        rlSong.setVisibility(View.VISIBLE);
//        TextView tvSongName = findViewById(R.id.tv_songname);
//        TextView tvSingerName = findViewById(R.id.tv_singername);
        mViewBinding.title.tvTitle.setText(songName);
        mViewBinding.title.tvSingername.setText(singerName);
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     */
    public boolean judgeContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    private void setSongDetailBean(SongInfo songDetail) {
        String coverUrl = songDetail.getSongCover();

        Glide.with(this)
                .load(coverUrl)
                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_music_play_default).error(R.mipmap.ic_music_play_default))
                .into(mViewBinding.ivRecord);

        Glide.with(this)
                .load(coverUrl)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 25)))
                .transition(new DrawableTransitionOptions().crossFade(1500))
                .into(mViewBinding.ivBg);
//        calculateColors(coverUrl);
    }

    @OnClick({R.id.iv_play, R.id.iv_like, R.id.iv_add, R.id.iv_share,
            R.id.iv_play_mode, R.id.iv_pre, R.id.iv_next, R.id.iv_list, R.id.rl_center, R.id.lrc})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_center:
                isShowLyrics = true;
                showLyrics(true);
                break;
            case R.id.iv_play:
                if (SongPlayManager.getInstance().isPlaying()) {
                    Log.d("zzz", "播放状态 - 》 应该暂停");
                    if(mViewBinding.ivRecord.getVisibility() == View.VISIBLE)
                        pointerAnim(35f, 0f);
                    SongPlayManager.getInstance().pauseMusic();
                } else if (SongPlayManager.getInstance().isPaused()) {
                    Log.d("zzz", "暂停状态 -》 应该播放");
                    SongPlayManager.getInstance().playMusic();
                } else if (SongPlayManager.getInstance().isIdle()) {
                    Log.d("zzz", "空闲 - 》 开始播放");
                    SongPlayManager.getInstance().clickASong(currentSongInfo);
                }
                break;
            case R.id.iv_like:
                if (isCollect(currentSongInfo.getSongId())) {
                    mPresenter.delCollect(mActivity, currentSongInfo.getSongId());
                } else {
                    mPresenter.addCollect(mActivity, currentSongInfo.getSongId());
                }
                break;
            case R.id.iv_add:
                MyToast.show(mActivity,"添加");
                break;
            case R.id.iv_share:
//                intent.setClass(SongActivity.this, SongDetailActivity.class);
//                intent.putExtra(SONG_INFO, currentSongInfo);
//                startActivity(intent);
//                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
//                ToastUtils.showShort("分享");
                sharePoster();
                break;
            case R.id.iv_play_mode:
                if (playMode == SongPlayManager.MODE_LIST_LOOP_PLAY) {
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_SINGLE_LOOP_PLAY);
                    mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_music_single_cycle);
                    playMode = SongPlayManager.MODE_SINGLE_LOOP_PLAY;

                    MyToast.show(mActivity,"切换到单曲循环模式");
                } else if (playMode == SongPlayManager.MODE_SINGLE_LOOP_PLAY) {
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_RANDOM);
                    mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_music_random_play);
                    playMode = SongPlayManager.MODE_RANDOM;
                    MyToast.show(mActivity,"切换到随机播放模式");
                } else if (playMode == SongPlayManager.MODE_RANDOM) {
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_LIST_LOOP_PLAY);
                    mViewBinding.ivPlayMode.setImageResource(R.mipmap.ic_play_in_order);
                    playMode = SongPlayManager.MODE_LIST_LOOP_PLAY;
                    MyToast.show(mActivity,"切换到列表循环模式");
                }
                break;
            case R.id.iv_pre:
                if (SongPlayManager.getInstance().getSongList() == null) {
                    return;
                }
//                SongPlayManager.getInstance().playPreMusic();
                int toIndex2 = SongPlayManager.getInstance().getCurrentSongIndex() - 1;
                if (toIndex2 < 0) {
                    toIndex2 = SongPlayManager.getInstance().getSongList().size() - 1;

                }
                if (SongPlayManager.getInstance().getSongList() != null && SongPlayManager.getInstance().getSongList().size() > 0) {
                    SongPlayManager.getInstance().switchMusic(toIndex2);
                }

                break;
            case R.id.iv_next:
                if (SongPlayManager.getInstance().getSongList() == null) {
                    return;
                }

                int toIndex = SongPlayManager.getInstance().getCurrentSongIndex() + 1;
                if (toIndex >= SongPlayManager.getInstance().getSongList().size()) {
//                    MyToast.show(mActivity , "这是最后一首歌了");
                    toIndex = 0;
                }
                SongPlayManager.getInstance().switchMusic(toIndex);
                break;
            case R.id.iv_list:
                intent.setClass(SongActivity.this, SongListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
        }
    }

    //根据isShowLyrics来判断是否展示歌词
    private void showLyrics(boolean isShowLyrics) {
        mViewBinding.ivRecord.setVisibility(isShowLyrics ? View.GONE : View.VISIBLE);
        mViewBinding.ivPlayBg.setVisibility(isShowLyrics ? View.GONE : View.VISIBLE);
        if (isShowLyrics) {
            mViewBinding.ivPointer.setAnimation(null);
            mViewBinding.ivPointer.setVisibility(View.GONE);
        } else {
            mViewBinding.ivPointer.setVisibility(View.VISIBLE);
            pointerAnim(0f, 35f);
        }
        mViewBinding.lrc.setVisibility(isShowLyrics ? View.VISIBLE : View.GONE);
    }

    private void initLrcListener() {
        mViewBinding.lrc.setListener(time -> {
            SongPlayManager.getInstance().seekTo(time);
            if (SongPlayManager.getInstance().isPaused()) {
                SongPlayManager.getInstance().playMusic();
            } else if (SongPlayManager.getInstance().isIdle()) {
                SongPlayManager.getInstance().clickASong(currentSongInfo);
            }
            return true;
        });

        mViewBinding.lrc.setCoverChangeListener(() -> {
            showLyrics(false);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mTimerTask.removeUpdateProgressTask();
        if (rotateAnimator != null) {
            if (rotateAnimator.isRunning()) {
                rotateAnimator.cancel();
            }
            rotateAnimator = null;
        }
        if (alphaAnimator != null) {
            if (alphaAnimator.isRunning()) {
                alphaAnimator.cancel();
            }
            alphaAnimator = null;
        }
        if (rotate != null)
            rotate = null;
    }

    public void updateDuration(SongInfo songInfo) {
        long duration = songInfo.getDuration();
        if (mViewBinding.seekBar.getMax() != duration) {
            mViewBinding.seekBar.setMax((int) duration);
        }
        mViewBinding.totalTime.setText(MusicTimeUtil.getTimeNoYMDH(duration));
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void addCollect(BaseEntity data, String songId) {
        if (data.getCode() == 0) {
            MyToast.show(mActivity,"已添加到我喜欢的音乐");
//                if (isLike) {
//                    ToastUtils.show("Sorry啊，我没有找到取消喜欢的接口");
//                } else {
//                    mPresenter.likeMusic(ids);
//                }
            Set<String> songIds = SongPlayManager.getInstance().getCollectList();
            songIds.add(songId);


            mViewBinding.ivLike.setImageResource(R.mipmap.ic_music_like_check);
        } else {
            MyToast.show(mActivity,data.getMsg());
        }
    }

    @Override
    public void resetCollect() {
        //收藏状态设置
        mViewBinding.ivLike.setImageResource(R.mipmap.ic_collection);
        for (String songId : SongPlayManager.getInstance().getCollectList()) {
            if (songId.equals(currentSongInfo.getSongId())) {
                mViewBinding.ivLike.setImageResource(R.mipmap.ic_music_like_check);
            }
        }
    }

    //是否收藏
    public boolean isCollect(String id) {
        for (String songId : SongPlayManager.getInstance().getCollectList()) {
            if (songId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SongPlayManager.getInstance().setErrorAutoNext(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SongPlayManager.getInstance().setErrorAutoNext(true);
    }

    @Override
    public void crawMusicList(List<BeanMusic> beanMusicList) {

        //弹出走丢
        if (beanMusicList.size() == 0) {
            if(mActivity == null || mActivity.isDestroyed() ){
                return;
            }
            new android.support.v7.app.AlertDialog.Builder(mActivity)
                    .setCancelable(true)
                    .setMessage("该音乐播放地址走丢了，点击搜一搜查找更多播放地址。")
                    .setPositiveButton("搜一搜", (dialog, which) -> {
                        dialog.dismiss();
                        if (currentSongInfo != null) {
                            MusicSearchActivity.intentMe(mActivity, currentSongInfo.getSongName());
                        }
                        finish();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        finish();
                    })
                    .create()
                    .show();
        }
        //弹出爬取列表
        else {
            BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);

            if(dataBean == null || dataBean.getMusic() == null){
                return;
            }

            if (dataBean.getMusic().getIs_open_crawl() == 1) {
                if(mActivity == null || mActivity.isDestroyed() ){
                    return;
                }
                new CrawMusicListDialog(mActivity, beanMusicList, new CrawMusicListDialog.CrawMusicListListener() {
                    @Override
                    public void onItemClick(BeanMusic beanMusic) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", "");
                        //歌曲名
                        map.put("song_name", beanMusic.getSongName());
                        //歌手名字
                        map.put("singer", beanMusic.getSinger());
                        //播放地址
                        map.put("song_url", beanMusic.getLink());
                        mPresenter.addSongs(mActivity, map);
                    }
                }).show();
            }
        }

    }

    @Override
    public void addSongs(AddSongEntity bean, Map<String, Object> map) {
        if (bean.getCode() == 0) {
            if (mActivity != null) {
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(bean.getData().getId());
                songInfo.setSongName((String) map.get("song_name"));
                songInfo.setSongUrl((String) map.get("song_url"));
                songInfo.setArtist((String) map.get("singer"));

                SongPlayManager.getInstance().addSongAndPlay(songInfo);
                currentSongInfo = songInfo;
                checkMusicState();
            }
        }


    }


    @SuppressLint("SetTextI18n")
    public void sharePoster() {
        View shareView = View.inflate(mActivity, R.layout.view_music_share_poster, null);
        TextView tvDes = shareView.findViewById(R.id.tv_des);
        ImageView ivRecord = shareView.findViewById(R.id.iv_record);
        TextView tvInviteCode = shareView.findViewById(R.id.tv_invite_code);
        ImageView imgQrCode = shareView.findViewById(R.id.img_qr_code);
        ConstraintLayout parentView = shareView.findViewById(R.id.parent_view);

        parentView.setBackgroundResource(getShareBg());
        StringBuilder desStringBuilder = new StringBuilder();
        desStringBuilder.append("我正在掘金宝听：")
                .append("\n")
                .append(getString(currentSongInfo.getArtist() + "的" + "《" + currentSongInfo.getSongName() + "》"))
                .append("\n")
                .append("已单曲循环很多次，邀您一起来听");

        tvDes.setText(desStringBuilder.toString());
        tvInviteCode.setText("邀请码：" + LoginEntity.getInvitation());


        if(TextUtils.isEmpty(currentSongInfo.getSongCover())){
            ivRecord.setImageResource(R.mipmap.ic_music_play_default);

            GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                @Override
                public void bitmapDone(Bitmap bitmap) {
                    ShareInfo shareInfo = new ShareInfo();
                    shareInfo.setBitmap(bitmap);
//                        String shareContent = getShareContent() + "\n"  + movieInfoEntity.getData().getMovie_info().getTitle()  + "，邀请码：" + LoginEntity.getInvitation()+ "\n" + movieInfoEntity.getData().getDownload_info().getDownload_url();
                    shareInfo.setShareContent(getShareContent());
                    shareInfo.setIsPictrueBybase64(1);
                    shareInfo.setUrl_type(ShareInfo.TYPE_MUSIC);
                    shareInfo.setUrl_path(ShareInfo.PATH_MUSIC);
                    ShareDialog shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                    shareDialog.show();
                }
            });

            Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getYingYongBaoUrl(), (int) getResources().getDimension(R.dimen.ws100dp), (int) getResources().getDimension(R.dimen.ws100dp));
            imgQrCode.setImageBitmap(qrbitmap);

            getPicByView.viewToImage(mActivity, shareView);
            return;
        }

        Glide.with(mActivity).asBitmap().load(currentSongInfo.getSongCover()).apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_music_play_default).error(R.mipmap.ic_music_play_default)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                ivRecord.setImageBitmap(resource);

                GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                    @Override
                    public void bitmapDone(Bitmap bitmap) {
                        ShareInfo shareInfo = new ShareInfo();
                        shareInfo.setBitmap(bitmap);
//                        String shareContent = getShareContent() + "\n"  + movieInfoEntity.getData().getMovie_info().getTitle()  + "，邀请码：" + LoginEntity.getInvitation()+ "\n" + movieInfoEntity.getData().getDownload_info().getDownload_url();
                        shareInfo.setShareContent(getShareContent());
                        shareInfo.setIsPictrueBybase64(1);
                        shareInfo.setUrl_type(ShareInfo.TYPE_MUSIC);
                        shareInfo.setUrl_path(ShareInfo.PATH_MUSIC);
                        ShareDialog shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                        shareDialog.show();
                    }
                });

                Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getYingYongBaoUrl(), (int) getResources().getDimension(R.dimen.ws100dp), (int) getResources().getDimension(R.dimen.ws100dp));
                imgQrCode.setImageBitmap(qrbitmap);

                getPicByView.viewToImage(mActivity, shareView);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                ivRecord.setImageResource(R.mipmap.ic_music_play_default);

                GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                    @Override
                    public void bitmapDone(Bitmap bitmap) {
                        ShareInfo shareInfo = new ShareInfo();
                        shareInfo.setBitmap(bitmap);
//                        String shareContent = getShareContent() + "\n"  + movieInfoEntity.getData().getMovie_info().getTitle()  + "，邀请码：" + LoginEntity.getInvitation()+ "\n" + movieInfoEntity.getData().getDownload_info().getDownload_url();
                        shareInfo.setShareContent(getShareContent());
                        shareInfo.setIsPictrueBybase64(1);
                        shareInfo.setUrl_type(ShareInfo.TYPE_MUSIC);
                        shareInfo.setUrl_path(ShareInfo.PATH_MUSIC);
                        ShareDialog shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                        shareDialog.show();
                    }
                });

                Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getYingYongBaoUrl(), (int) getResources().getDimension(R.dimen.ws100dp), (int) getResources().getDimension(R.dimen.ws100dp));
                imgQrCode.setImageBitmap(qrbitmap);

                getPicByView.viewToImage(mActivity, shareView);
            }
        });


    }


    public String getString(String str) {

        if (str.length() > 15) {
            String str1 = str.substring(0, 15) + "......";
            return str1;
        } else {
            return str;
        }
    }

    private int[] shareBgs = new int[]{
            R.mipmap.bg_share_music_1,
            R.mipmap.bg_share_music_2
    };

    private int getShareBg() {
        Random random = new Random();
        return shareBgs[random.nextInt(shareBgs.length)];
    }

    public String getShareContent() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

//        邀请码：" + LoginEntity.getInvitation()+ "\n" + movieInfoEntity.getData().getDownload_info().getDownload_url()
        if (random.nextInt(2) == 0) {
            stringBuilder.append("我正在掘金宝免费听：")
                    .append(currentSongInfo.getArtist() + "的" + "《" + currentSongInfo.getSongName() + "》")
                    .append("，这首音乐我已听了很多次，快来一起听一下。")
                    .append("\n")
                    .append("点击下方链接立即听歌！更能免费赚豪车洋房，从此走上人生巅峰！")
                    .append("\n")
                    .append("邀请码：" +LoginEntity.getInvitation() )
                    .append("\n")
                    .append(ShareConfigEntity.getYingYongBaoUrl());

            return stringBuilder.toString();
        } else {
            stringBuilder.append(currentSongInfo.getArtist() + "的" + "《" + currentSongInfo.getSongName() + "》"+"的歌曲在这里都能免费听，快来下载吧！")
                    .append("\n")
                    .append("这里不仅可以免费听歌更可以在平台赚得豪车洋房，人人实现财富梦！")
                    .append("\n")
                    .append("邀请码：" +LoginEntity.getInvitation() )
                    .append("\n")
                    .append(ShareConfigEntity.getYingYongBaoUrl());
            return stringBuilder.toString();

        }

    }


    public static void intentMe(Context context, SongInfo songInfo) {
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra(SongActivity.SONG_INFO, songInfo);
        context.startActivity(intent);
    }
}


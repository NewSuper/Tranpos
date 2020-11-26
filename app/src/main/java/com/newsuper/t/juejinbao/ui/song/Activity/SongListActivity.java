package com.newsuper.t.juejinbao.ui.song.Activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.lzx.starrysky.provider.SongInfo;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivitySongListBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.ui.song.adapter.SongListAdapter;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class SongListActivity extends BaseActivity<BasePresenter, ActivitySongListBinding> {

    SongListAdapter adapter;
    private Animation toTranslateIn;
    private Animation toTranslateOut;
    private List<SongInfo> songList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (toTranslateIn == null) {
            toTranslateIn = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_in);
            toTranslateIn.setFillAfter(true);
            toTranslateIn.setStartOffset(200);
        }
        if (toTranslateOut == null) {
            toTranslateOut = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_out);
            toTranslateOut.setFillAfter(true);
        }
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_song_list;
    }

    @Override
    public void initView() {

        mViewBinding.maskView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewBinding.maskView.setVisibility(View.VISIBLE);
            }
        },300);
    }

    @Override
    public void initData() {
        mViewBinding.rvPlaylist.setLayoutManager(new LinearLayoutManager(this));
        songList = SongPlayManager.getInstance().getSongList();

        mViewBinding.loadingView.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loadingView.setEmptyText("暂无播放歌曲");
        if(songList.size() == 0){
            mViewBinding.loadingView.showEmpty();
        }else{
            mViewBinding.loadingView.showContent();
        }

        adapter = new SongListAdapter(songList,this);
        adapter.setListener(listener);
        mViewBinding.rvPlaylist.setAdapter(adapter);

        mViewBinding.tvTotal.setText(new StringBuilder().append("( " ).append("共 ").append(songList.size()).append(" 首 ）"));

        if (SongPlayManager.getInstance().isPlaying() || SongPlayManager.getInstance().isPaused()) {
            mViewBinding.rvPlaylist.scrollToPosition(SongPlayManager.getInstance().getCurrentSongIndex());
        }

        setPlayMode(SongPlayManager.getInstance().getMode());
    }
    private void setPlayMode(int playMode) {
        switch (playMode) {
            case SongPlayManager.MODE_LIST_LOOP_PLAY:
                mViewBinding.tvPlayMode.setText("列表循环");
                setPlayModeImageColor(R.mipmap.ic_play_in_order_black);
                break;
            case SongPlayManager.MODE_SINGLE_LOOP_PLAY:
                mViewBinding.tvPlayMode.setText("单曲循环");
                setPlayModeImageColor(R.mipmap.ic_music_single_cycle_black);
                break;
            case SongPlayManager.MODE_RANDOM:
                mViewBinding.tvPlayMode.setText("随机播放");
                setPlayModeImageColor(R.mipmap.ic_music_random_play_black);
                break;
        }
    }
    private void setPlayModeImageColor(int resId) {
        // 使用代码设置drawableleft
        Drawable drawable = getResources().getDrawable(resId);
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mViewBinding.tvPlayMode.setCompoundDrawables(drawable, null, null, null);
    }
    SongListAdapter.OnSongClickListener listener = new SongListAdapter.OnSongClickListener() {
        @Override
        public void onMusicClick(int position) {

            //当前歌曲正在暂停
            if(SongPlayManager.getInstance().isCurMusicPaused(SongPlayManager.getInstance().getSongList().get(position).getSongId())) {
                //恢复播放
                SongPlayManager.getInstance().playMusic();
                return;
            }

            SongPlayManager.getInstance().switchMusic(position);
            songList = SongPlayManager.getInstance().getSongList();
            adapter.notifyDataSetChanged(songList);

            if(songList.size() == 0){
                mViewBinding.loadingView.showEmpty();
            }else{
                mViewBinding.loadingView.showContent();
            }

        }

        @Override
        public void onDelClick(int position) {

            if(!ClickUtil.isNotFastClick()){
                MyToast.show(mActivity , "请不要连续点击");
                return;
            }

            SongPlayManager.getInstance().deleteSong(position);
            songList = SongPlayManager.getInstance().getSongList();
            adapter.notifyDataSetChanged(songList);

            if(songList.size() == 0){
                mViewBinding.loadingView.showEmpty();
            }else{
                mViewBinding.loadingView.showContent();
            }

            if(position==SongPlayManager.getInstance().getCurrentSongIndex()){
                //应测试要求禅道5731 第2点，删除后继续播放，不自动切歌
                //2020-2-18 应李闯要求恢复原样
                SongPlayManager.getInstance().switchMusic(position);
                songList = SongPlayManager.getInstance().getSongList();
                adapter.notifyDataSetChanged(songList);


                //无歌则退出activity
               if(songList.size() == 0){
                   SongPlayManager.getInstance().clearSongList();
                    SongPlayManager.getInstance().cancelPlay();
                    finish();
                }

            }
        }
    };


    @Override
    public void finish() {
        super.finish();
        mViewBinding.maskView.setVisibility(View.GONE);
//        mViewBinding.view.startAnimation(toTranslateOut);
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @OnClick({R.id.mask_view, R.id.rl_play_mode, R.id.iv_trash_can})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mask_view:
                finish();
                break;
            case R.id.rl_play_mode:
                if (SongPlayManager.getInstance().getMode() == SongPlayManager.MODE_LIST_LOOP_PLAY) {
                    MyToast.show(mActivity,"已切换到单曲循环");
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_SINGLE_LOOP_PLAY);
                } else if (SongPlayManager.getInstance().getMode() == SongPlayManager.MODE_SINGLE_LOOP_PLAY) {
                    MyToast.show(mActivity,"已切换到列表随机");
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_RANDOM);
                } else if (SongPlayManager.getInstance().getMode() == SongPlayManager.MODE_RANDOM) {
                    MyToast.show(mActivity,"已切换到列表循环");
                    SongPlayManager.getInstance().setMode(SongPlayManager.MODE_LIST_LOOP_PLAY);
                }
                setPlayMode(SongPlayManager.getInstance().getMode());
                break;
            case R.id.iv_trash_can:

                new android.support.v7.app.AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("确定要清空列表吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();

                            SongPlayManager.getInstance().clearSongList();
                            SongPlayManager.getInstance().cancelPlay();
                            songList = SongPlayManager.getInstance().getSongList();
                            adapter.notifyDataSetChanged(songList);

                            if(songList.size() == 0){
                                mViewBinding.loadingView.showEmpty();
                            }else{
                                mViewBinding.loadingView.showContent();
                            }
                            finish();

                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();

//                SongPlayManager.getInstance().clearSongList();
//                SongPlayManager.getInstance().cancelPlay();
//                songList = SongPlayManager.getInstance().getSongList();
//                adapter.notifyDataSetChanged(songList);
//                finish();
                break;
        }
    }


}

package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityPlayLocalVideoBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;

import java.io.File;

import cn.jzvd.Jzvd;

/**
 * 播放本地视频
 */
public class PlayLocalVideoActivity extends BaseActivity<BasePresenter, ActivityPlayLocalVideoBinding> {

    public static final String VIDEO_PATH = "path";
    private String mVideoPath;

    public static void start(Context context, String path) {
        Intent intent = new Intent(context,PlayLocalVideoActivity.class);
        intent.putExtra(VIDEO_PATH,path);
        context.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_local_video;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewBinding.player.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (mViewBinding.player.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void initView() {
        mVideoPath = getIntent().getStringExtra(VIDEO_PATH);
        try {
            String[] split = mVideoPath.split(File.separator);
            mViewBinding.player.setUp(mVideoPath, split[split.length - 1], Jzvd.SCREEN_NORMAL);
        } catch (Exception e) {
            mViewBinding.player.setUp(mVideoPath, mVideoPath, Jzvd.SCREEN_NORMAL);
            e.printStackTrace();
        }

        mViewBinding.player.startButton.performClick();
        mViewBinding.player.startVideo();
    }

    @Override
    public void initData() {
        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
}

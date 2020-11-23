package com.newsuper.t.juejinbao.ui.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityPlayvideoBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.PagerCons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static io.paperdb.Paper.book;

//播放广告视频
public class PlayVideoActivity extends BaseActivity<PublicPresenterImpl , ActivityPlayvideoBinding> {
//    public static final int TOTHIS = 108;

    private String title;
    private String image;
    private String coin;
    private String url;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }
        book().write(PagerCons.PLAYSTART , System.currentTimeMillis());

    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_playvideo;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        coin = getIntent().getStringExtra("coin");
        url = getIntent().getStringExtra("url");

        mViewBinding.tvTitle.setText(title);

        setResult(RESULT_OK);



        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        if(TextUtils.isEmpty(url)){
            return;
        }
        startPlay(title , url , image);
    }

    @Override
    public void initData() {

    }

    //使用原生播放器播放视频
    public void startPlay(String videoTitle , String videoUrl , String imgUrl){

        mViewBinding.player.setUp(videoUrl, videoTitle);
//        Glide.with(mActivity).load(imgUrl).into(mViewBinding.player.thumbImageView);
        mViewBinding.player.setStateListener(new JzvdStd.StateListener() {
            @Override
            public void preparing() {

            }

            @Override
            public void startPlay() {
                Log.e("zy" , "onPause视频时长：" + mViewBinding.player.getDuration());
                book().write(PagerCons.PLAYCURRENT , mViewBinding.player.getDuration());
                mViewBinding.player.gotoScreenFullscreen();
            }

            @Override
            public void pause() {

            }

            @Override
            public void endPlay() {
                new AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("已看完视频，是否返回领取" + coin + "金币")
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }

            @Override
            public void error() {

            }

            @Override
            public void progress(int num) {

            }

            @Override
            public void screennormal() {

            }

            @Override
            public void seekStart() {

            }

            @Override
            public void seekComplete(int currentPosition , int bufferProgress) {

            }

        });
        mViewBinding.player.startVideo();

    }


    @Override
    protected void onPause() {
        try {

            if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING) {



                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.goOnPlayOnPause();
                } else {
                    Jzvd.goOnPlayOnPause();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onPause();


    }

//    @Override
//    public void onBackPressed() {
//
//        if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING) {
//            try {
//                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
//                    Jzvd.backPress();
//                } else {
//                    Jzvd.releaseAllVideos();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            super.onBackPressed();
//        }
//    }


    public static void intentMe(Activity activity, String title, String image , String coin, String url , int requestCode) {
        Intent intent = new Intent(activity, PlayVideoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("image", image);
        intent.putExtra("coin", coin);
        intent.putExtra("url", url);
        activity.startActivityForResult(intent , requestCode);
    }

    public static void intentMe(Fragment fragment, String title, String image , String coin, String url , int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), PlayVideoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("image", image);
        intent.putExtra("coin", coin);
        intent.putExtra("url", url);
        fragment.startActivityForResult(intent , requestCode);
    }

    /**
     * 8.0透明主题下视频全屏崩溃bug兼容
     * @return
     */
    private boolean isTranslucentOrFloating(){
        boolean isTranslucentOrFloating = false;
        try {
            int [] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }
    private boolean fixOrientation(){
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //切换为竖屏
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {

            mViewBinding.llTitle.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        //切换为横屏
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            mViewBinding.llTitle.setVisibility(View.GONE);
        }
        super.onConfigurationChanged(newConfig);
    }

}

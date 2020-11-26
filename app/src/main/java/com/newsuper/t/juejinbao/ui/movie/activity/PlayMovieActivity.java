package com.newsuper.t.juejinbao.ui.movie.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityPlaymovieBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.movie.bean.DownloadSniff;
import com.newsuper.t.juejinbao.ui.movie.bean.SelectSniff;
import com.newsuper.t.juejinbao.ui.movie.bean.Sniff;
import com.newsuper.t.juejinbao.ui.movie.bean.UpdateEntity;
import com.newsuper.t.juejinbao.ui.movie.bean.YXL;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieThirdIframeEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.PlayerADEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayMovieImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.JzvdStdEx;
import com.newsuper.t.juejinbao.ui.movie.utils.NetWorkStateReceiver;
import com.newsuper.t.juejinbao.ui.movie.utils.PlayerExFunc;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.view.DownloadListPopupWindow;
import com.newsuper.t.juejinbao.ui.movie.view.ForceShareByPlayMovieDialog;
import com.newsuper.t.juejinbao.ui.movie.view.SelectTVPlayPopupWindow;
import com.newsuper.t.juejinbao.ui.movie.view.YXLDialog;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.DialogManage;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.qq.e.comm.util.StringUtil;
import com.squareup.otto.Subscribe;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static io.paperdb.Paper.book;

/**
 * 全网vip播放界面
 */
public class PlayMovieActivity extends BaseActivity<PlayMovieImpl, ActivityPlaymovieBinding> implements PlayMovieImpl.MvpView {
    private static final String TYPE_NORMALVIDEO = "normalVideo";
    private static final String TYPE_NORMALIFRAME = "normalIframe";
    private static final String TYPE_THIRDIFRAME = "thirdIframe";
    private static final String TYPE_NORMALVIDEOANDIFRAME = "normalVideoAndIframe";

    //播放列表数据
    private MovieThirdIframeEntity movieThirdIframeEntity = null;

    //解析计时
    private Subscription subscription;


    private int seconds = 0;

    //云线路选择弹框
    private YXLDialog yxlDialog;
    private List<YXL> yxls = null;

    //云线路
    private String connectUrl = null;

    //暗webview
    private BridgeWebView webView2;
    //播放进度
//    private String progressCache = null;
//    private String[] caches = null;

    //流量kb
    private double totalByte = 0;

    //是否是vip影院
    private boolean isVip;
    private ForceShareByPlayMovieDialog shareByPlayMovieDialog;
    //是否显示源网页
    private boolean isShowUrl = false;
    //源网页地址
    private String url;

    //播放器扩展功能
    private PlayerExFunc playerExFunc;

    //第一次进去时读取进度
    private boolean flag = true;

    //网络监听广播
    private NetWorkStateReceiver netWorkStateReceiver;


    private PlayerADEntity playerADEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(Build.VERSION.SDK_INT == Build.VERSION_CODES.O ? R.style.AppTheme_NoActionBar : R.style.AppTheme_Transparent);

//        Log.e("zy", getIntent().getStringExtra("data"));
        String str = book().read(PagerCons.KEY_PLAYMOVIE_DATA );
        if(str != null) {
            movieThirdIframeEntity = JSON.parseObject(str, MovieThirdIframeEntity.class);
        }
        if(movieThirdIframeEntity == null){
            MyToast.show(mActivity , "数据格式错误");
            finish();
            return;
        }

        //重置一下测速
        try {
            Utils.getKByte();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
//            boolean result = fixOrientation();
//        } else {

        //播放历史进入
        if (movieThirdIframeEntity.getDownlist() != null) {

            if (movieThirdIframeEntity.getDownlist().size() == movieThirdIframeEntity.getSourceList().size()) {
                for (int i = 0; i < movieThirdIframeEntity.getDownlist().size(); i++) {
                    if (movieThirdIframeEntity.getDownlist().get(i).getPlayed() == 1) {
                        //找到选集
                        movieThirdIframeEntity.setCurrentIndex(i);
                        //找到默认下载
                        movieThirdIframeEntity.setDownIndex(i);
                    }
                }
            }
        }

        if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEO)) {
            //直接横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            //非观看历史
            if(movieThirdIframeEntity.getDownlist() == null) {
                //构造下载列表数据
                List<MovieThirdIframeEntity.DownListBean> downListBeans = new ArrayList<>();
                for (MovieThirdIframeEntity.SourceListBean sourceListBean : movieThirdIframeEntity.getSourceList()) {
                    MovieThirdIframeEntity.DownListBean downListBean = new MovieThirdIframeEntity.DownListBean();
                    downListBean.setName(sourceListBean.getTitle());
                    downListBean.setVideoDownloadUrl(sourceListBean.getUrl());
                    downListBeans.add(downListBean);
                }
                movieThirdIframeEntity.setDownIndex(movieThirdIframeEntity.getCurrentIndex());
                movieThirdIframeEntity.setDownlist(downListBeans);
                movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).setSelected(1);
            }
        } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME)) {
            //非观看历史
            if(movieThirdIframeEntity.getDownlist() == null) {
                //构造下载列表数据
                List<MovieThirdIframeEntity.DownListBean> downListBeans = new ArrayList<>();
                for (MovieThirdIframeEntity.SourceListBean sourceListBean : movieThirdIframeEntity.getSourceList()) {
                    MovieThirdIframeEntity.DownListBean downListBean = new MovieThirdIframeEntity.DownListBean();
                    downListBean.setName(sourceListBean.getTitle());
                    downListBean.setUrl(sourceListBean.getUrl());
                    downListBeans.add(downListBean);
                }
                movieThirdIframeEntity.setDownIndex(movieThirdIframeEntity.getCurrentIndex());
                movieThirdIframeEntity.setDownlist(downListBeans);
            }
//            movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).selected = 1;

        } else if (movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) {
            //非观看历史
            if(movieThirdIframeEntity.getDownlist() == null) {
                //构造下载列表数据
                List<MovieThirdIframeEntity.DownListBean> downListBeans = new ArrayList<>();
                for (MovieThirdIframeEntity.SourceListBean sourceListBean : movieThirdIframeEntity.getSourceList()) {
                    MovieThirdIframeEntity.DownListBean downListBean = new MovieThirdIframeEntity.DownListBean();
                    downListBean.setName(sourceListBean.getTitle());
                    downListBean.setUrl(sourceListBean.getUrl());
                    downListBeans.add(downListBean);
                }
                movieThirdIframeEntity.setDownIndex(movieThirdIframeEntity.getCurrentIndex());
                movieThirdIframeEntity.setDownlist(downListBeans);
            }
//            movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).selected = 1;

        } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEOANDIFRAME)) {
            //直接横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).setSelected(1);
        }
//        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_playmovie;
    }

    @Override
    public void initView() {

        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();

        setResult(RESULT_OK);

        isVip = getIntent().getBooleanExtra("isVip", false);


        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //关闭提示框
        mViewBinding.rlAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.rlAlert.setVisibility(View.GONE);
            }
        });

        // 查看源网页
        mViewBinding.rlUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowUrl = !isShowUrl;
                mViewBinding.llUrl.setVisibility(isShowUrl ? View.VISIBLE : View.GONE);
                mViewBinding.ivUrl.setImageResource(isShowUrl ? R.mipmap.ic_down : R.mipmap.ic_up);
            }
        });

        // 跳转源网页
        mViewBinding.llUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtil.isEmpty(url)) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(url);
                        intent.setData(uri);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (movieThirdIframeEntity == null) {
            finish();
            return;
        } else {

        }

        mViewBinding.tvTitle.setText(movieThirdIframeEntity.getTitle());

        //点击切换播放源
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示云线路
                if (movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) {
                    if (yxlDialog == null) {
                        if (yxls != null) {
                            yxlDialog = new YXLDialog(mActivity, yxls, new YXLDialog.ChangeYXL() {
                                @Override
                                public void change(String name, String url) {


                                    mViewBinding.tvYxl.setText(name);
                                    openCover(true, null);
                                    connectUrl = url;
                                    sniffing(url + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);


                                }
                            });
                        } else {
                            return;
                        }
                    }
                    yxlDialog.show();
                }
                //关闭页面
                else {
                    finish();
                }
            }
        });

//        mViewBinding.tvTitle.setText(movieThirdIframeEntity.getTitle());


        mViewBinding.llYxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yxlDialog == null) {
                    if (yxls != null) {
                        yxlDialog = new YXLDialog(mActivity, yxls, new YXLDialog.ChangeYXL() {
                            @Override
                            public void change(String name, String url) {
                                mViewBinding.tvYxl.setText(name);
                                openCover(true, null);
                                connectUrl = url;
                                sniffing(url + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);
                            }
                        });
                    } else {
                        return;
                    }
                }
                yxlDialog.show();
            }
        });

        mViewBinding.player.setStateListener(new JzvdStdEx.StateListener() {
            @Override
            public void preparing() {
                mViewBinding.player.gotoScreenFullscreen();

                if (netTimer != null) {
                    netTimer.cancel();
                    netTimer = null;
                }
                if (timerTask != null) {
                    timerTask.cancel();
                    timerTask = null;
                }

            }

            @Override
            public void startPlay() {
                Log.e("zy", "startPlay hideNetkb");
                mViewBinding.player.hideNetkb();
                //隐藏标题栏
                mViewBinding.llTitle.setVisibility(View.GONE);

                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                    subscription = null;
                }
//                mViewBinding.player.hideNetkb();
                //显示下载按钮


                mViewBinding.player.showCs(mViewBinding.tvCs, false);

                mViewBinding.player.showLLProgress(mActivity, false, "", false);
                mViewBinding.player.gotoScreenFullscreen();

//                if(mPresenter.loadProgress(movieThirdIframeEntity) == 0) {
//                    mViewBinding.player.gotoScreenFullscreen();
//                }

                if (flag) {
                    flag = false;

                    //上次有观看
                    if (mPresenter != null && mPresenter.loadProgress(movieThirdIframeEntity) > 0) {
                        long duration = mViewBinding.player.getDuration();
                        int progress = mPresenter.loadProgress(movieThirdIframeEntity);

                        if (progress == 100) {
                            progress -= 2;
                        }


                        duration = duration * progress / 100;

                        mViewBinding.player.pregressToMedia(progress);
                        mViewBinding.player.showLLProgress(mActivity, true, "上次看到" + Utils.secondsToSFM(duration / 1000L), false);


//                    mViewBinding.llProgress.setVisibility(View.VISIBLE);
//                    mViewBinding.tvProgress.setText("上次看到" + Utils.secondsToSFM(duration / 1000L));
//                    mViewBinding.tvProgressPlay.setText("跳转播放");

//                    mViewBinding.tvProgressPlay.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            mViewBinding.player.pregressToMedia(progress);
////                            mViewBinding.llProgress.setVisibility(View.GONE);
////                            mViewBinding.player.gotoScreenFullscreen();
//                        }
//                    });
                    }
//                if(caches != null){
//                    try {
//                        if(Integer.parseInt(caches[1]) == movieThirdIframeEntity.getCurrentIndex()) {
//                            mViewBinding.player.pregressToMedia(Integer.parseInt(caches[2]));
//                        }
//                    }catch (Exception e){}
//                    caches = null;
//                }
                }


                //开启引导
                Configuration mConfiguration = getResources().getConfiguration(); //获取设置的配置信息
                int ori = mConfiguration.orientation; //获取屏幕方向
                if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
                    boolean noFirstInto = book().read(PagerCons.KEY_MOVIEPLAY_NOFIRST, false);
                    if (!noFirstInto) {
                        book().write(PagerCons.KEY_MOVIEPLAY_NOFIRST, true);
                        mViewBinding.player.showGuide();
                    }
                }
                /**
                 * 分享之后才能继续观看
                 */
                try {
                    checkShareTimes();
                } catch (Exception e) {

                }


                //开启UI
                mViewBinding.player.showUI();

            }

            @Override
            public void pause() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (playerADEntity != null) {
                            mPresenter.loadADAndShow(PlayMovieActivity.this, playerADEntity);
                        } else {
                            mPresenter.requestADData(mActivity);
                        }
//                        mPresenter.loadInteractionAd(PlayMovieActivity.this);
                    }
                });
//                MyToast.show(mActivity , "弹出广告");

            }

            @Override
            public void endPlay() {
                mViewBinding.player.showLLProgress(mActivity, false, "", false);


                //进度设置为0
                if (mPresenter != null) {
                    mPresenter.saveProgress(movieThirdIframeEntity, 0);
                }

//                int index = movieThirdIframeEntity.getDownIndex();
//                int size = movieThirdIframeEntity.getDownlist().size();
//                if (index + 1 < size) {
//                    movieThirdIframeEntity.setCurrentIndex(index + 1);
////                    mViewBinding.tvNext.setText("正在准备下一集...");
//                    mViewBinding.rlCover.setVisibility(View.VISIBLE);
//                    startAnalysis(false);
//
//                } else {
//                    finish();
//                }

                //强制点击下一集
                mViewBinding.player.rl_next.performClick();
            }

            @Override
            public void error() {


                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mViewBinding.rlCover.setVisibility(View.GONE);
                                mViewBinding.llSource.setVisibility(View.GONE);
                                Map<String, String> map = new HashMap<>();
                                map.put("vid", movieThirdIframeEntity.getId() + "");
                                map.put("source", movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getSource());
                                mPresenter.shieldSource(mActivity, map);
                            } catch (Exception e) {
                                mActivity.finish();
                            }
                        }
                    });
                }

            }

            @Override
            public void progress(int num) {
                //切换屏幕时时重新播放会=0
                if (num == 0) {
                    return;
                }

                if (mPresenter != null) {
                    mPresenter.saveProgress(movieThirdIframeEntity, num);

                    int index = movieThirdIframeEntity.getDownIndex();
                    int size = movieThirdIframeEntity.getDownlist().size();
                    if (index + 1 < size) {

                        long duration = mViewBinding.player.getDuration();
                        long nowDuration = (long) (duration / 100d * num);

//                        long alertTime = 0;
//                        if((duration / 100) < (20 * 1000) ){
//                            alertTime = 20 * 1000;
//                        }else{
//                            alertTime = duration / 100 + 1;
//                        }


                        //百分之一小于20秒
                        if (duration / 100d < 20 * 1000) {
                            long alertTime = 20 * 1000;
                            //最后20秒提示
                            if (duration - nowDuration < alertTime) {
                                mViewBinding.player.showLLProgress(mActivity, true, "即将自动为您播放下一集", true);
                            } else {
                                mViewBinding.player.showLLProgress(mActivity, false, "即将自动为您播放下一集", false);
                            }
                        }
                        //百分之一大于20秒，则99时显示
                        else {
                            if (num >= 99) {
                                mViewBinding.player.showLLProgress(mActivity, true, "即将自动为您播放下一集", true);
                            } else {
                                mViewBinding.player.showLLProgress(mActivity, false, "即将自动为您播放下一集", false);
                            }
                        }

                    }

                }

                //到达100直接隐藏提示
                if (num == 100) {
                    mViewBinding.player.showLLProgress(mActivity, false, "即将自动为您播放下一集", false);
                }

//                mViewBinding.player.hideNetkb();
            }

            @Override
            public void screennormal() {
                if (playerExFunc != null) {
                    playerExFunc.hidePopup();
                }
            }

            @Override
            public void seekStart() {
                seekStart = 1;
                startSeekNetkbUIAndTimer();
            }

            @Override
            public void seekComplete(int currentPosition , int bufferProgress) {
//                Log.e("zy", "seekComplete hideNetkb " + currentPosition + " ," + bufferProgress);
                if(seekStart > 0){
                    //滑动后前N次不做操作，防止currnet比currentPosition大很多
                    current = currentPosition;
                    seekStart--;
                    return;
                }


                //滑动加载有时候currentPosition会减少一会儿
                if(current > currentPosition){
                    Log.e("zy" , "卡顿中");
                    startSeekNetkbUIAndTimer();
                    current = currentPosition;
                }
                else if(current + 500 > currentPosition){
                    Log.e("zy" , "播放卡顿中");
                    startSeekNetkbUIAndTimer();
                }else {
                    current = currentPosition;


                    if (!replay) {
                        mViewBinding.player.hideNetkb();
                    }
                    if (netTimer != null) {
                        netTimer.cancel();
                        netTimer = null;
                    }
                    if (timerTask != null) {
                        timerTask.cancel();
                        timerTask = null;
                    }
                }
            }


        });


        mViewBinding.player.setFinishListener(new Jzvd.FinishListener() {
            @Override
            public void clickcontain() {
                hideBottomUIMenu();
            }

            @Override
            public void finish() {
                PlayMovieActivity.this.finish();
            }
        });

        mViewBinding.player.setLocalVideoUI();
    }


    //播放进度
    private int current;
    //开启滑动进度
    private int seekStart = 0;

    //seek网速定时器
    Timer netTimer;
    TimerTask timerTask;

    //开启加载中时的网速显示
    private void startSeekNetkbUIAndTimer() {
        mViewBinding.player.showNetkb();
//        mViewBinding.player.setNetkb((int) Utils.getKByte());

        if (netTimer != null) {
            netTimer.cancel();
            netTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        netTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mViewBinding.player.setNetkb((int) Utils.getKByte());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        };
        netTimer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void initData() {
        mPresenter.requestADData(mActivity);

        hideBottomUIMenu();
//        try {
//            progressCache = book().read(PagerCons.PLAYMOVIEPROGRESS);
//            if (TextUtils.isEmpty(progressCache)) {
//                progressCache = null;
//            } else {
//                caches = progressCache.split("~");
//                if (caches.length != 3) {
//                    progressCache = null;
//                    caches = null;
//                } else {
//                    if (!caches[0].equals(movieThirdIframeEntity.getTitle())) {
//                        progressCache = null;
//                        caches = null;
//                    } else {
////                        if (Integer.parseInt(caches[1]) > movieThirdIframeEntity.getCurrentIndex()) {
////                            progressCache = null;
////                            caches = null;
////                        }else{
////                            movieThirdIframeEntity.setCurrentIndex(Integer.parseInt(caches[1]));
////                        }
//                    }
//                }
//            }
//        }catch (Exception e){}


        //请求闪电app下载地址
        Map params = new HashMap();
        params.put("version_code", Utils.getLocalVersion(mActivity) + "");
        params.put("from", "jjb");
        mPresenter.updateApp(mActivity, params);

        startAnalysis(true);
    }

    protected void hideBottomUIMenu() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                decorView.setSystemUiVisibility(uiOptions);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        } catch (Exception e) {
        }
    }

    //开始分析数据
    private void startAnalysis(boolean isFirst) {
        //先初始化一下
        try {
            if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                Jzvd.backPress();
                mViewBinding.llTitle.setVisibility(View.VISIBLE);
                Jzvd.goOnPlayOnPause();
            } else {
                Jzvd.goOnPlayOnPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Jzvd.releaseAllVideos();


        //直接播放视频
        if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEO)) {
            openCover(false, null);
            //开启倒计时
            mViewBinding.player.showCs(mViewBinding.tvCs, true);

            startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle(), movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);
        }
        //直接嗅探网页
        else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME)) {


            openCover(true, null);
            sniffing(movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);
        }
        //请求线路后拼接地址并嗅探网页
        else if (movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) {

            openCover(true, null);
            if (isFirst) {
                mViewBinding.rlAlert.setVisibility(View.VISIBLE);
            }
            //请求云线路
            mPresenter.requestYXL(mActivity);
        }
        //可以直接播放，但下载地址需要网页嗅探
        else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEOANDIFRAME)) {
            openCover(false, null);
//            mViewBinding.player.showCs(mViewBinding.tvCs, true);

            startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle(), movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);

        }

    }

    @Override
    protected void onPause() {
        try {
            if (Jzvd.CURRENT_JZVD != null) {
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


    @Override
    public void onBackPressed() {
        finish();
//        if (Jzvd.CURRENT_JZVD != null) {
//            try {
//                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
//                    Jzvd.backPress();
//                    mViewBinding.llTitle.setVisibility(View.VISIBLE);
//                } else {
////                    Jzvd.releaseAllVideos();
//                    finish();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            super.onBackPressed();
//        }
    }


    public static void intentMe(Context context, boolean isVip) {
        Intent intent = new Intent(context, PlayMovieActivity.class);
        intent.putExtra("isVip", isVip);
        context.startActivity(intent);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //切换为竖屏
        if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {

//            mViewBinding.llTitle.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

            if (playerExFunc != null) {
                playerExFunc.hidePopup();
            }
        }
        //切换为横屏
        else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
//            mViewBinding.llTitle.setVisibility(View.GONE);

        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void requestYXL(List<YXL> yxls) {
        if (yxls.size() == 0) {
            return;
        }
        this.yxls = yxls;
//        connectUrl = yxls.get(0).url;
        mViewBinding.llYxl.setVisibility(View.VISIBLE);
        mViewBinding.llSource.setVisibility(View.VISIBLE);
        url = movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl();
        mViewBinding.tvUrl.setText(url);

        connectUrl = yxls.get(0).url;
        sniffing(yxls.get(0).url + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getUrl(), null);
    }

    @Override
    public void error() {

    }

    @Override
    public void updateApp(UpdateEntity updateEntity) {
        getPlayerExFunc().setAppDownloadUrl(updateEntity.getData().getDownloadUrl(), updateEntity.getData().getIs_screen());
//       playerExFunc.setAppDownloadUrl(updateEntity.getData().getDownloadUrl(), new JzvdStdEx.LightDownloadListener() {
//            @Override
//            public void download(String url) {
//                MyToast.show(mActivity, "开始下载“闪电视频加速”");
//                String fileName = "shandianshipinjiasu.apk";
//                String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
//
//                downFile(url, dir + fileName);
//            }
//        });
    }

    @Override
    public void requestADData(PlayerADEntity playerADEntity) {
        this.playerADEntity = playerADEntity;
    }

    //是否在嗅探中
//    private boolean isSniffing = false;
    Sniff sniff;

    //嗅探网页
    private void sniffing(String url, Sniff sniff2) {
//        isSniffing = true;
        this.sniff = sniff2;
        if (webView2 == null) {
            //暗加载webview并注入
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            webView2 = new BridgeWebView(getApplicationContext());
            webView2.setLayoutParams(params);
            webView2.setBackgroundColor(getResources().getColor(R.color.black));
            mViewBinding.llWebcontain2.addView(webView2);

            WebSettings websettings = webView2.getSettings();
            websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            websettings.setLoadsImagesAutomatically(true);
            websettings.setJavaScriptEnabled(true);
            websettings.setUseWideViewPort(true);
            websettings.setSupportMultipleWindows(true);
            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
            websettings.setAppCacheEnabled(false);
            websettings.setDomStorageEnabled(false);



//        websettings.setBlockNetworkImage(false); // 解决图片不显示
            websettings.setBlockNetworkImage(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView2.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
            }

            //与js弹框交互
            websettings.setJavaScriptEnabled(true);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);

            websettings.setMediaPlaybackRequiresUserGesture(false);
//            websettings.setAllowFileAccessFromFileURLs(true);

            webView2.setWebViewClient(new WebViewClient() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    //回调失败的相关操作

//                    isSniffing = false;
                    desotroyWebView();
                    if (sniff != null) {
                        getPlayerExFunc().DownloadSnffingResult(sniff, false);
                        return;
                    }

                    try {
                        Log.e("zy", "加载失败 error.getErrorCode() = " + error.getErrorCode());
                        if ((error.getErrorCode() != -8) && (error.getErrorCode() != -2)) {

                            mViewBinding.rlCover.setVisibility(View.GONE);
                            mViewBinding.llSource.setVisibility(View.GONE);
                            Map<String, String> map = new HashMap<>();
                            map.put("vid", movieThirdIframeEntity.getId() + "");
                            map.put("source", movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getSource());
                            mPresenter.shieldSource(mActivity, map);

                        }
                    } catch (Exception e) {
                        mActivity.finish();
                    }

                    desotroyWebView();

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    Log.e("zy2", String.valueOf(request.getUrl()));

                    if (String.valueOf(request.getUrl()).contains(".png")
                            || String.valueOf(request.getUrl()).contains(".jpg")
                            || String.valueOf(request.getUrl()).contains(".gif")
                            || String.valueOf(request.getUrl()).contains(".jpeg")
                            || String.valueOf(request.getUrl()).contains(".webp")
                            || String.valueOf(request.getUrl()).contains(".css")
                            || String.valueOf(request.getUrl()).contains(".bmp")
                    ) {
                        return super.shouldInterceptRequest(view, request);
                    }


                    try {
                        URL realUrl = new URL(request.getUrl().toString());
                        URLConnection connection = realUrl.openConnection();
                        Map<String, List<String>> map = connection.getHeaderFields();
                        Log.e("zy", String.valueOf(map.get("Content-Type")));
                        if (map.get("Content-Type") != null) {
                            if (String.valueOf(map.get("Content-Type")).contains("mpeg")
                                    || String.valueOf(map.get("Content-Type")).contains("flv")
                                    || String.valueOf(map.get("Content-Type")).contains("3gpp")
                                    || String.valueOf(map.get("Content-Type")).contains("wmv")
                                    || String.valueOf(map.get("Content-Type")).contains("asf")
                                    || String.valueOf(map.get("Content-Type")).contains("rm")
                                    || String.valueOf(map.get("Content-Type")).contains("rmvb")
                                    || String.valueOf(map.get("Content-Type")).contains("avi")
                                    || String.valueOf(map.get("Content-Type")).contains("mov")
                                    || String.valueOf(map.get("Content-Type")).contains("dat")
                                    || String.valueOf(map.get("Content-Type")).contains("mpg")
                                    || String.valueOf(map.get("Content-Type")).contains("mp4")) {
                                Log.e("zy", "播放：" + String.valueOf(map.get("Content-Type")));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle(), request.getUrl().toString(), sniff);
                                    }
                                });
                            }

                            if(request.getUrl().toString().contains("https://cache.m.iqiyi.com")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle(), request.getUrl().toString(), sniff);
                                    }
                                });
                            }

                        }
                        return super.shouldInterceptRequest(view, request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //非vip，可直接播放.m3u8后缀
                    if (!isVip) {
                        if (String.valueOf(request.getUrl()).contains(".m3u8")) {
                            startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle(), request.getUrl().toString(), sniff);
                        }
                    }

                    return super.shouldInterceptRequest(view, request);
                }


//                @Override
//                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
//                    handler.proceed();
//                }

            });
            webView2.setWebChromeClient(new WebChromeClient());
        }


        webView2.loadUrl(url);
    }

    //使用原生播放器播放视频
    public void startPlay(String videoTitle, String videoUrl, Sniff sniff) {

        if (mActivity == null) {
            return;
        }

        if (mPresenter == null) {
            return;
        }

        //播放计数上报
        if (movieThirdIframeEntity.getId() != 0) {
            Map<String, String> map = new HashMap<>();
            map.put("vod_id", movieThirdIframeEntity.getId() + "");
            mPresenter.movieRead(mActivity, map);
        }


//        isSniffing = false;
        desotroyWebView();
        //是下载需要的视频地址
        if (sniff != null) {
            //嗅探成功后刷新数据
            sniff.videoDownloadUrl = videoUrl;
            //下载嗅探
            if (sniff instanceof DownloadSniff) {
                getPlayerExFunc().DownloadSnffingResult(sniff, true);
                return;
            }
            //选集嗅探
            if (sniff instanceof SelectSniff) {
                //设置标题
                videoTitle = movieThirdIframeEntity.getTitle() + " " + ((SelectSniff) sniff).title;
                getPlayerExFunc().SelectSniffingReuslt(sniff, true);
            }

        }
        //启动界面后及播放后嗅探得到得地址在下载列表中默认选中
        else {

//            if ((movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) || (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME))) {
            try {
                movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).setVideoDownloadUrl(videoUrl);
                movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).setSelected(1);

                //选集选中
                movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex()).setPlayed(1);
                getPlayerExFunc().refreshDownloadList();
            } catch (Exception e) {
            }

//            }


//

            startSeekNetkbUIAndTimer();
//            movieThirdIframeEntity.getDownlist().get(movieThirdIframeEntity.getDownIndex());
        }

        play();
    }

    //开启播放器
    private void play() {
        if (movieThirdIframeEntity == null) {
            MyToast.show(mActivity, "无数据");
            finish();
            return;
        }

        MovieThirdIframeEntity.DownListBean playedListBean = null;
        for (MovieThirdIframeEntity.DownListBean playedListBean2 : movieThirdIframeEntity.getDownlist()) {
            if (playedListBean2.getPlayed() == 1) {
                playedListBean = playedListBean2;
                break;
            }
        }


        if (playedListBean != null) {
            getPlayerExFunc();
            playerExFunc.setVideoUrl(playedListBean.getVideoDownloadUrl());
            playerExFunc.setSelectTVPopupWindow();

            mViewBinding.rlCover.setVisibility(View.GONE);
            mViewBinding.llSource.setVisibility(View.GONE);
            mViewBinding.player.setUp(playedListBean.getVideoDownloadUrl(), movieThirdIframeEntity.getTitle() + " " + playedListBean.getName() , Jzvd.SCREEN_FULLSCREEN);
            mViewBinding.player.startVideo();
            mViewBinding.player.setMovie();
            replay = false;
            startSeekNetkbUIAndTimer();
        }

        //保存选集播放历史
        mPresenter.savePlayed(movieThirdIframeEntity);

    }


    private void desotroyWebView() {
        try {
            if (webView2 != null) {
                // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
                // destory()
                ViewParent parent = webView2.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(webView2);
                }

                webView2.stopLoading();
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                webView2.getSettings().setJavaScriptEnabled(false);
                webView2.clearHistory();
                webView2.clearView();
                webView2.removeAllViews();

                try {
                    webView2.destroy();
                } catch (Throwable ex) {

                }

                webView2 = null;
                Log.e("zy", "desotroyWebView成功 webView = null");
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        if (netWorkStateReceiver != null) {
            unregisterReceiver(netWorkStateReceiver);
        }
        desotroyWebView();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        try {
            JzvdStd.releaseAllVideos();
        } catch (Exception e) {

        }


        if (playerExFunc != null) {
            playerExFunc.destory();
        }

        if (netTimer != null) {
            netTimer.cancel();
            netTimer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }


        super.onDestroy();

    }

    //开启计时
    private void openCover(boolean isJIEXI, Sniff sniff) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        //播放的倒计时
        if (sniff == null) {

            if (isJIEXI) {
                mViewBinding.rlCover.setVisibility(View.VISIBLE);

//            //关闭视频
//            if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING) {
//                Jzvd.releaseAllVideos();
//            }


                seconds = 0;


                RequestOptions options = new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);

                //todo 测速请求
                Glide.with(mActivity)
                        .load("http://jjlmobile.juejinchain.com/other/speed_test.png")
                        .apply(options)
                        .into(mViewBinding.ivSpeed);

                //计时
                subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .take(600)
                        .subscribe(new Subscriber<Long>() {

                            @Override
                            public void onCompleted() {
                                Log.e("zy", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
//                        Log.e("zy" , "onNext" + aLong);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        seconds++;
//                                if(isFirst) {

                                        mViewBinding.tvNext.setText("正在解析(" + seconds + "s)");
//                                }else{
//                                    mViewBinding.tvNext.setText("正在准备下一集(" + seconds + "s)");
//                                }

                                        //获取流量速度
//                                        Log.e("zy", "网络测速：" + Utils.getKByte());
                                        try {
                                            totalByte += Utils.getKByte();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        mViewBinding.tvConfirm.setVisibility(View.GONE);
                                        if (seconds >= 30) {
                                            mViewBinding.tvNext.setText("解析时间过长，是否切换播放源");
                                            mViewBinding.tvConfirm.setVisibility(View.VISIBLE);

                                            if (seconds == 30) {

                                                //嗅探网页情况下，流量大于50KB 说明网速正常，上报错误
                                                if (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME)) {
                                                    //TODO
                                                    if (mPresenter != null) {
                                                        try {
                                                            Map<String, String> map = new HashMap<>();
                                                            map.put("vod_id", movieThirdIframeEntity.getId() + "");
                                                            map.put("source", movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getSource());
                                                            mPresenter.movieSearchOutTime(mActivity, map);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }


                                        }
                                    }
                                });


                            }
                        });
            }
            //不是解析，直接播放
            else {
                mViewBinding.rlCover.setVisibility(View.GONE);

                seconds = 30;
                //计时
                subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .take(300)
                        .subscribe(new Subscriber<Long>() {

                            @Override
                            public void onCompleted() {
                                Log.e("zy", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
//                        Log.e("zy" , "onNext" + aLong);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        seconds--;
//                                        Log.e("zy", "直接播放：" + Utils.getKByte());


                                        mViewBinding.player.showCsNum(mViewBinding.tvCs, seconds);
                                        if (seconds <= 0) {
                                            //上报
                                            if (mActivity != null) {
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            mViewBinding.rlCover.setVisibility(View.GONE);
                                                            Map<String, String> map = new HashMap<>();
                                                            map.put("vid", movieThirdIframeEntity.getId() + "");
                                                            map.put("source", movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getSource());
                                                            mPresenter.shieldSource(mActivity, map);
                                                        } catch (Exception e) {
                                                            mActivity.finish();
                                                        }
                                                    }
                                                });
                                            }

                                        }
                                    }
                                });


                            }
                        });
            }
        }
        //下载嗅探的倒计时
        else {
            //计时
            subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(30)
                    .subscribe(new Subscriber<Long>() {

                        @Override
                        public void onCompleted() {
                            //webview2 ！= null说明超时
                            if (webView2 != null) {
                                desotroyWebView();

                                if (subscription != null && !subscription.isUnsubscribed()) {
                                    subscription.unsubscribe();
                                    subscription = null;
                                }

                                getPlayerExFunc().DownloadSnffingResult(sniff, false);
                                MyToast.show(PlayMovieActivity.this, "获取地址超时");

                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Long aLong) {
//
                        }
                    });
        }
    }


    /**
     * 分享判断
     */
    public void checkShareTimes() {
        int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
        ShareInfo shareInfo = book().read(PagerCons.PLAY_MOVIE_SHARE_INFO);

        if (times == 0) {
            book().write(PagerCons.PLAY_MOVIE_FIRST_TIME, System.currentTimeMillis());
        }
        int needShareCount = book().read(PagerCons.KEY_MOVIE_PLAY_COUNR_NEED_SHARE, 0);
        //分享之后才可以观看
        if (needShareCount != 0 && times == needShareCount && shareInfo != null) {
            mViewBinding.player.gotoScreenNormal();
            showForceShareDialog();
        } else {
            //24小时之后 重置分享次数
            if (System.currentTimeMillis() - (long) Paper.book().read(PagerCons.PLAY_MOVIE_FIRST_TIME,0l) >= 24 * 60 * 60 * 1000) {
                book().write(PagerCons.PLAY_MOVIE_TIMES, 1);
            } else {
                book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
            }
        }

    }

    /**
     * 分享弹框
     *
     * @param shareInfo
     */
    public void showShareDialog(ShareInfo shareInfo) {

        ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
            @Override
            public void result() {
//                shareByPlayMovieDialog.dismiss();
                int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
                book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);

            }
        });
//        dialogManage.addDialog(mShareDialog, 2);
        mShareDialog.show();

    }

//    DialogManage dialogManage = new DialogManage();

    /**
     * 强制分享蒙层
     */
    public void showForceShareDialog() {
        onPause();
        shareByPlayMovieDialog = new ForceShareByPlayMovieDialog(this);
        shareByPlayMovieDialog.setClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                ShareInfo shareInfo = book().read(PagerCons.PLAY_MOVIE_SHARE_INFO);
                if (TextUtils.isEmpty(shareInfo.getUrl())) {
                    shareByPlayMovieDialog.dismiss();
                    showShareDialog(shareInfo);
                }
            }
        });

        DialogManage.getInstance().addDialog(shareByPlayMovieDialog, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);

        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 1) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);
            int times = book().read(PagerCons.PLAY_MOVIE_TIMES, 0);
            book().write(PagerCons.PLAY_MOVIE_TIMES, times + 1);
        }

        flag = true;
        play();
    }


    //用于控制seekcomplete不让它隐藏网速加载
    private boolean replay = false;

    private PlayerExFunc getPlayerExFunc() {
        if (playerExFunc == null) {
            playerExFunc = new PlayerExFunc(PlayMovieActivity.this, movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle() , movieThirdIframeEntity.getId() + "", mViewBinding.player, movieThirdIframeEntity.getDownlist(),

                    //下载监听
                    new DownloadListPopupWindow.DownloadSniffingListener() {
                        @Override
                        public boolean startSniffing(DownloadSniff sniff2, boolean force) {

                            if (webView2 == null) {
                                if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEOANDIFRAME)) {
                                    openCover(true, sniff2);
                                    sniffing(sniff2.webUrl, sniff2);
                                    return false;
                                } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEO)) {
                                    return false;
                                } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME)) {
                                    openCover(true, sniff2);
                                    sniffing(sniff2.webUrl, sniff2);
                                    return false;
                                } else if (movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) {
                                    openCover(true, sniff2);
                                    sniffing(connectUrl + sniff2.webUrl, sniff2);
                                    return false;
                                }
                            }

                            return true;


                        }

                        @Override
                        public String getVideoName() {
                            return movieThirdIframeEntity.getTitle();
                        }
                    },
                    //选集监听
                    new SelectTVPlayPopupWindow.SelectSniffingListener() {

                        @Override
                        public void startSniffing(SelectSniff sniff3, boolean force) {
                            mViewBinding.player.showNetkb();
                            //关闭之前的嗅探
//                    if (webView2 != null) {
//                        desotroyWebView();
//                    }
                            flag = true;
//                    startSeekNetkbUIAndTimer();
//                    Jzvd.releaseAllVideos();
                            replay = true;
                            //关闭播放器，黑布开启
                            playerExFunc.closePlay();


                            //不存在地址，需要嗅探
                            if (TextUtils.isEmpty(sniff3.videoDownloadUrl)) {

                                if (webView2 == null) {
                                    if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEOANDIFRAME)) {
                                        openCover(true, sniff3);
                                        sniffing(sniff3.webUrl, sniff3);
                                    } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALVIDEO)) {

                                    } else if (movieThirdIframeEntity.getType().equals(TYPE_NORMALIFRAME)) {
                                        openCover(true, sniff3);
                                        sniffing(sniff3.webUrl, sniff3);

                                    } else if (movieThirdIframeEntity.getType().equals(TYPE_THIRDIFRAME)) {
                                        openCover(true, sniff3);
                                        sniffing(connectUrl + sniff3.webUrl, sniff3);

                                    }
                                }


                            }
                            //已有地址，无需继续嗅探
                            else {
                                startPlay(sniff3.title, sniff3.videoDownloadUrl, sniff3);

                            }
                            startSeekNetkbUIAndTimer();
                        }

                        @Override
                        public String getVideoName() {
                            return null;
                        }
                    });

        }

        return playerExFunc;
    }


    //网络状态更新
    @Subscribe
    public void changeNetState(Message message) {
        if (message.what == BusConstant.MOVIESEARCH_PLAYER_NETSTATE) {
            mViewBinding.player.changeNetState((String) message.obj);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (playerExFunc != null) {
                    playerExFunc.volumeChange(true);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                ;
                if (playerExFunc != null) {
                    playerExFunc.volumeChange(false);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

package com.newsuper.t.juejinbao.ui.movie.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBridgewebviewBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.WXRespEvent;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.PaxWebChromeClient;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.utils.WebViewUtils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebViewClient;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.smtt.sdk.WebChromeClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static io.paperdb.Paper.book;

/**
 * 网页承载activity
 */
public class BridgeWebViewActivity extends BaseActivity<PublicPresenterImpl, ActivityBridgewebviewBinding> {
    private static final String INTENT_TAG_VIDEOTITLE = "videoTitle";

    //    //登录界面跳转回来
//    public static final int RESQUESTCODE_LOGIN_JS_BACK = 100;
//
//    //登录回调JS
//    private CallBackFunction logonBackFunction;
//
//    //下载回调JS
//    private CallBackFunction downloadFunction;

    private BridgeWebView webView;
    //    private BridgeWebView webView;
    private FrameLayout fullVideo;
    private View customView = null;
//    private ProgressBar progressBar;

//    private boolean loading = false;

    WebViewUtils webViewUtils;

    ShareDialog mShareDialog;

    GestureDetector gestureDetector;

    MyWebChromeClient myWebChromeClient;

    private boolean hideLoading;


    //注入的JS
    private String getVideoJs = null;
    private String nowUrl;
    //暗webview
    private com.tencent.smtt.sdk.WebView webView2;
    private boolean isReading = false;
//    private Ad ad;

    private long startTime;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_bridgewebview;
    }

    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //是否带标题栏判断
        try {
            String title = getIntent().getStringExtra("title");
            String url = getIntent().getStringExtra("url");
            boolean showTitle = getIntent().getBooleanExtra("showTitle", false);
            if (showTitle) {
                mViewBinding.rlTitle.setVisibility(View.VISIBLE);
                mViewBinding.tvTitle.setText(title);
                mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            }
        } catch (Exception e) {
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
//        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);//开启硬件加速
        webView.setLayoutParams(params);
        webView.setBackgroundColor(getResources().getColor(R.color.white));
        mViewBinding.llWebcontain.addView(webView);

//        webView = findViewById(R.id.webView);

        fullVideo = findViewById(R.id.full_video);

        hideLoading = getIntent().getBooleanExtra("hideLoading", false);
        isReading = getIntent().getBooleanExtra("isReading", false);
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void initData() {

        if (!NetUtil.isNetworkAvailable(this)) {
            mViewBinding.loadingView.showError();
        } else {
            mViewBinding.loadingView.showContent();
        }

        try {
            if (isReading) {
                startTime = System.currentTimeMillis();
                mViewBinding.ivAdvEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (ad != null) {
//                          //  ad.show();
//                        }
                    }
                });
            } else {
                //mViewBinding.progressBar.setVisibility(View.GONE);
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        RelativeLayout loadingView = null;
        if (!hideLoading) {
            loadingView = findViewById(R.id.rl_loading);
        }

        webViewUtils = new WebViewUtils(this, null, webView, loadingView, new WebViewUtils.WebViewUtilsListener() {
            @Override
            public void logout() {
                Utils.logout(BridgeWebViewActivity.this);
                finish();
            }

            @Override
            public void showRightBottomAD(String imgUrl, String link) {

            }

            @Override
            public void getWebMovieInfo(final String url) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(getVideoJs)) {
                            getVideoJs = Utils.downloadStr(RetrofitManager.VIP_JS_URL + "/live/js/getJkkVideo.js");
                        }
                        if (!TextUtils.isEmpty(getVideoJs)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webView2Create(url);
                                }
                            });

                        }

                    }
                }).start();


            }

            @Override
            public void getWebLiveInfo(String url, String js) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView2Create(url, js);
                    }
                });
            }
        });


        webView.setWebChromeClient(myWebChromeClient = new MyWebChromeClient(mActivity));
        if (isReading) {
            webView.setWebViewClient(new BridgeWebViewClient(webView) {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (isReading) {
                            String url = request.getUrl().toString();
                            if (url.contains("read")) {
                                mViewBinding.ivAdvEnter.setVisibility(View.GONE);
                            } else {
                                if (url.startsWith("http")) {
                                    readBackUrl = url;
                                }
                            }
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (isReading) {
                        if (url.contains("read")) {
                            mViewBinding.ivAdvEnter.setVisibility(View.GONE);
                        } else {
                            if (url.startsWith("http")) {
                                readBackUrl = url;
                            }
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
        }
//        mViewBinding.x5webview.loadUrl("file:///android_asset/hhh.html");
//        webView.clearCache(true);

        Log.e("TAG", "initView:注解状态=======>>>>>>> " + url);
        webView.loadUrl(url);
//        webView.loadUrl("https://www.iqiyi.com/");
//        mViewBinding.x5webview.loadUrl("http://192.168.1.3/#/film/detail/439911");

//        gestureDetector = new GestureDetector(new MyGestureListener());
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                gestureDetector.onTouchEvent(event);
//                webView.onTouchEvent(event);
//                return true;
//            }
//        });
        mViewBinding.loadingView.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(BridgeWebViewActivity.this)) {
                    mViewBinding.loadingView.showContent();
                    webView.loadUrl(url);
                } else {
                    ToastUtils.getInstance().show(BridgeWebViewActivity.this, "请连接网络后重试");
                }

            }
        });


        //判断权限
        new RxPermissions(mActivity).request(
                Manifest.permission.READ_PHONE_STATE
        ).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: ++===============>>>>>" + e.toString());
            }

            @Override
            public void onNext(Boolean aBoolean) {
//                if (aBoolean) {
//                    //初始化推啊广告
//                    ad = new Ad("4BVnp1k1bnNGyf8ynrpC1r9KoLfJ", "304292", LoginEntity.getUserToken(), UUIDUtil.getMyUUID(mActivity));
//
//                    ad.init(mActivity, null, new AdCallBack() {
//                        @Override
//                        public void onActivityClose() {
//                            Log.i("tuia", "onActivityClose: ");
//                        }
//
//                        @Override
//                        public void onActivityShow() {
//                            Log.i("tuia", "onActivityShow: ");
//                        }
//
//                        @Override
//                        public void onRewardClose() {
//                            Log.i("tuia", "onRewardClose: ");
//                        }
//
//                        @Override
//                        public void onRewardShow() {
//                            Log.i("tuia", "onRewardShow: ");
//                        }
//
//                        @Override
//                        public void onPrizeClose() {
//                            Log.i("tuia", "onPrizeClose: ");
//                        }
//
//                        @Override
//                        public void onPrizeShow() {
//                            Log.i("tuia", "onPrizeShow: ");
//                        }
//                    });
//                }
            }
        });


        //带播放器头的bridge
        try {
            String url = getIntent().getStringExtra("url");
            boolean videoTitle = getIntent().getBooleanExtra(INTENT_TAG_VIDEOTITLE, false);
            if (videoTitle) {
                mViewBinding.player.setLiveVideoUI();
                mViewBinding.clPlay.setVisibility(View.VISIBLE);
                mViewBinding.player.setFinishListener(new Jzvd.FinishListener() {
                    @Override
                    public void clickcontain() {

                    }

                    @Override
                    public void finish() {
                        if (mViewBinding.player.screen == Jzvd.SCREEN_FULLSCREEN) {
//                            mViewBinding.player.backPress();
                        } else {
                            BridgeWebViewActivity.this.finish();
                        }
                    }
                });
                mViewBinding.player.setLiveStateListener(new Jzvd.LiveStateListener() {
                    @Override
                    public void startPlay() {
                        mViewBinding.player.hideNetkb();

                        //开启UI
                        mViewBinding.player.showUI();
                        if (netTimer != null) {
                            netTimer.cancel();
                            netTimer = null;
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                            timerTask = null;
                        }
                    }
                });
                webViewUtils.setLiveListener(new WebViewUtils.LiveListener() {
                    @Override
                    public void play(final String title, final String url, final int live) {

                        if (live == 1) {
                            startPlay(title, url);
                            mViewBinding.player.stopSeekBar();
                        }
                        //回看
                        else {
                            startPlay(title, url);
                            mViewBinding.player.startSeekBar();
                        }

                    }
                });

            }

        } catch (Exception e) {
        }


        //外部链接，需要自己控制loading
        try {
            boolean isOutUrl = getIntent().getBooleanExtra("outUrl", false);
            if (isOutUrl) {
                webView.setWebViewClient(new BridgeWebViewClient(webView) {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        mViewBinding.rlLoading.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        mViewBinding.rlLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                        // super.onReceivedSslError(view, handler, error);
                        // 接受所有网站的证书，忽略SSL错误，执行访问网页
                        handler.proceed();
                    }
                });
            }

        } catch (Exception e) {
        }
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bridgewebview);
//
//        webView = findViewById(R.id.webView);
//        fullVideo=findViewById(R.id.full_video);
//
//        webViewUtils = new WebViewUtils(this, null, webView, findViewById(R.id.rl_loading), new WebViewUtils.WebViewUtilsListener() {
//            @Override
//            public void logout() {
//                Utils.logout(BridgeWebViewActivity.this);
//                finish();
//            }
//        });
//        init3();
////        mViewBinding.x5webview.loadUrl("file:///android_asset/hhh.html");
////        webView.clearCache(true);
//        String url = getIntent().getStringExtra("url");
//        webView.loadUrl(url);
////        webView.loadUrl("https://www.iqiyi.com/");
////        mViewBinding.x5webview.loadUrl("http://192.168.1.3/#/film/detail/439911");
//    }

    @Override
    protected void onResume() {
        webViewUtils.onResume();
        super.onResume();
        Glide.with(this).load(R.drawable.ic_top_loading).into((ImageView) findViewById(R.id.progress));

        //分享回调
        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 1) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);
            webViewUtils.shareCallBack();
        }
    }

    @Override
    protected void onPause() {
        try {
            if (Jzvd.CURRENT_JZVD != null) {
                Jzvd.goOnPlayOnPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
        webViewUtils.onPause();
    }


    public static void intentMe(Context context, String url) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String url, boolean hideLoading) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("hideLoading", hideLoading);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String url, boolean hideLoading, boolean isReading) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("hideLoading", hideLoading);
        intent.putExtra("isReading", isReading);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String title, String url, boolean showTitle) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("showTitle", showTitle);
        context.startActivity(intent);
    }

    public static void intentMeForLive(Context context, String url) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra(INTENT_TAG_VIDEOTITLE, true);
        context.startActivity(intent);
    }

    public static void intentMeForHaveTitleAndNoLoading(Context context, String title, String url) {
        Intent intent = new Intent(context, BridgeWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("showTitle", true);
        intent.putExtra("hideLoading", true);
        intent.putExtra("outUrl", true);
        context.startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        myWebChromeClient.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
       // UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        webViewUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {

        try {
            doOnDestory();
        } catch (Exception e) {

        }

        super.onDestroy();

    }

    public void doOnDestory() {
        EventBus.getDefault().unregister(this);
        if (isReading) {
            long endTime = System.currentTimeMillis();
            int bookTime = (int) ((endTime - startTime) / 1000);
            //埋点（统计小说页面用户在线时间）
            Map<String, Object> time = new HashMap<>();
            time.put("bookTimeInSeconds", bookTime);
           // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.VIP_READ_ONLINE_TIME, time);
        }

        if (webViewUtils != null) {
            webViewUtils.onDestory();
        }

        if (mShareDialog != null) {
            mShareDialog.destory();
        }
        //释放内存
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
            if (webView2 != null) {
                webView2.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                webView2.clearHistory();
                ((ViewGroup) webView2.getParent()).removeView(webView2);
                webView2.destroy();
                webView2 = null;
            }

        }
        Glide.get(JJBApplication.getInstance()).clearMemory();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(JJBApplication.getInstance()).clearDiskCache();
            }
        }).start();

        //清空View资源
//        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        if(view != null) {
//            view.removeAllViews();
//        }

        try {
            JzvdStd.releaseAllVideos();
        } catch (Exception e) {

        }


        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

//        if (ad != null) {
//            ad.destroy();
//        }

    }


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    //记住最后一个非阅读页的页面，用于返回
    private String readBackUrl;


    private class MyWebChromeClient extends PaxWebChromeClient {

        public MyWebChromeClient(@NonNull Activity mActivit) {
            super(mActivit);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            Toast.makeText(mActivity , message , Toast.LENGTH_SHORT).show();
            return super.onJsAlert(view, url, message, result);
        }

        @SuppressLint("SourceLockedOrientationActivity")
        @Override
        public void onHideCustomView() {

            if (customView == null) {
                return;
            }
            fullVideo.removeView(customView);
            fullVideo.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//清除全屏

        }

        @SuppressLint("SourceLockedOrientationActivity")
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            customView = view;
            fullVideo.setVisibility(View.VISIBLE);
            fullVideo.addView(customView);
            fullVideo.bringToFront();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //显示进度条
//            if(isReading) {
//                mViewBinding.progressBar.setProgress(newProgress);
//            }
            if (newProgress == 100) {
                if (isReading && !isShowAd) {
                    Glide.with(mActivity).load(R.mipmap.icon_story_adv_enter).into(mViewBinding.ivAdvEnter);
                    mViewBinding.ivAdvEnter.setVisibility(View.VISIBLE);
                    isShowAd = true;
                }
                //加载完毕隐藏进度条
                //mViewBinding.progressBar.setVisibility(View.GONE);
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
            }
            Log.v("readprogress", newProgress + "");
        }
    }

    private boolean isShowAd = false;

    //分享弹框
    public void showShareDialog(ShareInfo shareInfo) {
        if (LoginEntity.getIsLogin()) {
            mShareDialog = new ShareDialog(this, shareInfo, new ShareDialog.OnResultListener() {
                @Override
                public void result() {
                    webViewUtils.shareCallBack();
                }
            });
            //          分享方式为空时 显示分享框
            if (TextUtils.isEmpty(shareInfo.getPlatform_type())) {
                mShareDialog.show();
            }
        } else {
            Intent intent = new Intent(this, GuideLoginActivity.class);
            startActivity(intent);
            return;
        }
    }


    /**
     * 8.0透明主题下视频全屏崩溃bug兼容
     *
     * @return
     */
    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
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


//    //右滑退出监听器
//    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if(e2.getX() -e1.getX() >100 && Math.abs(e2.getY() -e1.getY()) <100) {
//                if(webView.canGoBack()) {
//                    webView.goBack();
//                } else {
//                    finish();
//                }
//            }
//            return super.onFling(e1, e2, velocityX, velocityY);
//        }
//    }

    //暗加载webview
    private void webView2Create(String url, String getVideoJs) {
        this.getVideoJs = getVideoJs;
        webView2Create(url);
    }

    //暗加载webview
    private void webView2Create(String url) {

        if (webView2 != null) {
//            return;
        } else {

            //暗加载webview并注入
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            webView2 = new com.tencent.smtt.sdk.WebView(getApplicationContext());
            webView2.setLayoutParams(params);
            webView2.setBackgroundColor(getResources().getColor(R.color.black));
            mViewBinding.llWebcontain2.addView(webView2);

            com.tencent.smtt.sdk.WebSettings websettings = webView2.getSettings();
            websettings.setLoadsImagesAutomatically(true);
            websettings.setJavaScriptEnabled(true);
            websettings.setUseWideViewPort(true);
            websettings.setSupportMultipleWindows(true);
            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
            websettings.setAppCacheEnabled(true);
            websettings.setDomStorageEnabled(true);
//        websettings.setBlockNetworkImage(false); // 解决图片不显示
            websettings.setBlockNetworkImage(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView2.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
            }

            //与js弹框交互
            websettings.setJavaScriptEnabled(true);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webView2.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
                @Override
                public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
                    super.onPageStarted(webView, s, bitmap);
                    nowUrl = url;
                }

                @Override
                public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                    super.onPageFinished(webView, s);

                    if (!TextUtils.isEmpty(nowUrl)) {
                        nowUrl = null;
                        webView2.evaluateJavascript(getVideoJs, new com.tencent.smtt.sdk.ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.e("zy", "value=" + s);
                            }
                        });

//                                ToastUtils.getInstance().show(mActivity, "强行注入");

                    }

                }

                //                @Override
//                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                    super.onPageStarted(view, url, favicon);
//                    nowUrl = url;
//                }
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    if (!TextUtils.isEmpty(nowUrl)) {
//                        nowUrl = null;
//                        webView2.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String value) {//js与native交互的回调函数
//                                Log.e("zy", "value=" + value);
//                            }
//                        });
//                    }
//                }
//            });
//
//            webView2.setWebChromeClient(new WebChromeClient() {
//                @Override
//                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
////                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    operate(message);
//                    result.cancel();
//                    return super.onJsAlert(view, url, message, result);
//                }
//
//                @Override
//                public void onProgressChanged(WebView view, int newProgress) {
//                    super.onProgressChanged(view, newProgress);
//                }
            });

        }

        webView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
                operate(s1);
                jsResult.cancel();
//                    return super.onJsAlert(view, url, message, result);
                return super.onJsAlert(webView, s, s1, jsResult);
            }
        });

        webView2.loadUrl(url);

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        //延迟15秒强行注入
        final String delayUrl = url;


        //热门时间段
        int intoForce;
        int totalHour = 0;
        try {
            totalHour = Integer.parseInt(StringUtils.getDateToHour(System.currentTimeMillis()));
        } catch (Exception e) {
        }
        int nowHour = totalHour % 24;

        if (nowHour >= 19 && nowHour < 23) {
            intoForce = 10;
        } else {
            intoForce = 5;
        }

        //计时
        subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(intoForce)
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {
                        if (!TextUtils.isEmpty(nowUrl)) {
                            if (nowUrl.equals(delayUrl)) {
                                nowUrl = null;
                                webView2.evaluateJavascript(getVideoJs, new com.tencent.smtt.sdk.ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {
                                        Log.e("zy", "value=" + s);
                                    }
                                });

//                                ToastUtils.getInstance().show(mActivity, "强行注入");

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                    }

                });

    }

    //注入计时
    private Subscription subscription;

    //方法解析
    private void operate(String json) {
        Log.e("zy", json);
        try {
            webViewUtils.crawlCallback(json);
        } catch (Exception e) {
        }

    }

    @Subscribe
    public void onWXRespEvent(WXRespEvent wxRespEvent) {
        //initData();
        webView.loadUrl("javascript:window.getMiniProgramWalk()");
    }


    //使用原生播放器播放视频
    public void startPlay(String videoTitle, String videoUrl) {
        startSeekNetkbUIAndTimer();
        //加载一个网页以停止原来的播放
//        mViewBinding.llWebcontain.setVisibility(View.GONE);
        mViewBinding.clPlay.setVisibility(View.VISIBLE);

        mViewBinding.player.setUp(videoUrl, videoTitle);
//        Glide.with(mActivity).load(imgUrl).into(mViewBinding.player.thumbImageView);

        mViewBinding.player.setVisibility(View.VISIBLE);
        mViewBinding.player.startVideo();
    }


    //网速定时器
    Timer netTimer;
    TimerTask timerTask;

    //开启加载中时的网速显示
    private void startSeekNetkbUIAndTimer() {
        mViewBinding.player.showNetkb();
        mViewBinding.player.setNetkb((int) Utils.getKByte());

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
                        mViewBinding.player.setNetkb((int) Utils.getKByte());
                    }
                });

            }
        };
        netTimer.schedule(timerTask, 1000, 1000);
    }

}

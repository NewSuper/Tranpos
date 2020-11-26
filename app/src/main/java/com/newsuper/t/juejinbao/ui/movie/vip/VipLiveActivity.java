package com.newsuper.t.juejinbao.ui.movie.vip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityVipliveBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebViewClient;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.Jzvd;

/**
 * 全网VIP -直播-CCTV网络电视-播放详情
 */
public class VipLiveActivity extends BaseActivity<PublicPresenterImpl, ActivityVipliveBinding> {
    private BridgeWebView webView;

    //网页全屏设置
    private FrameLayout fullVideo;
    private View customView = null;

    private long startTime;
    private long endTime;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Build.VERSION.SDK_INT == Build.VERSION_CODES.O ? R.style.AppTheme_NoActionBar : R.style.AppTheme_Transparent);


        Utils.getKByte();
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
//            boolean result = fixOrientation();
//        }
        startTime = System.currentTimeMillis();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_viplive;
    }


    @Override
    public void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        init();
    }

    private void init() {
        fullVideo = mViewBinding.fullVideo;

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebSettings websettings = webView.getSettings();
        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        websettings.setLoadsImagesAutomatically(true);
        websettings.setJavaScriptEnabled(true);
        websettings.setUseWideViewPort(true);
        websettings.setSupportMultipleWindows(true);
        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setAppCacheEnabled(true);
        websettings.setDomStorageEnabled(true);
        websettings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new BridgeWebViewClient(webView));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Toast.makeText(VipLiveActivity.this, message, Toast.LENGTH_SHORT).show();
                result.cancel();
                operate(message);
                return super.onJsAlert(view, url, message, result);
            }

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

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                customView = view;
                fullVideo.setVisibility(View.VISIBLE);
                fullVideo.addView(customView);
                fullVideo.bringToFront();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
            }
        });


    }

    @Override
    public void initData() {
        webView.loadUrl(getIntent().getStringExtra("url"));
        startPlay("", getIntent().getStringExtra("src"), getIntent().getStringExtra("picture"));
    }

    public static void intentMe(Context context, String src, String url, String picture, String type) {
        Intent intent = new Intent(context, VipLiveActivity.class);
        intent.putExtra("src", src);
        intent.putExtra("picture", picture);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        context.startActivity(intent);
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
        }catch (Exception e){e.printStackTrace();}
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.CURRENT_JZVD != null) {
            if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                Jzvd.backPress();
            } else {
                Jzvd.releaseAllVideos();
                webView.loadUrl("javascript:setIndex(" + 0 + ")");
            }
        } else {
            webView.loadUrl("javascript:setIndex(" + 0 + ")");
        }
    }

    @Override
    protected void onResume() {
        try {
            if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PAUSE) {
                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.goOnPlayOnResume();
                } else {
                    Jzvd.goOnPlayOnResume();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        endTime = System.currentTimeMillis();
        //埋点（统计直播页面用户在线时间）
        int alertTime = (int) ((endTime-startTime)/1000);
        Map<String, Object> time = new HashMap<>();
        time.put("alertTimeInSeconds",alertTime);
        //MobclickAgent.onEventObject(MyApplication.getContext(), EventID.VIP_LIVE_ONLINE_TIME, time);

        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();

            try {
                webView.destroy();
            } catch (Throwable ex) {

            }

            webView = null;

            //清空View资源
//            ViewGroup view = (ViewGroup) getWindow().getDecorView();
//            view.removeAllViews();
        }

        //释放内存
//        if (webView != null) {
//            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            webView.clearHistory();
//
//            ((ViewGroup) webView.getParent()).removeView(webView);
//            webView.destroy();
//            webView = null;
//        }

        //清空View资源
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();

        //释放饺子播放器
        Jzvd.releaseAllVideos();

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出H5界面

    }

    private void operate(String json) {
        try {
            String type = "";
            JSONObject data = null;

            JSONObject jsonObject = new JSONObject(json);
            type = jsonObject.optString("type");
            data = jsonObject.optJSONObject("data");


            //拉起原生播放
            if (type.equals("play")) {
                if (data != null) {
                    String src = data.optString("src");
                    String picture = data.optString("picture");
                    int live = data.optInt("live", 0);

                    if (!TextUtils.isEmpty(src)) {
                        mViewBinding.player.setLiveVideoUI();
                        mViewBinding.clPlay.setVisibility(View.VISIBLE);
                        mViewBinding.player.setFinishListener(new Jzvd.FinishListener() {
                            @Override
                            public void clickcontain() {

                            }

                            @Override
                            public void finish() {
                                VipLiveActivity.this.finish();
                            }
                        });

                        mViewBinding.player.setLiveStateListener(new Jzvd.LiveStateListener() {
                            @Override
                            public void startPlay() {
                                mViewBinding.rlBack.setVisibility(View.GONE);
                                //开启UI
                                mViewBinding.player.showUI();

                                mViewBinding.player.hideNetkb();
                                if(netTimer != null){
                                    netTimer.cancel();
                                    netTimer = null;
                                }
                                if(timerTask != null){
                                    timerTask.cancel();
                                    timerTask = null;
                                }
                            }
                        });


                        //直播
                        if (live == 1) {
                            startPlay("", src, picture);
                            mViewBinding.player.stopSeekBar();
                        }
                        //回看
                        else {
                            startPlay("", src, picture);
                            mViewBinding.player.startSeekBar();
                        }
                    }
                }
//                    startPlay("", src, picture);

            }else if(type.equals("closeLive")){
                finish();
            }


        } catch (JSONException e) {
        }
    }

    //使用原生播放器播放视频
    public void startPlay(String videoTitle, String videoUrl, String imgUrl) {
        startSeekNetkbUIAndTimer();
        //加载一个网页以停止原来的播放
//        mViewBinding.llWebcontain.setVisibility(View.GONE);
        mViewBinding.clPlay.setVisibility(View.VISIBLE);
        mViewBinding.player.loadingProgressBar.setVisibility(View.GONE);
        mViewBinding.player.setUp(videoUrl, videoTitle);
        Glide.with(mActivity).load(imgUrl).into(mViewBinding.player.thumbImageView);

        mViewBinding.player.setVisibility(View.VISIBLE);
        mViewBinding.player.startVideo();
    }

//    /**
//     * 8.0透明主题下视频全屏崩溃bug兼容
//     *
//     * @return
//     */
//    private boolean isTranslucentOrFloating() {
//        boolean isTranslucentOrFloating = false;
//        try {
//            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
//            final TypedArray ta = obtainStyledAttributes(styleableRes);
//            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
//            m.setAccessible(true);
//            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
//            m.setAccessible(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isTranslucentOrFloating;
//    }
//
//    private boolean fixOrientation() {
//        try {
//            Field field = Activity.class.getDeclaredField("mActivityInfo");
//            field.setAccessible(true);
//            ActivityInfo o = (ActivityInfo) field.get(this);
//            o.screenOrientation = -1;
//            field.setAccessible(false);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public void setRequestedOrientation(int requestedOrientation) {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
//            return;
//        }
//        super.setRequestedOrientation(requestedOrientation);
//    }

    //网速定时器
    Timer netTimer;
    TimerTask timerTask;

    //开启加载中时的网速显示
    private void startSeekNetkbUIAndTimer(){
        mViewBinding.player.showNetkb();
        mViewBinding.player.setNetkb((int) Utils.getKByte());

        if(netTimer != null){
            netTimer.cancel();
            netTimer = null;
        }
        if(timerTask != null){
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
        netTimer.schedule(timerTask ,1000 , 1000);
    }

}

package com.newsuper.t.juejinbao.ui.movie.vip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityVipplayBinding;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.jsbridge.BridgeWebViewClient;
import com.juejinchain.android.module.movie.bean.YXL;
import com.juejinchain.android.module.movie.presenter.impl.VipPlayImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.YXLDialog;
import com.ys.network.base.BaseActivity;
import com.ys.network.network.RetrofitManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 废弃
 */
public class VipPlayActivity extends BaseActivity<VipPlayImpl, ActivityVipplayBinding> implements VipPlayImpl.MvpView {
    private String title;
    private String url;

    private BridgeWebView webView;


    //注入的js
//    private String videoJs = "";

    //当前页面的url
    private String nowUrl = null;

    //网页全屏设置
    private FrameLayout fullVideo;
    private View customView = null;


    //云线路选择弹框
    private YXLDialog yxlDialog;
    List<YXL> yxls = null;

    //云线路
    private String connectUrl = null;
    //注入的JS
    private String getVideoJs = null;


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
        }

        try {
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) mViewBinding.rlAlert.getLayoutParams();
            param.setMargins(0 , getStateBarHeight() + Utils.dip2px(mActivity , 40) , 0 , 0);
            mViewBinding.rlAlert.setLayoutParams(param);
        } catch (Exception e) {
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vipplay;
    }

    @Override
    public void initView() {

        mViewBinding.tvAlert.setText(Html.fromHtml("<font color=\"#FFAA00\">如果遇到卡顿15秒以上无法播放，请切换线路试试</font><font color=\"#ffffff\"></font>"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        mViewBinding.tvTitle.setText(title);

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

        mViewBinding.llYxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yxlDialog == null) {
                    if (yxls != null) {
                        yxlDialog = new YXLDialog(mActivity, yxls, new YXLDialog.ChangeYXL() {
                            @Override
                            public void change(String name, String url) {
                                mViewBinding.tvYxl.setText(name);
                                connectUrl = url;
                                createUrl();
                            }
                        });
                    } else {
                        return;
                    }
                }
                yxlDialog.show();
            }
        });

        mViewBinding.rlAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.rlAlert.setVisibility(View.GONE);
            }
        });

    }

    private void init() {
        fullVideo = mViewBinding.fullVideo;

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
        websettings.setBlockNetworkImage(true); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
//        websettings.setBlockNetworkImage(true );

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Toast.makeText(VipPlayActivity.this, message, Toast.LENGTH_SHORT).show();
                result.cancel();
                operate(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onHideCustomView() {

                if (customView == null){
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
        mPresenter.requestYXL(mActivity);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getVideoJs = download(RetrofitManager.VIP_JS_URL + "/live/js/getVideo.js");
                if (!TextUtils.isEmpty(getVideoJs)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createUrl();
                        }
                    });

                }

            }
        }).start();
    }

    public static void intentMe(Context context, String title, String url) {
        Intent intent = new Intent(context, VipPlayActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {

        //释放内存
//        if (webView != null) {
//            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            webView.clearHistory();
//
//            ((ViewGroup) webView.getParent()).removeView(webView);
//            webView.destroy();
//            webView = null;
//        }
//
//        //清空View资源
//        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        view.removeAllViews();
//
//        //释放饺子播放器
//        Jzvd.releaseAllVideos();

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
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.removeAllViews();
        }

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

    //请求云线路返回
    @Override
    public void requestYXL(List<YXL> yxls) {
        this.yxls = yxls;
        if(yxls.size() > 0){
            connectUrl = yxls.get(0).url;
            createUrl();
        }
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
        if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING) {
            if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                Jzvd.backPress();
            } else {
                Jzvd.releaseAllVideos();
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void error() {

    }

    private class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            nowUrl = url;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

//            webView.getSettings().setBlockNetworkImage(true); // 解决图片不显示
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
//        }

            if (!TextUtils.isEmpty(nowUrl)) {
                nowUrl = null;
                webView.evaluateJavascript(getVideoJs, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {//js与native交互的回调函数
                        Log.e("zy", "value=" + value);
                    }
                });
            }

        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("zy", url);
            return super.shouldOverrideUrlLoading(view, url);
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.e("zy2", String.valueOf(request.getUrl()));


            if( String.valueOf(request.getUrl()).contains(".png")
                    ||  String.valueOf(request.getUrl()).contains(".jpg")
                    ||  String.valueOf(request.getUrl()).contains(".gif")
                    ||  String.valueOf(request.getUrl()).contains(".jpeg")
                    ||  String.valueOf(request.getUrl()).contains(".webp")
                    ||  String.valueOf(request.getUrl()).contains(".css")
                    ||  String.valueOf(request.getUrl()).contains(".bmp")
            ){
//                return super.shouldInterceptRequest(view, request);
                String response = "<html><html>";
                WebResourceResponse webResourceResponse = new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(response.getBytes()));
                return webResourceResponse;
            }

            try {
                URL realUrl = new URL(request.getUrl().toString());
                URLConnection connection = realUrl.openConnection();
                Map<String, List<String>> map = connection.getHeaderFields();
                Log.e("zy" , String.valueOf(map.get("Content-Type")));
                if(map.get("Content-Type") != null ){
                    if(String.valueOf(map.get("Content-Type")).contains("mpeg")
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
                        Log.e("zy" , "播放：" + String.valueOf(map.get("Content-Type")));
                        VipPlayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startPlay("", request.getUrl().toString(), null);
                            }
                        });
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            if (String.valueOf(request.getUrl()).contains(".jpg")
//                    || String.valueOf(request.getUrl()).contains(".png")
//                    || String.valueOf(request.getUrl()).contains(".gif")
//                    || String.valueOf(request.getUrl()).contains(".bmp")
//                    || String.valueOf(request.getUrl()).contains(".jpeg")
//                    || String.valueOf(request.getUrl()).contains(".webp")) {
//                String response = "<html><html>";
//                WebResourceResponse webResourceResponse = new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(response.getBytes()));
//                return webResourceResponse;
//            }
//
//            boolean ad;
//            if (!loadedUrls.containsKey(url)) {
//                ad = AdBlocker.isAd(url);
//                loadedUrls.put(url, ad);
//            } else {
//                ad = loadedUrls.get(url);
//            }
//            return ad ? AdBlocker.createEmptyResource() :
//                    super.shouldInterceptRequest(view, url);

            return super.shouldInterceptRequest(view, request);
        }


    }


    public String download(String urlStr) {
//        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        URL url = null;
        try {
            // 创建一个URL对象
            url = new URL(urlStr);
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
//            // 使用IO流读取数据
//            buffer = new BufferedReader(new InputStreamReader(
//                    urlConn.getInputStream()));
//            while ((line = buffer.readLine()) != null) {
//                sb.append(line);
//            }
            byte[] buff = new byte[2048];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = urlConn.getInputStream().read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            getVideoJs = fromFile.toString();
            urlConn.getInputStream().close();
            fromFile.close();


        } catch (Exception e) {
            System.out.println("download:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                System.out.println("download buffer.close():" + e.getMessage());
                e.printStackTrace();
            }
        }
        return getVideoJs;
    }


    //方法解析
    private void operate(String json) {
        try {
            String type = "";
            JSONObject data = null;

            JSONObject jsonObject = new JSONObject(json);
            type = jsonObject.optString("type");
            data = jsonObject.optJSONObject("data");


            //拉起原生播放
            if (type.equals("play")) {
                JSONObject params = data.optJSONObject("params");
                if (params != null) {
                    String src = params.optString("src");
                    String picture = params.optString("picture");
                    startPlay("", src, picture);
                }
            }


        } catch (JSONException e) {
        }

    }

//    public static class YXL extends EasyAdapter.TypeBean {
//        public String name = "";
//        public String url = "";
//        public boolean select = false;
//    }

    //加载网页
    public void createUrl() {
        mViewBinding.llWebcontain.setVisibility(View.VISIBLE);
        mViewBinding.player.setVisibility(View.INVISIBLE);
        Jzvd.releaseAllVideos();
        mViewBinding.clPlay.setVisibility(View.GONE);

        if (connectUrl != null && getVideoJs != null) {
//            Toast.makeText(mActivity, connectUrl + url, Toast.LENGTH_SHORT).show();
            webView.loadUrl(connectUrl + url);
        }
    }

    //使用原生播放器播放视频
    public void startPlay(String videoTitle, String videoUrl, String imgUrl) {
        //加载一个网页以停止原来的播放
        mViewBinding.llWebcontain.setVisibility(View.GONE);
        mViewBinding.clPlay.setVisibility(View.VISIBLE);

//        jzvdPlayer.setUp(src, playerModel.getTitle(), Jzvd.SCREEN_NORMAL);
//        Glide.with(context).load(playerModel.getImg_url().get(1)).into(jzvdPlayer.thumbImageView);
//        jzvdPlayer.startVideo();

        mViewBinding.player.setUp(videoUrl, videoTitle);
        if(imgUrl != null) {
            Glide.with(mActivity).load(imgUrl).into(mViewBinding.player.thumbImageView);
        }
//        mViewBinding.imgVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewBinding.player.setVisibility(View.VISIBLE);
//                mViewBinding.player.startVideo();
//
//            }
//        });
        mViewBinding.player.setVisibility(View.VISIBLE);


        mViewBinding.player.setStateListener(new JzvdStd.StateListener() {
            @Override
            public void preparing() {

            }

            @Override
            public void startPlay() {
                mViewBinding.player.gotoScreenFullscreen();
            }

            @Override
            public void pause() {

            }

            @Override
            public void endPlay() {

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

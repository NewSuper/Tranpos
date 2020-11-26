package com.newsuper.t.juejinbao.ui.movie.vip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityVipwebdetaiBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayMovieActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieThirdIframeEntity;
import com.newsuper.t.juejinbao.ui.movie.view.PlayTeachDialog;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebViewClient;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * VIP-全网vip-腾讯视频
 */
public class VipWebActivity extends BaseActivity<PublicPresenterImpl, ActivityVipwebdetaiBinding> {
    private String title;
    private String url;

    private BridgeWebView webView;

    private PlayTeachDialog playTeachDialog;

    //注入的js
    private String videoJs = "";

    //当前页面的url
    private String nowUrl = null;

    //页面标识
    private String tag = "";


    //是否需要注入解析 1为注入解析 0不需要注入解析，采取网页嗅探视频功能
    private int is_resolving = 0;

    //网页全屏设置
    private FrameLayout fullVideo;
    private View customView = null;


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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vipwebdetai;
    }

    @Override
    public void initView() {
        Glide.with(mActivity).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);

        fullVideo = mViewBinding.fullVideo;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        tag = getIntent().getStringExtra("tag");
        is_resolving = getIntent().getIntExtra("is_resolving", 0);


        if (is_resolving == 1) {
            mViewBinding.rl1.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.rl1.setVisibility(View.GONE);
        }

        mViewBinding.tvTitle.setText(title);
        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();//返回上个页面
                } else {
                    finish();
                }
            }
        });

        init();


        mViewBinding.tvTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playTeachDialog == null) {
                    playTeachDialog = new PlayTeachDialog(mActivity);
                }
                playTeachDialog.show();
//                BridgeWebViewActivity.intentMe(mActivity ,  "https://www.hao123.com");
            }
        });

//        mViewBinding.llYxl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (yxlDialog == null) {
//                    yxlDialog = new YXLDialog(mActivity);
//                }
//                yxlDialog.show();
//            }
//        });

//        if (isPlay) {
//            mViewBinding.rl1.setVisibility(View.GONE);
//        } else {
//            mViewBinding.rl2.setVisibility(View.GONE);
//        }

    }

    @SuppressLint("WrongConstant")
    private void init() {

//        if (is_resolving == 1) {

            WebSettings websettings = webView.getSettings();
//            websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            websettings.setLoadsImagesAutomatically(true);
//            websettings.setJavaScriptEnabled(true);
//            websettings.setUseWideViewPort(true);
//            websettings.setSupportMultipleWindows(true);
//            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//            websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//            websettings.setAppCacheEnabled(true);
//            websettings.setDomStorageEnabled(true);
//
//
//            WebSettings s = webView.getSettings();
//            s.setBuiltInZoomControls(true);
//            s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            s.setUseWideViewPort(true);
//            s.setLoadWithOverviewMode(true);
//            s.setSavePassword(true);
//            s.setSaveFormData(true);
//            s.setJavaScriptEnabled(true);     // enable navigator.geolocation
//            s.setGeolocationEnabled(true);
//            s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
//            s.setDomStorageEnabled(true);
//            webView.requestFocus();
//            webView.setScrollBarStyle(0);


            websettings.setDomStorageEnabled(true);
            websettings.setBlockNetworkImage(false); // 解决图片不显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
            }

            //与js弹框交互
            websettings.setJavaScriptEnabled(true);
            websettings.setJavaScriptCanOpenWindowsAutomatically(true);

            webView.setWebViewClient(new MyWebViewClient(webView));
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Toast.makeText(VipWebActivity.this, message, Toast.LENGTH_SHORT).show();
                    operate(message);
                    return super.onJsAlert(view, url, message, result);
                }

                @Override
                public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(VipWebActivity.this);
                    builder.setMessage(message);
                    builder.setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        result.confirm();
                    });
                    builder.setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                        result.cancel();
                    });
                    builder.setCancelable(false);
                    builder.setOnCancelListener(DialogInterface::dismiss);
                    builder.create().show();

                    return true;
//                    return super.onJsConfirm(view, url, message, result);
                }

                @Override
                public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                    return super.onJsPrompt(view, url, message, defaultValue, result);
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
//        } else {
//
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zy", "123");
    }

    @Override
    public void initData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://192.168.1.3:150/js/parse.js")
//                .build();

        if (is_resolving == 1) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    videoJs = download(RetrofitManager.VIP_JS_URL + "/live/js/parse.js");
//                Log.e("zy" , videoJs);
                    if (!TextUtils.isEmpty(videoJs)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(webView != null) {
                                    webView.loadUrl(url);
                                }
                            }
                        });

                    }

                }
            }).start();
        } else {
            if(webView != null) {
                webView.loadUrl(url);
            }
        }
    }

    public static void intentMe(Context context, String title, String url, String tag, int is_resolving) {
        Intent intent = new Intent(context, VipWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("tag", tag);
        intent.putExtra("is_resolving", is_resolving);
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

    private class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }


//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            return shouldOverrideUrlLoading(view, request.getUrl().toString());
//        }

//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//
////            super.onReceivedSslError(view, handler, error);
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            nowUrl = url;
            Log.e("zy" , url);
            mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mViewBinding.imgLoadingBg.setVisibility(View.GONE);

//            Log.e("zy", "url");
//
//            String jsStr = "";
//            try {
//                InputStream in = getAssets().open("video.js");
//                byte buff[] = new byte[2048];
//                ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
//                do {
//                    int numRead = in.read(buff);
//                    if (numRead <= 0) {
//                        break;
//                    }
//                    fromFile.write(buff, 0, numRead);
//                } while (true);
//                jsStr = fromFile.toString();
//                in.close();
//                fromFile.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            String js = "var newscript = document.createElement(\"script\");";
//            js += "newscript.src=\"http://192.168.1.3:150/js/parse.js\";";
//            js += "newscript.onload=function(){xxx();};";  //xxx()代表js中某方法
//            js += "document.body.appendChild(newscript);";
//


            if (is_resolving == 1) {

                if (!TextUtils.isEmpty(nowUrl)) {
                    nowUrl = null;
                    webView.evaluateJavascript(videoJs, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {//js与native交互的回调函数
                            Log.e("zy", "value=" + value);
                        }
                    });
                }
            }
        }


        @Override
        // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
            if(url.contains("hntvmobile://")||url.contains("imgotv://")||url.contains("tbopen://")) {
                return true;
            }else{
                return false;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

            //VIP注入的，不需要嗅探
            if(is_resolving == 1){
                return super.shouldInterceptRequest(view, request);
            }

            if(tag.equals("拍看看") || tag.equals("8点直播")){
                return super.shouldInterceptRequest(view, request);
            }

            if(tag.equals("体育直播")){
                if( String.valueOf(request.getUrl()).contains(".m3u8")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            List<MovieThirdIframeEntity.SourceListBean> listBeans = new ArrayList<>();
                            MovieThirdIframeEntity.SourceListBean sourceListBean = new MovieThirdIframeEntity.SourceListBean();
                            sourceListBean.setUrl(request.getUrl().toString());
                            sourceListBean.setTitle("");
                            sourceListBean.setSource("");
                            listBeans.add(sourceListBean);

                            MovieThirdIframeEntity movieThirdIframeEntity = new MovieThirdIframeEntity();
                            movieThirdIframeEntity.setTitle("");
                            movieThirdIframeEntity.setCurrentIndex(0);
                            movieThirdIframeEntity.setType("normalVideo");

                            movieThirdIframeEntity.setSourceList(listBeans);
                            book().write(PagerCons.KEY_PLAYMOVIE_DATA , JSON.toJSONString(movieThirdIframeEntity));
                            PlayMovieActivity.intentMe(mActivity  , true);

//                                startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle() , request.getUrl().toString());
                        }
                    });
                }

                return super.shouldInterceptRequest(view, request);
            }


            if( String.valueOf(request.getUrl()).contains(".png")
                    ||  String.valueOf(request.getUrl()).contains(".jpg")
                    ||  String.valueOf(request.getUrl()).contains(".gif")
                    ||  String.valueOf(request.getUrl()).contains(".jpeg")
                    ||  String.valueOf(request.getUrl()).contains(".webp")
                    ||  String.valueOf(request.getUrl()).contains(".css")
                    ||  String.valueOf(request.getUrl()).contains(".bmp")
            ){
                return super.shouldInterceptRequest(view, request);
            }



            try {
                URL realUrl = new URL(request.getUrl().toString());
                URLConnection connection = realUrl.openConnection();
                Map<String, List<String>> map = connection.getHeaderFields();
                Log.e("zy" , String.valueOf(map.get("Content-Type")));
                Log.e("zy" , request.getUrl().toString());
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
                        Log.e("zy" , "播放：" + request.getUrl().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                List<MovieThirdIframeEntity.SourceListBean> listBeans = new ArrayList<>();
                                MovieThirdIframeEntity.SourceListBean sourceListBean = new MovieThirdIframeEntity.SourceListBean();
                                sourceListBean.setUrl(request.getUrl().toString());
                                sourceListBean.setTitle("");
                                sourceListBean.setSource("");
                                listBeans.add(sourceListBean);

                                MovieThirdIframeEntity movieThirdIframeEntity = new MovieThirdIframeEntity();
                                movieThirdIframeEntity.setTitle("");
                                movieThirdIframeEntity.setCurrentIndex(0);
                                movieThirdIframeEntity.setType("normalVideo");

                                movieThirdIframeEntity.setSourceList(listBeans);

//                                PlayMovieActivity.intentMe(mActivity , JSON.toJSONString(movieThirdIframeEntity) , true);

//                                startPlay(movieThirdIframeEntity.getTitle() + " " + movieThirdIframeEntity.getSourceList().get(movieThirdIframeEntity.getCurrentIndex()).getTitle() , request.getUrl().toString());
                            }
                        });
                    }

                }
                return super.shouldInterceptRequest(view, request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.shouldInterceptRequest(view, request);


//            Log.e("zy2" , String.valueOf(request.getUrl()));


//            if(String.valueOf(request.getUrl()).contains("hntvmobile://")) {
//
//                String response = "<html><html>";
//
//                WebResourceResponse webResourceResponse = new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(response.getBytes()));
//
//
//                return webResourceResponse;
//            }




//            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
                if(dataBean != null) {
                    for(String ignore : dataBean.getIgnore_vip_parse()){
                        if (request.getUrl().toString().startsWith(ignore)) {
                            return true;
                        }
                    }

                }
            }

            return super.shouldOverrideUrlLoading(view, request);
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
            byte buff[] = new byte[2048];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = urlConn.getInputStream().read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            videoJs = fromFile.toString();
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
        return videoJs;
    }


    //方法解析
    private void operate(String json) {
        try {
            String type = "";
            JSONObject data = null;

            JSONObject jsonObject = new JSONObject(json);
            type = jsonObject.optString("type");
            data = jsonObject.optJSONObject("data");


            //2.1返回超级影院
            if (type.equals("returnCinema")) {
                finish();
            }
            //2.2跳转播放网页
            else if (type.equals("toPlay")) {
                String title = data.optString("title");
                String url = data.optString("url");

//                VipWebActivity.intentMe(mActivity, title, url, true);
//                VipPlayActivity.intentMe(mActivity, title, url);
                MovieThirdIframeEntity movieThirdIframeEntity = new MovieThirdIframeEntity();
                movieThirdIframeEntity.setTitle(title);
                movieThirdIframeEntity.setCurrentIndex(0);
                movieThirdIframeEntity.setType("thirdIframe");

                MovieThirdIframeEntity.SourceListBean sourceListBean = new MovieThirdIframeEntity.SourceListBean();
                sourceListBean.setTitle("");
                sourceListBean.setSource("");
                sourceListBean.setUrl(url);

                List<MovieThirdIframeEntity.SourceListBean> listBeans = new ArrayList<>();
                listBeans.add(sourceListBean);

                movieThirdIframeEntity.setSourceList(listBeans);
                book().write(PagerCons.KEY_PLAYMOVIE_DATA , JSON.toJSONString(movieThirdIframeEntity));
                PlayMovieActivity.intentMe(this, true);

            }


        } catch (JSONException e) {
        }

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

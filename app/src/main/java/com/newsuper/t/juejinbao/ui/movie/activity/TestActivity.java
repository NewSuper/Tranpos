package com.newsuper.t.juejinbao.ui.movie.activity;//package com.juejinchain.android.module.movie.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.content.res.TypedArray;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.juejinchain.android.R;
//import com.juejinchain.android.databinding.ActivityPlayvideoBinding;
//import com.juejinchain.android.databinding.ActivityTestaBinding;
//import com.juejinchain.android.jsbridge.BridgeWebView;
//import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
//import com.juejinchain.android.module.movie.utils.ParseWebUrlHelper;
//import com.juejinchain.android.module.movie.vip.VipPlayActivity;
//import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
//import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//import com.ys.network.base.BaseActivity;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.List;
//import java.util.Map;
//
//public class TestActivity extends BaseActivity<PublicPresenterImpl, ActivityTestaBinding> {
//    private BridgeWebView webView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//
//
//    }
//
//    @Override
//    public boolean setStatusBarColor() {
//        return false;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_testa;
//    }
//
//    @Override
//    public void initView() {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        webView = new BridgeWebView(getApplicationContext());
//        webView.setLayoutParams(params);
//        ((LinearLayout)findViewById(R.id.ll_webcontain)).addView(webView);
//
//        WebSettings websettings = webView.getSettings();
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        websettings.setLoadsImagesAutomatically(true);
//        websettings.setJavaScriptEnabled(true);
//        websettings.setUseWideViewPort(true);
//        websettings.setSupportMultipleWindows(true);
//        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        websettings.setAppCacheEnabled(true);
//        websettings.setDomStorageEnabled(true);
//        websettings.setBlockNetworkImage(false); // 解决图片不显示
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
//        }
//
//        //与js弹框交互
//        websettings.setJavaScriptEnabled(true);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        webView.setWebViewClient(new WebViewClient(){
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                Log.e("zy2", String.valueOf(request.getUrl()));
//
//
//                try {
//                    URL realUrl = new URL(request.getUrl().toString());
//                    URLConnection connection = realUrl.openConnection();
//                    Map<String, List<String>> map = connection.getHeaderFields();
//                    Log.e("zy" , String.valueOf(map.get("Content-Type")));
//                    if(map.get("Content-Type") != null ){
//                        if(String.valueOf(map.get("Content-Type")).contains("mpeg")
//                                || String.valueOf(map.get("Content-Type")).contains("flv")
//                                || String.valueOf(map.get("Content-Type")).contains("3gpp")
//                                || String.valueOf(map.get("Content-Type")).contains("wmv")
//                                || String.valueOf(map.get("Content-Type")).contains("asf")
//                                || String.valueOf(map.get("Content-Type")).contains("rm")
//                                || String.valueOf(map.get("Content-Type")).contains("rmvb")
//                                || String.valueOf(map.get("Content-Type")).contains("avi")
//                                || String.valueOf(map.get("Content-Type")).contains("mov")
//                                || String.valueOf(map.get("Content-Type")).contains("dat")
//                                || String.valueOf(map.get("Content-Type")).contains("mpg")
//                                || String.valueOf(map.get("Content-Type")).contains("mp4")) {
//                            Log.e("zy" , "播放：" + String.valueOf(map.get("Content-Type")));
//                            TestActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    startPlay("" , String.valueOf(request.getUrl()) , "");
//                                }
//                            });
//                        }
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return super.shouldInterceptRequest(view, request);
//            }
//
//        });
//        webView.setWebChromeClient(new WebChromeClient());
//
//
//
//    }
//
//    @Override
//    public void initData() {
//        webView.loadUrl("https://www.baidu.com");
//    }
//
//    //使用原生播放器播放视频
//    public void startPlay(String videoTitle, String videoUrl, String imgUrl) {
//        //加载一个网页以停止原来的播放
//        mViewBinding.llWebcontain.setVisibility(View.GONE);
//        mViewBinding.clPlay.setVisibility(View.VISIBLE);
//
////        jzvdPlayer.setUp(src, playerModel.getTitle(), Jzvd.SCREEN_NORMAL);
////        Glide.with(context).load(playerModel.getImg_url().get(1)).into(jzvdPlayer.thumbImageView);
////        jzvdPlayer.startVideo();
//
//        mViewBinding.player.setUp(videoUrl, videoTitle);
//        if(imgUrl != null) {
//            Glide.with(mActivity).load(imgUrl).into(mViewBinding.player.thumbImageView);
//        }
////        mViewBinding.imgVideo.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mViewBinding.player.setVisibility(View.VISIBLE);
////                mViewBinding.player.startVideo();
////
////            }
////        });
//        mViewBinding.player.setVisibility(View.VISIBLE);
//        mViewBinding.player.startVideo();
//    }
//
//
//
//    public static void intentMe(Context context) {
//        Intent intent = new Intent(context, TestActivity.class);
//        context.startActivity(intent);
//    }
//
//
//
//}

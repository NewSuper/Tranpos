package com.newsuper.t.juejinbao.ui.movie.activity;//package com.juejinchain.android.module.movie.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.webkit.ClientCertRequest;
//import android.webkit.CookieManager;
//import android.webkit.CookieSyncManager;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebStorage;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.juejinchain.android.R;
//import com.juejinchain.android.databinding.AcitivtyX5webviewBinding;
//import com.juejinchain.android.module.movie.presenter.impl.X5WebViewImpl;
//import com.ys.network.base.BaseActivity;
//
//
////
////import android.annotation.SuppressLint;
////import android.content.Context;
////import android.content.Intent;
////import android.graphics.PixelFormat;
////import android.os.Build;
////import android.os.Bundle;
////import android.support.annotation.RequiresApi;
////import android.view.KeyEvent;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.Toast;
////
////import com.alibaba.fastjson.JSON;
////import com.juejinchain.android.R;
////import com.juejinchain.android.databinding.AcitivtyX5webviewBinding;
////import com.ys.network.base.PagerCons;
////import com.juejinchain.android.module.login.activity.LoginActivity;
////import com.ys.network.base.LoginEntity;
////import com.juejinchain.android.module.movie.presenter.impl.X5WebViewImpl;
////import com.juejinchain.android.module.movie.utils.X5Ack;
////import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
////import com.tencent.smtt.sdk.WebChromeClient;
////import com.tencent.smtt.sdk.WebSettings;
////import com.tencent.smtt.sdk.WebView;
////import com.tencent.smtt.sdk.WebViewClient;
////import com.ys.network.base.BaseActivity;
////
////import io.paperdb.Paper;
////
//public class X5WebViewActivity extends BaseActivity<X5WebViewImpl, AcitivtyX5webviewBinding> implements X5WebViewImpl.MvpView{
//    @Override
//    public boolean setStatusBarColor() {
//        return false;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.acitivty_x5webview;
//    }
//
//    @Override
//    public void initView() {
//
//        mViewBinding.wb.resumeTimers();
//
//        WebSettings websettings = mViewBinding.wb.getSettings();
//
//        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        websettings.setLoadsImagesAutomatically(true);
//        websettings.setSupportMultipleWindows(true);
//        websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        websettings.setBuiltInZoomControls(false);
//        websettings.setSupportZoom(false);
//        websettings.setAppCacheEnabled(true);
//        websettings.setDomStorageEnabled(true);
//
////        websettings.setTextZoom(100);//防止系统字体大小影响布局
//
//        //与js弹框交互
//        websettings.setJavaScriptEnabled(true);
//        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        mViewBinding.wb.setWebChromeClient(new WebChromeClient());
//
//        mViewBinding.wb.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//
//
//            @Override
//            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
//                super.onReceivedClientCertRequest(view, request);
//            }
//        });
//        mViewBinding.wb.loadUrl("https://www.hao123.com");
//    }
//
//    @Override
//    public void initData() {
//
//
//    }
//
//    public static void intentMe(Context context, String url) {
//        Intent intent = new Intent(context, X5WebViewActivity.class);
//        intent.putExtra("url", url);
//        context.startActivity(intent);
//    }
//
//
//}
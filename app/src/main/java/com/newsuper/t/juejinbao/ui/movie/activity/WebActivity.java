package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityNewwebBinding;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.module.movie.presenter.impl.WebActivityImpl;
import com.ys.network.base.BaseActivity;

import static io.paperdb.Paper.book;

public class WebActivity extends BaseActivity<WebActivityImpl, ActivityNewwebBinding> implements WebActivityImpl.MvpView {

    private String title;
    private String url;

    private BridgeWebView webView;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(Build.VERSION.SDK_INT == Build.VERSION_CODES.O ? R.style.AppTheme_NoActionBar : R.style.AppTheme_Transparent);

        try{
            boolean screenH = getIntent().getBooleanExtra("screenH" , false);

            if(screenH){
                //直接横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

        }catch (Exception e){}
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newweb;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewBinding.tvTitle.setText(title);
        init();



        webView.loadUrl(url);
    }

    @Override
    public void initData() {

    }

    private void init(){
        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);//开启硬件加速

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

        //与js弹框交互
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

    }


    public static void intentMe(Context context, String title ,  String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void intentMe(Context context, String title ,  String url , boolean screenH) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("screenH", screenH);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {

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

            //清空View资源
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.removeAllViews();

            webView = null;
        }

//        //释放内存
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

        super.onDestroy();
    }



}

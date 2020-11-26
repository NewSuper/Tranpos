package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityNewwebbookBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.WebActivityImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.PaxWebChromeClient;
import com.newsuper.t.juejinbao.ui.movie.utils.WebViewUtils;
import com.newsuper.t.juejinbao.utils.NetworkUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.jsbridge.BridgeWebView;

import static io.paperdb.Paper.book;

public class WebBookActivity extends BaseActivity<PublicPresenterImpl, ActivityNewwebbookBinding> implements WebActivityImpl.MvpView {

    private String url;

    private BridgeWebView webView;

    WebViewUtils webViewUtils;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newwebbook;
    }

    @Override
    public void initView() {

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NetworkUtils.isConnected(WebBookActivity.this)){
                    ToastUtils.getInstance().show(mActivity,"请连接网络后重试");
                }else {
                    mViewBinding.loading.showLoading();
                    webView.loadUrl(url);
                }

            }
        });

        url = getIntent().getStringExtra("url");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        webViewUtils = new WebViewUtils(this, null, webView, findViewById(R.id.rl_loading), new WebViewUtils.WebViewUtilsListener() {
            @Override
            public void logout() {

            }

            @Override
            public void showRightBottomAD(String imgUrl, String link) {

            }

            @Override
            public void getWebMovieInfo(final String url) {

            }

            @Override
            public void getWebLiveInfo(String url, String js) {

            }
        });

        init();

        //无网络
        if(!NetworkUtils.isConnected(this)){
            mViewBinding.loading.showError();
        }else{
            webView.loadUrl(url);
        }

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

        webView.setWebChromeClient(new PaxWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mViewBinding.loading.showContent();
            }
        });

    }


    public static void intentMe(Context context,  String url) {
        Intent intent = new Intent(context, WebBookActivity.class);
        intent.putExtra("url", url);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上个页面
            return true;
        }else{
            new AlertDialog.Builder(mActivity)
                    .setCancelable(true)
                    .setMessage("确认退出阅读?")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        finish();

                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }

        return true;
    }

}

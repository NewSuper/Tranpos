package com.newsuper.t.consumer.function.top.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormFragment  extends Fragment {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_web_view,null);
        ButterKnife.bind(this,view);
        String title = getArguments().getString("title");
        toolbar.setTitleText(title);
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {

            }
        });

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
        webView.setVerticalScrollBarEnabled(true);//允许垂直滚动

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//       webView.addJavascriptInterface(new AndroidLog(),"AndroidLog");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                LogUtil.log("WeiWebView","shouldOverrideUrlLoading == "+s);
                return false;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
                LogUtil.log("WeiWebView","sslError == "+sslError.getPrimaryError());
//                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                LogUtil.log("WeiWebView","onReceivedError == "+webResourceError.getErrorCode());
                super.onReceivedError(webView, webResourceRequest, webResourceError);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtil.log("WeiWebView","newProgress == "+newProgress);
                if (newProgress != 100){
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }

            }
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                new AlertDialog.Builder(getContext()).
                        setTitle("Alert").setMessage(s1).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).create().show();
                jsResult.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                jsResult.confirm();
                return super.onJsConfirm(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                jsPromptResult.confirm();
                return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {

            }

            @Override
            public void onHideCustomView() {

            }

        });
        String url = getArguments().getString("url");
        if (!url.startsWith("http")){
            url = RetrofitManager.BASE_URL_H5 + url;
        }
        String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
        //链接加入coookie ，值为token
        syncCookie(url,cookie);
        LogUtil.log("WeiWebView",url);
        webView.loadUrl(url);
        return view;
    }
    /**
     * 将cookie同步到WebView
     * @param url WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(String url,String cookie) {
        CookieSyncManager.createInstance(getContext());
        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptThirdPartyCookies(webView, true);
        cm.setAcceptCookie(true);
        cm.removeAllCookie();
        cm.setCookie(url, cookie);
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
            return;
        }
        getActivity().onBackPressed();
    }
    @Override
    public void onDestroy() {
        if (webView != null){
            webView.destroy();
        }
        super.onDestroy();
    }

}

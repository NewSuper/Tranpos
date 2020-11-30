package com.newsuper.t.consumer.function.person.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebHistoryItem;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import static com.xunjoy.lewaimai.consumer.manager.RetrofitManager.BASE_URL_H5;

/**
 * 签到,积分，抽奖
 */
public class SignActivity extends BaseActivity {
    //签到
    private String url_sign =  BASE_URL_H5 + "h5/lwm/waimai/mine_sign_up?";
    //积分
    private String url_integral = BASE_URL_H5 + "h5/lwm/waimai/mine_accumulate?";
    //抽奖
    private String url_draw = BASE_URL_H5 + "h5/lwm/waimai/mine_prize?";

    private String url_guangwang = "https://www.lewaimai.com/";
    private WebView webView;
    private int type;
    CustomToolbar toolbar;
    ProgressBar progressBar;
    String url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        webView = (WebView)findViewById(R.id.webview);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setMenuText("");
        toolbar.setTitleText("");
        toolbar.setBackgroundColor(Color.parseColor("#FB797B"));
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                if (webView.canGoBack())
                    onWebViewGoBack();
                else
                    finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
        webView.setVerticalScrollBarEnabled(true);//允许垂直滚动
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                LogUtil.log("SignActivity","shouldOverrideUrlLoading == "+s);
                webView.loadUrl(s);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
                LogUtil.log("SignActivity","sslError == "+sslError.getPrimaryError());
//                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                getWebTitle();
            }
        });

        type = getIntent().getIntExtra("type",0);
        if (type == 1){
            url = getIntent().getStringExtra("url");
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            //链接加入coookie ，值为token
            syncCookie(url,cookie);
            LogUtil.log("SignActivity",type + "  url == "+url);
            webView.loadUrl(url);
        }else if (type == 2){
            url = url_integral;
            String s = "admin_id=" + SharedPreferencesUtil.getAdminId();
            url = url + s;
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            //链接加入coookie ，值为token
            syncCookie(url,cookie);
            LogUtil.log("SignActivity",type + "  url == "+url);
            webView.loadUrl(url);
        }else if (type == 3){
            url = getIntent().getStringExtra("url");
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            //链接加入coookie ，值为token
            syncCookie(url,cookie);
            LogUtil.log("SignActivity",type + "  url == "+url);
            webView.loadUrl(url);
        }else if (type == 4){
            url = url_draw +"&admin_id="+ Const.ADMIN_ID;
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            //链接加入coookie ，值为token
            syncCookie(url,cookie);
            LogUtil.log("SignActivity",type + "  url == "+url);
            webView.loadUrl(url);
        }else if (type == 5){
            url = url_guangwang;
            webView.loadUrl(url);
        }

    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }
    /**
     * 将cookie同步到WebView
     * @param url WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(String url,String cookie) {
        if (StringUtils.isEmpty(url)){
            return;
        }
        CookieSyncManager.createInstance(this);
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

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
            return;
        }
        if (type == 1|| type == 2){
            setResult(RESULT_OK);
            finish();
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        if (webView != null){
            webView.destroy();
        }
        super.onDestroy();
    }

    private void getWebTitle(){
        if (type == 5){
            toolbar.setTitleText("乐外卖官网");
            toolbar.setVisibility(View.VISIBLE);
            return;
        }
        WebBackForwardList forwardList = webView.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            toolbar.setTitleText(item.getTitle());
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    private void onWebViewGoBack(){
        webView.goBack();
        getWebTitle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                onWebViewGoBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

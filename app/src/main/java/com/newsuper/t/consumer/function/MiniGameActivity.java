package com.newsuper.t.consumer.function;

import android.widget.ProgressBar;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.UserEntity;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UserUtils;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.LinkedHashMap;
import java.util.Map;

//接入腾讯小游戏
public class MiniGameActivity extends BaseActivity {
    @BindView(R.id.pb_view)
    ProgressBar progressbar;
    @BindView(R.id.webview)
    WebView webView;

    private String key = "345aa0489d66441ba8f63f37646b5a1a";
    private int channel = 12737;
    @Override
    public void initData() {
        setContentView(R.layout.activity_mini_game);
    }

    @Override
    public void initView() {
        setup();
    }
    private void setup() {
        com.tencent.smtt.sdk.WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new MyWebViewClient());
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }
        });
        webView.loadUrl(getUrl());
    }

    private String getUrl(){
        UserEntity userInfo = UserUtils.getUserInfo();
        String userId = SharedPreferencesUtil.getUserId();
        if(userInfo == null){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        try {

            builder.append("http://www.shandw.com/auth?");

            Map<String, String> param = new LinkedHashMap<>();
            param.put("channel", channel + "");
            param.put("openid", userId);
            param.put("time", System.currentTimeMillis() / 1000 + "");
            param.put("nick", URLEncoder.encode(userInfo.getName(), "UTF-8"));
            param.put("avatar", userInfo.getCardImage());
            param.put("sex", userInfo.getSex() + "");
            param.put("phone", "");
            param.put("sign", getSign(userInfo, userId));
            param.put("sdw_simple", "1");
            param.put("sdw_ld", "1");


            if (param == null) {
                return builder.toString();
            }
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                if(!"sdw_ld".equals(entry.getKey())) {
                    builder.append("&");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return builder.toString();
        }
    }

    private String getSign(UserEntity userInfo, String userId) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("channel", channel + "");
        param.put("openid", userId);
        param.put("time", System.currentTimeMillis() / 1000 + "");
        param.put("nick", userInfo.getName());
        param.put("avatar", userInfo.getCardImage());
        param.put("sex", userInfo.getSex() + "");
        param.put("phone", "");

        StringBuilder builder = new StringBuilder();
        if (param == null) {
            return builder.toString();
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            if(!"phone".equals(entry.getKey())) {
                builder.append("&");
            }
        }
        builder.append(key);
        return StringUtils.md5(builder.toString()).toLowerCase();

    }

    private class MyWebViewClient extends com.tencent.smtt.sdk.WebChromeClient {
        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
            new AlertDialog.Builder(getApplicationContext()).
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
        public boolean onJsConfirm(WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
            jsResult.confirm();
            return super.onJsConfirm(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, com.tencent.smtt.export.external.interfaces.JsPromptResult jsPromptResult) {
            jsPromptResult.confirm();
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(progressbar == null){
                return;
            }
            if (newProgress == 100) {
                //  progressbar.setVisibility(GONE);
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            View normalView = webView;
            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
            viewGroup.removeView(normalView);
            viewGroup.addView(view);
            myVideoView = view;
            myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (webView != null) {
                webView.destroy();
            }
        }catch (Exception e){

        }
    }

}

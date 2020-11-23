package com.newsuper.t.juejinbao.ui.movie.vip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.databinding.ActivityAlertwebBinding;
import com.juejinchain.android.databinding.ActivityVipliveBinding;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.jsbridge.BridgeWebViewClient;
import com.juejinchain.android.module.ad.BaoquGameActivity;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.jzvd.Jzvd;

import static io.paperdb.Paper.book;

/**
 * 全网VIP -直播-CCTV网络电视
 */
public class AlertWebActivity extends BaseActivity<PublicPresenterImpl, ActivityAlertwebBinding> {

    private BridgeWebView webView;

    String url = "";

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
        return R.layout.activity_alertweb;
    }

    @Override
    public void initView() {

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewBinding.tvTitle.setText(getIntent().getStringExtra("title"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new BridgeWebView(getApplicationContext());
        webView.setLayoutParams(params);
        mViewBinding.llWebcontain.addView(webView);

        url = getIntent().getStringExtra("url");
        init();
    }

    private void init() {

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
//                Toast.makeText(AlertWebActivity.this, message, Toast.LENGTH_SHORT).show();
                result.cancel();
                operate(message);
                return super.onJsAlert(view, url, message, result);
            }
        });


        webView.loadUrl(url);

    }

    @Override
    public void initData() {

    }

    public static void intentMe(Context context, String title, String url) {
        Intent intent = new Intent(context, AlertWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        //释放内存
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }

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


            //跳转直播详情
            if (type.equals("toLiveDetail")) {
                if (LoginEntity.getIsLogin()) {;
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }


                String src = data.optString("src");
                String picture = data.optString("picture");
                String url = data.optString("url");
                String location = data.optString("type");
//                    startPlay("", src, picture);

                VipLiveActivity.intentMe(mActivity , src , url , picture , location);
            }


        } catch (JSONException e) {
        }
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

}

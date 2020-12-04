package com.newsuper.t.consumer.function.top.avtivity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.top.fragment.WeiWebFragment;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeiWebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_web_view);
        WeiWebFragment weiWebFragment = new WeiWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",getIntent().getStringExtra("url"));
        weiWebFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,weiWebFragment).commitAllowingStateLoss();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
package com.newsuper.t.juejinbao.ui.home.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityWebBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.ys.network.base.BaseActivity;

public class WebActivity extends BaseActivity<PublicPresenterImpl, ActivityWebBinding> {

    private String url;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        mViewBinding.webView.loadUrl(url);
    }


    @Override
    public void initData() {

    }
}

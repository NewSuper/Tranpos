package com.newsuper.t.juejinbao.ui.home.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityWebBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;


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

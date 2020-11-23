package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityWebmoviedetailBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.ys.network.base.BaseActivity;

public class JSWebMovieDetailActivity extends BaseActivity<PublicPresenterImpl , ActivityWebmoviedetailBinding> {

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webmoviedetail;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void intentMe(Context context, String title, String url) {
        Intent intent = new Intent(context, JSWebMovieDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}

package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityWebmoviedetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;


public class JSWebMovieDetailActivity extends BaseActivity<PublicPresenterImpl, ActivityWebmoviedetailBinding> {

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

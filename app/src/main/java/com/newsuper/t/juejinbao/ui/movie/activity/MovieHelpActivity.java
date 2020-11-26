package com.newsuper.t.juejinbao.ui.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMoviehelpBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

/**
 * 影视教程
 */
public class MovieHelpActivity extends BaseActivity<PublicPresenterImpl, ActivityMoviehelpBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_moviehelp;
    }

    @Override
    public void initView() {
        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    public static void intentMe(Activity activity) {
        Intent intent = new Intent(activity, MovieHelpActivity.class);
        activity.startActivity(intent);
    }


}

package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMoviesearchMzsmBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

/**
 * 影视搜索免责声明
 */
public class MovieSearchMZSMActivity extends BaseActivity<PublicPresenterImpl, ActivityMoviesearchMzsmBinding> {

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
        return R.layout.activity_moviesearch_mzsm;
    }

    @Override
    public void initView() {

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    public static void intentMe(Context context){

        if (!ClickUtil.isNotFastClick()) {
            return;
        }


        Intent intent = new Intent(context , MovieSearchMZSMActivity.class);
        context.startActivity(intent);
    }

}

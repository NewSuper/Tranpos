package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityMoviehelpBinding;
import com.juejinchain.android.databinding.ActivityScreenteachBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

/**
 * 投屏教程
 */
public class ScreenTeachActivity extends BaseActivity<PublicPresenterImpl, ActivityScreenteachBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        setTheme(Build.VERSION.SDK_INT == Build.VERSION_CODES.O ? R.style.AppTheme_NoActionBar : R.style.AppTheme_Transparent);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_screenteach;
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


    public static void intentMe(Context context){
        Intent intent = new Intent(context , ScreenTeachActivity.class);
        context.startActivity(intent);
    }

}

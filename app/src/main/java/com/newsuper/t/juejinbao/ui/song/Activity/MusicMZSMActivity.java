package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityMusicMzsmBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.utils.ClickUtil;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

public class MusicMZSMActivity extends BaseActivity<PublicPresenterImpl , ActivityMusicMzsmBinding> {

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
        return R.layout.activity_music_mzsm;
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


        Intent intent = new Intent(context , MusicMZSMActivity.class);
        context.startActivity(intent);
    }

}

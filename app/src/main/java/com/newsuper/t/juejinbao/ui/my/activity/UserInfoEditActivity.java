package com.newsuper.t.juejinbao.ui.my.activity;

import android.os.Bundle;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityUserInfoEditBinding;
import com.juejinchain.android.module.my.presenter.impl.UserInfoEditPresenterMmpl;
import com.ys.network.base.BaseActivity;

public class UserInfoEditActivity extends BaseActivity<UserInfoEditPresenterMmpl, ActivityUserInfoEditBinding> {

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info_edit;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}

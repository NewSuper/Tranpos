package com.newsuper.t.juejinbao.ui.my.activity;

import android.os.Bundle;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityUserInfoEditBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.my.presenter.impl.UserInfoEditPresenterMmpl;

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

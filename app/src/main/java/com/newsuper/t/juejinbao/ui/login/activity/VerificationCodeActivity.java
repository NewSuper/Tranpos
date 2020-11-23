package com.newsuper.t.juejinbao.ui.login.activity;

import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.databinding.ActivityVerificationCodeBinding;
import com.juejinchain.android.module.movie.activity.BridgeWebViewActivity;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.utils.androidUtils.StatusBarUtil;

public class VerificationCodeActivity extends BaseActivity<BasePresenter, ActivityVerificationCodeBinding> {

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification_code;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public void initData() {
        mViewBinding.activityVerificationCodeBar.moduleIncludeTitleBarTitle.setText("收不到验证码");
        mViewBinding.activityVerificationCodeBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewBinding.activityVerificationCodeCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AwardFeedback?from=nomessage
                //BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.CALL_SERVICE);
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_FEED_BACK);
            }
        });
    }
}

package com.newsuper.t.juejinbao.ui.login.activity;

import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityVerificationCodeBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;


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

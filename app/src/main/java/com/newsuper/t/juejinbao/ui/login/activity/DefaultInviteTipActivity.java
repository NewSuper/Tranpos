package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityDefaultInviteTipBinding;
import com.juejinchain.android.module.login.entity.DefaultIntviteCodeEntity;
import com.juejinchain.android.module.login.presenter.DefaultInvitePresenter;
import com.juejinchain.android.module.login.presenter.impl.DefaultInvitePresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.BaseApplication;
import com.ys.network.utils.ToastUtils;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.io.Serializable;
import java.util.HashMap;

public class DefaultInviteTipActivity extends BaseActivity<DefaultInvitePresenterImpl, ActivityDefaultInviteTipBinding> implements DefaultInvitePresenter.View {

    public static final String INVITE_CODE = "inviteCode";
    private String inviterCode;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default_invite_tip;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarLeftTitle.setText("没有邀请人？");
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ClipboardUtil.Clip(mActivity,"123456");
                Intent intent = new Intent();
                intent.putExtra(INVITE_CODE,inviterCode);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        // 原有渠道不提供官方邀请码
        if(
                BaseApplication.getChannel().equals("normal")||
                        BaseApplication.getChannel().equals("vivo")||
                        BaseApplication.getChannel().equals("yingyongbao")||
                        BaseApplication.getChannel().equals("other")
        ){
            mViewBinding.tvDesc.setText("【邀请码】是掘金宝为会员提供的专属编码，新会员需要有老会员的邀请码推荐才能在掘金宝得到会员身份，并获得会员权益。\n" +
                    "您可以百度搜索“掘金宝邀请码”发现您身边的会员，快来一起体验掘金宝的会员生活吧。");
            mViewBinding.btnCopy.setVisibility(View.GONE);
            mViewBinding.tvInviteCode.setVisibility(View.GONE);
        }else {
            mPresenter.getDefaultInviteCode(new HashMap<>(),mActivity);
            mViewBinding.tvDesc.setText("如果没有邀请人，无法获得邀请码，你可以输入掘 金宝官方邀请码。");
            mViewBinding.tvInviteCode.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDefaultInviteCodeSuccess(Serializable serializable) {
        DefaultIntviteCodeEntity defaultIntviteCodeEntity = (DefaultIntviteCodeEntity) serializable;
        if(defaultIntviteCodeEntity.getCode()==0){
            if(!TextUtils.isEmpty(defaultIntviteCodeEntity.getData().get(0))){
                inviterCode = defaultIntviteCodeEntity.getData().get(0);
                mViewBinding.tvInviteCode.setText("官方邀请：" + defaultIntviteCodeEntity.getData().get(0));
                mViewBinding.tvInviteCode.setVisibility(View.VISIBLE);
                mViewBinding.btnCopy.setVisibility(View.VISIBLE);
            }else {
                mViewBinding.tvDesc.setText("【邀请码】是掘金宝为会员提供的专属编码，新会员需要有老会员的邀请码推荐才能在掘金宝得到会员身份，并获得会员权益。\n" +
                        "您可以百度搜索“掘金宝邀请码”发现您身边的会员，快来一起体验掘金宝的会员生活吧。");
                mViewBinding.tvInviteCode.setText(View.GONE);
                mViewBinding.btnCopy.setVisibility(View.GONE);
            }

        }else {
            ToastUtils.getInstance().show(mActivity,defaultIntviteCodeEntity.getMsg());
        }
    }

    @Override
    public void onError(String s) {

    }
}

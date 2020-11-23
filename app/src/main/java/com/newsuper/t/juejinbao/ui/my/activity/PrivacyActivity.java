package com.newsuper.t.juejinbao.ui.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityPrivacyBinding;
import com.juejinchain.android.module.home.entity.GetCoinEntity;
import com.juejinchain.android.module.home.entity.RewardEntity;
import com.juejinchain.android.module.home.ppw.ActicleRewardPop;
import com.juejinchain.android.module.my.entity.UserInfoEntity;
import com.juejinchain.android.module.my.presenter.PrivacyPresenter;
import com.juejinchain.android.module.my.presenter.impl.PrivacyPresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.ToastUtils;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.io.Serializable;
import java.util.HashMap;

public class PrivacyActivity extends BaseActivity<PrivacyPresenterImpl, ActivityPrivacyBinding> implements PrivacyPresenter.View {

    private static final String ENTRY_TYPE = "entryType";
    public static final int REQUEST_CODE_TASK = 0x1;
    private int entryType;


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    public void initView() {

        entryType = getIntent().getIntExtra(ENTRY_TYPE, 0);


        StatusBarUtil.setStatusBarDarkTheme(this, true);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("隐私设置");
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {


        mViewBinding.cbPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("edit_field", "switch_show_mobile");
                map.put("value", mViewBinding.cbPhone.isChecked() ? "1" : "0");
                mPresenter.settingPrivacy(map, PrivacyActivity.this);
            }
        });

        mViewBinding.cbWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("edit_field", "switch_show_wechat_number");
                map.put("value", mViewBinding.cbWeChat.isChecked() ? "1" : "0");
                mPresenter.settingPrivacy(map, PrivacyActivity.this);
            }
        });

        mViewBinding.cbWeChatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("edit_field", "switch_show_wx_qrcode");
                map.put("value", mViewBinding.cbWeChatGroup.isChecked() ? "1" : "0");
                mPresenter.settingPrivacy(map, PrivacyActivity.this);
            }
        });

        mViewBinding.cbQqGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("edit_field", "switch_show_qq_qrcode");
                map.put("value", mViewBinding.cbQqGroup.isChecked() ? "1" : "0");
                mPresenter.settingPrivacy(map, PrivacyActivity.this);
            }
        });
        mPresenter.getUserInfo(mActivity);
    }

    public static void intentMe(Context context) {
        Intent intent = new Intent(context, PrivacyActivity.class);
        context.startActivity(intent);
    }

    public static void intentMe(Activity context, int type, int requestCode) {
        Intent intent = new Intent(context, PrivacyActivity.class);
        intent.putExtra(ENTRY_TYPE, type);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void getUserInfoSuccess(Serializable serializable) {
        UserInfoEntity userInfo = (UserInfoEntity) serializable;
        if (userInfo.getData() == null)
            return;
        mViewBinding.cbPhone.setChecked(userInfo.getData().getShow_mobile_to_uplevel() == 1);
        mViewBinding.cbWeChat.setChecked(userInfo.getData().getShow_wechat_number_to_uplevel() == 1);
        mViewBinding.cbWeChatGroup.setChecked(userInfo.getData().getShow_wx_qrcode_to_uplevel() == 1);
        mViewBinding.cbQqGroup.setChecked(userInfo.getData().getShow_qq_qrcode_to_uplevel() == 1);


        if (entryType == 1) {
            mViewBinding.cbPhone.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.cbPhone.setChecked(true);
                    mViewBinding.cbWeChat.setChecked(true);
                    mViewBinding.cbWeChatGroup.setChecked(true);
                    mViewBinding.cbQqGroup.setChecked(true);
                    mPresenter.setAllSwitch(mActivity);
                }
            }, 0);


        }

    }

    @Override
    public void setAllSwitchBack(Serializable serializable) {
        GetCoinEntity entity = (GetCoinEntity) serializable;

        if (entity.getCode() == 0) {
            if (entity.getData().getCoin() != 0) {

                RewardEntity rewardEntity = new RewardEntity(
                        "奖励到账",
                        entity.getData().getCoin(),
                        "隐私设置奖励",
                        "",
                        true
                );
                ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
                acticleRewardPop.setView(rewardEntity);
                acticleRewardPop.showPopupWindow();

            } else {
                ToastUtils.getInstance().show(mActivity, entity.getMsg());
            }
//            finish();
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

    @Override
    public void settingPrivacySuccess(Serializable serializable) {
    }

    @Override
    public void showError(String s) {
    }
}

package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivitySetPasswordBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.LoginStateEvent;
import com.newsuper.t.juejinbao.ui.home.ppw.TimeRewardPopup;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.impl.SetPasswordPresenterImpl;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.paperdb.Paper;

public class SetPasswordActivity extends BaseActivity<SetPasswordPresenterImpl, ActivitySetPasswordBinding> implements SetPasswordPresenterImpl.SetPasswordView {
    private String phone;
    private Context context = this;
    private String token;
    private int pswType = 0;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        phone = getIntent().getStringExtra("phone");
        token = getIntent().getStringExtra("token");

    }

    @Override
    public void initData() {
        //初始化手机号
        mViewBinding.activitySetPasswordPhone.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        if (getIntent().getIntExtra("isShow", 0) == 1) {
            double coin = getIntent().getDoubleExtra("bindCode", 250);
            TimeRewardPopup timeRewardPopup = new TimeRewardPopup(this);
            timeRewardPopup.setBindPhoneView((int) coin);
            timeRewardPopup.showPopupWindow();
        }
    }

    @OnClick({R.id.activity_set_password_code_chage, R.id.activity_set_password_commit,
            R.id.activity_set_password_qq, R.id.module_include_title_bar_return})
    public void onClick(View view) {
        switch (view.getId()) {
            //修改
            case R.id.activity_set_password_code_chage:
                pswType = 1;
                //初始化密码
                mViewBinding.activitySetPasswordCode.setEnabled(true);
                mViewBinding.activitySetPasswordCode.setText(phone.replaceAll("(\\d{0})\\d{5}(\\d{6})", ""));
                break;
            //提交
            case R.id.activity_set_password_commit:
                if (mViewBinding.activitySetPasswordCode.getText().toString().trim().length() < 6) {
                    MyToast.showToast("请输入6-20位密码");
                    return;
                }
                initCommit();
                break;
            //不符合当前逻辑，暂时去掉
            case R.id.activity_set_password_qq:
                break;
            case R.id.module_include_title_bar_return:
                finish();
                break;


        }
    }

    private void initCommit() {
        Map<String, String> map = new HashMap<>();
        map.put("user_token", token);
        map.put("password", pswType == 0 ? phone.substring(phone.length() - 6) : mViewBinding.activitySetPasswordCode.getText().toString().trim());
        mPresenter.SetPsw(map, this);
    }


    //QQ登录
    @Override
    public void showOtherSuccess(Serializable serializable) {

    }

    //绑定邀请码
    @Override
    public void showBindCodeSuccess(Serializable serializable) {
        BindInviterEntity bindInviterEntity = (BindInviterEntity) serializable;
        if (bindInviterEntity.getCode() == 0) {
           LoginEntity loginEntity =  Paper.book().read(PagerCons.USER_DATA);
           if(loginEntity!=null){
               loginEntity.getData().setInviter_uid(bindInviterEntity.getData().getInviter_uid() + "");
           }

            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
            configDialog.dismiss();
            finish();
            MyToast.showToast("登陆成功");
        } else {
            MyToast.showToast(bindInviterEntity.getMsg());
        }

    }

    //设置密码
    @Override
    public void showSetPswSuccess(Serializable serializable) {
        LoginEntity loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            loginEntity.setLogin(true);
            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);
//            if (loginEntity.getData().getIs_new() == 1 && loginEntity.getData().getInviter_uid().length() == 0) {
//                initDialog();
//                return;
//            }
            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
            finish();
        }
    }

    private FragmentMyConfigDialog configDialog;

    //输入邀请码
    private void initDialog() {
        configDialog = new FragmentMyConfigDialog(this, 5);
        configDialog.setCanceledOnTouchOutside(false);
        configDialog.setCancelable(false);
        configDialog.setOnDialogClickListener(new FragmentMyConfigDialog.OnDialogClickListener() {
            @Override
            public void onOKClick(String code) {
                if (!TextUtils.isEmpty(code)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("invitation_code", code);
                    mPresenter.BindCode(map, mActivity);
                } else {
                    MyToast.showToast("请输入邀请码");
                }
            }

            @Override
            public void onCancelClick() {
                EventBus.getDefault().post(new LoginStateEvent(true));
                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
                configDialog.dismiss();
                finish();
            }
        });
        configDialog.show();
    }

    @Override
    public void showError(String s) {

    }
}

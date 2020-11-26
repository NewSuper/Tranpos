package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivitySetAndChangePswBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.LoginStateEvent;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.PhoneIsRegistEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.SetAndChangePresenter;
import com.newsuper.t.juejinbao.ui.login.presenter.impl.SetAndChangePresenterImpl;
import com.newsuper.t.juejinbao.ui.my.dialog.ConfigDialog;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.RxjavaTimer;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.functions.Action0;

public class SetAndChangePswActivity extends BaseActivity<SetAndChangePresenterImpl, ActivitySetAndChangePswBinding>
        implements SetAndChangePresenter.SetAndChangeView {
    //1 修改密码 2忘记密码
    private int activityType = Constant.ONE;
    private String phone;
    private Context context = this;
    private LoginEntity loginEntity;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_and_change_psw;
    }

    @Override
    public void initView() {

        StatusBarUtil.setStatusBarDarkTheme(this, true);
        activityType = getIntent().getIntExtra("activityType", Constant.ONE);
        phone = TextUtils.isEmpty(getIntent().getStringExtra("phone")) ? "" : getIntent().getStringExtra("phone");
        //1修改密码 2 忘记密码
        switch (activityType) {
            case Constant.ONE:
                mViewBinding.activitySetAndChangePswSet.setVisibility(View.VISIBLE);
                mViewBinding.activitySetAndChangePswChange.setVisibility(View.GONE);
                mViewBinding.activityLoginBar.moduleIncludeTitleBarTitle.setText("修改密码");
                mViewBinding.activitySetAndChangePswSetPhone.setText(phone);
                break;
            case Constant.TWO:
                mViewBinding.activitySetAndChangePswSet.setVisibility(View.GONE);
                mViewBinding.activitySetAndChangePswChange.setVisibility(View.VISIBLE);
                mViewBinding.activityLoginBar.moduleIncludeTitleBarTitle.setText("忘记密码");
                if (!TextUtils.isEmpty(phone)) {
                    mViewBinding.activityLoginEditPhone.setText(phone);
                }
                break;
        }
        intEditClick();
    }

    private void intEditClick() {
        if (activityType == 2) {
            //activity_login_edit_phone
            mViewBinding.activityLoginEditPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //activity_psw_img_code,activity_set_and_change_new_psw,activity_login_commit
                    if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                        mViewBinding.activityLoginEditPhoneClean.setVisibility(View.VISIBLE);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                        mViewBinding.activityLoginEditPhoneClean.setVisibility(View.GONE);
                    }
                }
            });
        }

        mViewBinding.activityPswCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (activityType == 2) {
                    if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                    }
                } else {
                    if (!TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                    }
                }

            }
        });

        mViewBinding.activitySetAndChangeNewPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (activityType == 2) {
                    if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                    }
                } else {
                    if (!TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                    }
                }

            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.activity_psw_get_code, R.id.activity_login_commit
            , R.id.activity_set_and_change_no_send_code, R.id.module_include_title_bar_return})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.module_include_title_bar_return:
                finish();
                break;
            //获取验证码
            case R.id.activity_psw_get_code:
                if (activityType == 2) {
                    if (TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim())) {
                        MyToast.showToast("请填写手机号码");
                        return;
                    }
                    if (mViewBinding.activityLoginEditPhone.getText().toString().trim().length() < 11) {
                        MyToast.showToast("请填写11位手机号码");
                        return;
                    }

                } else {

                    if (TextUtils.isEmpty(phone)) {
                        MyToast.showToast("手机号码为空");
                        return;
                    }

                }


                //请求
                initGetCode();
                break;

            //清除电话号码填写
            case R.id.activity_login_edit_phone_clean:
                mViewBinding.activityLoginEditPhone.setText("");
                break;
            //提交
            case R.id.activity_login_commit:
                initCommit();
                break;
            case R.id.activity_set_and_change_no_send_code:
                Intent intent = new Intent(mActivity, VerificationCodeActivity.class);
                startActivity(intent);
                break;
        }

    }

    //提交
    private void initCommit() {
        if (activityType == 2) {
            if (TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim())) {
                MyToast.showToast("请填写手机号码");
                return;
            }
        }

        if (TextUtils.isEmpty(mViewBinding.activityPswCode.getText().toString().trim())) {
            MyToast.showToast("请填写验证码");
            return;
        }
        if (TextUtils.isEmpty(mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim())) {
            MyToast.showToast("请填写新密码");
            return;
        }
        if (mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim().length() < 6) {
            MyToast.showToast("请输入长度为6-20位的密码");
            return;
        }

        /* 已登录：password=[密码]

        未登录：mobile=[手机号]
        code=[短信验证码]
        password=[密码]
        */
        Map<String, String> map = new HashMap<>();
        if (activityType == 2) {
            //忘记密码
            map.put("password", mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim());
            map.put("code", mViewBinding.activityPswCode.getText().toString().trim());
            map.put("mobile", mViewBinding.activityLoginEditPhone.getText().toString().trim());
            //是否是忘记密码
            map.put("isforgetPassword", "1");
            mPresenter.CommitPsw(map, this);

        } else if (activityType == 1) {
            //修改密码
            map.put("user_token", getIntent().getStringExtra("token"));
            map.put("password", mViewBinding.activitySetAndChangeNewPsw.getText().toString().trim());
            mPresenter.CommitLoginPsw(map, this);
        }
    }


    //获取短信验证码
    private void initGetCode() {
        Map<String, String> map = new HashMap<>();
        map.put("device_type", "android");
        if (activityType == 2) {
            //检测当前账号是否注册
            map.put("mobile", mViewBinding.activityLoginEditPhone.getText().toString().trim());
            mPresenter.PhoneIsRegistCode(map, this);
        } else {
            mViewBinding.activityPswGetCode.setClickable(false);
            //计时器
            initTimer();
            map.put("mobile", phone);
            mPresenter.SetPsw(map, this);
        }


    }

    private void initTimer() {
        RxjavaTimer.countdown(120).doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                mViewBinding.activityPswGetCode.setText("获取验证码");
                mViewBinding.activityPswGetCode.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                mViewBinding.activityPswGetCode.setClickable(true);
            }

            @Override
            public void onNext(final Integer integer) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewBinding.activityPswGetCode.setText("重新获取(" + integer + "s)");
                    }
                });
            }
        });
    }

    /*
     * 忘记密码
     * */
    @Override
    public void showSetOrChangePsw(Serializable serializable) {
        loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            //忘记密码
            if (loginEntity.getData().getShow_invitation_code_alert() == 1) {

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);


                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, 0);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            }

            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
            Log.e("TAG", "onIsLogin: 发出之前==============>>>>>" + loginEntity.getData().getUser_token());
//            if (loginEntity.getData().getIs_new() == 1 && loginEntity.getData().getInviter_uid().length() == 0) {
//                initDialog();
//                return;
//            }
            finish();
        } else {
            MyToast.showToast(loginEntity.getMsg());
        }

    }

    @Override
    public void showSetOrChangePswCode(Serializable serializable) {
//        SetAndChangePswEntity showSetOrChangePswCode = (SetAndChangePswEntity) serializable;
        SetAndChangePswEntity setAndChangePswEntity = (SetAndChangePswEntity) serializable;
        if (setAndChangePswEntity.getCode() == 1) {
            ToastUtils.getInstance().show(this, setAndChangePswEntity.getMsg());
        }
    }

    @Override
    public void showLoginPsw(Serializable serializable) {
        LoginEntity loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
//            if (loginEntity.getData().getIs_new() == 1 && loginEntity.getData().getInviter_uid().length() == 0) {
//                initDialog();
//                return;
//            }

            finish();
        } else {
            MyToast.showToast(loginEntity.getMsg());
        }
    }

    @Override
    public void showBindCodeSuccess(Serializable serializable) {
        BindInviterEntity entity = (BindInviterEntity) serializable;
        if (entity.getCode() == 0) {

          //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPEN_INSTALL_COUNT); //openInstall用户数量
            //使用过邀请锁粉邀请码之后，清除将邀请置为已使用
            Paper.book().write(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL, "used");

            //走第三方登录，如果手机号为空，走强邦手机号
            Intent intent;
            if (loginEntity.getData().getMobile().trim().length() == 0) {
                intent = new Intent(mActivity, BindMobilAcitivity.class);
                intent.putExtra("loginOtherType", 0);
                intent.putExtra("token", loginEntity.getData().getUser_token());
                startActivity(intent);
                finish();
                return;
            } else {
                if (loginEntity.getData().getPassword().length() == 0) {
                    intent = new Intent(mActivity, SetPasswordActivity.class);
                    intent.putExtra("phone", loginEntity.getData().getMobile());
                    intent.putExtra("token", loginEntity.getData().getUser_token());
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            loginEntity.getData().setInviter_uid(entity.getData().getInviter_uid() + "");
            loginEntity.setLogin(true);
            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);

            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
            finish();
            MyToast.showToast(entity.getMsg());

        } else {
            ToastUtils.getInstance().show(mActivity,entity.getMsg());
        }
    }

    //检测手机号是否注册
    @Override
    public void showPhoneIsRegistCodeSuccess(Serializable serializable) {
        PhoneIsRegistEntity phoneIsRegistEntity = (PhoneIsRegistEntity) serializable;
        if (phoneIsRegistEntity.getCode() == 0) {
            // 1未注册
            if (phoneIsRegistEntity.getData().getIs_new() == 1) {
                ConfigDialog configDialog = new ConfigDialog(this, 2);
                configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                    @Override
                    public void onOKClick(String code) {
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.putExtra("loginType", 1);
                        startActivity(intent);
                        configDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                configDialog.show();
            } else {
                mViewBinding.activityPswGetCode.setClickable(false);
                // 0 已注册
                Map<String, String> map = new HashMap<>();
                map.put("device_type", "android");
                if (activityType == 2) {
                    //计时器
                    initTimer();
                    map.put("mobile", mViewBinding.activityLoginEditPhone.getText().toString().trim());
                    mPresenter.SetPsw(map, this);
                }
            }

        } else {
            ToastUtils.getInstance().show(mActivity, phoneIsRegistEntity.getMsg());
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
                configDialog.dismiss();
                finish();
            }
        });
        configDialog.show();
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void showEmpty() {

    }

    public void setInvateCode(String invateCode) {
        Map<String, String> map = new HashMap<>();
        map.put("invitation_code", invateCode);
        map.put("user_token", loginEntity.getData().getUser_token());
        map.put("channel", JJBApplication.getChannel());
        map.put("from", "native_jjb");
        map.put("uid", "0");
        map.put("source_style", "8");
        map.put("version", StringUtils.getVersionCode(mActivity));
        map.put("vsn", StringUtils.getVersionCode(mActivity));
        mPresenter.BindCode(map, mActivity);
    }

}

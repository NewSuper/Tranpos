package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityLoginBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.LoginStateEvent;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.LoginPresenter;
import com.newsuper.t.juejinbao.ui.login.presenter.impl.LoginPresenterImpl;
import com.newsuper.t.juejinbao.ui.movie.entity.BindThirdEntity;
import com.newsuper.t.juejinbao.ui.my.dialog.ConfigDialog;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.PhoneUtils;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.paperdb.Paper;


public class LoginActivity extends BaseActivity<LoginPresenterImpl, ActivityLoginBinding> implements
        LoginPresenter.LoginView {
//    private IDDShareApi iddShareApi;
    //短信登录 1  账号密码登录2
    private int loginType = Constant.ONE;
    private Intent intent;
    private boolean isBindWechat = false;
    private LoginEntity loginEntity;
    private int loginOtherType = 0;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loginType = getIntent().getIntExtra("loginType", Constant.ONE);
        initViewLoginType(loginType);
        if (getIntent().getIntExtra("isWritePhone", 0) == 1) {
            mViewBinding.activityLoginEditPhone.setText(getIntent().getStringExtra("writePhone"));
        }
        timeCount = new TimeCount(120000,1000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loginType = intent.getIntExtra("loginType", Constant.ONE);
        if (getIntent().getIntExtra("isWritePhone", 0) == 1) {
            mViewBinding.activityLoginEditPhone.setText(getIntent().getStringExtra("writePhone"));
        }
        initViewLoginType(loginType);
    }

    @Override
    public void initData() {

        //初始化钉钉
       // iddShareApi = DDShareApiFactory.createDDShareApi(this, Constant.DDINGG_APP_ID, false);
        //edit的监听
        initEdit();
        mViewBinding.loadingProgressbarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        mPresenter.IsShowWchatorQQ(new HashMap<>(), this);
        try {
            IsShowQQEntity loginEntity = Paper.book().read(PagerCons.KEY_GUIDELOGIN_UI);
            showIsShowWchatorQQ(loginEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEdit() {
        //手机号监听
        mViewBinding.activityLoginEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim())) {
                    //activity_login_edit_phone_clean
                    mViewBinding.activityLoginEditPhoneClean.setVisibility(View.VISIBLE);
                } else {
                    mViewBinding.activityLoginEditPhoneClean.setVisibility(View.GONE);
                }

                if (loginType == 1) {
                    // 验证码登录
                    if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) &&
                            !TextUtils.isEmpty(mViewBinding.activityLoginEditPhoneCode.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);

                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);

                    }
                } else if (loginType == 2) {
                    // 账号密码登录
                    if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) && !
                            TextUtils.isEmpty(mViewBinding.activityLoginEditPsw.getText().toString().trim())) {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(true);
                    } else {
                        mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                        mViewBinding.activityLoginCommit.setEnabled(false);
                    }
                }

            }
        });
        //验证码监听
        mViewBinding.activityLoginEditPhoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) &&
                        !TextUtils.isEmpty(mViewBinding.activityLoginEditPhoneCode.getText().toString().trim())) {
                    mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                    mViewBinding.activityLoginCommit.setEnabled(true);
                } else {
                    mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                    mViewBinding.activityLoginCommit.setEnabled(false);
                }
            }
        });
        //6-20位密码监听activity_login_edit_psw
        mViewBinding.activityLoginEditPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPsw.getText().toString().trim())) {
                    mViewBinding.activityLoginEditPswClean.setVisibility(View.VISIBLE);
                } else {
                    mViewBinding.activityLoginEditPswClean.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) && !
                        TextUtils.isEmpty(mViewBinding.activityLoginEditPsw.getText().toString().trim())) {

                    mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_ed6b2c_round_100);
                    mViewBinding.activityLoginCommit.setEnabled(true);
                } else {
                    mViewBinding.activityLoginCommit.setBackgroundResource(R.drawable.shap_bg_50ed6b2c_round_100);
                    mViewBinding.activityLoginCommit.setEnabled(false);
                }
            }
        });
        //密码清除
        mViewBinding.activityLoginEditPswClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.activityLoginEditPsw.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (timeCount != null){
            timeCount.onFinish();
            timeCount.cancel();
            timeCount = null;
        }
    }

    @Subscribe
    public void onIsLoginEvent(LoginStateEvent loginStateEvent) {
        if (loginStateEvent.isLogin()) {
//            LoginEntity loginEntity = (LoginEntity) Paper.book().read(PagerCons.USER_DATA);
//            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
            finish();
        }
    }

    //其他登录
    @Override
    public void showOtherSuccess(Serializable serializable) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        loginEntity = (LoginEntity) serializable;

        Log.i("zzz", "showOtherSuccess: " + loginEntity.toString());

        if (loginEntity.getCode() == 0) {
            if (loginEntity.getData().getShow_invitation_code_alert() == 1) {

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);


                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, loginOtherType);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            } else {
                //走第三方登录，如果手机号为空，走强邦手机号
                if (loginEntity.getData().getMobile().trim().length() == 0) {
                    intent = new Intent(mActivity, BindMobilAcitivity.class);
                    intent.putExtra("loginOtherType", loginOtherType);
                    intent.putExtra("token", loginEntity.getData().getUser_token());
                    startActivity(intent);
                    return;
                } else {
                    if (loginEntity.getData().getPassword().length() == 0) {
                        intent = new Intent(mActivity, SetPasswordActivity.class);
                        intent.putExtra("phone", loginEntity.getData().getMobile());
                        intent.putExtra("token", loginEntity.getData().getUser_token());
                        startActivity(intent);
                        return;
                    }
                }
                loginEntity.setLogin(true);
                loginEntity.getData().setTakeToken(true);
                Paper.book().write(PagerCons.USER_DATA, loginEntity);

                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                finish();
                MyToast.showToast(loginEntity.getMsg());
            }
        } else {
            ToastUtils.getInstance().show(mActivity, loginEntity.getMsg());
        }
        initCancelLogin();
    }

    private void initCancelLogin() {
//        UMShareAPI.get(mActivity).deleteOauth(mActivity, SHARE_MEDIA.QQ, new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//
//            }
//        });
//        UMShareAPI.get(mActivity).deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//
//            }
//        });

    }

    @Override
    public void showIsShowWchatorQQ(Serializable serializable) {
        IsShowQQEntity isShowQQEntity = (IsShowQQEntity) serializable;
        if (isShowQQEntity.getCode() == 0) {
            if (isShowQQEntity.getData().getWechat() == 1) {
                mViewBinding.activityLoginWeixin.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.activityLoginWeixin.setVisibility(View.GONE);
            }

            if (isShowQQEntity.getData().getQq() == 1) {
                mViewBinding.activityLoginQq.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.activityLoginQq.setVisibility(View.GONE);

            }
        }
    }


    //一键登录
    @Override
    public void showLoginOnePswSuccess(Serializable serializable) {


    }

    private int pseClick = 0;

    //登录
    @Override
    public void showListSuccess(Serializable serializable) {
        mViewBinding.activityLoginCommit.setEnabled(true);
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {

            if (loginEntity.getData().getShow_invitation_code_alert() == 1) {

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);

                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, loginOtherType);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            } else {
                //如果密码为空就跳去设置密码,Data为空代表密码为空
                if (loginEntity.getData().getPassword().length() == 0) {
                    loginEntity.setLogin(false);
                    intent = new Intent(mActivity, SetPasswordActivity.class);
                    intent.putExtra("phone", mViewBinding.activityLoginEditPhone.getText().toString().trim());
                    intent.putExtra("token", loginEntity.getData().getUser_token());
                    startActivity(intent);
                    return;
                }
                loginEntity.setLogin(true);
                loginEntity.getData().setTakeToken(true);
                Paper.book().write(PagerCons.USER_DATA, loginEntity);
//            if (loginEntity.getData().getIs_new() == 1 && loginEntity.getData().getInviter_uid().length() == 0) {
//                initDialog();
//                return;
//            }

                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                MyToast.showToast(loginEntity.getMsg());
            }

            finish();
        } else if (loginEntity.getCode() == 4000) {
            //账号不存在
            ConfigDialog configDialog = new ConfigDialog(this, 2);
            configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                @Override
                public void onOKClick(String code) {
                    initViewLoginType(1);
                    loginType = 1;
                    mViewBinding.activityLoginToPsw.setVisibility(View.VISIBLE);
                    configDialog.dismiss();
                }

                @Override
                public void onCancelClick() {

                }
            });
            configDialog.show();

        } else if (loginEntity.getCode() == 5000) {
            //密码错误
//            MyToast.showToast(loginEntity.getMsg());
            pseClick++;
            if (pseClick > 3) {
                ConfigDialog configDialog = new ConfigDialog(this, 2, loginEntity.getMsg(), 1);
                configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                    @Override
                    public void onOKClick(String code) {
                        intent = new Intent(mActivity, SetAndChangePswActivity.class);
                        intent.putExtra("activityType", 2);
                        intent.putExtra("phone", mViewBinding.activityLoginEditPhone.getText().toString().trim());
                        startActivity(intent);
                        configDialog.dismiss();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                configDialog.show();
            }

        } else if (loginEntity.getCode() == 6000) {
            //当前未设置密码
            ConfigDialog configDialog = new ConfigDialog(this, 2, "您还未设置密码，请使用其他方式登录", 0);
            configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                @Override
                public void onOKClick(String code) {
                    initViewLoginType(1);
                    loginType = 1;
                    mViewBinding.activityLoginToPsw.setVisibility(View.VISIBLE);
                    configDialog.dismiss();
                }

                @Override
                public void onCancelClick() {

                }
            });
            configDialog.show();

        } else {
            ToastUtils.getInstance().show(mActivity, loginEntity.getMsg());

        }

    }

    private FragmentMyConfigDialog configDialog;

    //输入邀请码
    private void initDialog(boolean cancelAble) {
        configDialog = new FragmentMyConfigDialog(this, 5);
        configDialog.setCanceledOnTouchOutside(false);
        configDialog.setCancelable(false);
        configDialog.setCancelAble(cancelAble);
        configDialog.setOnDialogClickListener(new FragmentMyConfigDialog.OnDialogClickListener() {
            @Override
            public void onOKClick(String code) {
                if (!TextUtils.isEmpty(code)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("invitation_code", code);
                    map.put("user_token", loginEntity.getData().getUser_token());
                    map.put("channel", JJBApplication.getChannel());
                    map.put("from", "native_jjb");
                    map.put("uid", "0");
                    map.put("source_style", "7");
                    map.put("version", StringUtils.getVersionCode(mActivity));
                    map.put("vsn", StringUtils.getVersionCode(mActivity));

                    mPresenter.BindCode(map, mActivity);
                } else {
                    MyToast.showToast("请输入邀请码");
                }

            }

            @Override
            public void onCancelClick() {
                configDialog.dismiss();
                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                finish();
            }
        });
        configDialog.show();
    }

    //获取手机验证码
    @Override
    public void showSetOrChangePsw(SetAndChangePswEntity setAndChangePswEntity) {
        MyToast.showToast(setAndChangePswEntity.getMsg());
    }

    @Override
    public void showBindCode(Serializable serializable) {
        BindInviterEntity entity = (BindInviterEntity) serializable;
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        if (entity.getCode() == 0) {

          //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPEN_INSTALL_COUNT); //openInstall用户数量
            //使用过邀请锁粉邀请码之后，清除将邀请置为已使用
            Paper.book().write(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL, "used");

            //走第三方登录，如果手机号为空，走强邦手机号
            Intent intent;
            if (loginEntity.getData().getMobile().trim().length() == 0) {
                intent = new Intent(mActivity, BindMobilAcitivity.class);
                intent.putExtra("loginOtherType", loginOtherType);
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


            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
            EventBus.getDefault().post(new LoginStateEvent(true));
            finish();
            MyToast.showToast(entity.getMsg());

        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

    @Override
    public void bindWechatSuccess(Serializable serializable) {
        // 登录强绑微信成功
        isBindWechat = false;
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        BindThirdEntity entity = (BindThirdEntity) serializable;
        if (entity.getCode() == 0) {

            if (loginEntity.getData().getShow_invitation_code_alert() == 1) {

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);

                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, loginOtherType);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            } else {
                //如果密码为空就跳去设置密码,Data为空代表密码为空
                if (loginEntity.getData().getPassword().length() == 0) {
                    loginEntity.setLogin(false);
                    intent = new Intent(mActivity, SetPasswordActivity.class);
                    intent.putExtra("phone", mViewBinding.activityLoginEditPhone.getText().toString().trim());
                    intent.putExtra("token", loginEntity.getData().getUser_token());
                    startActivity(intent);
                    return;
                }
                loginEntity.setLogin(true);
                loginEntity.getData().setTakeToken(true);
                Paper.book().write(PagerCons.USER_DATA, loginEntity);
//            if (loginEntity.getData().getIs_new() == 1 && loginEntity.getData().getInviter_uid().length() == 0) {
//                initDialog();
//                return;
//            }

                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                MyToast.showToast(loginEntity.getMsg());
            }

            finish();
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }


    }

    @Override
    public void showError(String s) {
        ToastUtils.getInstance().show(mActivity, s);
        mViewBinding.activityLoginCommit.setEnabled(true);
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mViewBinding.activityLoginCommit.setEnabled(true);
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
    }


    @OnClick({R.id.module_include_title_bar_return, R.id.activity_login_weixin,
            R.id.activity_login_qq, R.id.activity_login_sina,
            R.id.activity_login_dding, R.id.activity_login_get_code,
            R.id.activity_login_commit, R.id.activity_login_to_phone_login,
            R.id.activity_login_forget_psw, R.id.activity_login_type,
            R.id.activity_login_edit_phone_clean, R.id.activity_login_phone_msg_code,
            R.id.activity_login_to_psw, R.id.login_use_agreement, R.id.login_sms_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.module_include_title_bar_return:
                finish();
                break;
            //微信
            case R.id.activity_login_weixin:
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自微信分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.WEIXIN);

                //埋点（点击微信登录按钮）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_WECHAT);

                loginOtherType = 2;

              //  authorization(SHARE_MEDIA.WEIXIN);
                break;
            //QQ
            case R.id.activity_login_qq:
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自QQ分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.QQ);

                //埋点（点击QQ登录按钮）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_QQ);

                loginOtherType = 1;
              //  authorization(SHARE_MEDIA.QQ);
                break;
            //新浪
            case R.id.activity_login_sina:
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自新浪微博分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.SINA);

             //   authorization(SHARE_MEDIA.SINA);
                break;
            //钉钉
            case R.id.activity_login_dding:
//                if (iddShareApi.isDDAppInstalled()) {
////                    ShareUtils.sendTextMessage(false, getApplicationContext(), iddShareApi, "这是来自钉钉分享的掘金宝消息");
//                    sendAuth();
//                } else {
//                    ToastUtils.getInstance().show(getApplicationContext(), "未安装钉钉客户端，请选择其他登录方式！");
//                }

                break;
            //获取验证码
            case R.id.activity_login_get_code:
                if (TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim())) {
                    MyToast.showToast("请输入11位手机号码");
                    return;
                }
                if (mViewBinding.activityLoginEditPhone.getText().toString().trim().length() < 11) {
                    MyToast.showToast("请输入11位手机号码");
                    return;
                }
                mViewBinding.activityLoginGetCode.setClickable(false);
                //计时器
                timeCount.start();
                //请求
                initGetCode();
                break;
            //登录
            case R.id.activity_login_commit:
                if (TextUtils.isEmpty(mViewBinding.activityLoginEditPhone.getText().toString().trim()) ||
                        mViewBinding.activityLoginEditPhone.getText().toString().trim().length() != 11) {
                    MyToast.showToast("请输入11位手机号码");
                    return;
                }


                if (loginType == 1) {
                    if (TextUtils.isEmpty(mViewBinding.activityLoginEditPhoneCode.getText().toString().trim())) {
                        MyToast.showToast("请输入验证码");
                        return;
                    }
                    mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
                    mViewBinding.activityLoginCommit.setEnabled(false);
                    initCommit();
                } else {
                    if (TextUtils.isEmpty(mViewBinding.activityLoginEditPsw.getText().toString().trim())) {
                        MyToast.showToast("请输入8-20位密码");
                        return;
                    }
                    if (mViewBinding.activityLoginEditPsw.getText().toString().trim().length() < 6) {
                        MyToast.showToast("请输入最少为6位数的密码");
                        return;
                    }
                    mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
                    mViewBinding.activityLoginCommit.setEnabled(false);
                    initPswCommit();
                }

                break;
            //账号密码登录（新版需求以去掉此流程）
            case R.id.activity_login_to_phone_login:
                initViewLoginType(2);
                loginType = 2;
                break;
            //短信登录
            case R.id.activity_login_type:
                //埋点（点击短信登录按钮）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_NOTE);

                initViewLoginType(1);
                loginType = 1;
                mViewBinding.activityLoginToPsw.setVisibility(View.VISIBLE);
                break;
            //账号密码登录
            case R.id.activity_login_to_psw:
                //埋点（点击账号密码登录按钮）
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_PASSWORD);
                initViewLoginType(2);
                loginType = 2;
                mViewBinding.activityLoginToPsw.setVisibility(View.GONE);
                break;

            //忘记密码
            case R.id.activity_login_forget_psw:
                intent = new Intent(mActivity, SetAndChangePswActivity.class);
                intent.putExtra("activityType", 2);
                startActivity(intent);
                break;
            //清除手机号码
            case R.id.activity_login_edit_phone_clean:
                mViewBinding.activityLoginEditPhone.setText("");
                break;
            //收不到验证码
            case R.id.activity_login_phone_msg_code:
                intent = new Intent(mActivity, VerificationCodeActivity.class);
                startActivity(intent);
                break;
            //用户协议
            case R.id.login_use_agreement:
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_USE_AGREEMENT, "掘金宝用户协议");
                break;
            //隐私协议
            case R.id.login_sms_agreement:
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_SMS_AGREEMENT, "掘金宝隐私条款");
                break;
        }
    }

    private void initViewLoginType(int type) {

        //短信登录
        if (type == 1) {
            mViewBinding.activityLoginPhoneMsgCode.setVisibility(View.VISIBLE);
            //获取验证码模块activity_login_edit_phone_code_about
            mViewBinding.activityLoginEditPhoneCodeAbout.setVisibility(View.VISIBLE);
            //账号密码模块activity_login_edit_psw_about
            mViewBinding.activityLoginEditPswAbout.setVisibility(View.GONE);
            //验证码标题activity_login_sms
            mViewBinding.activityLoginSms.setText("短信验证登录/注册");
            //验证码登录count activity_login_sms_count
            mViewBinding.activityLoginSmsCount.setText(getResources().getString(R.string.login_agree_to_a_deal));
            //切换回短信登录模块activity_login_to_phone_code_login_about
            mViewBinding.activityLoginToPhoneCodeLoginAbout.setVisibility(View.GONE);
            mViewBinding.activityLoginCommit.setText("登录/注册");
            //短信验证码登录
            mViewBinding.activityLoginToPsw.setVisibility(View.VISIBLE);
        } else {
            //账号密码

            mViewBinding.activityLoginPhoneMsgCode.setVisibility(View.GONE);
            //获取验证码模块activity_login_edit_phone_code_about
            mViewBinding.activityLoginEditPhoneCodeAbout.setVisibility(View.GONE);
            //账号密码模块activity_login_edit_psw_about
            mViewBinding.activityLoginEditPswAbout.setVisibility(View.VISIBLE);
            //验证码标题activity_login_sms
            mViewBinding.activityLoginSms.setText("密码登录");
            //验证码登录count activity_login_sms_count
            mViewBinding.activityLoginSmsCount.setText("登录表示同意");
            //切换回短信登录模块activity_login_to_phone_code_login_about
            mViewBinding.activityLoginToPhoneCodeLoginAbout.setVisibility(View.VISIBLE);
            mViewBinding.activityLoginToPsw.setVisibility(View.GONE);
            mViewBinding.activityLoginCommit.setText("确认登录");
        }
    }

    //密码登录
    private void initPswCommit() {
        Map<String, String> map = new HashMap<>();
        map.put("account", mViewBinding.activityLoginEditPhone.getText().toString().trim());
        map.put("password", mViewBinding.activityLoginEditPsw.getText().toString().trim());
        mPresenter.LoginPsw(map, this);
    }

    //验证码登录
    private void initCommit() {
        Map<String, String> map = new HashMap<>();
        map.put("account", mViewBinding.activityLoginEditPhone.getText().toString().trim());
        map.put("code", mViewBinding.activityLoginEditPhoneCode.getText().toString().trim());
        mPresenter.Login(map, this);
    }

    private void initGetCode() {
        Map<String, String> map = new HashMap<>();
        map.put("device_type", "android");
        map.put("mobile", mViewBinding.activityLoginEditPhone.getText().toString().trim());
        mPresenter.SetPsw(map, this);

    }

    //weixin登录
    private void initWXCommit(String unionid, String access_token, String openid) {
        if (mPresenter == null || TextUtils.isEmpty(access_token) || TextUtils.isEmpty(openid)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", access_token);
        map.put("openid", openid);

        //绑定微信号
        if (isBindWechat) {
            map.put("user_token", loginEntity.getData().getUser_token());
            map.put("channel", JJBApplication.getChannel());
            map.put("uid", "0");
            map.put("source_style", "7");
            map.put("from", "native_jjb");
            map.put("version", StringUtils.getVersionCode(mActivity));
            map.put("vsn", StringUtils.getVersionCode(mActivity));
            map.put("uuid", PhoneUtils.getMyUUID(mActivity));
            mPresenter.BindWeChat(map, this);
        } else {
            //微信登录
            mPresenter.LoginWX(map, this);
        }
    }

    //QQ登录
    private void initQQCommit(String access_token, String openid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", access_token);
        map.put("openid", openid);
        if (mPresenter != null) {
            mPresenter.LoginQQ(map, this);
        }
    }

    //新浪登录
    private void initSinaCommit(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", uid);
        mPresenter.LoginQQ(map, this);
    }

    private TimeCount timeCount;
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            mViewBinding.activityLoginGetCode.setClickable(false);
            mViewBinding.activityLoginGetCode.setText("("+millisUntilFinished / 1000 +")s后重新获取");
        }

        @Override
        public void onFinish() {
            mViewBinding.activityLoginGetCode.setText("重新获取验证码");
            mViewBinding.activityLoginGetCode.setClickable(true);
        }
    }

    //钉钉登录
    private void sendAuth() {

//        SendAuth.Req req = new SendAuth.Req();
//        req.scope = SendAuth.Req.SNS_LOGIN;
//        req.state = "test";
//        if (req.getSupportVersion() > iddShareApi.getDDSupportAPI()) {
//            Toast.makeText(this, "钉钉版本过低，不支持登录授权", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        iddShareApi.sendReq(req);
    }

    //授权
//    private void authorization(SHARE_MEDIA share_media) {
//        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//                Log.e("TAG", "onStart " + "授权开始");
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//                Log.e("TAG", "onStart =============》》》》》》》" + "授权完成");
//                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
//                String uid = map.get("uid");
//                String openid = map.get("openid");//微博没有
//                String unionid = map.get("unionid");//微博没有
//                String access_token = map.get("access_token");
//                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//                String expires_in = map.get("expires_in");
//                String name = map.get("name");
//                String gender = map.get("gender");
//                String iconurl = map.get("iconurl");
//                Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
//                        + "uid=" + uid
//                        + "openid=" + openid
//                        + "unionid =" + unionid
//                        + "access_token =" + access_token
//                        + "refresh_token=" + refresh_token
//                        + "expires_in=" + expires_in
//                        + "gender=" + gender
//                        + "iconurl=" + iconurl);
//                mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
//                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
//                    initWXCommit(unionid, access_token, openid);
//                } else if (share_media.equals(SHARE_MEDIA.QQ)) {
//                    initQQCommit(access_token, openid);
//                } else if (share_media.equals(SHARE_MEDIA.SINA)) {
//
//                }
//
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
//                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
//
//                    ToastUtils.getInstance().show(getApplicationContext(), "未安装微信客户端，请选择其他登录方式！");
//                } else if (share_media.equals(SHARE_MEDIA.QQ)) {
//                    ToastUtils.getInstance().show(getApplicationContext(), "未安装QQ，请安装QQ或选择其他登录方式！");
//                }
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//                mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
//                Log.d("TAG", "onCancel " + "授权取消");
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
   //     UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void setInvateCode(String invateCode) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
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

package com.newsuper.t.juejinbao.ui.login.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityLoginSetInviteCodeBinding;
import com.juejinchain.android.event.LoginEvent;
import com.juejinchain.android.event.LoginStateEvent;
import com.juejinchain.android.module.login.entity.BindInviterEntity;
import com.juejinchain.android.module.login.presenter.LoginSetInviteCodePresenter;
import com.juejinchain.android.module.login.presenter.impl.LoginSetInviteCodeImpl;
import com.juejinchain.android.module.movie.entity.BindThirdEntity;
import com.juejinchain.android.utils.MyToast;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.BaseApplication;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.utils.PhoneUtils;
import com.ys.network.utils.StringUtils;
import com.ys.network.utils.ToastUtils;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import razerdp.util.InputMethodUtils;


public class LoginSetInviteCodeActivity extends BaseActivity<LoginSetInviteCodeImpl, ActivityLoginSetInviteCodeBinding> implements
        LoginSetInviteCodePresenter.View, View.OnClickListener {

    public static final String LOGIN_DATE = "login_data";
    public static final String LOGIN_OTHER_TYPE = "login_other_type";
    private static final int REQUEST_CODE_NO_INVITER = 0X01;
    private LoginEntity loginEntity;
    private int loginOtherType;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_set_invite_code;
    }

    @Override
    public void initView() {


        StatusBarUtil.setStatusBarDarkTheme(this, true);
        Intent intent = getIntent();
        loginEntity = (LoginEntity) intent.getSerializableExtra(LOGIN_DATE);
        loginOtherType = intent.getIntExtra(LOGIN_OTHER_TYPE, 0);


        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("邀请码");
        mViewBinding.edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mViewBinding.btnCommmit.setAlpha(0.5f);
                } else {
                    mViewBinding.btnCommmit.setAlpha(1f);
                }
            }
        });
        mViewBinding.btnCommmit.setOnClickListener(this);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(this);
        mViewBinding.tvNoInvite.setOnClickListener(this);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setVisibility(View.GONE);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(this);
        mViewBinding.loadingProgressbarLogin.setOnClickListener(this);


        if (loginEntity.getData().getRequest() == 0) {
            mViewBinding.modelTitleBar.moduleIncludeTitleBarMore.setText("跳过");
            mViewBinding.modelTitleBar.moduleIncludeTitleBarMore.setOnClickListener(this);
        }

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        InputMethodUtils.showInputMethod(mViewBinding.edInput);
    }

    public static void intentMe(Activity context, LoginEntity loginEntity, int loginOtherType) {
        Intent intent = new Intent(context, LoginSetInviteCodeActivity.class);
        intent.putExtra(LOGIN_DATE, loginEntity);
        intent.putExtra(LOGIN_OTHER_TYPE, loginOtherType);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);
    }

    @Override
    public void showBindCode(Serializable serializable) {
        BindInviterEntity entity = (BindInviterEntity) serializable;
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        Log.i("zzz", "showBindCode: " + entity.toString());
        if (entity.getCode() == 0) {
//            configDialog.dismiss();
//            EventBus.getDefault().post(new LoginEvent());
//            EventBus.getDefault().post(new LoginStateEvent(true));
//            finish();
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

        } else if (entity.getCode() == 802) {
            authorization(SHARE_MEDIA.WEIXIN);
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

    @Override
    public void bindWechatSuccess(Serializable serializable) {

        //绑定微信号
        BindThirdEntity entity = (BindThirdEntity) serializable;
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        Log.i("zzz", "bindWechatSuccess: " + entity.toString());
        if (entity.getCode() == 0) {

            setInvateCode(mViewBinding.edInput.getText().toString());

        } else {

//            ToastUtils.getInstance().show(mActivity, entity.getMsg());

            showFailDialog(entity.getMsg());
        }
    }

    public void showFailDialog(String msg){
        AlertDialog.Builder build = new AlertDialog.Builder(mActivity);
        build.setMessage(msg);
        build.setCancelable(true);
        build.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        build.show();
    }

    @Override
    public void showError(String s) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        ToastUtils.getInstance().show(mActivity, s);
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commmit: //提交
                Log.i("zzz", "bindQQ: " + loginEntity.getData().getUser_token());
                if (TextUtils.isEmpty(mViewBinding.edInput.getText().toString())) {
                    ToastUtils.getInstance().show(mActivity, "请输入邀请码");
                } else {
                    mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);

//                    authorization(SHARE_MEDIA.WEIXIN);
                        setInvateCode(mViewBinding.edInput.getText().toString());
                }
                break;
            case R.id.tv_no_invite: //没有邀请码
                startActivityForResult(new Intent(LoginSetInviteCodeActivity.this, DefaultInviteTipActivity.class), REQUEST_CODE_NO_INVITER);
                break;
            case R.id.module_include_title_bar_return: //返回
                clickBack();
                break;
            case R.id.module_include_title_bar_more:
                //选填
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
                loginEntity.getData().setInviter_uid("");
                loginEntity.setLogin(true);
                loginEntity.getData().setTakeToken(true);
                Paper.book().write(PagerCons.USER_DATA, loginEntity);

                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                finish();
                break;
        }
    }

    public void setInvateCode(String invateCode) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("invitation_code", invateCode);
        map.put("user_token", loginEntity.getData().getUser_token());
        map.put("channel", BaseApplication.getChannel());
        map.put("from", "native_jjb");
        map.put("uid", "0");
        map.put("source_style", "7");
        map.put("version", StringUtils.getVersionCode(mActivity));
        map.put("vsn", StringUtils.getVersionCode(mActivity));
        mPresenter.BindCode(map, mActivity);
    }

    public void bindWechat(String access_token, String openid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", access_token);
        map.put("openid", openid);
        map.put("user_token", loginEntity.getData().getUser_token());
        map.put("channel", BaseApplication.getChannel());
        map.put("uid", "0");
        map.put("source_style", "7");
        map.put("from", "native_jjb");
        map.put("version", StringUtils.getVersionCode(mActivity));
        map.put("vsn", StringUtils.getVersionCode(mActivity));
        map.put("uuid", PhoneUtils.getMyUUID(mActivity));
        mPresenter.BindWeChat(map, this);
    }

    public void clickBack() {
        if (loginEntity.getData().getRequest() == 1) {
            //必填
            ToastUtils.getInstance().show(mActivity, "请输入邀请码");
        } else {
            //选填
            //走第三方登录，如果手机号为空，走强邦手机号
            Intent intent;
            if (TextUtils.isEmpty(loginEntity.getData().getMobile()) || loginEntity.getData().getMobile().trim().length() == 0) {
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
            loginEntity.getData().setInviter_uid("");
            loginEntity.setLogin(true);
            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);

            EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent, GuideLoginActivity.params));
            EventBus.getDefault().post(new LoginStateEvent(true));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NO_INVITER) {
            if (data != null) {
                String stringExtra = data.getStringExtra(DefaultInviteTipActivity.INVITE_CODE);
                if (!TextUtils.isEmpty(stringExtra)) {
                    mViewBinding.edInput.setText(stringExtra);
                }
            }

        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

                Log.e("zzz", "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                Log.e("zzz", "onStart =============》》》》》》》" + "授权完成");
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
                        + "uid=" + uid
                        + "openid=" + openid
                        + "unionid =" + unionid
                        + "access_token =" + access_token
                        + "refresh_token=" + refresh_token
                        + "expires_in=" + expires_in
                        + "gender=" + gender
                        + "iconurl=" + iconurl);
                mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {

                    Log.e("zzz", "调用绑定微信号接口");
                    bindWechat(access_token, openid);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
                if (share_media.equals(SHARE_MEDIA.WEIXIN)) {

                    ToastUtils.getInstance().show(getApplicationContext(), "未安装微信客户端，请选择其他登录方式！");
                } else if (share_media.equals(SHARE_MEDIA.QQ)) {
                    ToastUtils.getInstance().show(getApplicationContext(), "未安装QQ，请安装QQ或选择其他登录方式！");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
                showFailDialog("设置邀请码前请先绑定微信");
                Log.d("TAG", "onCancel " + "授权取消");
            }
        });
    }

}

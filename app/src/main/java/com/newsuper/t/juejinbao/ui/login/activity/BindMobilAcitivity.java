package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.juejinchain.android.R;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.databinding.ActivityBindMobilBinding;
import com.juejinchain.android.event.LoginEvent;
import com.juejinchain.android.event.LoginStateEvent;
import com.juejinchain.android.module.login.entity.BindInviterEntity;
import com.juejinchain.android.module.login.entity.SetAndChangePswEntity;
import com.juejinchain.android.module.login.presenter.impl.BindMobilPresenterImpl;
import com.juejinchain.android.module.my.dialog.ConfigDialog;
import com.juejinchain.android.module.my.dialog.FragmentMyConfigDialog;
import com.juejinchain.android.utils.MyToast;
import com.juejinchain.android.utils.RxjavaTimer;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.BaseApplication;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.utils.StringUtils;
import com.ys.network.utils.ToastUtils;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.paperdb.Paper;

public class BindMobilAcitivity extends BaseActivity<BindMobilPresenterImpl, ActivityBindMobilBinding> implements BindMobilPresenterImpl.LoginView {
    private String token;

    FragmentMyConfigDialog configDialog;
    private LoginEntity loginEntity;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_mobil;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        token = getIntent().getStringExtra("token");
        if (!EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onIsLoginEvent(LoginStateEvent loginStateEvent) {
        if (loginStateEvent.isLogin()) {
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            loginEntity.setLogin(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            finish();
        }
    }

    @Override
    public void initData() {
        mViewBinding.activityBindMobilBar.moduleIncludeTitleBarTitle.setText("完善登录信息");
    }

    @OnClick({R.id.activity_bind_mobil_commit,
            R.id.module_include_title_bar_return,
            R.id.activity_bind_mobil_get_code})
    public void OnClinck(View view) {
        switch (view.getId()) {
            //提交
            case R.id.activity_bind_mobil_commit:
                if (TextUtils.isEmpty(mViewBinding.activityBindMobilMobil.getText().toString().trim())) {
                    MyToast.showToast("请输入11位手机号码");
                    return;
                }

                if (TextUtils.isEmpty(mViewBinding.activityBindMobilCode.getText().toString().trim())) {
                    MyToast.showToast("请输入手机验证码");
                    return;
                }
//                Map<String, String> map = new HashMap<>();
//                //account 手机号    code 验证码
//                map.put("account", mViewBinding.activityBindMobilMobil.getText().toString().trim());
//                map.put("code", mViewBinding.activityBindMobilCode.getText().toString().trim());
//                mPresenter.Login(map, this);

                Map<String, String> map = new HashMap<>();
                //account 手机号    code 验证码
                map.put("account", mViewBinding.activityBindMobilMobil.getText().toString().trim());
                map.put("code", mViewBinding.activityBindMobilCode.getText().toString().trim());
                map.put("user_token", token);
                if (getIntent().getIntExtra("loginOtherType", 0) == 1) {
                    map.put("platform", "qq");
                } else if (getIntent().getIntExtra("loginOtherType", 0) == 2) {
                    map.put("platform", "wechat");
                }
                mPresenter.bindQQ(map, this);
                break;
            //返回
            case R.id.module_include_title_bar_return:
                finish();
                break;
            case R.id.activity_bind_mobil_get_code:
                if (TextUtils.isEmpty(mViewBinding.activityBindMobilMobil.getText().toString().trim())) {
                    MyToast.showToast("请输入11位手机号码");
                    return;
                }
                //防止获取验证码一直点击
                mViewBinding.activityBindMobilGetCode.setClickable(false);
                //计时器
                initTimer();
                //请求
                initGetCode();
                break;

        }
    }

    int integer = 120;

    //倒计时
    private void initTimer() {

//        RxjavaTimer.countdown(120).doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//
//            }
//        }).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                mViewBinding.activityBindMobilGetCode.setText("获取验证码");
//                mViewBinding.activityBindMobilGetCode.setClickable(true);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mViewBinding.activityBindMobilGetCode.setClickable(true);
//            }
//
//            @Override
//            public void onNext(final Integer integer) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mViewBinding.activityBindMobilGetCode.setText("重新获取(" + integer + "s)");
//                    }
//                });
//            }
//        });
        integer = 120;
        RxjavaTimer.interval(1000, new RxjavaTimer.IRxNext() {
            @Override
            public void doNext(long number) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(integer!=0){
                            integer--;
                            mViewBinding.activityBindMobilGetCode.setText("重新获取(" + integer + "s)");
                        }else {
                            mViewBinding.activityBindMobilGetCode.setText("重新获取");
                            mViewBinding.activityBindMobilGetCode.setClickable(true);
                        }

                    }
                });
            }
        });
    }

    //获取验证码
    private void initGetCode() {
        Map<String, String> map = new HashMap<>();
        map.put("device_type", "android");
        map.put("mobile", mViewBinding.activityBindMobilMobil.getText().toString().trim());

        mPresenter.GetCode(map, this);
    }

    //提交成功
    @Override
    public void showListSuccess(Serializable serializable) {
        LoginEntity loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            Paper.book().delete(PagerCons.USER_DATA);
            Intent intent = new Intent(mActivity, SetAndChangePswActivity.class);
            intent.putExtra("activityType", 1);
            intent.putExtra("phone", mViewBinding.activityBindMobilMobil.getText().toString().trim());
            startActivity(intent);
        }
        MyToast.showToast(loginEntity.getMsg());
    }

    //获取验证码
    @Override
    public void showCode(Serializable serializable) {
        SetAndChangePswEntity setAndChangePswEntity = (SetAndChangePswEntity) serializable;
        if (setAndChangePswEntity.getCode() == 1) {
            ToastUtils.getInstance().show(this,setAndChangePswEntity.getMsg());
        }
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void bindQQ(Serializable serializable) {
        LoginEntity loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            Log.i("zzz", "bindQQ: "  + loginEntity.getData().getUser_token());
            if(loginEntity.getData().getShow_invitation_code_alert()==1){

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);
                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, 0);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            }

            if(loginEntity.getData().getPassword().length()==0){
                Paper.book().delete(PagerCons.USER_DATA);
                RxjavaTimer.cancel();
                Intent intent = new Intent(mActivity, SetPasswordActivity.class);
                intent.putExtra("phone", mViewBinding.activityBindMobilMobil.getText().toString().trim());
                intent.putExtra("token", TextUtils.isEmpty(loginEntity.getData().getUser_token()) ? token : loginEntity.getData().getUser_token());
                intent.putExtra("isShow", 1);
                intent.putExtra("bindCode", loginEntity.getData().getCoin());
                startActivity(intent);
            }else {
                loginEntity.setLogin(true);
                loginEntity.getData().setTakeToken(true);
                Paper.book().write(PagerCons.USER_DATA, loginEntity);

                EventBus.getDefault().post(new LoginEvent(GuideLoginActivity.intentEvent , GuideLoginActivity.params));
                EventBus.getDefault().post(new LoginStateEvent(true));
                finish();
            }


        } else if (loginEntity.getCode() == 666) {
            Paper.book().delete(PagerCons.USER_DATA);
            ConfigDialog configDialog = new ConfigDialog(this, 5, loginEntity.getMsg(), 3);
            configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                @Override
                public void onOKClick(String code) {
                    RxjavaTimer.cancel();
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    intent.putExtra("loginType", 1);
                    intent.putExtra("isWritePhone", 1);
                    intent.putExtra("writePhone", mViewBinding.activityBindMobilMobil.getText().toString().trim());
                    startActivity(intent);
                    finish();
                    MyToast.showToast("请获取验证码登录");
                }

                @Override
                public void onCancelClick() {
                    RxjavaTimer.cancel();
                    mViewBinding.activityBindMobilMobil.setText("");
                    mViewBinding.activityBindMobilCode.setText("");
                    mViewBinding.activityBindMobilGetCode.setText("获取验证码");
                    mViewBinding.activityBindMobilGetCode.setClickable(true);
                    configDialog.dismiss();
                }
            });
            configDialog.show();
        } else if (loginEntity.getCode() == 888) {
            Paper.book().delete(PagerCons.USER_DATA);
            ConfigDialog configDialog = new ConfigDialog(this, 6, "您已绑定成功，请直接登录", 3);
            configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
                @Override
                public void onOKClick(String code) {
                    RxjavaTimer.cancel();
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelClick() {

                }
            });
            configDialog.show();

        } else {

            MyToast.showToast(loginEntity.getMsg());
        }
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showBindCode(Serializable serializable) {
        BindInviterEntity entity = (BindInviterEntity) serializable;

        if (entity.getCode() == 0) {
            MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPEN_INSTALL_COUNT); //openInstall用户数量
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
            finish();
            MyToast.showToast(entity.getMsg());

        } else {
            ToastUtils.getInstance().show(mActivity,entity.getMsg());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Paper.book().delete(PagerCons.USER_DATA);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxjavaTimer.cancel();
        EventBus.getDefault().unregister(this);
    }

    public void setInvateCode(String invateCode) {
        Map<String, String> map = new HashMap<>();
        map.put("invitation_code", invateCode);
        map.put("user_token", loginEntity.getData().getUser_token());
        map.put("channel", BaseApplication.getChannel());
        map.put("from", "native_jjb");
        map.put("uid", "0");
        map.put("source_style", "8");
        map.put("version", StringUtils.getVersionCode(mActivity));
        map.put("vsn", StringUtils.getVersionCode(mActivity));
        mPresenter.BindCode(map, mActivity);
    }
}

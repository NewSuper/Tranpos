package com.newsuper.t.juejinbao.ui.login.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityGuideLoginBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.LoginStateEvent;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.impl.LoginPresenterImpl;
import com.newsuper.t.juejinbao.ui.my.dialog.FragmentMyConfigDialog;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.PhoneInfoUtils;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import io.paperdb.Paper;
import rx.functions.Action1;

public class GuideLoginActivity extends BaseActivity<LoginPresenterImpl, ActivityGuideLoginBinding> implements LoginPresenterImpl.LoginView {
    //登录后跳转影视搜索
    public static final int TOMOIVESEATCH = 100;

    //登录后发送的跳转事件
    public static int intentEvent = -1;
    public static HashMap<String , String> params = new HashMap<>();


    private Intent intent;
    private RxPermissions rxPermissions;
    private Context context = this;
    private String phone = "";
    private int loginOtherType = 0;
    private LoginEntity loginEntity;
    private boolean isOffline;
    private AlertDialog alertDialog;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_login;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);


        //进来直接竖屏
        if(Build.VERSION.SDK_INT != Build.VERSION_CODES.O ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //登录成功后的跳转参数
        try {
            intentEvent = getIntent().getIntExtra("intentEvent", -1);
            params = (HashMap<String, String>) getIntent().getSerializableExtra("params");
        }catch (Exception e){e.printStackTrace();}

        if (!EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);
        }
        rxPermissions = new RxPermissions(this);
        //获取权限
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).
                subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
//                            String number = OSUtils.getMSISDN(context);
                            PhoneInfoUtils phoneInfoUtils = new PhoneInfoUtils(context);
                            String number = phoneInfoUtils.getNativePhoneNumber();
                            if (number == null) {
                                return;
                            }
                            if (number.length() > 11) {
                                phone = number.substring(number.length() - 11);
                            } else {
                                phone = number;
                            }

                        }
                    }
                });
        setResult(RESULT_OK);
        mViewBinding.loadingProgressbarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        isOffline = getIntent().getBooleanExtra("isOffline",false);
        if(isOffline) {
            if(alertDialog == null) {
                alertDialog = new AlertDialog.Builder(this).setTitle("提示").setMessage(getIntent().getStringExtra("msg")).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).create();
            }
            alertDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("zy", "onRequestPermissionsResult");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onIsLoginEvent(LoginStateEvent loginStateEvent) {
        if (loginStateEvent.isLogin()) {
//            LoginEntity loginEntity = (LoginEntity) Paper.book().read(PagerCons.USER_DATA);
//            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            finish();
        }
    }

    @Override
    public void initData() {
        mViewBinding.activityGuideLoginBar.moduleIncludeTitleBarTitle.setText("登录");
//        mPresenter.IsShowWchatorQQ(new HashMap<>(), this);

        try{
            IsShowQQEntity loginEntity = Paper.book().read(PagerCons.KEY_GUIDELOGIN_UI);
            showIsShowWchatorQQ(loginEntity);
        }catch (Exception e){e.printStackTrace();}

    }

    @OnClick({R.id.activity_guide_login_mobil,
            R.id.module_include_title_bar_return,
            R.id.activity_guide_login_wechat,
            R.id.activity_guide_logina_wechat,
            R.id.activity_guide_login_qq,
            R.id.activity_guide_logina_phone,
            R.id.ll_wxlogin,R.id.login_use_agreement, R.id.login_sms_agreement})
    public void OnClinck(View view) {
        switch (view.getId()) {
            //账号密码登录
            case R.id.activity_guide_login_mobil:
                intent = new Intent(mActivity, LoginActivity.class);
                intent.putExtra("loginType", 2);
                startActivity(intent);
                break;
            //返回
            case R.id.module_include_title_bar_return:
                finish();
                break;
            //手机号一建登录
            case R.id.activity_guide_login_wechat:
                //埋点（点击手机号一键注册/登录按钮）
             //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.MOBILE_PHONE_NUMBER_ONE_KEY_TO_LOGIN);

//                if (phone != null && phone.length() > 0 && phone.length() == 11) {
//                    //直接登录
//                   /* signature=[签名，算法:md5(md5(account+expire)+secretKey+version)]
//                    expire=[有效期时间戳，取当前时间+300秒]
//                    version=[版本号]
//                    account=[手机号]
//
//                    签名key：
//                    secretKey = "1dTR掘22a6&金e76_PO306宝Android#c5d7@7ae";
//                    */
//                    FragmentMyConfigDialog configDialog = new FragmentMyConfigDialog(context, 6,
//                            phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
//                    configDialog.setOnDialogClickListener(new FragmentMyConfigDialog.OnDialogClickListener() {
//                        @Override
//                        public void onOKClick(String code) {
//                            //更换为账号密码登录
//                            intent = new Intent(mActivity, LoginActivity.class);
//                            intent.putExtra("loginType", 2);
//                            startActivity(intent);
//                            configDialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClick() {
//                            //一键登录
//                            String time = String.valueOf((System.currentTimeMillis() / 1000 + 300));
//                            String signature = MD5Utils.encryptMD5ToString(MD5Utils.encryptMD5ToString(phone + time) + "1dTR掘22a6&金e76_PO306宝Android#c5d7@7ae" + Utils.getVersion());
//                            Map<String, String> map = new HashMap<>();
//                            map.put("signature", signature);
//                            map.put("expire", time);
//                            map.put("version", Utils.getVersion());
//                            map.put("account", phone);
//                            mPresenter.LoginOnePsw(map, mActivity);
//                            configDialog.dismiss();
//                        }
//                    });
//                    configDialog.show();
//                } else {
//                    //验证码登录
//                    intent = new Intent(mActivity, LoginActivity.class);
//                    intent.putExtra("loginType", 2);
//                    startActivity(intent);
//                }

                //验证码登录
                intent = new Intent(mActivity, LoginActivity.class);
                intent.putExtra("loginType", 1);
                startActivity(intent);
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自微信分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.WEIXIN);

//                authorization(SHARE_MEDIA.WEIXIN);
                break;
            //QQ
            case R.id.activity_guide_login_qq:
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自QQ分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.QQ);

                //埋点（点击QQ登录按钮）
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_QQ);

                loginOtherType = 1;
             //   authorization(SHARE_MEDIA.QQ);

                break;
             case R.id.activity_guide_logina_wechat:
//                ShareUtils.shareWeb(this, "http://www.juejinchain.com/", "掘金宝",
//                        "这是来自QQ分享的掘金宝消息", "", R.mipmap.icon_app, SHARE_MEDIA.QQ);

                //埋点（点击微信登录按钮）
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_WECHAT);

                loginOtherType = 2;
              //  authorization(SHARE_MEDIA.WEIXIN);
                break;
                //切换登录形态
            case R.id.activity_guide_logina_phone:
                setLoginUI(1 , -1 , 1);
                break;
            case R.id.ll_wxlogin:
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.LOGIN_WITH_WECHAT);

                loginOtherType = 2;
             //   authorization(SHARE_MEDIA.WEIXIN);
                break;
            //用户协议
            case R.id.login_use_agreement:
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_USE_AGREEMENT,"掘金宝用户协议");
                break;
            //隐私协议
            case R.id.login_sms_agreement:
                WebSmsActivity.intentMe(mActivity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_SMS_AGREEMENT,"掘金宝隐私条款");
                break;
        }
    }

//    private void authorization(SHARE_MEDIA share_media) {
//        UMShareAPI.get(this).getPlatformInfo(this, share_media, umAuthListener);
//    }

    /**
     * 授权监听
     */
//    private UMAuthListener umAuthListener = new UMAuthListener() {
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//            //授权开始的回调
//            Log.e("TAG", "onStart =============》》》》》》》" + "授权开始");
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> map) {
//
//            Log.e("TAG", "onComplete =============》》》》》》》" + "授权完成");
//            //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
//            String uid = map.get("uid");
//            String openid = map.get("openid");//微博没有
//            String unionid = map.get("unionid");//微博没有
//            String access_token = map.get("access_token");
//            String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//            String expires_in = map.get("expires_in");
//            String name = map.get("name");
//            String gender = map.get("gender");
//            String iconurl = map.get("iconurl");
//            Log.e("TAG", "onComplete: 打印第三方获取的参数=======>>>>>" + "name=" + name
//                    + "uid=" + uid
//                    + "openid=" + openid
//                    + "unionid =" + unionid
//                    + "access_token =" + access_token
//                    + "refresh_token=" + refresh_token
//                    + "expires_in=" + expires_in
//                    + "gender=" + gender
//                    + "iconurl=" + iconurl);
//
//            mViewBinding.loadingProgressbarLogin.setVisibility(View.VISIBLE);
//            if (share_media.equals(SHARE_MEDIA.WEIXIN)) {
//                initWXCommit(access_token, openid);
//            } else if (share_media.equals(SHARE_MEDIA.QQ)) {
//                initQQCommit(access_token, openid);
//            } else if (share_media.equals(SHARE_MEDIA.SINA)) {
//
//            }
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
//            Log.e("TAG", "onError: ======>>>>>" + action);
//            if (platform.equals(SHARE_MEDIA.WEIXIN)) {
//                ToastUtils.getInstance().show(getApplicationContext(), "未安装微信客户端，请选择其他登录方式！");
//            } else if (platform.equals(SHARE_MEDIA.QQ)) {
//                ToastUtils.getInstance().show(getApplicationContext(), "未安装QQ，请安装QQ或选择其他登录方式！");
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
//        }
//    };

    //weixin登录
    private void initWXCommit(String access_token, String openid) {
       /* http://dev.api.juejinchain.cn/v1/wechat/oath
        access_token=[友盟获取到的access_token]
        openid=[友盟获取到的openid]*/
        if (access_token == null) {
            return;
        }
        if (openid == null)
            return;
        if (mPresenter == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", access_token);
        map.put("openid", openid);
        mPresenter.LoginWX(map, this);
    }

    //QQ登录
    private void initQQCommit(String access_token, String openid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("access_token", access_token);
        map.put("openid", openid);
        if(mPresenter != null) {
            mPresenter.LoginQQ(map, this);
        }
    }

    @Override
    public void showListSuccess(Serializable serializable) {

    }

    //其他登录获得
    @Override
    public void showOtherSuccess(Serializable serializable) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {

            if(loginEntity.getData().getShow_invitation_code_alert()==1){

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);


                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, loginOtherType);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            }

            if (loginEntity.getData().getMobile().trim().length() == 0) {

                intent = new Intent(mActivity, BindMobilAcitivity.class);

                intent.putExtra("loginOtherType", loginOtherType);
                intent.putExtra("token", loginEntity.getData().getUser_token());
                startActivity(intent);
                return;
            } else {
                if (loginEntity.getData().getPassword() == null || loginEntity.getData().getPassword().length() == 0) {
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
            EventBus.getDefault().post(new LoginEvent(intentEvent , params));
            finish();
            MyToast.showToast(loginEntity.getMsg());

        } else {
            MyToast.showToast(loginEntity.getMsg());
        }
        //注销微信QQ登录
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

    //是否显示微信或者QQ
    @Override
    public void showIsShowWchatorQQ(Serializable serializable) {
        IsShowQQEntity isShowQQEntity = (IsShowQQEntity) serializable;
        if (isShowQQEntity.getCode() == 0) {
//            if (isShowQQEntity.getData().getWechat() == 1) {
//                mViewBinding.activityGuideLoginaWechatAbout.setVisibility(View.VISIBLE);
//            } else {
//                mViewBinding.activityGuideLoginaWechatAbout.setVisibility(View.GONE);
//            }
//
//            if (isShowQQEntity.getData().getQq() == 1) {
//                mViewBinding.activityGuideLoginaQqAbout.setVisibility(View.VISIBLE);
//            } else {
//                mViewBinding.activityGuideLoginaQqAbout.setVisibility(View.GONE);
//
//            }
            setLoginUI(isShowQQEntity.getData().getWechat() , isShowQQEntity.getData().getQq() , isShowQQEntity.getData().getIs_mobile_first());
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
                EventBus.getDefault().post(new LoginEvent(intentEvent , params));
                finish();
            }
        });
        configDialog.show();
    }

    //一键登录
    @Override
    public void showLoginOnePswSuccess(Serializable serializable) {

        loginEntity = (LoginEntity) serializable;
        if (loginEntity.getCode() == 0) {
            if(loginEntity.getData().getShow_invitation_code_alert()==1){

                String invateCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL);


                if (TextUtils.isEmpty(invateCode) || "used".equals(invateCode)) {
                    LoginSetInviteCodeActivity.intentMe(mActivity, loginEntity, loginOtherType);
                    finish();

                } else {
                    setInvateCode(invateCode);
                    return;
                }

            }

            if (loginEntity.getData().getPassword().length() == 0) {
                loginEntity.setLogin(false);
                intent = new Intent(mActivity, SetPasswordActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("token", loginEntity.getData().getUser_token());
                startActivity(intent);
                return;
            }
            loginEntity.setLogin(true);
            loginEntity.getData().setTakeToken(true);
            Paper.book().write(PagerCons.USER_DATA, loginEntity);
            EventBus.getDefault().post(new LoginEvent(intentEvent , params));
            MyToast.showToast(loginEntity.getMsg());
            finish();
        } else {
            MyToast.showToast(loginEntity.getMsg());
        }

    }

    @Override
    public void showSetOrChangePsw(SetAndChangePswEntity setAndChangePswEntity) {

    }

    @Override
    public void showBindCode(Serializable serializable) {
        BindInviterEntity entity = (BindInviterEntity) serializable;
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
        if (entity.getCode() == 0) {
//            configDialog.dismiss();
//            EventBus.getDefault().post(new LoginEvent());
//            EventBus.getDefault().post(new LoginStateEvent(true));
//            finish();
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



            EventBus.getDefault().post(new LoginStateEvent(true));
            EventBus.getDefault().post(new LoginEvent(intentEvent , params));
            finish();
            MyToast.showToast(entity.getMsg());

        } else {
            ToastUtils.getInstance().show(mActivity,entity.getMsg());
        }
    }

    @Override
    public void bindWechatSuccess(Serializable serializable) {

    }

    @Override
    public void showError(String s) {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mViewBinding.loadingProgressbarLogin.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    /**
     *
     * @param context
     * @param isOffline 是否是被挤下线
     * @param msg 提示
     */
    public static void start(Context context,boolean isOffline,String msg) {
        Intent intent = new Intent(context,GuideLoginActivity.class);
        intent.putExtra("isOffline",isOffline);
        intent.putExtra("msg",msg);
        context.startActivity(intent);
    }

    //根据参数改变UI显示与隐藏
    public void setLoginUI(int wechat , int qq ,int is_mobile_first){
//        mViewBinding.activityGuideLoginWechat;
//        mViewBinding.activityGuideLoginMobil;
//        mViewBinding.llWxlogin;
//
//        mViewBinding.activityGuideLoginaWechatAbout;
//        mViewBinding.activityGuideLoginaPhone;
//        mViewBinding.activityGuideLoginaQqAbout;

        if(wechat == 1){
            if(is_mobile_first == 1){
                mViewBinding.activityGuideLoginWechat.setVisibility(View.VISIBLE); //不再显示一键登录
                mViewBinding.activityGuideLoginMobil.setVisibility(View.VISIBLE);
                mViewBinding.llWxlogin.setVisibility(View.GONE);

                mViewBinding.activityGuideLoginaWechatAbout.setVisibility(View.VISIBLE);
                mViewBinding.activityGuideLoginaPhone.setVisibility(View.GONE);

            }else{
                mViewBinding.activityGuideLoginWechat.setVisibility(View.GONE);
                mViewBinding.activityGuideLoginMobil.setVisibility(View.GONE);
                mViewBinding.llWxlogin.setVisibility(View.VISIBLE);

                mViewBinding.activityGuideLoginaWechatAbout.setVisibility(View.GONE);
                mViewBinding.activityGuideLoginaPhone.setVisibility(View.VISIBLE);
            }
        }else{
            mViewBinding.activityGuideLoginWechat.setVisibility(View.VISIBLE); //不再显示一键登录
            mViewBinding.activityGuideLoginMobil.setVisibility(View.VISIBLE);
            mViewBinding.llWxlogin.setVisibility(View.GONE);

            mViewBinding.activityGuideLoginaWechatAbout.setVisibility(View.GONE);
            mViewBinding.activityGuideLoginaPhone.setVisibility(View.GONE);

        }

        if(qq == 1){
            mViewBinding.activityGuideLoginaQqAbout.setVisibility(View.VISIBLE);
        }else if(qq == 0){
            mViewBinding.activityGuideLoginaQqAbout.setVisibility(View.GONE);
        }
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

    //跳转登录界面，登录成功后发送参数登录事件
    public static void intentMeAndToActivity(Context context , int intentEvent, HashMap<String , String> params){
        Intent intent = new Intent(context , GuideLoginActivity.class);
        intent.putExtra("intentEvent" , intentEvent);
        intent.putExtra("params" , params);
        context.startActivity(intent);
    }

}

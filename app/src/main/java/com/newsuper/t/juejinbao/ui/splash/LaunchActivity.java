package com.newsuper.t.juejinbao.ui.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.databinding.ActivityLaunchBinding;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.my.dialog.ConfigDialog;
import com.juejinchain.android.utils.PermissionPageUtils;
import com.juejinchain.android.view.UserAgreementDialog;
import com.qq.e.comm.util.StringUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.network.RetrofitManager;
import com.ys.network.utils.ActivityCollector;

import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import rx.Observer;

public class LaunchActivity extends BaseActivity<PublicPresenterImpl, ActivityLaunchBinding> {
    private Context context = this;
    private int type = 0;
    //是否是从后台切换至前台
    boolean isBackGroundChange;
    String stringExtra;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取唤醒参数
//        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);

        //获取OpenInstall安装数据
//        OpenInstall.getInstall(new AppInstallAdapter() {
//            @Override
//            public void onInstall(AppData appData) {
//                String bindData = appData.getData();
//                //获取渠道数据
//                String channelCode = appData.getChannel();
//
//                try {
//                    JSONObject jsonObject = new JSONObject(bindData);
//                    final String inviteCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL,   jsonObject.optString("inviteCode"));
//
//
//
//                    if (!TextUtils.isEmpty(inviteCode) &&  !"used".equals(inviteCode)) {
//                        Paper.book().write(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL, inviteCode);
//                    }
//
////                    if(!TextUtils.isEmpty(inviteCode) &&  !"used".equals(inviteCode)){
////                        //获取自定义数据
////                        ToastUtils.getInstance().show(mActivity, "获取邀请码为：" + inviteCode);
////                    }else if(!TextUtils.isEmpty(inviteCode) &&  "used".equals(inviteCode)){
////                        ToastUtils.getInstance().show(mActivity, "邀请码已被使用：" + inviteCode);
////                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
//        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

//    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
//        @Override
//        public void onWakeUp(AppData appData) {
//            String bindData = appData.getData();
//            //获取渠道数据
//            String channelCode = appData.getChannel();
//
//            try {
//                JSONObject jsonObject = new JSONObject(bindData);
//
//
//                final String inviteCode = Paper.book().read(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL,   jsonObject.optString("inviteCode"));
//
//                if (!TextUtils.isEmpty(inviteCode) &&  !"used".equals(inviteCode)) {
//                    Paper.book().write(PagerCons.KEY_INVATECODE_FROM_OPENINSTALL, inviteCode);
//                }
//
////                if(!TextUtils.isEmpty(inviteCode) &&  !"used".equals(inviteCode)){
////                    //获取自定义数据
////                    ToastUtils.getInstance().show(mActivity, "获取邀请码为：" + inviteCode);
////                }else if( "used".equals(inviteCode)){
////                    ToastUtils.getInstance().show(mActivity, "邀请码已被使用：" + inviteCode);
////                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    };

    @Override
    public int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_launch;
    }

    @Override
    public void initView() {
//        SplashAD splashAD = new SplashAD(this, "1108823726", "3000053978942747", null);
//        splashAD.preLoad();

    }

    private RxPermissions rxPermissions;
    private ConfigDialog configDialog;

    @Override
    public void initData() {

        try{
            init();
        }catch (Exception e){
            e.printStackTrace();
            goToMainActivity();
        }


    }

    private void init() {

        isBackGroundChange = ActivityCollector.isActivityExist(MainActivity.class);
        if (!RetrofitManager.RELEASE) {
            String api = getIntent().getStringExtra("api");
            String res = getIntent().getStringExtra("res");
            String web = getIntent().getStringExtra("web");
            if (!StringUtil.isEmpty(api)) {
                RetrofitManager.APP_URL_DOMAIN = api;
                RetrofitManager.host = RetrofitManager.HTTP + api;
            }

            if (!StringUtil.isEmpty(res))
//                RetrofitManager.WEB_URL_COMMON = res;

                if (!StringUtil.isEmpty(web)) {
                    RetrofitManager.WEB_URL_ONLINE = web;
                    RetrofitManager.VIP_JS_URL = web;
                }
        }

        stringExtra = getIntent().getStringExtra("to");

        rxPermissions = new RxPermissions(this);
        configDialog = new ConfigDialog(context, 4);
        configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
            @Override
            public void onOKClick(String code) {
                type++;
                PermissionPageUtils permissionPageUtils = new PermissionPageUtils(context);
                permissionPageUtils.jumpPermissionPage();

            }

            @Override
            public void onCancelClick() {
                requestPermision();
                configDialog.dismiss();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermision();

    }

    public void requestPermision() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        rxPermissions.request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
        ).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: ++===============>>>>>" + e.toString());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (!StringUtil.isEmpty(stringExtra) && stringExtra.equals("toBridgeWebViewActivity")) {
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            goToMainActivity();
                            timer.cancel();
                        }
                    }, 500);
                    return;
                }

                if (aBoolean) {
                    if (Paper.book().read(PagerCons.WELCOME) == null) {
                        new UserAgreementDialog(mActivity , new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, WelcomeGuideActivity.class);
                                startActivity(intent);
                                /*注意，需要使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
                                finish();
                            }
                        }).show();


                    } else {
                        Intent intent = new Intent(context, SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // 选用AUTO页面采集模式
                    MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

                } else {
                    configDialog.show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_PV);
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
                MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_NEWUSER_UV);
            }else{
                MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_OLDUSER_UV);
            }
        }else{
            MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_TOURISTS_UV);
        }
    }

    private void goToMainActivity() {
        if (isBackGroundChange) {
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        wakeUpAdapter = null;
    }
}
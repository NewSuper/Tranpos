package com.newsuper.t.juejinbao.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityLaunchBinding;
import com.newsuper.t.juejinbao.base.ActivityCollector;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.my.dialog.ConfigDialog;
import com.newsuper.t.juejinbao.ui.splash.SplashActivity;
import com.newsuper.t.juejinbao.ui.splash.WelcomeGuideActivity;
import com.newsuper.t.juejinbao.utils.PermissionPageUtils;
import com.newsuper.t.juejinbao.view.UserAgreementDialog;
import com.qq.e.comm.util.StringUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import rx.Observer;


public class JueJinBaoLaunchActivity extends BaseActivity<PublicPresenterImpl, ActivityLaunchBinding> {
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
    public int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_launch;
    }

    @Override
    public void initView() {

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

        isBackGroundChange = ActivityCollector.isActivityExist(JunjinBaoMainActivity.class);
     //   if (!RetrofitManager.RELEASE) {
            String api = getIntent().getStringExtra("api");
            String res = getIntent().getStringExtra("res");
            String web = getIntent().getStringExtra("web");
            if (!StringUtil.isEmpty(api)) {
                RetrofitManager.APP_URL_DOMAIN = api;
                RetrofitManager.host = RetrofitManager.HTTP + api;
            }

            if (!StringUtil.isEmpty(res))
                if (!StringUtil.isEmpty(web)) {
                    RetrofitManager.WEB_URL_ONLINE = web;
                    RetrofitManager.VIP_JS_URL = web;
                }
       // }

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
                  //  MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
                } else {
                    configDialog.show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    //    MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_PV);
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_NEWUSER_UV);
            }else{
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_OLDUSER_UV);
            }
        }else{
           // MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_TOURISTS_UV);
        }
    }

    private void goToMainActivity() {
        if (isBackGroundChange) {
            finish();
        } else {
            Intent intent = new Intent(this, JunjinBaoMainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}

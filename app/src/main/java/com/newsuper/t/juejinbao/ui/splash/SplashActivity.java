package com.newsuper.t.juejinbao.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.juejinchain.android.R;
import com.juejinchain.android.base.BaseEntity;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.config.TTAdManagerHolder;
import com.juejinchain.android.databinding.ActivitySplashBinding;
import com.juejinchain.android.hook.DeviceUtil;
import com.juejinchain.android.hook.entity.OwnADEntity;
import com.juejinchain.android.hook.presenter.ADOpenPresenter;
import com.juejinchain.android.hook.presenter.impl.ADOpenPresenterImpl;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.ad.ADCommitType;
import com.juejinchain.android.module.home.entity.AuditEntity;
import com.juejinchain.android.utils.MD5Utils;
import com.juejinchain.android.utils.PermissionPageUtils;
import com.juejinchain.android.utils.WeakHandler;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.utils.ActivityCollector;
import com.ys.network.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

/**
 * 广告页目前实现： 切到后台及退出app记录时间 弹出广告时间由后台接口控制
 */
public class SplashActivity extends BaseActivity<ADOpenPresenterImpl, ActivitySplashBinding> implements WeakHandler.IHandler, ADOpenPresenter.View {


    private boolean canJump;


    private TTAdNative mTTAdNative;
    //是否强制跳转到主页面
    private boolean mForceGoMain;
    //开屏广告加载发生超时但是SDK没有及时回调结果的时候，做的一层保护。
    private final WeakHandler mHandler = new WeakHandler(this);

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private static final int MSG_GO_MAIN = 1;
    //开屏广告是否已经加载
    private boolean mHasLoaded;
    private String TAG = "zzz";

    //开屏广告使用：1自平台，0广点通，2穿山甲 3 搜狗
    private int adType;

    public final static int OPEN_AD_TYPE_CSJ = 0;
    public final static int OPEN_AD_TYPE_GDT = 1;
    public final static int OPEN_AD_TYPE_SELF = 2;
    public final static int OPEN_AD_TYPE_SG = 3;

    public static final int COUNTER_LONG = 5000;


    // 规定时间
    public static final long INTERVAL_TIME = 1000 * 60 * 15;
    // 启动限制次数
    public static final long LAUNCH_LIMIT_OF_LAUNCH = 3;

    //默认的置于后台广告显示间隔时间
    long BACKGROUND_INTERVAL_TIME_DEFAULT = 1000 * 60 * 5;

    //是否是从后台切换至前台
    boolean isBackGroundChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mPresenter.getAuditData(mActivity);

        adType = Paper.book().read(PagerCons.AD_TYPE_CHCHE) == null ? OPEN_AD_TYPE_GDT : Paper.book().read(PagerCons.AD_TYPE_CHCHE);
        //运行时权限处理
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            //获取下次广告类型
            mPresenter.loadOwnADAndNestTimeOpenType(mActivity);
            mViewBinding.llOwnAd.setVisibility(View.GONE);


            // 前后台切换广告显示时间间隔
            long backGroundIntervalTime = Paper.book().read(PagerCons.KET_BACKGROUND_INTERVAL_TIME, BACKGROUND_INTERVAL_TIME_DEFAULT);

            // 重启app显示广告时间间隔
            long exitAppShowAdTime = Paper.book().read(PagerCons.KET_BACK_INTERVAL_TIME, 0l);

            //上次退出app的时间
            long lastExitAppTime = Paper.book().read(PagerCons.KET_EXIT_APP_TIME, System.currentTimeMillis());

            //上次app退至后台的时间
            long lastBackGroundTime = Paper.book().read(PagerCons.KET_BACKGROUND_TIME, System.currentTimeMillis());

            HashMap<String, Integer> auditMap = Paper.book().read(PagerCons.KEY_AUDIT_MAP);
            if (auditMap == null) {
                auditMap = new HashMap();
            }
            // 是否关闭广告
            int isCloseOpenAD = auditMap.get(Constant.KEY_AD_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_AD_CONTROL);


            if (ActivityCollector.isActivityExist(MainActivity.class)) {
                isBackGroundChange = true;

                // mainactivity已存在 说明是后台切换至前台

                long currentTimeMillis = System.currentTimeMillis();

                if (currentTimeMillis - lastBackGroundTime > backGroundIntervalTime && isCloseOpenAD != 1) {
                    openAd();
                } else {
                    finish();
                }

            } else {

                isBackGroundChange = false;
                // mainactivity不存在 说明启动app
                if (System.currentTimeMillis() - lastExitAppTime > exitAppShowAdTime && isCloseOpenAD != 1) {
                    openAd();
                } else {
                    mViewBinding.container.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    },2000);

                }

            }
        }

    }

    public void openAd() {
        if (adType == OPEN_AD_TYPE_SELF) {
            //自有广告
            mViewBinding.llOwnAd.setVisibility(View.VISIBLE);
        } else if (adType == OPEN_AD_TYPE_CSJ) {
            // 穿山甲
            loadSplashAd();
        }else {
            // 广点通
            requestAds();
        }
    }



    private CountDownTimer mCountDownTimer = new CountDownTimer(COUNTER_LONG, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mViewBinding.button.setText("跳过 " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


    /*
     *
     * 请求开屏广告
     * */
    private void requestAds() {
        String appId = "1108823726";
        String adId = "3000053978942747";
        SplashAD splashAD = new SplashAD(this, appId, adId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                //广告显示完毕
                forward();

            }

            @Override
            public void onNoAD(AdError adError) {
                //广告加载失败
                forward();
            }

            @Override
            public void onADPresent() {
                //广告加载成功
                commitAdData(ADCommitType.AD_TYPE_GDT_SHOW);
            }

            @Override
            public void onADClicked() {
                //埋点（点击开屏页）
                MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPENING_THE_PAGE_ADVERTISEMENT);
                MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_ADS_CLICKUSERS);
                //广告被点击
                commitAdData(ADCommitType.AD_TYPE_GDT_CLICK);
            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onADExposure() {
//                forward();
            }
        });
        splashAD.fetchAndShowIn(mViewBinding.container);

    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            forward();
        }
        canJump = true;
    }

    private void forward() {
        if (canJump) {
            //跳转到MainActivity
            if (isBackGroundChange) {
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            canJump = true;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();

                            PermissionPageUtils permissionPageUtils = new PermissionPageUtils(mActivity);
                            permissionPageUtils.jumpPermissionPage();
                            finish();
                            return;
                        }
                    }
//                    requestAds();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        mHandler.sendEmptyMessageDelayed(MSG_GO_MAIN, AD_TIME_OUT);

        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(TTAdManagerHolder.POS_ID_OPEN)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                mHasLoaded = true;
//                showToast(message);
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                mHasLoaded = true;
//                showToast("开屏广告加载超时");
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                mHasLoaded = true;
                mHandler.removeCallbacksAndMessages(null);
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                mViewBinding.container.removeAllViews();
                //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                mViewBinding.container.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        //埋点（点击开屏页）
                        MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPENING_THE_PAGE_ADVERTISEMENT);
                        MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_ADS_CLICKUSERS);
                        commitAdData(ADCommitType.AD_TYPE_CSJ_CLICK);
                    }

                    @Override
                    public void onAdShow(View view, int type) {

                        commitAdData(ADCommitType.AD_TYPE_CSJ_SHOW);
                    }

                    @Override
                    public void onAdSkip() {

                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {

                        goToMainActivity();
                    }
                });
            }
        }, AD_TIME_OUT);
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        if (isBackGroundChange) {
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

//    private void showToast(String msg) {
//        MyToast.showToast(msg);
//    }

    @Override
    public void handleMsg(Message msg) {
        if (msg.what == MSG_GO_MAIN) {
            if (!mHasLoaded) {
//                showToast("广告已超时，跳到主页面");
                goToMainActivity();
            }
        }
    }

    /**
     * 统计广告查看或点击
     *
     * @param type 1代表点击 0代表浏览
     */
    void takeAd(String type, int id) {
        Map<String, String> param = new HashMap<>();
        param.put("ad_id", id + "");
        param.put("type", type);
        param.put("uuid", DeviceUtil.getUUID(getBaseContext()));
        mPresenter.clickAdCount(param, mActivity);
    }

    @Override
    public void loadAdDateSuccess(Serializable serializable) {
        OwnADEntity ownADEntity = (OwnADEntity) serializable;
        /**
         *  广告类型
         */
        int ad_platform_type = ownADEntity.getData().getAd_platform_type();

        /**
         *  前后台广告间隔时间
         */
        int reopen_time = ownADEntity.getData().getReopen_time();
        int restart_time = ownADEntity.getData().getRestart_time();
        int showSenselessAd = ownADEntity.getData().getShow_senseless_ad();

        Paper.book().write(PagerCons.KET_BACK_INTERVAL_TIME, (long) restart_time * 1000);
        Paper.book().write(PagerCons.KET_BACKGROUND_INTERVAL_TIME, (long) reopen_time * 1000l);
        Paper.book().write(PagerCons.KEY_SHOW_SENSELESS_AD, showSenselessAd);
        //緩存首頁頭部背景圖
        Paper.book().write(PagerCons.KEY_HOME_TITLE_BG, ownADEntity.getData().getTop_bg());
        //緩存VIP頭部背景圖
        Paper.book().write(PagerCons.KEY_VIP_TITLE_BG, ownADEntity.getData().getMovie_bg());
        //缓存首页广告间隔
        Paper.book().write(PagerCons.KEY_INTERVAL_HOME_PAGE_AD, ownADEntity.getData().getInterval_home_page_ad());
        Log.i(TAG, "loadListAd: 拉取广告次数" + ownADEntity.getData().getInterval_home_page_ad());
        //缓存小视频广告间隔
        Paper.book().write(PagerCons.KEY_INTERVAL_SMALLVIDEO_AD, ownADEntity.getData().getInterval_smallvideo_ad());


        Paper.book().write(PagerCons.KEY_INTERVAL_SMALLVIDEO_AD, ownADEntity.getData().getInterval_smallvideo_ad());

        //是否关闭开屏广告
        Paper.book().write(PagerCons.KEY_IS_CLOSE_OPEN_AD, ownADEntity.getData().getIs_close_open_ad());

//        Paper.book().write(PagerCons.AD_TYPE_CHCHE, ad_platform_type);
        takeAd("0", ownADEntity.getData().getAd_info().get(0).getId());

        if (adType == OPEN_AD_TYPE_SELF) {
            commitAdData(ADCommitType.AD_TYPE_JJB_SHOW);
            mViewBinding.llOwnAd.setVisibility(View.VISIBLE);
            Glide.with(mActivity).load(ownADEntity.getData().getAd_info().get(0).getImages().get(0)).into(mViewBinding.imageView);
            mCountDownTimer.start();

            mViewBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //埋点（点击开屏页）
                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.OPENING_THE_PAGE_ADVERTISEMENT);
                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.LUNCHPAGE_ADS_CLICKUSERS);
//                Log.d(TAG, "onClick: 点击了图片");
                    takeAd("1", ownADEntity.getData().getAd_info().get(0).getId());
                    commitAdData(ADCommitType.AD_TYPE_JJB_CLICK);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
//                    Uri content_url = Uri.parse("http://www.cnblogs.com");
                    String url = ownADEntity.getData().getAd_info().get(0).getLink();
                    if (url != null && url.trim().length() == 0) {
                        url = ownADEntity.getData().getAd_info().get(0).getDownload_android();
                    }
                    if (url == null || url.trim().length() == 0) return;
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    //有些手机不存在
                    startActivity(intent);
                    canJump = false;
                    mCountDownTimer.onFinish();
                    mCountDownTimer.cancel();
                }
            });


        }
    }

    /**
     * 签名算法：md5(md5(token+expire)+secretKey+version+ad_contribute_type)
     * version:1.8.7
     * user_token:5c2dafb101e0bb6d0da35669eb8341f2
     * nowTime:1565406392
     * expire:1565406692
     * ad_contribute_type:show_partner
     */
    public void commitAdData(String type) {
        String version = StringUtils.getVersionCode(this);
        String user_token = LoginEntity.getUserToken();
        long nowTime = System.currentTimeMillis() / 1000;
        long expire = nowTime + 300;
        String ad_contribute_type = type;

        String encrypt = MD5Utils.md5(user_token + expire);

        String signature = MD5Utils.md5(encrypt + ADCommitType.AD_SECREKEY + version + ad_contribute_type);

        Map<String, String> param = new HashMap<>();
        param.put("signature", signature);
        param.put("expire", expire + "");
        param.put("ad_contribute_type", ad_contribute_type);
        mPresenter.commitAdData(param, mActivity);
    }

    @Override
    public void clickAdCountBack(Serializable serializable) {

    }

    @Override
    public void onAdDataCommitSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;
        Log.i(TAG, "onAdDataCommitSuccess: " + baseEntity.getCode());
    }

    /**
     * 过审控制
     *
     * @param serializable
     */
    @Override
    public void onAuditDataBack(Serializable serializable) {
        AuditEntity auditEntity = (AuditEntity) serializable;

        if(auditEntity.getCode()==0){
            if (auditEntity != null && auditEntity.getData() != null) {
                Log.i(TAG, "onAuditDataBack: " + auditEntity.getData().getFun1());

                HashMap hashMap = new HashMap();
                hashMap.put(Constant.KEY_AD_CONTROL,auditEntity.getData().getFun1());
                hashMap.put(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL,auditEntity.getData().getFun2());
                hashMap.put(Constant.KEY_BOTTOM_CONTROL,auditEntity.getData().getFun3());

                Paper.book().write(PagerCons.KEY_AUDIT_MAP,hashMap);
            }
        }else {
            HashMap hashMap = new HashMap();
            hashMap.put(Constant.KEY_AD_CONTROL,0);
            hashMap.put(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL,0);
            hashMap.put(Constant.KEY_BOTTOM_CONTROL,0);

            Paper.book().write(PagerCons.KEY_AUDIT_MAP,hashMap);
        }


    }

    @Override
    public void onerror(String errResponse) {

    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

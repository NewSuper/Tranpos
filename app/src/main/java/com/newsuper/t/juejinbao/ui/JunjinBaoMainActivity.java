package com.newsuper.t.juejinbao.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.MainActivity;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMainiBinding;
import com.newsuper.t.juejinbao.base.ActivityCollector;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.EventBusOffLineEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.PushDataEvent;
import com.newsuper.t.juejinbao.bean.ReadArticleLeaveEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.bean.ShowTabPopupWindowEvent;
import com.newsuper.t.juejinbao.bean.SwitchTabEvent;
import com.newsuper.t.juejinbao.bean.TabSelectedEvent;
import com.newsuper.t.juejinbao.bean.WatchAdGetRewardEvent;
import com.newsuper.t.juejinbao.ui.ad.HomeAdDialogEntity;
import com.newsuper.t.juejinbao.ui.ad.PushAdDialog;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.dialog.BackExitDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.CommonDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.HomeTipsAlertDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.MiniProgramRewardDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.WalkMiniProgramRewardDialog;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;
import com.newsuper.t.juejinbao.ui.home.entity.BackCardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeWalkMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeBottomTabEntity;
import com.newsuper.t.juejinbao.ui.home.entity.NewTaskEvent;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TabChangeEvent;
import com.newsuper.t.juejinbao.ui.home.entity.UnReadEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelFareRewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelfareEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.Homefragment;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.ppw.TimeRewardPopup;
import com.newsuper.t.juejinbao.ui.home.ppw.WelComeAdPop;
import com.newsuper.t.juejinbao.ui.home.presenter.MainPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.MainPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarMovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarSearchListEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.PushEntity;
import com.newsuper.t.juejinbao.ui.movie.fragment.VipFragment;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.my.MyFragment;
import com.newsuper.t.juejinbao.ui.my.activity.InviteFriendActivity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDomainEntity;
import com.newsuper.t.juejinbao.ui.share.entity.SharePicsEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.view.ShouyeMoviePopupWindow;
import com.newsuper.t.juejinbao.ui.song.view.ShouyeRenwuPopupWindow;
import com.newsuper.t.juejinbao.ui.song.view.ShouyeZhuanchezhuanfangPopupWindow;
import com.newsuper.t.juejinbao.ui.task.fragment.TaskFragment;
import com.newsuper.t.juejinbao.ui.task.sleep.SleepMoneyActivity;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.NoDoubleListener;
import com.newsuper.t.juejinbao.utils.PreloadVideoUtils;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.UpAppUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.OSUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.PermissionsUtils;
import com.newsuper.t.juejinbao.view.BottomBar;
import com.newsuper.t.juejinbao.view.BottomBarTab;
import com.newsuper.t.juejinbao.view.NewGiftBagForEggsDialog;
import com.newsuper.t.juejinbao.view.NewGiftBagSuccessDialog;
import com.newsuper.t.juejinbao.view.ReceiveFourEggsDialog;
import com.newsuper.t.juejinbao.view.TaskPopupWindow;
import com.newsuper.t.juejinbao.view.alerter.Alerter;
import com.squareup.otto.Subscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;

public class JunjinBaoMainActivity extends BaseActivity<MainPresenterImpl, ActivityMainiBinding> implements MainPresenter.View {
    public static final String TAG = "MainActivity.class";
    private Handler mHandler = new Handler();
    private RotateAnimation mRotateAnimation;
    private BottomBarTab mBottomBarMovie;
    private BottomBarTab mBottomBarTask;
    private BottomBarTab mBottomBarMine;
    public static final int HOME = 0;
    public static final int MOVIE = 1;      //影视
    public static final int Make_Money = 2; //赚车赚房
    public static final int TASK = 3;       //任务
    public static final int MINE = 4;       //我的
    private int mCurrPosition;
    private int mPrePosition;
    private long lastClickTime = 0;
    public static final int MIN_CLICK_DELAY_TIME = 1500;
    public boolean Default_Show_Movie_Status = true;          //默认显示还是隐藏
    public boolean Is_Show_Movie = Default_Show_Movie_Status; //是否显示影视模块
    public boolean Load_ShowBarAPI_Finish;                    //切换接口请求完成

    private boolean isNewTaskClick = false;// 是否是点击新手任务跳转
    private boolean isReceiveEggs = false;// 是否是点击领鸡蛋跳转

    //赚车赚房页面url
    public String make_money_url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_RICH_PLAY;

    //相关资源弹出框
//    public DependentResourceDialog dependentResourceDialog;

  //  public static TTAdNative mTTAdNative;
    private BackCardEntity backCardEntity;

    BackExitDialog mBackDialog;
    Alerter mUnreadAlerter; //未读信息提示

    //是否直接跳转到任务页
    private boolean toTask = false;
    //插屏广告
    private boolean mIsLoading = false;
    private ImageView mAdImageView;
    private ImageView mCloseImageView;
    private Dialog mAdDialog;
    private ViewGroup mRootView;
    private TextView mDislikeView;
//    private RequestManager mRequestManager;

    //推送弹窗
    private PushAdDialog pushAdDialogs;
    //推送弹窗数据实体
    private PushEntity dialogPushEntity;
    private BottomBarTab mBottomBarHome;
    private BottomBarTab mBottomBarMoney;
    CommonDialog commonDialog;
    public boolean isCheckUpdate = true;
    //获取影视搜索列表规则
    public static MovieRadarSearchListEntity movieRadarSearchListEntity;
    //影视详情规则
    public static Map<String, MovieRadarMovieDetailEntity> movieRadarMovieDetailEntityMap = new HashMap<>();

    //过审控制
    private HashMap<String, Integer> auditMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化广告管理对象
       // TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(mActivity);

       // mTTAdNative = ttAdManager.createAdNative(getApplicationContext());

        initYuYueTui();

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // 从已保存状态恢复成员的值
            Is_Show_Movie = savedInstanceState.getBoolean("Is_Show_Movie", false);
        } else {
            // 可能初始化一个新实例的默认值的成员
        }

        EventBus.getDefault().register(this);
        PermissionsUtils.checkPermissions(this, 266, PermissionsUtils.BASIC_PERMISSIONS);
        //StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.bg_yellow));
        if (getIntent().getIntExtra("pushType", 0) == 1) {
            if (getIntent().getStringExtra("pushMsg") != null) {
                String pushMessag = getIntent().getStringExtra("pushMsg");
                Log.e("TAG", "onNewIntent:=====?>>>> " + pushMessag);
                PushEntity pushEntity = JSON.parseObject(pushMessag, PushEntity.class);
                initGetPushData(pushEntity);
            }
        }
        Log.e("TAG", "onCreate: 获取手机厂商信息=======>>>>>>>是否小米=" + OSUtils.isMiuiRom() +
                "是否华为=" + OSUtils.isHuaweiRom() + "是否OPPORom=" + OSUtils.isOPPORom() + "是否vivo=" +
                OSUtils.isVivoRom());
        if (Paper.book().read(PagerCons.PUSH_TYPE) == null) {
            Paper.book().write(PagerCons.PUSH_TYPE, "1");
        }
        if (Paper.book().read(PagerCons.PUSH_TYPE).equals("1")) {
            initPush();
        }

        Map<String, String> map = new HashMap<>();
        mPresenter.getHomeBottomTab(map, this);
        JJBApplication.mainActivity = this;
    }

    //注册推送
    private void initPush() {

        if (OSUtils.isMiuiRom()) {
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
//            MiPushClient.setAlias(this, (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())), "");
            Map<String, String> map = new HashMap<>();
            map.put("alias", loginEntity == null ? "" : loginEntity.getData() == null ? "" : String.valueOf(loginEntity.getData().getUid()));
            map.put("type", "xiaomi");
            mPresenter.postAdAlias(map, mActivity);
        } else if (OSUtils.isHuaweiRom()) {
            initHuaWei();
        }

        else {
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            //初始化推送service
//            PushManager.getInstance().initialize(getApplicationContext(), MyPushService.class);
//            //在个推SDK初始化后，注册 IntentService 类
//            Log.e("TAG", "onCreate() returned: useId============>>>>>>" + (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())));
//            PushManager.getInstance().registerPushIntentService(getApplicationContext(), CustomPushService.class);
//            PushManager.getInstance().bindAlias(getApplicationContext(), (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())), (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())));
           /* v1/user/set_push_alias
            alias=[别名或token]
            type=[推送平台{"xiaomi":"小米","getui":"个推","huawei":"华为"}]*/
            if (LoginEntity.getIsLogin()) {
              //  JPushInterface.setAlias(mActivity, 0, LoginEntity.getUid() + "");
            }

            Map<String, String> map = new HashMap<>();
            map.put("alias", loginEntity == null ? "" : loginEntity.getData() == null ? "" : String.valueOf(loginEntity.getData().getUid()));
            map.put("type", "jiguang");
            mPresenter.postAdAlias(map, mActivity);
        }
    }

    //    show_type:展示方式
//    {
//        "1":"通知栏",
//            "2":"首页栏下方跑马灯",
//            "3":"我的页面上方跑马灯",
//            "4":"任务页面跑马灯",
//            "5":"首页弹窗广告"
//    }
//    {
//             "1": "点击领豪车-首页",
//            "2": "180天赚房赚车计划-发财计划",
//            "3": "查看我的奖励 - 奖励明细页面",
//            "7": "开启宝箱 - 开宝箱页面",
//            "8": "如何极速上手 - 手把手教你赚掘金宝",
//            "10": "点击进入任务页面（自动签到）",
//            "11": "我的页面",
//            "12": "文章详情页",
//            "15": "暴富秘籍",
//            "17": "手把手教你如何邀请好友",
//            "18": "实物奖励表页面",
//            "19":"我的消息",
//            "20":"内置浏览器打开网页",
//            "21":"外部浏览器打开网页"
//             22 ： 阅读奖励
//             23：未读消息
//             24：邀请好友
//             25：免费看首页
//             26： 赚钱首页
//             27： 睡眠赚

    //    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getIntExtra("pushType", 0) == 1) {
            if (intent.getStringExtra("pushMsg") != null) {
                String pushMessag = intent.getStringExtra("pushMsg");
                Log.e("TAG", "onNewIntent:=====?>>>> " + pushMessag);
                PushEntity pushEntity = JSON.parseObject(pushMessag, PushEntity.class);
                initGetPushData(pushEntity);
            }
        }
    }

    //跳转逻辑
    private void initGetPushData(PushEntity pushEntity) {

        if (pushEntity == null) {
            return;
        }

        Log.i(TAG, "initGetPushData: " + pushEntity.toString());

        //执行动作
        switch (Integer.valueOf(pushEntity.getShow_type())) {
            //通知栏消息
            case 1:
                initPushAction(Integer.valueOf(pushEntity.getAction_type()), pushEntity, true);
                break;
        }
    }

    /*
     * @param type
     * @param pushEntity
     * @param isAutoJump 是否直接跳转
     */
    private void initPushAction(int type, PushEntity pushEntity, boolean isAutoJump) {
        LoginEntity loginEntity;
        Intent intent;
        switch (type) {
            case 1:
                mViewBinding.bottomBar.setCurrentItem(0);
                break;
            case 2:
                if (Is_Show_Movie) {

                    mViewBinding.bottomBar.setCurrentItem(2);
                } else {
                    mViewBinding.bottomBar.setCurrentItem(1);
                }
                break;
            case 3:
                //开宝箱
            case 7:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }
//                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.MY_WALLET);
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
                break;
            //                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.MY_WALLET);
            //手把手教你赚取掘金宝
            case 8:
            case 17: // "17": "手把手教你如何邀请好友",
                //                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.MY_MAKE_HELP);
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_EARN_MONEY);

                break;
            //我的页面
            case 11:
                if (Is_Show_Movie) {
                    mViewBinding.bottomBar.setCurrentItem(4);
                } else {
                    mViewBinding.bottomBar.setCurrentItem(3);
                }
                break;
            //任务页面
            case 10:
                if (Is_Show_Movie) {
                    mViewBinding.bottomBar.setCurrentItem(3);
                } else {
                    mViewBinding.bottomBar.setCurrentItem(2);
                }
                break;
            //文章详情
            case 12:
                intent = new Intent(mActivity, HomeDetailActivity.class);
                intent.putExtra("id", pushEntity.getAction_url());
                mActivity.startActivity(intent);
                //             BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + pushEntity.getAction_url());
                break;
            // "20":"内置浏览器打开网页",
            case 20:
                WebActivity.intentMe(mActivity, StringUtils.isEmail(pushEntity.getShow_title()) ? "" : pushEntity.getShow_title(), pushEntity.getAction_url());
                break;
            case 15: //  "15": "暴富秘籍",
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_BURST_RICH);
                break;
            case 18: //"18": "实物奖励表页面",
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_REWARD_LIST);
                break;
            case 19: //"19":"我的消息",
//                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE + ("1".equals(pushEntity.getMsg_type()) ? "?msg_type=1" : ""));
                break;
            //  "21": "外部浏览器打开网页"
            case 21:
                Intent intents = new Intent();
                intents.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(pushEntity.getAction_url());
                intents.setData(content_url);
                startActivity(intents);
                break;

            //阅读奖励
            case 22:
                if (!pushEntity.getDesc().equals("0")) {
                    Log.e("TAG", "initPushAction: ========》》》》》》》》阅读奖励");
                    TimeRewardPopup timeRewardPopup = new TimeRewardPopup(mActivity);
                    timeRewardPopup.setView(pushEntity.getDesc());
                    timeRewardPopup.showPopupWindow();
                }
                break;
            //未读消息
            case 23:
                if (!isAutoJump) {
                    mUnreadAlerter = Alerter.create(JunjinBaoMainActivity.this);
                    mBottomBarMine.showUnreadDot(true);
                    mUnreadAlerter
                            .setTitle(pushEntity.getShow_title())
                            .setText(pushEntity.getDesc())
                            .setOnClickListener(new NoDoubleListener() {
                                @Override
                                protected void onNoDoubleClick(View v) {

                                    Alerter.hide();
                                    mBottomBarMine.showUnreadDot(false);
//                                    BridgeWebViewActivity.intentMe(MainActivity.this, RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                                    BridgeWebViewActivity.intentMe(JunjinBaoMainActivity.this, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE + ("1".equals(pushEntity.getMsg_type()) ? "?msg_type=1" : ""));
                                    Log.v("msg_type", pushEntity.getMsg_type());
                                }
                            }).setDuration(5000).show();
                    mUnreadAlerter.enableSwipeToDismiss();
                } else {
                    mBottomBarMine.showUnreadDot(false);
//                    BridgeWebViewActivity.intentMe(MainActivity.this, RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE + ("1".equals(pushEntity.getMsg_type()) ? "?msg_type=1" : ""));
                }
                Log.e("TAG", "initPushAction: ========》》》》》》》》未读消息");

                break;
            case 24: //邀请好友
                InviteFriendActivity.intentMe(mActivity);
                break;
            case 25: //免费看首页
                mViewBinding.bottomBar.setCurrentItem(1);
                break;
            case 26: //赚钱首页
                mViewBinding.bottomBar.setCurrentItem(3);
                break;
            case 27:
                loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(mActivity, SleepMoneyActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void initHuaWei() {
//        HMSAgent.connect(this, new ConnectHandler() {
//            @Override
//            public void onConnect(int rst) {
//                Log.e("TAG", "onResult: 111华为需要的=======》》》》" + rst);
//
//            }
//        });
//        HMSAgent.Push.getToken(new GetTokenHandler() {
//            @Override
//            public void onResult(int rst) {
//                Log.e("TAG", "onResult: 华为需要的=======》》》》" + rst);
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_maini;
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
    public void initView() {

        mPresenter.getADConfig(new HashMap<>(),mActivity);

        auditMap = Paper.book().read(PagerCons.KEY_AUDIT_MAP);
        if (auditMap == null) {
            auditMap = new HashMap();
        }

        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("from", "native");
        mPresenter.getShareConfig(objectObjectHashMap, mActivity);
        mPresenter.getBaseConfig(new HashMap<>(), mActivity);

        mPresenter.getExitCardInfo(new HashMap<String, String>(), mActivity);
        mBottomBarMovie = new BottomBarTab(mActivity, R.mipmap.ic_movie_vip_unselect, getString(R.string.movie));
        mBottomBarTask = new BottomBarTab(mActivity, R.mipmap.ic_nav_task_normal, getString(R.string.task));
        mBottomBarMine = new BottomBarTab(mActivity, R.mipmap.ic_nav_my_normal, getString(R.string.mine));
        mBottomBarHome = new BottomBarTab(mActivity, R.mipmap.ic_nav_home_normal, getString(R.string.home));
        mBottomBarMoney = new BottomBarTab(mActivity, R.mipmap.ic_car_and_money, null);

        setBottomBar(Is_Show_Movie);

        //广告推送弹窗规则：后台控制弹出次数  领取了大礼包才弹
        Long lastTimeShowAdDialog = Paper.book().read(PagerCons.KEY_HOME_DIALOG_TIME, 0l);
        Long intervalTime = Paper.book().read(PagerCons.KEY_HOME_DIALOG_INTERVAL_TIME, 0l);
        if (lastTimeShowAdDialog == 0 || System.currentTimeMillis() - lastTimeShowAdDialog > intervalTime) {
            if (LoginEntity.getIsGetGiftPacks()) {
//                mPresenter.getAdDataDialog(new HashMap<String, String>(), mActivity);
                //推啊广告
                mPresenter.getWelFareData(mActivity);
            }
        }

        if (!NetUtil.isNetworkAvailable(mActivity)) {
            setBottomBar(Default_Show_Movie_Status);
        }

        PreloadVideoUtils.getInstance().clearCache(mActivity.getApplicationContext());

//        mPresenter.getShareFullPics(new HashMap<>(), mActivity);

        // 穿山甲插屏广告
//        loadInteractionAd(TTAdManagerHolder.POS_ID_MOVIEPAUSE);

        Integer read = Paper.book().read(PagerCons.KEY_IS_FIRST_OPEN_APP_JUMP_ZCZF, 0);
        if (read == 0 && Is_Show_Movie) {
            mViewBinding.bottomBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.bottomBar.setCurrentItem(2);
                    Paper.book().write(PagerCons.KEY_IS_FIRST_OPEN_APP_JUMP_ZCZF, 1);
                }
            }, 1000);
        }
    }

    //    private ShouyeGuideDialog mShouyeGuideDialog;
    private ShouyeMoviePopupWindow shouyeMoviePopupWindow;
    private ShouyeZhuanchezhuanfangPopupWindow shouyeZhuanchezhuanfangPopupWindow;
    private ShouyeRenwuPopupWindow shouyeRenwuPopupWindow;
    private TaskPopupWindow mPopupWindow;

    void showTaskPopup(String msg) {
        //非首页只提示点，不显示popup
//        if (mCurrPosition != HOME) {
//            mBottomBarTask.showUnreadDot(true);
//            return;
//        }
        if (!mViewBinding.bottomBar.isVisible()) {
            return;
        }
        if (mActivity == null || mActivity.isFinishing())
            return;
        if (mPopupWindow == null || !mPopupWindow.isShowing()) {
            mPopupWindow = new TaskPopupWindow(mActivity, msg);

            int[] location = new int[2];
            int position = 2;
            int delta = position == 1 ? 35 : 25;

            View anchor = mBottomBarTask;
            // 解决 bugly 上报错误 BadTokenException：该异常表示不能添加窗口，通常是所要依附的view已经不存在导致的。
            if (anchor == null || anchor.getWindowToken() == null)
                return;
            anchor.getLocationOnScreen(location);
            mPopupWindow.setTouchable(true); // 设置popupwindow可点击
            mPopupWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
//            mPopupWindow.showAsDropDown(mBottomBarTask, -1 * Utils.getScreenWidth(mActivity) / 5 - Utils.dip2px(mActivity , 16),  -1 * Utils.dip2px(mActivity , 110));
            mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0] - anchor.getWidth() / 2 - Utils.dip2px(this, 16),
                    (location[1] - Utils.dip2px(this, delta)) == 0 ? Utils.dip2px(this, 63) : location[1] - Utils.dip2px(this, delta));
            mBottomBarTask.showUnreadDot(true);
        }

    }

    @Subscribe
    public void closeTaskAlert(Message message) {
        if (message.what == BusConstant.TASK_CLOSE_ALERT) {
            mBottomBarTask.showUnreadDot(false);
        }
    }

    /**
     * 退出文章详情后 将时间传给后台做记录
     */
    @org.greenrobot.eventbus.Subscribe()
    public void leaveReadActiclePage(ReadArticleLeaveEvent event) {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", event.getId());
        hashMap.put("type", "arc");
        hashMap.put("starttime", event.getStartTime() + "");
        mPresenter.leavePageCommit(hashMap, mActivity);
    }


    void setBottomBar(final boolean isShowMovie) {
        mViewBinding.bottomBar.clearTab();
        //TODO  张野：因为每次setbottombar时会执行 addItem方法，而addItem又会为tab按钮设置监听，然后连续执行2次setBottomBar会造成双击刷新事件，所以根据双击事件设定，每次setBottomBar则将双击时间重置
        lastClickTime = Calendar.getInstance().getTimeInMillis();
        //影视动态加载   .addItem(mBottomBarMovie)
        mViewBinding.bottomBar.addItem(mBottomBarHome);
        int bottomControl = auditMap.get(Constant.KEY_BOTTOM_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_BOTTOM_CONTROL);
        if (bottomControl == 0) {
            mViewBinding.bottomBar.addItem(mBottomBarMovie);
            mViewBinding.bottomBar.addItem(mBottomBarMoney);
            mViewBinding.bottomBar.addItem(mBottomBarTask);
        }
        mViewBinding.bottomBar.addItem(mBottomBarMine);
        setDelayEnableTouch();
//        mBottomBarMine.setUnreadCount(3);提示点
//        mBottomBarTask.showUnreadDot(true);
        mViewBinding.bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, int prePosition) {
                Log.e("TAG", "onTabSelected: tab下标========>>>>>>>" + position);
                if (position == 0) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    //未登录
                    if (loginEntity == null && Constant.IS_SHOW == 1) {
                        HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.llMain);
                        mHomeGiftDialog.show();
                        Constant.IS_SHOW = 0;
                    }
                }
                EventBus.getDefault().post(new TabChangeEvent(position));
//                //未登录点击赚车赚房需要跳到登录
//                if (!LoginEntity.getIsLogin() && position == 2) {
//                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
//                    startActivity(intent);
//                    mViewBinding.bottomBar.setCurrentItem(prePosition);
//                    return;
//                }

                mCurrPosition = position;
                mPrePosition = prePosition;

                showTagFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {
//                L.d(TAG, "onTabUnselected: "+position);
//                if (position == MINE && mBottomBarMine.isShowUnreadDot()) requestUnreadHintEvent(null);
            }

            @Override
            public void onTabReselected(int position) {

                if (position == HOME) {
                    showTagFragment(position);

                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        Log.e("zy", "onTabReselected刷新");
                        lastClickTime = currentTime;
//                        Homefragment homeFragment = (Homefragment) mFragmentList.get(HOME);

                        if (homefragment == null) {
                            Log.e("zy", "fragment == null");
                            return;
                        }

                        if (homefragment.currChannel == null) return;
                        EventBus.getDefault().post(new TabSelectedEvent(position, homefragment.currChannel.getName()));
                        //展示loading
                        mViewBinding.bottomBar.getItem(HOME).showLoadingByPos(R.drawable.refresh, HOME);
                    } else {
//                        Log.w(TAG,"点击过快");
                    }
                } else if (position == TASK) {
//                    if (UserModel.isLogin()) mBottomBarTask.showUnreadDot(false);
                }
            }
        });

        if (toTask) {
            toTask = false;

            showTagFragment(isShowMovie ? 3 : 2);
        } else {
            mViewBinding.bottomBar.setCurrentItem(0);

        }
    }

    //延时可点击
    void setDelayEnableTouch() {
        mViewBinding.bottomBar.setIntercept(true);
        mViewBinding.bottomBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewBinding.bottomBar.setIntercept(false);
            }
        }, 2000);
    }

    @org.greenrobot.eventbus.Subscribe()
    public void reloginEvent(LoginEvent event) { //登录后
//        mPresenter.getUnReadMessage(new HashMap<>(), mActivity);
        initYuYueTui();

        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("from", "native");
        mPresenter.getShareConfig(objectObjectHashMap, mActivity);
        if (OSUtils.isMiuiRom()) {
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
//            MiPushClient.setAlias(this, (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())), null);
            Map<String, String> map = new HashMap<>();
            map.put("alias", loginEntity == null ? "" : loginEntity.getData() == null ? "" : String.valueOf(loginEntity.getData().getUid()));
            map.put("type", "xiaomi");
            mPresenter.postAdAlias(map, mActivity);
        } else if (OSUtils.isHuaweiRom()) {
            initHuaWei();
        } else {
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            //初始化推送service
//            PushManager.getInstance().initialize(getApplicationContext(), MyPushService.class);
//            //在个推SDK初始化后，注册 IntentService 类
//            PushManager.getInstance().bindAlias(getApplicationContext(), (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())), (loginEntity == null ? "" : loginEntity.getData() == null ? "" :
//                    String.valueOf(loginEntity.getData().getUid())));

         //   JPushInterface.setAlias(mActivity, 0, LoginEntity.getUid() + "");

            Map<String, String> map = new HashMap<>();
            map.put("alias", loginEntity == null ? "" : loginEntity.getData() == null ? "" : String.valueOf(loginEntity.getData().getUid()));
            map.put("type", "jiguang");
            mPresenter.postAdAlias(map, mActivity);
        }

        //领鸡蛋入口
        Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,false);
        Paper.book().write(PagerCons.FINISH_TASK1_RECEIVE_EGGS,false);
        Paper.book().write(PagerCons.FINISH_TASK2_RECEIVE_EGGS,false);
        mPresenter.getEggsWelfare(mActivity);
    }


    @Override
    public void initData() {
        mPresenter.IsShowWchatorQQ(new HashMap<>(), this);
        mPresenter.movieRadarCrawListValue(mActivity);
    }

    //弹出退出对话框
    void showExitDialog() {
//        QuickPopupBuilder.with(getContext()).contentView(R.layout.dialog_get_gift_success).show();
//        if (mBackDialog == null)
        mBackDialog = new BackExitDialog(mActivity, 0, backCardEntity);
        mBackDialog.setCanceledOnTouchOutside(false);
        mBackDialog.show();
        mBackDialog.setClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (view.getId() == R.id.btn_back) {    //去领取
                    //切换到任务页
                    mBottomBarTask.performClick();
                } else if (view.getId() == R.id.btn_exit) { //退出
//                    mActivity.finish();
                    ActivityCollector.removeAllActivity();
                }
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mBackDialog != null) {
            mBackDialog.dismiss();
        }

        //横竖屏切换重新创建Dialog
        if (pushAdDialogs != null && pushAdDialogs.isShowing()) {
            showPushDialog(dialogPushEntity);
        }
    }

    private Fragment currentFragment = new Fragment();

    /**
     * fragment切换
     *
     * @param targetFragment
     * @return
     */

    private boolean addHome = false;
    private boolean addMovie = false;
    private boolean addCar = false;
    private boolean addTask = false;
    private boolean addMe = false;

    public final static String[] FRAGMENT_TAG = {"home", "movie", "money", "task", "me"};
    //fragment管理器
    private FragmentManager mFm;

    //新闻页
    private Homefragment homefragment;
    //影视页
    private VipFragment vipFragment;
    //赚房赚车页面
    private WebFragment WebFragment2;
    //任务页面
    private TaskFragment taskWebFragment;
    //个人页
    private MyFragment myFragment;

    //获取tag对应的fragment
    private void showTagFragment(int position) {
        if (position >= FRAGMENT_TAG.length) {
            position = FRAGMENT_TAG.length - 1;
        }
        try {
            if (Jzvd.CURRENT_JZVD != null) {
                Jzvd.goOnPlayOnPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mFm == null) {
            mFm = getSupportFragmentManager();
        }
        FragmentTransaction ft = mFm.beginTransaction();
        hideAllFragment(ft);
        int bottomControl = auditMap.get(Constant.KEY_BOTTOM_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_BOTTOM_CONTROL);
        if (bottomControl == 1) {
            if (position != 0) position = 4;
        }

        currentFragment = mFm.findFragmentByTag(FRAGMENT_TAG[position]);
//        Log.e("zy" , "showTagFragment position = " + position + " , currentFragment = " + currentFragment);
        if (currentFragment == null) {
            if (Is_Show_Movie) {
                switch (position) {
                    case 0:
                        if (homefragment != null) {
                            return;
                        }
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("type", "首页");
                        homefragment = Homefragment.newInstance(bundle1);
                        currentFragment = homefragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 1:
                        if (vipFragment != null) {
                            return;
                        }

                        if (Paper.book().read(PagerCons.SHOUYE_MOVIE_GUIDE) == null) {
                            Paper.book().write(PagerCons.SHOUYE_MOVIE_GUIDE, "1");
                            if (shouyeMoviePopupWindow == null) {
                                shouyeMoviePopupWindow = new ShouyeMoviePopupWindow(mActivity);
                            }
                            shouyeMoviePopupWindow.show(mActivity);
                        }


                        vipFragment = new VipFragment();
                        currentFragment = vipFragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 2:

                        if (WebFragment2 != null) {
                            return;
                        }

                        if (Paper.book().read(PagerCons.SHOUYE_ZHUANFANG_GUIDE) == null) {
                            Paper.book().write(PagerCons.SHOUYE_ZHUANFANG_GUIDE, "1");


                            if (shouyeZhuanchezhuanfangPopupWindow == null) {
                                shouyeZhuanchezhuanfangPopupWindow = new ShouyeZhuanchezhuanfangPopupWindow(mActivity);
                            }
                            shouyeZhuanchezhuanfangPopupWindow.show(mActivity);
                        }

                        WebFragment2 = new WebFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("type", "赚车赚房");
//                        bundle3.putString("url", RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                        bundle3.putString("url", make_money_url + query);
                        WebFragment2.setArguments(bundle3);
                        currentFragment = WebFragment2;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 3:
                        if (taskWebFragment != null) {
                            return;
                        }

                        if (Paper.book().read(PagerCons.SHOUYE_RENWU_GUIDE) == null) {
                            Paper.book().write(PagerCons.SHOUYE_RENWU_GUIDE, "1");
                            if (shouyeRenwuPopupWindow == null) {
                                shouyeRenwuPopupWindow = new ShouyeRenwuPopupWindow(mActivity);
                            }
                            shouyeRenwuPopupWindow.show(mActivity);
                        }

                        taskWebFragment = new TaskFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("type", "任务");
//                        bundle4.putString("url", RetrofitManager.WEB_URL_COMMON + "/task" + query);
                        bundle4.putString("url", RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
                        taskWebFragment.setArguments(bundle4);
                        currentFragment = taskWebFragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 4:
                        if (myFragment != null) {
                            return;
                        }
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("type", "我的");
                        myFragment = MyFragment.newInstance(bundle5);
                        currentFragment = myFragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                }

                long movieStartTime = 0;
                if (Paper.book().read(PagerCons.INTO_MOVIE_TIME) != null) {
                    String timeStr = Paper.book().read(PagerCons.INTO_MOVIE_TIME);
                    movieStartTime = Long.parseLong(timeStr);
                }
                if (position != 1 && movieStartTime != 0) {
                    //埋点（统计影视页面用户在线时间）
                    int movieTime = (int) ((System.currentTimeMillis() - movieStartTime) / 1000);
                    Map<String, Object> time = new HashMap<>();
                    time.put("movieTimeInSeconds", movieTime);
                 //   MobclickAgent.onEventObject(MyApplication.getContext(), EventID.VIP_MOVIE_ONLINE_TIME, time);
                    Paper.book().write(PagerCons.INTO_MOVIE_TIME, "0");
                }
            } else {
                switch (position) {
                    case 0:
                        if (homefragment != null) {
                            return;
                        }
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("type", "首页");
                        homefragment = Homefragment.newInstance(bundle1);
                        currentFragment = homefragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 1:
                        if (WebFragment2 != null) {
                            return;
                        }

                        WebFragment2 = new WebFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("type", "赚车赚房");
//                        bundle3.putString("url", RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                        bundle3.putString("url", make_money_url + query);
                        WebFragment2.setArguments(bundle3);
                        currentFragment = WebFragment2;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 2:
                        if (taskWebFragment != null) {
                            return;
                        }

                        taskWebFragment = new TaskFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("type", "任务");
                        bundle4.putString("url", RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
//                        bundle4.putString("url", RetrofitManager.WEB_URL_COMMON + "/task" + query);
                        taskWebFragment.setArguments(bundle4);
                        currentFragment = taskWebFragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                    case 3:
                        if (myFragment != null) {
                            return;
                        }
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("type", "我的");
                        myFragment = MyFragment.newInstance(bundle5);
                        currentFragment = myFragment;
                        ft.add(R.id.ll_main, currentFragment, FRAGMENT_TAG[position]);
                        break;
                }
            }


        }
        ft.show(currentFragment).commitAllowingStateLoss();

        if (isNewTaskClick) {
            //0、1、2、3、4 新手任务跳转
            isNewTaskClick = false;
            EventBus.getDefault().postSticky(new NewTaskEvent(position));
        }

        if(isReceiveEggs){
            //100、101、102、103、104 领鸡蛋任务跳转
            isReceiveEggs = false;
            EventBus.getDefault().postSticky(new NewTaskEvent(position+100));
        }
    }


    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction ft) {
//        Fragment fragment;

//        mFm.getFragments();

//        for (int i = 0; i < FRAGMENT_TAG.length; i++) {
//            fragment = mFm.findFragmentByTag(FRAGMENT_TAG[i]);
//            if (fragment != null) {
//                ft.hide(fragment);
//            }
//        }

        //貌似是任务fragment
//        Homefragment homefragment = null;
//        VipFragment vipFragment = null;
//        WebFragment webFragment = null;
//        TaskWebFragment taskWebFragment = null;
//        MyFragment myFragment = null;

//        Log.e("zy" , "fragment.size = " + mFm.getFragments().size());
        for (Fragment fragment : mFm.getFragments()) {
//            if(fragment instanceof Homefragment){
//                if(homefragment == null){
//                    homefragment = (Homefragment) fragment;
//                    ft.hide(fragment);
//                }else{
//                    ft.remove(homefragment);
//                    homefragment = (Homefragment) fragment;
//                }
//            }
//
//            if(fragment instanceof VipFragment){
//                if(vipFragment == null){
//                    vipFragment = (VipFragment) fragment;
//                    ft.hide(fragment);
//                }else{
//                    ft.remove(vipFragment);
//                    vipFragment = (VipFragment) fragment;
//                }
//            }
//
//            if(fragment instanceof WebFragment){
//                if(webFragment == null){
//                    webFragment = (WebFragment) fragment;
//                    ft.hide(fragment);
//                }else{
//                    ft.remove(webFragment);
//                    webFragment = (WebFragment) fragment;
//                }
//            }
//
//            if(fragment instanceof TaskWebFragment){
//                if(taskWebFragment == null){
//                    taskWebFragment = (TaskWebFragment) fragment;
//                    ft.hide(fragment);
//                }else{
//                    ft.remove(taskWebFragment);
//                    taskWebFragment = (TaskWebFragment) fragment;
//                }
//            }
//
//            if(fragment instanceof MyFragment){
//                if(myFragment == null){
//                    myFragment = (MyFragment) fragment;
//                    ft.hide(fragment);
//                }else{
//                    ft.remove(myFragment);
//                    myFragment = (MyFragment) fragment;
//                }
//            }
            if (fragment != null) {
                ft.hide(fragment);
            }
        }

    }


    @Override
    public void getExitCardInfoSuccess(Serializable serializable) {
        backCardEntity = (BackCardEntity) serializable;

    }

    @Override
    public void getHomeBottomTabSuccess(Serializable serializable) {
        HomeBottomTabEntity entity = (HomeBottomTabEntity) serializable;
        Load_ShowBarAPI_Finish = true;
        for (int i = 0; i < entity.getData().size(); i++) {
            if (entity.getData().get(i).getTxt().equals("影视")) {
                Is_Show_Movie = entity.getData().get(i).getShow() == 1;
                setBottomBar(Is_Show_Movie);
            } else if (entity.getData().get(i).getTxt().equals("赚车赚房")) {
                if (!TextUtils.isEmpty(entity.getData().get(i).getExt_url())) {
                    make_money_url = entity.getData().get(i).getExt_url();
                } else {
                    make_money_url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TECH_RICH_PLAY;
                }

            } else if (entity.getData().get(i).getTxt().equals("首页")) {
            }
        }
    }

    //获取APP自动更新以及分享域名
    @Override
    public void getShareAndAppUpHeaderSuccess(Serializable serializable) {
        ShareDomainEntity shareDomainEntity = (ShareDomainEntity) serializable;
        if (shareDomainEntity.getCode() == 0) {
            UpAppUtil.checkVersion(mActivity, shareDomainEntity.getData().getDomain());
        }
    }

    @Override
    public void showError(String errResponse) {

    }

    @Override
    public void getUnReadMessageSuccess(Serializable serializable) {
        UnReadEntity unReadEntity = (UnReadEntity) serializable;
        showUnreadAlert(unReadEntity.getData());

    }

    @Override
    public void getShareConfigSuccess(Serializable serializable) {
        ShareConfigEntity shareConfigEntity = (ShareConfigEntity) serializable;
        //获取分享配置保存到
        if (shareConfigEntity != null && shareConfigEntity.getData() != null) {
            Paper.book().write(PagerCons.SHARE_CONFIG, shareConfigEntity.getData());
        }

    }

    @Override
    public void getCoinOf60MinSuccess(Serializable serializable) {
//        Read60Reword entity = (Read60Reword) serializable;
//        if (entity.getData().getCoin() != 0) {
//            TimeRewardPopup timeRewardPopup = new TimeRewardPopup(mActivity);
//            timeRewardPopup.setView(entity.getData().getCoin());
//            timeRewardPopup.showPopupWindow();
//        }
    }

    /**
     * 获取弹框广告数据成功
     *
     * @param serializable
     */
    @Override
    public void getAdDialogDataSuccess(Serializable serializable) {
        final HomeAdDialogEntity entity = (HomeAdDialogEntity) serializable;
        Paper.book().write(PagerCons.KEY_HOME_DIALOG_TIME, System.currentTimeMillis());

        Paper.book().write(PagerCons.KEY_HOME_DIALOG_INTERVAL_TIME, (long) entity.getData().getAd_info().get(0).getAd_show_apart_time() * 60 * 1000);


        //广告推送弹窗
        final PushAdDialog pushAdDialog = new PushAdDialog(mActivity, 1, "", "", entity.getData().getAd_info().get(0).getImages().get(0));
        pushAdDialog.setClickListener(new PushAdDialog.PushAdClickListener() {
            @Override
            public void OkClick() {
                pushAdDialog.dismiss();
                WebActivity.intentMe(mActivity, entity.getData().getAd_info().get(0).getTitle(), entity.getData().getAd_info().get(0).getLink());
            }

            @Override
            public void CancleClick() {

            }
        });
        pushAdDialog.show();

    }

    @Override
    public void leavePageCommitSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;
        Log.i("zzz", "leavePageCommitSuccess: " + baseEntity.getMsg());

    }

    @Override
    public void watchAdVideoInTaskSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;
        if (baseEntity.getCode() == 0) {
//            TimeRewardPopup timeRewardPopup = new TimeRewardPopup(this);
//            timeRewardPopup.setView("20");
//            timeRewardPopup.showPopupWindow();


            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    20,
                    "观看视频奖励",
                    "",
                    true
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(entity);
            acticleRewardPop.showPopupWindow();

        } else {
            ToastUtils.getInstance().show(mActivity, baseEntity.getMsg());
        }

    }

    /**
     * 分享图片保存至本地
     *
     * @param serializable
     */
    @Override
    public void getShareFullPicsSuccess(Serializable serializable) {
        SharePicsEntity sharePicsEntity = (SharePicsEntity) serializable;
        List<String> imgs = sharePicsEntity.getData().getImgs();


        List<File> cechePicList = Paper.book().read(PagerCons.KEY_SHARE_PICS);
        int ret = 0;
        if (cechePicList != null) {
            for (int i = 0; i < cechePicList.size(); i++) {
                File file = cechePicList.get(i);
                if (!file.exists()) {
                    ret++;
                }
            }
        }
        //缓存有变化 缓存为空 缓存数据为空 需要更新  =》 更新缓存
        if (ret != 0 || cechePicList == null || cechePicList.size() == 0 || sharePicsEntity.getData().getIs_update() == 1) {
            if (cechePicList != null) {
                for (int i = 0; i < cechePicList.size(); i++) {
                    File file = cechePicList.get(i);
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                }
            }
            updateCacheShareImgs(imgs);
        }

    }

    /**
     * 基础配置
     *
     * @param serializable
     */
    @Override
    public void getBaseConfigSuccess(Serializable serializable) {
        BaseConfigEntity entity = (BaseConfigEntity) serializable;
        if (entity != null && entity.getData() != null) {
            //缓存当天观看影视第n 次需要分享
            Paper.book().write(PagerCons.KEY_MOVIE_PLAY_COUNR_NEED_SHARE, entity.getData().getShare_movie_count());
            //缓存app防护开关配置
            Paper.book().write(PagerCons.KEY_IS_OPEN_APP_PROTECT, entity.getData().getIs_open_app_protect());
            //缓存信息流广告商
            Paper.book().write(PagerCons.KEY_AD_TYPE, entity.getData().getAdvertiser_type());

            //图集浏览奖励次数
            Paper.book().write(PagerCons.ACTICLE_REWARD_SCANPICTURE_REWARD_NUMBER, entity.getData().getPicture().getReward_num());

            Paper.book().write(PagerCons.KEY_JJB_CONFIG, entity.getData());

        }
    }

    /**
     * 广告配置
     * @param serializable
     */
    @Override
    public void getADConfigSuccess(Serializable serializable) {
        ADConfigEntity entity = (ADConfigEntity) serializable;

//        ADConfigEntity entity = DataService.getAdConfig();

        if(entity.getCode()==0){
            Paper.book().write(PagerCons.KEY_AD_CONFIG,entity);

            Paper.book().write(PagerCons.AD_TYPE_CHCHE, entity.getData().getAd_type1().getPlatform());
        }
    }

    //获取登录界面的显示方式
    @Override
    public void showIsShowWchatorQQ(IsShowQQEntity loginEntity) {
        try {
            Paper.book().write(PagerCons.KEY_GUIDELOGIN_UI, loginEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostAliasError(Serializable serializable) {

    }

    //奖励兑换成功
    @Override
    public void onExchangeSuccess(ExChangeMiniProgramEntity entity) {

        if (entity.getCode() == 0) {
            new MiniProgramRewardDialog(mActivity, 0, entity).show();
        } else {

        }

    }

    @Override
    public void onWalkExchangeSuccess(ExChangeWalkMiniProgramEntity entity) {
        if (entity.getCode() == 0) {
            new WalkMiniProgramRewardDialog(mActivity, 0, entity).show();
        } else {

        }
    }

    @Override
    public void onWelFareDataSuccess(WelFareRewardEntity entity) {

        if (entity.getData() != null) {
            Paper.book().write(PagerCons.KEY_HOME_DIALOG_TIME, System.currentTimeMillis());

            Paper.book().write(PagerCons.KEY_HOME_DIALOG_INTERVAL_TIME, (long) entity.getData().getAd_show_apart_time() * 60 * 1000);


            if (entity.getCode() == 0 && entity.getData().getReward_value() != 0) {
                WelComeAdPop pop = new WelComeAdPop(mActivity);
                pop.setView(entity);
                pop.showPopupWindow();
            }
        }
    }

    @Override
    public void onEggsWelfareSuccess(WelfareEntity entity) {
        if(entity.getCode()==0 && entity.getData()!=null){
            //app任务完成状态：0 全部完成; 1 将要完成任务1; 2 将要完成任务2
            if(entity.getData().getFinish_app_task_status()==0){
                Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,false);
            }else if(entity.getData().getFinish_app_task_status()==1){
                Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,true);
            }else{
                Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,true);
                Paper.book().write(PagerCons.FINISH_TASK1_RECEIVE_EGGS,true);
            }
            if(entity.getData().getReward_num()==0)
                return;
            // 领鸡蛋新人大礼包弹窗
            NewGiftBagForEggsDialog eggsDialog = new NewGiftBagForEggsDialog(mActivity,entity.getData());
            eggsDialog.setClickListener((position, object) -> {
                if(position==1){
                    //领取成功
                    NewGiftBagSuccessDialog dialog = new NewGiftBagSuccessDialog(mActivity,object);
                    dialog.setClickListener((position1, object1) -> {
                        if(position1 ==1){
                            //再领4枚蛋
                            ReceiveFourEggsDialog fourEggsDialog = new ReceiveFourEggsDialog(mActivity);
                            fourEggsDialog.setClickListener((position2, object2) -> {
                                //点击马上开始进入赚车赚房页面
                                if(currentFragment!=null && currentFragment instanceof WebFragment){
                                    EventBus.getDefault().postSticky(new NewTaskEvent(mCurrPosition+100));
                                }else{
                                    isReceiveEggs = true;
                                    mViewBinding.bottomBar.setCurrentItem(Is_Show_Movie?2:1);
                                }
                            });
                            fourEggsDialog.show();
                        }
                    });
                    dialog.show();
                }
            });
            eggsDialog.show();
        }else{
            Paper.book().write(PagerCons.HAS_TASK_RECEIVE_EGGS,false);
        }
    }


    public void updateCacheShareImgs(List<String> imgs) {
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            int finalI = i;
            Glide.with(mActivity).asBitmap().load(imgs.get(i)).into(
                    new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // 保存图片至相册

//                            File file = ImageUtil.saveBmp2Gallery(mActivity, resource, "sharePic" + finalI);
                            File file = saveImageToGallery(resource);
                            fileList.add(file);
                            Paper.book().write(PagerCons.KEY_SHARE_PICS, fileList);
                        }
                    }
            );
        }
    }

    /**
     * 保存至本地但不更新相册
     *
     * @param bmp
     * @return
     */
    public File saveImageToGallery(Bitmap bmp) {
        //生成路径
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dirName = "erweima16";
        File appDir = new File(root, dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //文件名为时间
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".jpg";

        //获取文件
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    @Override
    public void getPostAdAliassuccess(Serializable serializable) {

    }

    /**
     * 显示未读消息alert
     */
    public void showUnreadAlert(List<UnReadEntity.DataBean> list) {
        if (mUnreadAlerter == null) mUnreadAlerter = Alerter.create(JunjinBaoMainActivity.this);

        UnReadEntity.DataBean item = list.get(0);
        mBottomBarMine.showUnreadDot(true);
        mUnreadAlerter
                .setTitle(StringUtils.filterHTMLTag(item.getTitle()))
                .setText(StringUtils.filterHTMLTag(item.getContent()))
                .setOnClickListener(new NoDoubleListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {

                        Alerter.hide();
                        mBottomBarMine.showUnreadDot(false);
//                        BridgeWebViewActivity.intentMe(MainActivity.this, RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                        BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE);
                    }
                }).setDuration(5000).show();
        mUnreadAlerter.enableSwipeToDismiss();

    }

    @Override
    public void onBackPressed() {

        try {
            if (backCardEntity != null) {

                if (Jzvd.CURRENT_JZVD != null) {
                    if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.backPress();
                    } else {
                        Jzvd.releaseAllVideos();
                    }
                } else {
                    if (mCurrPosition == 0) {
                        showExitDialog();
                    } else {
                        mViewBinding.bottomBar.setCurrentItem(0);
                    }
                }
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {

        }


    }

    @Override
    protected void onPause() {
        try {
            if (Jzvd.CURRENT_JZVD != null) {
                if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.goOnPlayOnPause();
                } else {
                    Jzvd.goOnPlayOnPause();
                }
            }
           // MobclickAgent.onPause(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();

        addHome = false;
        addMovie = false;
        addCar = false;
        addTask = false;
        addMe = false;
    }


    private int jumpPage = -1;
    private String query = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Subscribe
    public void switchFragment(Message message) {

        //onResume时跳转
        if (message.what == BusConstant.MAIN_SWITCH) {
            if (Is_Show_Movie) {
                jumpPage = message.arg1;
            } else {
                if (message.arg1 > 0) {
                    jumpPage = message.arg1 - 1;
                } else {
                    jumpPage = 0;
                }
            }

            //跳转时加参
            query = (String) message.obj;
//            switchFragment(mFragmentList.get(message.arg1)).commit();
        }
        //立即跳转
        else if (message.what == BusConstant.MAIN_SWITCH2) {
            query = (String) message.obj;


            if (Is_Show_Movie) {
                mViewBinding.bottomBar.setCurrentItem(message.arg1);
                if (message.arg1 == 2) {
                    if (WebFragment2 == null) {
                        showTagFragment(message.arg1);
                        return;
                    }
//                    WebFragment2.loadUrl(RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                    WebFragment2.loadUrl(make_money_url + query);

                } else if (message.arg1 == 3) {
                    if (taskWebFragment == null) {
                        showTagFragment(message.arg1);
                        return;
                    }
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_COMMON + "/task" + query);
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
                }
                showTagFragment(message.arg1);
            } else {
                mViewBinding.bottomBar.setCurrentItem(message.arg1 - 1);
                int page;
                if (message.arg1 > 0) {
                    page = message.arg1 - 1;
                } else {
                    page = 0;
                }

                if (page == 1) {
                    if (WebFragment2 == null) {
                        showTagFragment(page);
                        return;
                    }
//                    WebFragment2.loadUrl(RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                    WebFragment2.loadUrl(make_money_url + query);
                } else if (page == 2) {
                    if (taskWebFragment == null) {
                        showTagFragment(page);
                        return;
                    }
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_COMMON + "/task" + query);
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
                }
                showTagFragment(page);
            }


//            query = "";
        }
    }

    @Override
    protected void onResume() {

//        setBottomBar(true);
        try {
            //初始化获取App更新或者分享域名
            if (isCheckUpdate) {
                Map<String, String> param = new HashMap<>();
                param.put("type", "temp");
                mPresenter.getShareAndAppUpHeader(param, this);
            }

            if (LoginEntity.getIsLogin()) {
                mPresenter.exchangeMiniProgramToJJB(mActivity);
                mPresenter.exchangeWalkMiniProgramToJJB(mActivity);
            }


            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            super.onResume();
           // MobclickAgent.onResume(this);

            if (jumpPage != -1) {
                mViewBinding.bottomBar.setCurrentItem(jumpPage);

                if (Is_Show_Movie) {
                    if (jumpPage == 2) {
                        if (WebFragment2 == null) {
                            return;
                        }
//                    WebFragment2.loadUrl(RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                        WebFragment2.loadUrl(make_money_url + query);
                    } else if (jumpPage == 3) {
                        if (taskWebFragment == null) {
                            return;
                        }
//                        taskWebFragment.loadUrl(RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_COMMON + "/task" + query);
                    }

                } else {
                    if (jumpPage == 1) {
                        if (WebFragment2 == null) {
                            return;
                        }
//                    WebFragment2.loadUrl(RetrofitManager.WEB_URL_COMMON + "/make_money" + query);
                        WebFragment2.loadUrl(make_money_url + query);
                    } else if (jumpPage == 2) {
                        if (taskWebFragment == null) {
                            return;
                        }
//                    taskWebFragment.loadUrl(RetrofitManager.WEB_URL_COMMON + "/task" + query);
//                        taskWebFragment.loadUrl(RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TASK + query);
                    }

                }

                jumpPage = -1;
            }

        } catch (Exception e) {

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JJBApplication.mainActivity = null;
        ActivityCollector.removeAllActivity();
        EventBus.getDefault().unregister(this);
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }

      //  AdManager.getInstance(this).onAppExit();

        try {
            SongPlayManager.getInstance().cancelPlay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.greenrobot.eventbus.Subscribe()
    public void onBottomTabPopShowEvent(ShowTabPopupWindowEvent event) {
        String str = "";

        if (LoginEntity.getIsNew()) {
            str = "连续签到7天得" + Utils.FormatGold(event.count) + "金币";
        } else {
            str = "今日奖励可领取";
        }
        final String finalStr = str;
        mViewBinding.bottomBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                showTaskPopup(finalStr);
            }
        }, 1000);

    }

    /**
     * 任务界面看广告赚金币回调
     *
     * @param event
     */
    @org.greenrobot.eventbus.Subscribe()
    public void watchAdGetReward(WatchAdGetRewardEvent event) {
        Log.i(PlayRewardVideoAdActicity.TAG, "在首页接口issuccess的值：" + event.isSuccess);
        if (event.isSuccess) {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    20,
                    "观看视频奖励",
                    "",
                    true
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(entity);
            acticleRewardPop.showPopupWindow();
        } else {
            mPresenter.watchAdVideoInTask(new HashMap<>(), mActivity);
        }


    }

    /**
     * 切换首页tab
     *
     * @param event
     */
    @org.greenrobot.eventbus.Subscribe()
    public void SwitchTab(SwitchTabEvent event) {
        HashMap<String, Integer> auditMap = Paper.book().read(PagerCons.KEY_AUDIT_MAP) == null ? new HashMap() : Paper.book().read(PagerCons.KEY_AUDIT_MAP);
        int bottomControl = auditMap.get(Constant.KEY_BOTTOM_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_BOTTOM_CONTROL);
        if (bottomControl == 1) { //绕审状态 不做切换
            return;
        }

        int index = 0;
        if (SwitchTabEvent.HOME.equals(event.tab)) {
            index = 0;
        } else if (SwitchTabEvent.MOVIE.equals(event.tab)) {
            index = 1;
        } else if (SwitchTabEvent.ZCZF.equals(event.tab)) {
            index = Is_Show_Movie?2:1;
        } else if (SwitchTabEvent.TASK.equals(event.tab)) {
            index = Is_Show_Movie?3:2;
        } else if (SwitchTabEvent.ME.equals(event.tab)) {
            index = Is_Show_Movie?4:3;;
        }
        isNewTaskClick = event.isNewTaskClick;
        isReceiveEggs = event.isReceiveEggs;
        mViewBinding.bottomBar.setCurrentItem(index);
    }

    //显示推送弹窗
    private void showPushDialog(PushEntity pushEntity) {
        if (pushEntity == null) {
            return;
        }
        //防止显示多个弹窗
        if (pushAdDialogs != null && pushAdDialogs.isShowing()) {
            pushAdDialogs.dismiss();
        }
        pushAdDialogs = new PushAdDialog(mActivity, 0, pushEntity.getShow_title(), pushEntity.getDesc(), pushEntity.getImgurl());
        pushAdDialogs.setClickListener(new PushAdDialog.PushAdClickListener() {
            @Override
            public void OkClick() {
                initPushAction(Integer.valueOf(pushEntity.getAction_type()), pushEntity, true);
                pushAdDialogs.dismiss();
            }

            @Override
            public void CancleClick() {

            }
        });
        pushAdDialogs.show();
    }

    //透传消息接受event
    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onPushDataMessage(PushDataEvent pushDataEvent) {

        if (pushDataEvent.getPushType() == 1) {
            String pushMessag = pushDataEvent.getPushMessge();
            Log.e("TAG", "onNewIntent:=====11111>>>> " + pushMessag);
            Log.e("zy", "推送：" + pushMessag);
            final PushEntity pushEntity = JSON.parseObject(pushMessag, PushEntity.class);
            dialogPushEntity = pushEntity;
            if (pushEntity != null) {
                if (Integer.valueOf(pushEntity.getShow_type()) == 5) {

                    //显示推送弹窗
                    showPushDialog(dialogPushEntity);
                } else if (Integer.valueOf(pushEntity.getShow_type()) == 3 || Integer.valueOf(pushEntity.getShow_type()) == 4) {
                    initPushAction(Integer.valueOf(pushEntity.getAction_type()), pushEntity, false);
                } else {
                    mUnreadAlerter = Alerter.create(JunjinBaoMainActivity.this);
                    mUnreadAlerter
                            .setTitle(pushEntity.getShow_title())
                            .setText(pushEntity.getDesc())
                            .setUrlIcon(pushEntity.getImgurl())
                            .setOnClickListener(new NoDoubleListener() {
                                @Override
                                protected void onNoDoubleClick(View v) {

                                    Alerter.hide();
                                    mBottomBarMine.showUnreadDot(false);
//                                    BridgeWebViewActivity.intentMe(MainActivity.this, RetrofitManager.WEB_URL_COMMON + Constant.MY_MESSGE);
                                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_MESSAGE + ("1".equals(pushEntity.getMsg_type()) ? "?msg_type=1" : ""));
                                }
                            }).setDuration(5000).show();
                    mUnreadAlerter.enableSwipeToDismiss();
                }

            }

        } else if (pushDataEvent.getPushType() == 2) {
            //华为单独处理
            String pushMessag = pushDataEvent.getPushMessge();
            Log.e("TAG", "onNewIntent:=====2222>>>> " + pushMessag);
            final PushEntity pushEntity = JSON.parseObject(pushMessag, PushEntity.class);
            if (pushEntity != null) {
                if (Integer.valueOf(pushEntity.getShow_type()) == 5) {
                    final PushAdDialog pushAdDialog = new PushAdDialog(mActivity, 0, pushEntity.getShow_title(), pushEntity.getDesc(), pushEntity.getImgurl());
                    pushAdDialog.setClickListener(new PushAdDialog.PushAdClickListener() {
                        @Override
                        public void OkClick() {
                            initPushAction(Integer.valueOf(pushEntity.getAction_type()), pushEntity, true);
                            pushAdDialog.dismiss();
                        }

                        @Override
                        public void CancleClick() {

                        }
                    });
                    pushAdDialog.show();
                } else {
                    initPushAction(Integer.valueOf(pushEntity.getAction_type()), pushEntity, false);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 10086){
//            //任务界面看广告赚金币
//            Log.i(TAG, "onActivityResult:看视频广告回调 " + requestCode);
//            mPresenter.watchAdVideoInTask(new HashMap<>(),mActivity);
//        }
        super.onActivityResult(requestCode, resultCode, data);
       // UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("Is_Show_Movie", Is_Show_Movie);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Is_Show_Movie = savedInstanceState.getBoolean("Is_Show_Movie");
        super.onRestoreInstanceState(savedInstanceState);

    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserOffLine(EventBusOffLineEntity event) {
        if (705 == event.getCode()) {
            Paper.book().delete(PagerCons.USER_DATA);
            EventBus.getDefault().post(new SettingLoginEvent());
//            GuideLoginActivity.start(this, true, event.getMsg());

            //705不管是否登录成功，都跳到主界面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            GuideLoginActivity.start(this, false, "");
        }
    }

    /**
     * 是否开启wifi代理
     *
     * @return
     */
    private boolean isWifiProxy() {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(this);
            proxyPort = android.net.Proxy.getPort(this);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }


    public void initYuYueTui() {
      //  AdManager.getInstance(this).init(this, Constant.YUYUETUI_APP_ID, LoginEntity.getUid() + "", Constant.YUYUETUI_APP_KEY, "");
    }

    //监听touch，所有带有广告的页面都需监听。建议在所有带有广告的Activity的dispatchTouchEvent中调用 搜狗广告
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //也可以采用自己的方式调用此方法，需保证Down事件和Up事件都经过此方法
        return super.dispatchTouchEvent(ev);
    }

}

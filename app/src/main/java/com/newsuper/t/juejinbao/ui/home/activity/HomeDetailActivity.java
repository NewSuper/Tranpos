package com.newsuper.t.juejinbao.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityHomeDetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.basepop.util.InputMethodUtils;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.adapter.DetailRecomentAdapter;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.dialog.ActicleDetailRewordDialog01;
import com.newsuper.t.juejinbao.ui.home.dialog.ArticleNewTaskRewordDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.GuideActicleRewardDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.GuideTextSettingDialog;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleDetailEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleShowBigPicEntity;
import com.newsuper.t.juejinbao.ui.home.entity.CommentCommitEntity;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ADRemovePopup;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeDetailPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.HomeDetailPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.my.entity.BaseDefferentEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.dialog.TextSettingDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.KeyboardChangeListener;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.OSUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.rewardAnim.RewardAnimManager;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import io.paperdb.Paper;


import static io.paperdb.Paper.book;

public class HomeDetailActivity extends BaseActivity<HomeDetailPresenterImpl, ActivityHomeDetailBinding> implements HomeDetailPresenter.HomeDetailView {

    private List<Object> mData = new ArrayList<>();
    private HomePagerAdapter homePagerAdapter;
    //作者id
    private int mid = 0;
    //是否关注
    private boolean is_Focus = false;
    //是否收藏
    private boolean is_Collect = false;
    //评论列表
    private DetailRecomentAdapter adapter;
    private List<Object> mList = new ArrayList<>();
    private int page = 1;
    private ShareDialog mShareDialog;
    private InputMethodManager manager;
    private View managerView;
    //评论列表是否刷新
    private boolean isRefresh = true;
    //评论列表是否有广告
    private boolean isHaveAd = false;
    //评论列表广告的位置
    private int haveAdPosition = 0;
    //评论列表广告是否统计 0未统计 1统计
    private int haveAdStatistical = 0;
    //评论列表广告id
    private int ad_id = 0;
    //评论列表点击统计 0未统计 1统计
    private int haveAdStatisticalClick = 0;

    //第一个广告位是否统计
    private int haveAdFirstStatistical = 0;
    //是否有第一个广告
    private boolean isHaveAdFirst = false;
    //第一个广告的id
    private int ad_id_first = 0;
    //第一个广告点击统计 0未统计 1统计
    private int haveAdFirstStatisticalClick = 0;


    //第二个广告位是否统计
    private int haveAdTwoStatistical = 0;
    //是否有第二个广告
    private boolean isHaveAdTwo = false;
    //第二个广告的id
    private int ad_id_two = 0;
    //第二个广告点击统计 0未统计 1统计
    private int haveAdTwoStatisticalClick = 0;
    //第二个广告的位置
    private int haveAdTwoPosition = 0;

    //第三个广告位是否统计
    private int haveAdThreeStatistical = 0;
    //是否有第三个广告
    private boolean isHaveAdThree = false;
    //第三个广告的id
    private int ad_id_three = 0;
    //第三个广告点击统计 0未统计 1统计
    private int haveAdThreeStatisticalClick = 0;
    //第三个广告的位置
    private int haveAdThreePosition = 0;
    //文章id
    private String articId = "";
    //跳转来源（用于区分新手任务跳转）
    private String from = "";
    //新手任务(阅读文章)
    private UserDataEntity.DataBean.Task task;
    //跳转类型 sigletop
    private int intentType = 0;

    private int countDownTime = COUNTDOWNINTERVAL;

    private static final int COUNTDOWNINTERVAL = 30 * 1000; //奖励转圈时间

    private PollTask pollTask;
    private Timer pollTimer;

    // 获取奖励跳转到登录
    private int REQUEST_CODE_GET_ACTICLE_REWARD = 0x01;
    private static final int REQUEST_CODE_LOGIN = 0X02;

    private long startReadTime;

    private boolean isClickShare = false;

   // private TTAdNative mTTAdNative;

    private static final int AD_POSITION = 3;
    private boolean isShowAd = false; //是否关闭广告
    private WebSettings mSettings;
    //评论历史 用于处理重复评论刷屏情况
    List<String> commentHistory = new ArrayList<>();

    boolean isCommentTop = false;


    //激励广告弹窗动画效果
    private RewardAnimManager rewardAnimManager;
    //双倍奖励时间戳
    private long doubleRewardTime = 0;

    private NativeExpressAD mADManager;
    private WebView mWebView;

    private GetCoinEntity coinEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_detail;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        //step1:初始化sdk
//        TTAdManager ttAdManager = TTAdManagerHolder.get();
//        //step2:创建TTAdNative对象,用于调用广告请求接口
//        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
//        super.onCreate(savedInstanceState);
//
//        MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ARTICLEDETAILS_PV);
//        MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ARTICLEDETAILS_UV);
//    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        startReadTime = System.currentTimeMillis() / 1000;

//        mViewBinding.modelTitleBar.tvStatus.setBackgroundResource(R.color.app_color);
        /**
         * 因为用了singletop
         */

        if (intentType == 0) {
            articId = TextUtils.isEmpty(getIntent().getStringExtra("id")) ? "" : getIntent().getStringExtra("id");
            from = getIntent().getStringExtra("from");
            task = (UserDataEntity.DataBean.Task) getIntent().getSerializableExtra("task");
            intentType = 1;
        }
        mViewBinding.LoadingView.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetUtil.isNetworkAvailable(mActivity)) {
                    initNet();
                } else {
                    ToastUtils.getInstance().show(mActivity, "请连接网络后重试");
                }

            }
        });
        if (!NetUtil.isNetworkAvailable(mActivity)) {
            mViewBinding.LoadingView.showError();
            return;
        }
        initNet();
        mViewBinding.LoadingView.showLoading();
        mViewBinding.circlePercentProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginEntity.getIsLogin()) {
//                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.ACTICLE_REWARD);
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TODAT_COIN);
                } else {
                    startActivityForResult(new Intent(mActivity, GuideLoginActivity.class), REQUEST_CODE_LOGIN);
                }

            }
        });
        mViewBinding.activityHomeDetailAbOneTagClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADRemovePopup mRemovePopup = new ADRemovePopup(mActivity);
                mRemovePopup.setBlurBackgroundEnable(false)
                        .linkTo(v);
                mRemovePopup.showPopupWindow(v);
                mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                    @Override
                    public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                        mViewBinding.activityHomeDetailAb.setVisibility(View.GONE);
                    }
                });
            }
        });

        try {
            pollTimer = new Timer();


        } catch (Exception e) {

        }

        mViewBinding.activityHomeDetailEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    mViewBinding.activityHomeDetailInputCommit.setEnabled(false);
                    mViewBinding.activityHomeDetailInputCommit.setTextColor(Color.parseColor("#666666"));
                } else {
                    mViewBinding.activityHomeDetailInputCommit.setEnabled(true);
                    mViewBinding.activityHomeDetailInputCommit.setClickable(true);
                    mViewBinding.activityHomeDetailInputCommit.setTextColor(Color.parseColor("#4683FF"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler.postDelayed(tenSecondCountDownRun, 10 * 1000);

    }

    /**
     * 开启读秒倒计时
     */
    public void startCountDown() {

        if (pollTask != null) {
            pollTask.cancel();
            pollTask = null;
        }
        pollTask = new PollTask();
        //schedule 计划安排，时间表
        if (pollTimer == null) {
            pollTimer = new Timer();
        }
        pollTimer.schedule(pollTask, 0, 1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Paper.book().write(PagerCons.KEY_COUNT_DOWN_TIME, countDownTime);
        articId = TextUtils.isEmpty(intent.getStringExtra("id")) ? "" : intent.getStringExtra("id");
        from = getIntent().getStringExtra("from");
        task = (UserDataEntity.DataBean.Task) getIntent().getSerializableExtra("task");
        mViewBinding.activityHomeDetailScroll.scrollTo(0, 0);
        initView();
        initData();
    }

    private void initNet() {
        mViewBinding.rlCircleReward.setVisibility(View.GONE);
        if (rewardAnimManager != null) {
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("aid", articId);
        //文章详情
        mPresenter.getHomeDetail(map, this);
    }

    private void initList() {
        mViewBinding.loadingProgressbar.setVisibility(View.VISIBLE);
        Map<String, String> mapComment = new HashMap<>();
        mapComment.put("aid", articId);
        mapComment.put("page", String.valueOf(page));
        if (mPresenter != null) {
            mPresenter.getArticleComment(mapComment, mActivity);
        }
    }

    @Override
    public void initData() {
        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("文章详情");
        mViewBinding.modelTitleBar.moduleIncludeTitleBarShare.setImageResource(R.mipmap.share_article);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarSet.setImageResource(R.mipmap.icon_set_textsize);
        initListRe();
        initRecometListRe();
        //软键盘监听
        KeyboardChangeListener softKeyboardStateHelper = new KeyboardChangeListener(this);
        softKeyboardStateHelper.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    //键盘的弹出
                    //     et_seartch.setCursorVisible(true);
                } else {
                    //键盘的收起
                    mViewBinding.activityHomeDetailInput.setVisibility(View.GONE);
                    mViewBinding.activityHomeDetailEditInput.setFocusable(false);
                    mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(false);
                    //   et_seartch.setCursorVisible(false);
                }
            }
        });
        manager = ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
        managerView = getWindow().peekDecorView();
        //软键盘监听
        mViewBinding.activityHomeDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.activityHomeDetailInput.setVisibility(View.VISIBLE);
                if (null != managerView) {
                    manager.showSoftInput(managerView, 0);
                }
                mViewBinding.activityHomeDetailEditInput.setFocusable(true);
                mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(true);
                mViewBinding.activityHomeDetailEditInput.requestFocus();
                mViewBinding.activityHomeDetailEditInput.setCursorVisible(true);
                InputMethodUtils.showInputMethod(mViewBinding.activityHomeDetailEditInput);
            }
        });
        //点击评论  滚动到第一个消息
        mViewBinding.activityHomeDetailContentAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.activityHomeDetailScroll.smoothScrollTo(0, mViewBinding.activityHomeDetailComments.getTop());
            }
        });
        //发布
        mViewBinding.activityHomeDetailInputCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    return;
                }
                if (TextUtils.isEmpty(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
                    ToastUtils.getInstance().show(mActivity, "请输入评论内容");
                    return;
                }
                initCommit();
            }
        });
/*
    说明：
        真正的总高度应该调用NestedScrollView.getChildAt(0).getMeasuredHeight()来获取,需要注意，这是控件高度，不是滑动高度,
        不要傻白甜的用这个与scrollY 去除获取滑动比例,
        如果这样做的说明你对scrollY这个参数理解不够，这个参数是代表的滑动距离，在滑动之前已经是有一屏的距离了，所以它们之间的关系可以表示为:

        此处用M代表 NestedScrollView.getMeasuredHeight()，CM代表NestedScrollView.getChildAt(0).getMeasuredHeight()

        当滑动到底部:    scrollY + M = CM
        总滑动距离 = CM-M
        当前滑动的比例 = scrollY / 总滑动距离
        完整比例公式： scrollY/(CM-M)
*/
        //总高度
        int scrollHeight = mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight();
        //第一屏高度
        int scrollFirstHeight = mViewBinding.activityHomeDetailScroll.getMeasuredHeight();
        //      Log.e("TAG", "onScrollChange: ======>>>>>>>>>scrollFirstHeight=" + scrollFirstHeight + "总scrollHeight=" + scrollHeight);

        //scoll监听
        mViewBinding.activityHomeDetailScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScroll, int oldScrollY) {
//                scrollX 当前横向滑动的距离
//                scrollY 当前垂直滑动的距离
//                oldScrollX 上一次停止后横向滑动的距离
//                oldScrollY 上一次停止后垂直滑动的距离
//                这些距离都是以px作为单位,实际使用注意进行px/dp转换
                // Log.e("TAG", "onScrollChange: ======>>>>>>>>>scrollFirstHeight=" + (mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight()));
                //               Log.e("TAG", "onScrollChange: recycler滚动距离===》" + scrollY);
//                Log.e("TAG", "onScrollChange: 滚动距离===》" + (mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight()));

                //滑动到了底部,注意如果有padding设置还需要减去一个padding的数值
                //预加载评论列表
                if (mList.size() > 4) {
                    //当mlist大于3的时候，滑到列表的倒数第三个位置开始预加载
                    if (scrollY >= nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() - (nestedScrollView.getHeight() / mList.size() * 3)) {
                        if (isRefresh) {
                            page++;
                            initList();
                            isRefresh = false;
                        }
                    }
                } else if (mList.size() > 0 && mList.size() <= 4) {
                    if (scrollY >= mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight() - mViewBinding.activityHomeDetailComments.getHeight()) {
                        if (isRefresh) {
                            page++;
                            initList();
                            isRefresh = false;
                        }
                    }
                }
                if (scrollY == nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight() && mList.size() > 0) {
                    mViewBinding.loadingProgressbar.setVisibility(View.VISIBLE);
                }
                //滑动到评论广告位置进行统计，类似于我的页面
                //mViewBinding.activityHomeDetailComments.getChildAt(haveAdPosition).getTop()一定要在条件内，否则空指针，因为没被初始化
//                if (mList.size() > 0) {
//                    if (isHaveAd && haveAdStatistical == 0 && mViewBinding.activityHomeDetailComments.getChildAt(haveAdPosition) != null) {
//                        if (scrollY >= mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() -
//                                mViewBinding.activityHomeDetailScroll.getMeasuredHeight() -
//                                (mViewBinding.activityHomeDetailComments.getHeight() -
//                                        mViewBinding.activityHomeDetailComments.getChildAt(haveAdPosition).getTop())) {
//                            initStatisticalAd(0, ad_id);
//                            haveAdStatistical = 1;
//                        }
//                    }
//                }
//                //滑动到第一个固定广告位的统计
//                if (haveAdFirstStatistical == 0 && isHaveAdFirst && mViewBinding.activityHomeDetailAb != null) {
//                    if (scrollY >= mViewBinding.activityHomeDetailAb.getTop() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight()) {
//                        initStatisticalAd(0, ad_id_first);
//                        haveAdFirstStatistical = 1;
//                    }
//                }
//                //第二个广告位的统计
//                if (haveAdTwoStatistical == 0 && isHaveAdTwo) {
//                    if (mViewBinding.activityHomeDetailRecommended.getChildAt(haveAdTwoPosition) == null) {
//                        return;
//                    }
//                    if (scrollY >= mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight() - mViewBinding.activityHomeDetailComments.getHeight() - mViewBinding.activityHomeDetailRecommended.getHeight() + mViewBinding.activityHomeDetailRecommended.getChildAt(haveAdTwoPosition).getTop()) {
//                        initStatisticalAd(0, ad_id_two);
//                        haveAdTwoStatistical = 1;
//                    }
//                }
//                //第三个广告位的统计
//                if (haveAdThreeStatistical == 0 && isHaveAdThree) {
//                    if (mViewBinding.activityHomeDetailRecommended.getChildAt(haveAdThreePosition) == null) {
//                        return;
//                    }
//                    if (scrollY >= mViewBinding.activityHomeDetailScroll.getChildAt(0).getMeasuredHeight() - mViewBinding.activityHomeDetailScroll.getMeasuredHeight() - mViewBinding.activityHomeDetailComments.getHeight() - mViewBinding.activityHomeDetailRecommended.getHeight() + mViewBinding.activityHomeDetailRecommended.getChildAt(haveAdThreePosition).getTop()) {
//                        initStatisticalAd(0, ad_id_three);
//                        haveAdThreeStatistical = 1;
//                    }
//                }
            }
        });
    }

    //评论区域广告统计
    private void initStatisticalAd(int type, int ad_id) {
        //type 0浏览 1点击
        Map<String, String> map = new HashMap<>();
        map.put("ad_id", String.valueOf(ad_id));
        map.put("type", String.valueOf(type));
        map.put("uuid", OSUtils.getIMEI(mActivity));
        mPresenter.getLookOrOnTach(map, mActivity);
    }


    //发布评论
    private void initCommit() {
        if (TextUtils.isEmpty(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
            ToastUtils.getInstance().show(mActivity, "请输入评论");
            return;
        }
        if (commentHistory.contains(mViewBinding.activityHomeDetailEditInput.getText().toString().trim())) {
            ToastUtils.getInstance().show(mActivity, "请勿发布重复评论");
            return;
        }

//        aid: 6074904
//        content: 爱仕达大奥
        Map<String, String> map = new HashMap<>();
        map.put("aid", articId);
        map.put("content", mViewBinding.activityHomeDetailEditInput.getText().toString().trim());
        mPresenter.getArticleCollectCommit(map, mActivity);
    }

    @OnClick({R.id.activity_home_detail_finish,
            R.id.module_include_title_bar_return,
            R.id.activity_home_detail_author_fcous,
            R.id.activity_home_detail_author_detail,
            R.id.activity_home_detail_collect,
            R.id.activity_home_detail_share,
            R.id.module_include_title_bar_share,
            R.id.module_include_title_bar_set})
    public void onClick(View view) {
        LoginEntity loginEntity;
        switch (view.getId()) {
            //
            case R.id.activity_home_detail_finish:
            case R.id.module_include_title_bar_return:
                finish();
                break;
            //关注
            case R.id.activity_home_detail_author_fcous:
                loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    return;
                }
                if (mid == 0) {
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("mid", String.valueOf(mid));
                if (!is_Focus) {
                    map.put("status", "1");
                } else {
                    map.put("status", "2");
                }
                mPresenter.getArticleAuthorFcous(map, mActivity);
                break;
            //跳转个人详情
            case R.id.activity_home_detail_author_detail:
                if (mid == 0) {
                    return;
                }
//                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.AUTHORE_DETAIL + mid);
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MEDIACODEDETAIL + mid);
                break;
            //收藏
            case R.id.activity_home_detail_collect:
                loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    return;
                }
                if (TextUtils.isEmpty(articId)) {
                    ToastUtils.getInstance().show(mActivity, "暂不支持收藏该文章");

                    return;
                }
                Map<String, String> mapCollect = new HashMap<>();
                if (!is_Collect) {
                    mapCollect.put("status", "1");
                } else {
                    mapCollect.put("status", "2");
                }
                mapCollect.put("aid", articId);
                mPresenter.getArticleCollect(mapCollect, mActivity);
                break;
            //字体设置
            case R.id.module_include_title_bar_set:
                TextSettingDialog textSettingDialog = new TextSettingDialog(mActivity);
                textSettingDialog.show();
                break;
            //分享
            case R.id.module_include_title_bar_share:
            case R.id.activity_home_detail_share:
                if (TextUtils.isEmpty(articId)) {
                    ToastUtils.getInstance().show(mActivity, "暂不支持分享该文章");
                    return;
                }
                loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN);
                    return;
                }

                if (getIntent().getIntExtra("entry_type", 0) == 1) {
                 //   MobclickAgent.onEvent(mActivity, EventID.HOMEPAGE_JUEJIN_ARTICLE_TOPRIGHT_SHARE);    //首页-掘金宝文章分享-埋点
                } else {
                 //   MobclickAgent.onEvent(mActivity, EventID.HOMEPAGE_RECOMMEND_ARTICLE_TOPRIGHT_SHARE);    //首页-资讯详情页分享-埋点
                }

                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_ARTICLE);
                shareInfo.setUrl_path(ShareInfo.PATH_ARTICLE + "/" + articId);
                shareInfo.setId(articId);

                shareInfo.setType("article");
                if (articleDetailEntity != null && articleDetailEntity.getData() != null && !articleDetailEntity.getData().getImg_url().isEmpty()) {
                    shareInfo.setColumn_id(articleDetailEntity.getData().getColumn_id());
                    if (articleDetailEntity.getData().getImg_url().size() > 0) {
                        shareInfo.setSharePicUrl(articleDetailEntity.getData().getImg_url().get(0));
                    }
                } else {
                    shareInfo.setSharePicUrl("null"); //通过null去判断走默认图
                }
                mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("tag", "article");
                        map.put("aid", articId);
                        if (mPresenter != null) {
                            mPresenter.getRewardByShare(map, mActivity);
                        }
                    }
                });
                isClickShare = true;
                mShareDialog.show();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startCountDown();
        //分享回调
        int state = book().read(ShareDialog.SHARE_NOTIFY, 0);
        if (state == 1 && isClickShare) {
            book().write(ShareDialog.SHARE_NOTIFY, 0);

            HashMap<String, String> map = new HashMap<>();
            map.put("tag", "article");
            map.put("aid", articId);
            if (mPresenter != null) {
                mPresenter.getRewardByShare(map, mActivity);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (LoginEntity.getIsLogin() && mPresenter != null) {
            mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);


            switch (requestCode) {
                //双倍奖励
                case PlayRewardVideoAdActicity.DOUBLEREWARD:
                    if (resultCode == RESULT_OK) {
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put("aid", articId);
                        hashMap.put("type", "arc");
                        hashMap.put("read_time", doubleRewardTime + "");
                        mPresenter.getRewardDouble(hashMap, mActivity);
                        doubleRewardTime = 0;
                    }
                    break;
            }
        }

    }

    private void initRecometListRe() {
        adapter = new DetailRecomentAdapter(mActivity, mList, articId);
        adapter.setReplyOnItemClick(new DetailRecomentAdapter.ReplyOnItemClick() {
            @Override
            public void onClick(int position) {
                DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);
                Intent intent = new Intent(mActivity, ArticleCollectReplyActivity.class);
                intent.putExtra("cid", bean.getCid());
                intent.putExtra("content", bean.getContent());
                intent.putExtra("avatar", bean.getAvatar());
                intent.putExtra("fabulous", bean.getFabulous());
                intent.putExtra("name", bean.getNickname());
                intent.putExtra("is_fabulous", bean.getIs_fabulous());
                intent.putExtra("aid", articId);
                intent.putExtra("reply", bean.getReply());
                intent.putExtra("comment_time", bean.getComment_time());
                startActivity(intent);
            }

            //广告点击统计
            @Override
            public void tatistical(int id) {
                if (haveAdStatisticalClick == 0) {
                    initStatisticalAd(1, id);
                    haveAdStatisticalClick = 1;
                }

            }


        });
        LinearLayoutManager manager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mViewBinding.activityHomeDetailComments.setLayoutManager(manager);
        mViewBinding.activityHomeDetailComments.setAdapter(adapter);

    }

    private void initListRe() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        //设置分割线
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line));
        mViewBinding.activityHomeDetailRecommended.addItemDecoration(itemDecoration);
        homePagerAdapter = new HomePagerAdapter(mActivity, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mViewBinding.activityHomeDetailRecommended.setLayoutManager(linearLayoutManager);

        mViewBinding.activityHomeDetailRecommended.setAdapter(homePagerAdapter);
        homePagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        homePagerAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                if (mData.size() < position || position < 0) {
                    homePagerAdapter.notifyDataSetChanged();
                    return;
                }
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                if (mData.get(position) instanceof HomeListEntity.DataBean) {
                    HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) mData.get(position);
//                    view.findViewById(R.id.tv_title).setEnabled(false);
//                    dataBean.setSelected(true);
//                    homePagerAdapter.notifyItemChanged(position);
                    if (dataBean.getShowtype() == 5) {

                    } else if (dataBean.getShowtype() == 8) {  //点击短视频标题
                        VideoDetailActivity.intentMe(mActivity, dataBean);
                    } else {
                        if (dataBean.getType().equals("picture")) {

//                            Intent intent = new Intent(mActivity, PictureViewPagerActivity.class);
//                            intent.putExtra("id", dataBean.getId());
//                            intent.putExtra("tabId", 0);
//                            startActivity(intent);
                            PictureViewPagerActivity.intentMe(mActivity, dataBean.getId(), 0);

                            return;
                        }
//                        Intent intent = new Intent(mActivity, BridgeWebViewActivity.class);
                        //原生详情

                        Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                        intent.putExtra("id", String.valueOf(dataBean.getId()));
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
//                        if (TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {
//
////                            String url = RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + dataBean.getId();
////                        intent.putExtra("url", url);
////                        mActivity.startActivity(intent);
////                            BridgeWebViewActivity.intentMe(mActivity, url);
//
//                        } else {
//                            BridgeWebViewActivity.intentMe(mActivity, dataBean.getOther().getRedirect_url());
//                        }

                        //阅读记录数据缓存
                        try {
                            ArrayList<HomeListEntity.DataBean> dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                            if (dataBeanArrayList == null) {
                                dataBeanArrayList = new ArrayList<>();
                            }


//                             只缓存最近30条记录
                            if (dataBeanArrayList.size() > 30) {
                                int a = dataBeanArrayList.size() - 30;
                                List<HomeListEntity.DataBean> dataBeans = dataBeanArrayList.subList(0, a);
                                dataBeanArrayList.removeAll(dataBeans);
                            }

                            //去重
                            for (HomeListEntity.DataBean dataBean1 : dataBeanArrayList) {
                                if (dataBean1.getId() == dataBean.getId()) {
                                    return;
                                }
                            }

                            dataBeanArrayList.add(dataBean);
                            book().write(PagerCons.KEY_READOBJECT, dataBeanArrayList);
                        } catch (Exception e) {

                        }

                    }
                }

            }

            @Override
            public void tatistical(int id, int type) {
                if (type == 1) {
                    if (haveAdTwoStatisticalClick == 0) {
                        initStatisticalAd(1, id);
                        haveAdTwoStatisticalClick = 1;
                    }
                } else {
                    if (haveAdThreeStatisticalClick == 0) {
                        initStatisticalAd(1, id);
                        haveAdThreeStatisticalClick = 1;
                    }
                }

            }
        });
    }

    ArticleDetailEntity articleDetailEntity;

    //获取文本
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void getHomeDetailSuccess(Serializable serializable) {
        articleDetailEntity = (ArticleDetailEntity) serializable;
        Map<String, String> map = new HashMap<>();
        map.put("aid", articId);
        map.put("column_id", articleDetailEntity.getData().getColumn_id() + "");
        map.put("publish_time", articleDetailEntity.getData().getPublish_time() + "");

        if (articleDetailEntity.getCode() == 0 && articleDetailEntity.getData() != null) {

            isShowAd = (articleDetailEntity.getData().getColumn_id() == 101) || (articleDetailEntity.getData().getColumn_id() == 102);
            // 关注按钮
            if (articleDetailEntity.getData().getMid() != 0) {
                mid = articleDetailEntity.getData().getMid();
                mViewBinding.activityHomeDetailAuthorFcous.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.activityHomeDetailAuthorFcous.setVisibility(View.GONE);
            }
            //是否关注
            if (articleDetailEntity.getData().getIs_follow() == 1) {
                is_Focus = true;
                mViewBinding.activityHomeDetailAuthorFcous.setText("√ 已关注");
                mViewBinding.activityHomeDetailAuthorFcous.setBackgroundResource(R.drawable.shap_bg_bdbcbc_round_8);

            } else {
                is_Focus = false;
                mViewBinding.activityHomeDetailAuthorFcous.setText("+ 关注");
                mViewBinding.activityHomeDetailAuthorFcous.setBackgroundResource(R.drawable.shap_f45546_white_round_5);
            }
            //是否收藏
            if (articleDetailEntity.getData().getIs_collection() == 1) {
                is_Collect = true;
                mViewBinding.activityHomeDetailCollect.setImageResource(R.mipmap.icon_collect_ok);

            } else {
                is_Collect = false;
                mViewBinding.activityHomeDetailCollect.setImageResource(R.mipmap.icon_collect_black);
            }
            //阅读多长时间
            mViewBinding.activityHomeDetailAuthorRead.setText("阅读 " + Utils.FormatW(articleDetailEntity.getData().getRead_num()));
            //文章标题
            mViewBinding.activityHomeDetailTitle.setText(articleDetailEntity.getData().getTitle());
            //作者
            mViewBinding.activityHomeDetailAuthorName.setText(articleDetailEntity.getData().getAuthor());
            //头像
            Glide.with(mActivity).
                    load(articleDetailEntity.getData().getAuthor_logo()).
                    apply(RequestOptions.bitmapTransform(new CircleCrop()).
                            placeholder(R.mipmap.default_img)).
                    into(mViewBinding.activityHomeDetailAuthorHeader);
            //时间
            mViewBinding.activityHomeDetailAuthorTime.setText(Utils.experienceTime(articleDetailEntity.getData().getPublish_time()));
            //来源
            mViewBinding.activityHomeDetailSource.setText("来源：" + articleDetailEntity.getData().getSource());
            //第一个广告位

            mWebView = new WebView(mActivity);

            mSettings = mWebView.getSettings();

            mSettings.setSupportZoom(true);
            mSettings.setBuiltInZoomControls(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl("file:///android_asset/article.html");
            mWebView.setWebChromeClient(new WebChromeClient());
            MJavascriptInterface mJavascriptInterface = new MJavascriptInterface(this); //初始化本地方法接口
            mWebView.addJavascriptInterface(mJavascriptInterface, "article");//添加到webview
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView webView, String s) {
                    String content = articleDetailEntity.getData().getContent().replaceAll(" ", "juejinbao");
                    String html = Base64.encodeToString(URLEncoder.encode(content).getBytes(), Base64.NO_PADDING);

                    webView.loadUrl("javascript:setHtml('" + html + "')");


                    super.onPageFinished(webView, s);
                    mViewBinding.activityHomeDetailScroll.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mViewBinding.activityHomeDetailScroll.scrollTo(0, 0);
                                }
                            });

                        }
                    }, 500);
                    mViewBinding.LoadingView.showContent();

                    //防止 空指针
                    if (mPresenter != null) {
                        //推荐
                        mPresenter.getArticleList(map, HomeDetailActivity.this);
                        //评论列表
                        initList();
                    }
                }
            });

            mViewBinding.webContainer.removeAllViews();
            mViewBinding.webContainer.addView(mWebView);


            setTextSize(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));

            //设置文字大小 弹窗`
            boolean isShowTextSet = book().read(PagerCons.KEY_TEXTSET_IS_SHOW_GUIDE, true);
            if (isShowTextSet) {
                GuideTextSettingDialog textSettingDialog = new GuideTextSettingDialog(mActivity);
                textSettingDialog.show();
                Paper.book().write(PagerCons.KEY_TEXTSET_IS_SHOW_GUIDE, false);
            }

            countDownTime = COUNTDOWNINTERVAL;
            if (Paper.book().read(PagerCons.KEY_COUNT_DOWN_TIME) != null) {
                int time = Paper.book().read(PagerCons.KEY_COUNT_DOWN_TIME);
                if (time > 0) {
                    countDownTime = time;
                    mViewBinding.circlePercentProgress.setPercentage((COUNTDOWNINTERVAL - countDownTime) * 1000 / COUNTDOWNINTERVAL);
                }
            }

            boolean isShowGuide = book().read(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, true);
            if (LoginEntity.getIsLogin()) {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_arcticle_circle_reward);
                drawable.setBounds(0, 0, 70, 80);
                mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
                mViewBinding.tvCircleInnerPic.setText("");

                if (isShowGuide || (!TextUtils.isEmpty(from) && from.equals(Constant.FROM_NEW_TASK_INTENT))) {
                    showGuideDialog();
                } else {
                    showGuideShare();
                    if (LoginEntity.getIsLogin()) {
                        mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                    } else {
                        mViewBinding.rlCircleReward.setVisibility(View.GONE);
                    }
                    startCountDown();
                }
            }

            //埋点（记录完成浏览文章数量（进文章+1，；23:55求平均数，清空当天的数据，））
          //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.MISSION_EVERYDAYMISSION_ARTICLE_COUNT);
        } else if(articleDetailEntity.getCode() == 0 ){
            mViewBinding.LoadingView.showEmpty();
        } else{
            mViewBinding.LoadingView.showEmpty();
            ToastUtils.getInstance().show(mActivity,articleDetailEntity.getMsg());
        }
    }

    //获取推荐列表
    @Override
    public void getArticleListSuccess(Serializable serializable) {
        HomeListEntity newsEntity = (HomeListEntity) serializable;
        if (!newsEntity.getData().isEmpty() && newsEntity.getData().size() > 0) {
            mData.clear();
            for (int i = 0; i < newsEntity.getData().size(); i++) {
                if (newsEntity.getData().get(i).getType().equals("1")) {
                    newsEntity.getData().get(i).setShowtype(1);
                } else if (newsEntity.getData().get(i).getType().equals("2")) {
                    newsEntity.getData().get(i).setShowtype(2);
                } else if (newsEntity.getData().get(i).getType().equals("3")) {
                    newsEntity.getData().get(i).setShowtype(3);
                } else if (newsEntity.getData().get(i).getType().equals("4")) {
                    newsEntity.getData().get(i).setShowtype(4);
                }
            }
            mData.addAll(newsEntity.getData());


            homePagerAdapter.setIsFooter(false);
            homePagerAdapter.notifyDataSetChanged();


            loadRecommendListAd();

        } else {
            mData.clear();
            loadRecommendListAd();
            mViewBinding.activityHomeDetailRecommended.setVisibility(View.GONE);
        }


    }

    //关注
    @Override
    public void getArticleAuthorFcousSuccess(Serializable serializable) {
        HomeListEntity homeListEntity = (HomeListEntity) serializable;
        if (homeListEntity.getCode() == 0) {
            if (!is_Focus) {
                mViewBinding.activityHomeDetailAuthorFcous.setBackgroundResource(R.drawable.shap_bg_bdbcbc_round_8);
                mViewBinding.activityHomeDetailAuthorFcous.setText("√ 已关注");
                ToastUtils.getInstance().show(mActivity, "已关注");
                is_Focus = true;
            } else {
                mViewBinding.activityHomeDetailAuthorFcous.setBackgroundResource(R.drawable.shap_f45546_white_round_5);
                mViewBinding.activityHomeDetailAuthorFcous.setText("+ 关注");
                ToastUtils.getInstance().show(mActivity, "已取消关注");
                is_Focus = false;
            }
        }
    }

    //收藏
    @Override
    public void getArticleCollectSuccess(Serializable serializable) {

//        new RewardADPopupWindow(mActivity).show(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
        BaseDefferentEntity baseDefferentEntity = (BaseDefferentEntity) serializable;
        if (baseDefferentEntity.getCode() == 0) {
            if (!is_Collect) {
                mViewBinding.activityHomeDetailCollect.setImageResource(R.mipmap.icon_collect_ok);
                ToastUtils.getInstance().show(mActivity, "收藏成功");
                is_Collect = true;
            } else {
                mViewBinding.activityHomeDetailCollect.setImageResource(R.mipmap.icon_collect_black);
                ToastUtils.getInstance().show(mActivity, "已取消收藏");
                is_Collect = false;
            }
        } else {
            ToastUtils.getInstance().show(mActivity, baseDefferentEntity.getMsg());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pollTask != null) {
            pollTask.cancel();
            pollTask=null;
        }
    }


    //发布
    @Override
    public void getArticleCollectCommitSuccess(Serializable serializable) {
        CommentCommitEntity entity = (CommentCommitEntity) serializable;
        if (entity.getCode() == 0) {

            //comment_status=0为评论成功待审核通过后显示,  =1为评论成功直接显示
            if (entity.getData().getComment_status() == 0) {
                ToastUtils.getInstance().show(mActivity, entity.getMsg());
            }
            commentHistory.add(mViewBinding.activityHomeDetailEditInput.getText().toString().trim());
            mViewBinding.activityHomeDetailEditInput.setText("");
            mViewBinding.activityHomeDetailInput.setVisibility(View.GONE);
            mViewBinding.activityHomeDetailEditInput.setFocusable(false);
            mViewBinding.activityHomeDetailEditInput.setFocusableInTouchMode(false);
            manager = ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE));
            managerView = getWindow().peekDecorView();
            if (null != managerView) {
                manager.hideSoftInputFromWindow(managerView.getWindowToken(), 0);
            }
            page = 1;

            isCommentTop = true;
            initList();
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

    //评论列表
    @Override
    public void getArticleCommentSuccess(Serializable serializable) {
//        mViewBinding.loadingProgressbar.setVisibility(View.GONE);
        isRefresh = true;
        DetailRecomentEntity detailRecomentEntity = (DetailRecomentEntity) serializable;
        if (detailRecomentEntity.getCode() == 0) {
            if (detailRecomentEntity.getData() != null) {
                if (detailRecomentEntity.getData().getData() != null) {
                    //显示评论多少条
                    if (detailRecomentEntity.getData().getTotal() > 99) {
                        mViewBinding.activityHomeDetailBigContent.setText("99+");
                    } else {
                        mViewBinding.activityHomeDetailBigContent.setText(detailRecomentEntity.getData().getTotal() + "");

                    }
                    if (detailRecomentEntity.getData().getTotal() == 0) {
                        mViewBinding.activityHomeDetailBigContent.setVisibility(View.GONE);
                    } else {
                        mViewBinding.activityHomeDetailBigContent.setVisibility(View.VISIBLE);
                    }
                    //显示没有更多了
                    if (detailRecomentEntity.getData().getData().size() > 0) {
                        isRefresh = true;
//                        mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.INVISIBLE);
                    } else {
                        isRefresh = false;
                        if (detailRecomentEntity.getData().getTotal() == 0) {
                            mViewBinding.smallVideoPopCommentsListMore.setText("暂无相关评论");
                            mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                            mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                        } else {
                            mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                            mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                            mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                        }
                    }
                    //更新列表
                    if (page == 1) {
                        adapter.reloadRecyclerView(detailRecomentEntity.getData().getData(), true);
                    } else {
                        adapter.reloadRecyclerView(detailRecomentEntity.getData().getData(), false);

                    }
//                    if(page==1){
//                        loadCommentListAd();
//                    }


                } else {
                    mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                    mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                    mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                    isRefresh = false;
                }
            } else {
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                mViewBinding.smallVideoPopCommentsListMore.setText("暂无更多评论");
                mViewBinding.smallVideoPopCommentsListMore.setVisibility(View.VISIBLE);
                isRefresh = false;
            }
            //遍历list查看是否有广告，并且找到position
//            for (int i = 0; i < mList.size(); i++) {
//                if (mList.get(i) instanceof DetailRecomentEntity.DataBeanX.DataBean) {
//                    DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(i);
//                    if (bean.getType() == 100) {
//                        isHaveAd = true;
//                        haveAdPosition = i;
//                        ad_id = bean.getId();
//                    }
//                }
//            }

            //评论结束后跳转至最新评论处
            if (isCommentTop) {
                mViewBinding.activityHomeDetailScroll.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewBinding.activityHomeDetailScroll.smoothScrollTo(0, mViewBinding.activityHomeDetailComments.getTop());
                    }
                }, 500);

            }
        } else {
            ToastUtils.getInstance().show(mActivity, detailRecomentEntity.getMsg());
        }


    }

    //广告统计
    @Override
    public void showLookOrOnTachSuccess(Serializable serializable) {

    }

    /**
     * 阅读奖励
     *
     * @param serializable
     */
    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {
        coinEntity = (GetCoinEntity) serializable;
        if (coinEntity.getCode() == 0) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_article_reward_coin);
            drawable.setBounds(0, 0, 32, 32);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("+" + Utils.FormatGold(coinEntity.getData().getCoin()));

            UserDataEntity userDataEntity = Paper.book().read(PagerCons.PERSONAL);
            if ((!TextUtils.isEmpty(from) && from.equals(Constant.FROM_NEW_TASK_INTENT))) {
                showArticleNewTaskRewordDialog();
            } else if (userDataEntity != null && userDataEntity.getData().getNewbie_task() != null
                    && userDataEntity.getData().getNewbie_task().size() != 0) {
                for (UserDataEntity.DataBean.Task bean : userDataEntity.getData().getNewbie_task()) {
                    if (bean.getTag().equals("newbie_task_first"))
                        task = bean;
                }
                if (task != null) {
                    showArticleNewTaskRewordDialog();
                } else {
                    int acticleShowCount = Paper.book().read(PagerCons.ACTICLE_REWARD_STYLE, 0);
                    showRewardDialog(acticleShowCount, coinEntity.getData().getCoin());
                }
            } else {
                int acticleShowCount = Paper.book().read(PagerCons.ACTICLE_REWARD_STYLE, 0);
                showRewardDialog(acticleShowCount, coinEntity.getData().getCoin());
            }
        } else {
            if (coinEntity.getCode() == -1000) {
                mViewBinding.rlAlreadGetReward.setVisibility(View.VISIBLE);
                //点击后消失
                mViewBinding.rlAlreadGetReward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewBinding.rlAlreadGetReward.setVisibility(View.GONE);
                    }
                });
            } else {
                ToastUtils.getInstance().show(mActivity, coinEntity.getMsg());
            }

            if ((!TextUtils.isEmpty(from) && from.equals(Constant.FROM_NEW_TASK_INTENT))) {
                showArticleNewTaskRewordDialog();
            }
        }
    }

    @Override
    public void getNewTaskRewardSuccess(Serializable serializable) {
    }

    @Override
    public void getRewardByShareSuccess(Serializable serializable) {
        GetCoinEntity entity = (GetCoinEntity) serializable;
        if (entity.getData().getCoin() != 0) {
            RewardEntity rewardEntity = new RewardEntity(
                    "奖励到账",
                    entity.getData().getCoin(),
                    "分享文章奖励",
                    "",
                    false
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(rewardEntity);
            acticleRewardPop.showPopupWindow();
        }
    }

    @Override
    public void leavePageCommitSuccess(Serializable serializable) {
    }

    @Override
    public void getRewardDouble(RewardDoubleEntity rewardDoubleEntity) {
        RewardEntity entity = new RewardEntity(
                "奖励到账",
                rewardDoubleEntity.getData().getCoin(),
                "阅读奖励",
                "用掘金宝App读文章看视\n频，能多赚更多金币！",
                false
        );
        ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.showPopupWindow();
    }

    /**
     * 新手任务奖励弹窗
     */
    private void showArticleNewTaskRewordDialog() {
        ArticleNewTaskRewordDialog dialog = new ArticleNewTaskRewordDialog(mActivity, task);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setClickListener((position, view) -> {
            Map<String, String> map = new HashMap<>();
            map.put("tag", task.getTag());
            mPresenter.getNewTaskReward(map, mActivity);

            int acticleShowCount = Paper.book().read(PagerCons.ACTICLE_REWARD_STYLE, 0);
            showRewardDialog(acticleShowCount, coinEntity.getData().getCoin());
        });
        dialog.show();
    }

    /**
     * @param conut 次数
     * @param coin  数量
     */
    public void showRewardDialog(int conut, double coin) {

        if (conut == 0) {
            ActicleDetailRewordDialog01 detailRewordDialog01 = new ActicleDetailRewordDialog01(mActivity, coin, "阅读奖励");
            detailRewordDialog01.setCanceledOnTouchOutside(true);
            detailRewordDialog01.show();

        } else if (conut == 1) {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coin,
                    "阅读奖励",
                    "用掘金宝App读文章看视\n频，能多赚更多金币！",
                    false
            );

            if (rewardAnimManager != null) {
                rewardAnimManager.destory();
                rewardAnimManager = null;
            }

            if (rewardAnimManager == null) {
                rewardAnimManager = new RewardAnimManager(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), 10, 160, entity);
                rewardAnimManager.show();
            }

        } else {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coin,
                    "阅读奖励",
                    "用掘金宝App读文章看视\n频，能多赚更多金币！",
                    true
            );

            if (rewardAnimManager != null) {
                rewardAnimManager.destory();
                rewardAnimManager = null;
            }

            if (rewardAnimManager == null) {
                rewardAnimManager = new RewardAnimManager(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), 10, 160, entity);
                rewardAnimManager.show();
            }
        }
        Paper.book().write(PagerCons.ACTICLE_REWARD_STYLE, ++conut);

    }


    private void initOneAd(final ArticleDetailEntity articleDetailEntity) {
        if (articleDetailEntity.getData().getAd_info().getAd_position_27().getTitle() != null) {
            isHaveAdFirst = true;
            ad_id_first = articleDetailEntity.getData().getAd_info().getAd_position_27().getId();
            mViewBinding.activityHomeDetailAb.setVisibility(View.VISIBLE);
            mViewBinding.activityHomeDetailAbOneTitle.setText(articleDetailEntity.getData().getAd_info().getAd_position_27().getTitle());
            String ad_sign = articleDetailEntity.getData().getAd_info().getAd_position_27().getAd_sign();
            mViewBinding.activityHomeDetailAbOneTag.setText(TextUtils.isEmpty(ad_sign) ? "" : ad_sign);
            mViewBinding.activityHomeDetailAbOneTag.setVisibility(TextUtils.isEmpty(ad_sign) ? View.GONE : View.VISIBLE);
            Glide.with(mActivity).load(articleDetailEntity.getData().getAd_info().getAd_position_27().getImages().get(0)).into(mViewBinding.activityHomeDetailAbOneImg);
            mViewBinding.activityHomeDetailAbOneTagName.setText(articleDetailEntity.getData().getAd_info().getAd_position_27().getSub_title());
            mViewBinding.activityHomeDetailAb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    WebActivity.intentMe(mActivity, articleDetailEntity.getData().getAd_info().getAd_position_27().getTitle(),
                            articleDetailEntity.getData().getAd_info().getAd_position_27().getLink());
                    if (haveAdFirstStatisticalClick == 0) {
                        initStatisticalAd(1, ad_id_first);
                        haveAdFirstStatisticalClick = 1;
                    }

                }
            });
        }

    }

    @Override
    public void error(Throwable e) {

    }

    @Override
    public void onError(String str) {

    }

    /**
     * 上传阅读时间
     */
    public void commitReadTime() {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", mid + "");
        hashMap.put("type", "arc");
        hashMap.put("starttime", (System.currentTimeMillis() / 1000) - 50 + "");

        if (mPresenter != null)
            mPresenter.leavePageCommit(hashMap, mActivity);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        try {
            if (mWebView != null) {
                mWebView.loadUrl("javascript:clearTime()");
                mWebView.stopLoading();
                ViewParent parent = mWebView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mWebView);
                }
                mWebView.removeAllViewsInLayout();
                mWebView.removeAllViews();
                mWebView.setWebViewClient(null);
                CookieSyncManager.getInstance().stopSync();
                mWebView.destroy();
            }

        } catch (Exception throwable) {
            throwable.printStackTrace();
        }

        if (pollTimer != null) {
            pollTimer.cancel();
            pollTimer = null;
        }
        if (pollTask != null) {
            pollTask.cancel();
            pollTask = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        if (rewardAnimManager != null) {
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }


    Handler handler = new Handler();

    public class PollTask extends TimerTask {


        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {

                    countDownTime--;

                    if (countDownTime == 0) {
                        mViewBinding.circlePercentProgress.setPercentage(1000);
                        if (pollTask != null) {
                            pollTask.cancel();

                        }
                        if (LoginEntity.getIsLogin()) {
                            //上传阅读时间
                            commitReadTime();
                            getRewardOf30S();
                        }
                    } else {
                        mViewBinding.circlePercentProgress.setPercentage((COUNTDOWNINTERVAL - countDownTime) * 1000 / COUNTDOWNINTERVAL);
                    }


                }
            });


        }
    }

    /**
     * 阅读30s随机奖励
     */
    public void getRewardOf30S() {
        if (mPresenter == null || this.isDestroyed()) {
            return;
        }

        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", articId);
        hashMap.put("type", "arc");
        hashMap.put("starttime", (System.currentTimeMillis() / 1000) - 1000 + "");
        hashMap.put("read_time", (doubleRewardTime = System.currentTimeMillis()) + "");

        mPresenter.getRewardOf30second(hashMap, mActivity);
    }

    Runnable tenSecondCountDownRun = new Runnable() {
        @Override
        public void run() {
            if (pollTask != null) {
                pollTask.cancel();
                pollTask = null;
            }
        }
    };

    /**
     * 转圈奖励蒙层
     */
    public void showGuideDialog() {
        GuideActicleRewardDialog dialog = new GuideActicleRewardDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            if (LoginEntity.getIsLogin()) {
                mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.rlCircleReward.setVisibility(View.GONE);
            }
            startCountDown();
        });
        dialog.show();
        Paper.book().write(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, false);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startCountDown();
            handler.removeCallbacks(tenSecondCountDownRun);
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            handler.postDelayed(tenSecondCountDownRun, 10 * 1000);
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 加载推荐列表feed广告
     */
    private void loadRecommendListAd() {

        if (isShowAd) {
            return;
        }

       ADConfigEntity adConfigEntity = book().read(PagerCons.KEY_AD_CONFIG);
        int adType;
        if(adConfigEntity!=null){
            adType  = adConfigEntity.getData().getAd_type2().getPlatform();
        }else {
            adType = 1;
        }

        if (adType != 0) {
            initNativeExpressAD();
        } else {
            //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
//            AdSlot adSlot = new AdSlot.Builder()
//                    .setCodeId(TTAdManagerHolder.POS_ID)
//                    .setSupportDeepLink(true)
//                    .setImageAcceptedSize(640, 320)
//                    .setAdCount(2) //请求广告数量为1到3条
//                    .build();
//            //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
//            mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
//                @Override
//                public void onError(int code, String message) {
//
//                }
//
//                @Override
//                public void onFeedAdLoad(List<TTFeedAd> ads) {
//                    mViewBinding.activityHomeDetailRecommended.setVisibility(View.VISIBLE);
//                    if (ads == null || ads.isEmpty()) {
//                        return;
//                    }
//
//                    if (mData.iterator() != null) {
//                        Iterator<Object> iterator = mData.iterator();
//                        while (iterator.hasNext()) {
//                            Object value = iterator.next();
//                            if (value instanceof TTFeedAd) {
//                                iterator.remove();
//                            }
//                        }
//                    }
//
//                    if (ads.size() > 0 && mData.size() <= 2 && !mData.contains(ads.get(0))) {
//                        if (!mData.contains(ads.get(0))) {
//                            mData.add(0, ads.get(0));
//                        }
//
//                    }
//                    if (ads.size() > 0 && mData.size() > 3) { //第二条加入一条
//                        Random r = new Random();
//                        int a = r.nextInt(2);
//                        if (!mData.contains(ads.get(0))) {
//                            mData.add(a, ads.get(0));
//                        }
//                    }
//                    if (ads.size() >= 2 && mData.size() > 4) { //倒数第二再加入一条
//                        if (!mData.contains(ads.get(1))) {
//                            mData.add(mData.size() - 1, ads.get(1));
//                        }
//
//                    }
//                    homePagerAdapter.notifyDataSetChanged();
//                }
//            });
        }


    }

    private void initNativeExpressAD() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
//        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, (int) getResources().getDimension(R.dimen.ws100dp)); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(mActivity, adSize, GDTHolder.APPID, GDTHolder.NATIVE_ID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {
                mViewBinding.activityHomeDetailRecommended.setVisibility(View.VISIBLE);

                Log.i("zzz", "广点通+  onADLoaded: " + adList.size());
                if (adList == null || adList.isEmpty()) {
                    return;
                }

                if (mData.iterator() != null) {
                    Iterator<Object> iterator = mData.iterator();
                    while (iterator.hasNext()) {
                        Object value = iterator.next();
                       // if (value instanceof TTFeedAd) {
                            iterator.remove();
                       // }
                    }
                }

                if (adList.size() > 0 && mData.size() <= 2 && !mData.contains(adList.get(0))) {
                    if (!mData.contains(adList.get(0))) {
                        mData.add(0, adList.get(0));
                    }

                }
                if (adList.size() > 0 && mData.size() > 3) { //第二条加入一条
                    Random r = new Random();
                    int a = r.nextInt(2);
                    if (!mData.contains(adList.get(0))) {
                        mData.add(a, adList.get(0));
                    }
                }
                if (adList.size() >= 2 && mData.size() > 4) { //倒数第二再加入一条
                    if (!mData.contains(adList.get(1))) {
                        mData.add(mData.size() - 1, adList.get(1));
                    }

                }
                homePagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
            //    MobclickAgent.onEvent(MyApplication.getContext(), EventID.ARTICLEDETAILS_ADS_CLICKTIMES);
             //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.ARTICLEDETAILS_ADS_CLICKUSERS);
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onNoAD(AdError adError) {

            }
        });
        mADManager.loadAD(10);

    }

    /**
     * 加载评论广告
     */
    private void loadCommentListAd() {
        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(TTAdManagerHolder.POS_ID)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(640, 320)
//                .setAdCount(1) //请求广告数量为1到3条
//                .build();
//        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
//        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
//            @Override
//            public void onError(int code, String message) {
//
//            }
//
//            @Override
//            public void onFeedAdLoad(List<TTFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
//
//                    return;
//                }
//
//                if (ads.size() > 0 && mList.size() >= 5) { //第二条加入一条
//                    mList.add(mList.size() - 5, ads.get(0));
//                }
//
//                homePagerAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private class MJavascriptInterface {

        private Context context;

        public MJavascriptInterface(Context context) {
            super();
            this.context = context;
        }

        //展示大图详情
        @JavascriptInterface
        public void showBigPic(String params) {
            try {
                JSONObject jsonObject = new JSONObject(params);
                int position = jsonObject.optInt("position", 0);
                ArticleShowBigPicEntity entity = JSON.parseObject(params, ArticleShowBigPicEntity.class);

                if (entity.getList().size() > 0) {
                    Intent intent = new Intent(mActivity, ShowPictureActivity.class);
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("list", (ArrayList<String>) entity.getList());
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        int times = 0;

//        @JavascriptInterface
//        public void resize(final float height) {
////            mActivity.runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////
////
////                    Log.i("zzz", "run: " + height +  " - - -"  + times);
////                    if (height > 20000 && times < 2) {
////                        times++;
////                        webView.loadUrl("file:///android_asset/article.html");
////                        String content = articleDetailEntity.getData().getContent().replaceAll(" ", "juejinbao");
////                        String html = Base64.encodeToString(URLEncoder.encode(content).getBytes(), Base64.DEFAULT);
////                        webView.loadUrl("javascript:setHtml('" + html + "')");
////                        mViewBinding.activityHomeDetailScroll.scrollTo(0, 0);
////                    } else {
////                        webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
////                    }
////                }
////            });
//        }

        @JavascriptInterface
        public void openBridgeWebView(String url) {
            BridgeWebViewActivity.intentMe(mActivity, url);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextSizeSetting(TextSettingEvent event) {
        setTextSize(event.getLevel());
    }

    private void setTextSize(String level) {
        switch (level) {
            case "small":
                mViewBinding.activityHomeDetailTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_small));
                mSettings.setTextZoom(90);
                adapter.setTextSizeLevel("small");
                homePagerAdapter.setTextSizeLevel("small");
                break;
            case "middle":
                mViewBinding.activityHomeDetailTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_middle));
                mSettings.setTextZoom(100);
                adapter.setTextSizeLevel("middle");
                homePagerAdapter.setTextSizeLevel("middle");
                break;
            case "big":
                mViewBinding.activityHomeDetailTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_big));
                mSettings.setTextZoom(110);
                adapter.setTextSizeLevel("big");
                homePagerAdapter.setTextSizeLevel("big");
                break;
            case "large":
                mViewBinding.activityHomeDetailTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_lager));
                mSettings.setTextZoom(125);
                adapter.setTextSizeLevel("large");
                homePagerAdapter.setTextSizeLevel("large");
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Paper.book().write(PagerCons.KEY_COUNT_DOWN_TIME, countDownTime);
    }

    /**
     * 展示分享引导
     */
    public void showGuideShare() {

        if (System.currentTimeMillis() - (long) Paper.book().read(PagerCons.KEY_LEAD_ARTICLE_SHARE_TIME, 0l) >= 24 * 60 * 60 * 1000) {
            Paper.book().write(PagerCons.KEY_LEAD_ARTICLE_SHARE_TIME, System.currentTimeMillis());
            mViewBinding.shareLeadPop.setVisibility(View.VISIBLE);
            mViewBinding.shareLeadPop.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.shareLeadPop.setVisibility(View.GONE);
                }
            }, 5000);
            mViewBinding.shareLeadPop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewBinding.shareLeadPop.setVisibility(View.GONE);
                }
            });

        }


    }


}

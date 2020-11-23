package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomeBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.ClearHomeTabUnreadMsgEvent;
import com.newsuper.t.juejinbao.bean.HideShowGiftCarButtonEvent;
import com.newsuper.t.juejinbao.bean.HomeTabSelectEvent;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.LoginEvent;
import com.newsuper.t.juejinbao.bean.RequestUnreadApiEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.bean.ShowTabPopupWindowEvent;
import com.newsuper.t.juejinbao.bean.SwitchTabEvent;
import com.newsuper.t.juejinbao.bean.UpdateChannelEvent;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.activity.ChannelManagerActivity;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.activity.HomeSearchActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerFragmentAdapter;
import com.newsuper.t.juejinbao.ui.home.dialog.HomeTipsAlertDialog;
import com.newsuper.t.juejinbao.ui.home.dialog.NewTaskReadRewardDialog;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinCountDownEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.MessageHintEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ShenmaHotWordsEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TabChangeEvent;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.entity.UnreadMaseggeEntity;
import com.newsuper.t.juejinbao.ui.home.ppw.HomeTipsPopupWindow;
import com.newsuper.t.juejinbao.ui.home.ppw.RewardMoreCoinPop_2;
import com.newsuper.t.juejinbao.ui.home.presenter.HomePresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.HomePresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.NoDoubleListener;
import com.newsuper.t.juejinbao.utils.SPUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;

public class Homefragment extends BaseFragment<HomePresenterImpl, FragmentHomeBinding> implements View.OnClickListener, HomePresenter.HomePresenterView {

    private String type = "";
    //    public HomeTipsAlertDialog mHomeGiftDialog; //大礼包
    //    private HomePagerFragmentAdapter mAdapter;
    private HomePagerFragmentAdapter mAdapter;

    public List<ChannelEntity> mChannelList = new ArrayList<>();
    // 记录当前频道 用户首页点击tab刷新判断当前频道
    public ChannelEntity currChannel;

    private HomeTipsPopupWindow mPopupWindow;

    private boolean hasShowGiftDialog;
    private CountDownTimer mCountDownTimer;
    private boolean onCounting;
    private GetCoinCountDownEntity mCounterData;
    private LoginEntity loginEntity;
    private UserDataEntity userDataEntity;

    private long newUserStartTime = 0;//统计新用户使用时长
    private long oldUserStartTime = 0;//统计老用户使用时长
    private long touristsStartTime = 0;//统计游客使用时长

    ShareDialog mShareDialog;

    HashMap<Integer, Integer> unreadMessageMap = new HashMap<>();
    private RewardMoreCoinPop_2 acticleRewardPop;

    public static Homefragment newInstance(Bundle data) {
        Homefragment fragment = new Homefragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void initView() {

        List<ChannelEntity> channelCacheData = Paper.book().read(PagerCons.CHANNEL_CHCHE);
        if (channelCacheData != null && channelCacheData.size() > 0) {
            mViewBinding.loadingView.showContent();
            mChannelList.clear();
            mChannelList.addAll(channelCacheData);
            setTabsPage();
        } else {
            mViewBinding.loadingView.showLoading();
        }

        String topImgUrl = Paper.book().read(PagerCons.KEY_HOME_TITLE_BG);
        if (!TextUtils.isEmpty(topImgUrl)) {
            Glide.with(this).asDrawable().load(topImgUrl).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    mViewBinding.rlHead.setBackground(resource);
                }
            });
            //mViewBinding.btnSearch.setBackgroundResource(R.drawable.bg_search_home_shape);
        } else {
            mViewBinding.rlHead.setBackgroundResource(R.mipmap.bg_home_title);
            //mViewBinding.btnSearch.setBackgroundResource(R.drawable.bg_search_button_shape);
        }

        mViewBinding.btnAdd.setOnClickListener(this);
//        mViewBinding.btnSearch.setOnClickListener(this);
        mViewBinding.fragmetNewsFlipper.setOnClickListener(this);
//        mViewBinding.loadingView.showLoading();
        Boolean isShowGift = Paper.book().read(PagerCons.ISSHOWBIGGIFT);
        loginEntity = Paper.book().read(PagerCons.USER_DATA);

        //未登录
        if (loginEntity == null) {
            mViewBinding.adsView.setVisibility(View.VISIBLE);
//            if(mHomeGiftDialog==null){
//                mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
//            }
//            mHomeGiftDialog.show();
            HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
            mHomeGiftDialog.show();
        } else {
            //未领取
            if (!LoginEntity.getIsGetGiftPacks()) {
                mViewBinding.adsView.setVisibility(View.VISIBLE);
                //本地库里面没有大礼包弹出的时间就弹出(没有就代表肯定是第一次打开APP)，有的话，对比当前时间，如果大于24小时，那么弹出，小于24小时不弹出
                if (Paper.book().read(PagerCons.BIGGIFTTIME) == null) {
                    Paper.book().write(PagerCons.BIGGIFTTIME, System.currentTimeMillis());
                    mViewBinding.adsView.setVisibility(View.VISIBLE);
                } else {

                    if (isShowGift == null || isShowGift && (System.currentTimeMillis() - (long) Paper.book().read(PagerCons.BIGGIFTTIME) >= 24 * 60 * 60 * 1000)) {
                        Paper.book().write(PagerCons.BIGGIFTTIME, System.currentTimeMillis());
                        mViewBinding.adsView.setVisibility(View.VISIBLE);
                    } else {
                        mViewBinding.adsView.setVisibility(View.GONE);
                    }
                }
            } else {
                mViewBinding.adsView.setVisibility(View.GONE);
            }
        }
        mViewBinding.adsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(mHomeGiftDialog==null){
//                    mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
//                }
//                mHomeGiftDialog.show();

                HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
                mHomeGiftDialog.show();
            }
        });

        mViewBinding.lyLing.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mViewBinding.ivNewTaskTriangle.setVisibility(   View.GONE);
                Paper.book().write(PagerCons.NEW_TASK_TRIANGLE,true);
                //埋点（点击首页-领金币）
                //MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_COLLECT_GOLD_COINS);
                doGetCoin();
            }
        });
        final ShareInfo shareInfo = new ShareInfo();
        mViewBinding.imgShare.setOnClickListener(new NoDoubleListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (LoginEntity.getIsLogin()) {

                    //MobclickAgent.onEvent(mActivity, EventID.HOMEPAGE_TOPRIGHT_SHARE);  //首页-分享-埋点
                    if (mShareDialog == null) {

                        shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                        shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);

                        shareInfo.setId(LoginEntity.getUserToken());
                        shareInfo.setType("index");

                        mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                    }
                    mShareDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
            }
        });

        //关闭新手任务
        mViewBinding.ivNewTaskDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.rlNewTask.setVisibility(View.GONE);
            }
        });
        //点击新手任务
        mViewBinding.newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userDataEntity==null || userDataEntity.getData()==null || userDataEntity.getData().getNewbie_task()==null
                        || userDataEntity.getData().getNewbie_task().size()==0)
                    return;

                switch (userDataEntity.getData().getNewbie_task().get(0).getTag()){
                    case "newbie_task_first":
                        showNewTaskReadDialog();
                        break;
                    case "newbie_task_second":
                        EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.MOVIE,true));
                        break;
                    case "newbie_task_third":
                        EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.TASK,true));
                        break;
                    default:
                        break;
                }
            }
        });

        mPresenter.getHomeTabUnreadMsg(mActivity);

        //头条热榜列表
        mPresenter.getHotWordRank(mActivity);
    }

    private void launchMiniProgram(String path) {
        String appId = "你的appid"; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(mActivity, appId);

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "原始id"; // 填小程序原始id
        req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }



    @Override
    public void initData() {
        mPresenter.getChannelList(new HashMap<String, String>(), mActivity);
    }

    void setTabsPage() {
//        if (MainActivity.isXiaoMiChannelNotPass) {
//            mViewBinding.btnAdd.setVisibility(View.INVISIBLE);
//        } else {
//            mViewBinding.btnAdd.setVisibility(View.VISIBLE);
//        }
        mViewBinding.viewPager.removeAllViews(); //要调用此方法，因为修改频道要重置
        if (mAdapter != null) {
            mAdapter.clean();
            mAdapter = null; //置空也无法清除缓存，要用StateFragmentAdapter才行
        }
//
//        for (int i = 0; i < mChannelList.size(); i++) {
//            Fragment fragment;
//            if (mChannelList.get(i).getName().equals("视频")) {
//                fragment = HomePagerFragment.newInstance(mChannelList.get(i));
//            } else {
//                fragment = HomePagerFragment.newInstance(mChannelList.get(i));
//            }
//            fragments.add(fragment);
//        }

        mViewBinding.viewPager.setOffscreenPageLimit(3);
        mAdapter = new HomePagerFragmentAdapter(getChildFragmentManager(), mChannelList);
        mViewBinding.viewPager.setAdapter(mAdapter);

        currChannel = mChannelList.get(0);

//        mTabs.setViewPager(mViewPager);
        mViewBinding.activityHomePageTable.setViewPager(mViewBinding.viewPager);
//        setTabsValue();
        mViewBinding.viewPager.setCurrentItem(0, true);


        UpdateTabUI(0);

        mViewBinding.activityHomePageTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                UpdateTabUI(position);

            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //((AutoHeightViewPager)viewPager).resetHeight(position);
            }

            @Override
            public void onPageSelected(int position) {
                currChannel = mChannelList.get(position);
                UpdateTabUI(position);
                if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.releaseAllVideos();
                }

//                if (!currChannel.getName().equals("掘金宝")) {
//                    resetAreaColor();
//                } else {
//                    if (currentColor != 0) {
//                        setAreaColor(currentColor);
//                    }
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /*
     * 修改tab选中字体大小，同是可以设置选中tab以及未选中tab背景色
     *     实例：
     *     tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white))
     * */
    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, i == position ? 18 : 16);
        }

//        if(mChannelList.get(position).getName().equals("视频")){
//            MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_VIDEO_CLICK);
//        }else if(mChannelList.get(position).getName().equals("小视频")){
//            MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMPEPAGE_LITTLEVIDEO_CLICK);
//        }
    }


    //显示首页大礼包
    @Subscribe
    public void onBusEvent(String msg) {
        if (BusConstant.SHOW_AD_CARD_GIFT_DIALOG.equals(msg)) {
//            if (mHomeGiftDialog == null) {
//                if(mHomeGiftDialog==null){
//                    mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
//                }
//                mHomeGiftDialog.show();
//            }
            HomeTipsAlertDialog mHomeGiftDialog = new HomeTipsAlertDialog(mActivity, mViewBinding.root);
            mHomeGiftDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                if (mChannelList.size() > 0) {
                    Intent intent = new Intent(getActivity(), ChannelManagerActivity.class);
//                    intent.putExtra("myChannel", mChannelList);
                    Paper.book().write(PagerCons.CHANNEL_CHCHE, mChannelList);
                    startActivity(intent);
                } else {
                    ToastUtils.getInstance().show(getContext(), "当前频道还未加载完");
                }

                break;
//            case R.id.btn_search:
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                getActivity().startActivity(intent);
//                break;
//            case R.id.fragmet_news_flipper:
//
//                break;
        }
    }

    @Subscribe
    public void onChannelUpdate(UpdateChannelEvent event) {
        List<ChannelEntity> channelCacheData = Paper.book().read(PagerCons.CHANNEL_CHCHE);
        mViewBinding.activityHomePageTable.setCurrentTab(0);

        if (channelCacheData != null && channelCacheData.size() > 0) {
            mChannelList = channelCacheData;
            if (LoginEntity.getIsLogin()) {
                saveChannelToService();
            }
            setTabsPage();
        }
    }

    /**
     * 切换tab
     *
     * @param tabChangeEvent
     */
    @Subscribe
    public void onChangeTab(TabChangeEvent tabChangeEvent) {
        if (tabChangeEvent.getTabPosition() == 11) {

            //精彩小视频查看更多
            for (int i = 0; i < mChannelList.size(); i++) {
                if (mChannelList.get(i).getName().equals("小视频")) {
                    mViewBinding.viewPager.setCurrentItem(i);
                }
            }
        }else if (tabChangeEvent.getTabPosition() == 0) {
            mPresenter.getUserData(new HashMap<>(), mActivity);

            newUserStartTime = System.currentTimeMillis()/1000;
            oldUserStartTime = System.currentTimeMillis()/1000;
            touristsStartTime = System.currentTimeMillis()/1000;
        }

    }


    @Subscribe
    public void onIsShowGift(HideShowGiftCarButtonEvent event) {
        mViewBinding.adsView.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        newUserStartTime = System.currentTimeMillis()/1000;
        oldUserStartTime = System.currentTimeMillis()/1000;
        touristsStartTime = System.currentTimeMillis()/1000;

        mViewBinding.tvLingCount.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLingRewardPop();
            }
        }, 500);

//        mViewBinding.btnSearch.setText(LoginEntity.getUid() + "");

        //MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_PV);
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_UV);
            }
            mPresenter.getUserData(new HashMap<>(), mActivity);
        }else{
            mViewBinding.ivNewTaskTriangle.setVisibility(View.GONE);
            mViewBinding.rlNewTask.setVisibility(View.GONE);
        }
    }

    boolean Visible = false;
    boolean isVisible = false;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        this.isVisible = isVisible;
        if (isVisible && !Visible) {

        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        loadLingTimeApi();
        Visible = true;
        if (LoginEntity.getIsLogin()) {
            mPresenter.getMessageHint(new HashMap<String, String>(), mActivity);
        }
    }


    //保存频道到服务器
    void saveChannelToService() {
        Map<String, String> param = new HashMap<>();
        String ids = "";
        for (int i = 0; i < mChannelList.size(); i++) {
            ids += mChannelList.get(i).getId();
            if (i < mChannelList.size() - 1) ids += ",";
        }
        param.put("channel_id", ids);
        param.put("type", "article");

        mPresenter.setChannelList(param, mActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getChennelListSuccess(Serializable serializable) {
        mViewBinding.loadingView.showContent();
        ChannelInfo channelInfo = (ChannelInfo) serializable;
        mChannelList.clear();
//        mChannelList.add(new ChannelEntity(0,"推荐"));
        mChannelList.addAll(channelInfo.getData());
        Paper.book().write(PagerCons.CHANNEL_CHCHE, mChannelList);
        setTabsPage();

    }


    /**
     * 设置频道接口回调
     *
     * @param serializable
     */
    @Override
    public void setChennelListSuccess(Serializable serializable) {
//        ChannelInfo channelInfo = (ChannelInfo) serializable;
        Log.i("zzz", "saveSuccess: " + "首页频道保存成功");
    }

    @Override
    public void getMessageHintSuccess(Serializable serializable) {
        MessageHintEntity messageHintEntity = (MessageHintEntity) serializable;
        if (messageHintEntity.getData().getUnsigned() == 1) {
            double count = messageHintEntity.getData().getReward_coefficient() * 368;
            EventBus.getDefault().post(new ShowTabPopupWindowEvent(count));
        }
    }

    @Override
    public void getCoinOf30MinSuccess(Serializable serializable) {
        GetCoinEntity entity = (GetCoinEntity) serializable;
        if (entity.getData().getCoin() != 0) {
//            TimeRewardPopup timeRewardPopup = new TimeRewardPopup(getContext());
//            timeRewardPopup.setView(entity);
//            timeRewardPopup.showPopupWindow();

            showRewardPop(entity.getData().getCoin());

            //加载倒计时接口
            loadLingTimeApi();
        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }
    }

//    public void showRewardPop(double coin) {
//        RewardEntity rewardEntity = new RewardEntity();
//        rewardEntity.setCoin(coin);
//        rewardEntity.setTitle("时段奖励领取成功");
//
//        RewardMoreCoinPop acticleRewardPop = new RewardMoreCoinPop(mActivity);
//        acticleRewardPop.setView(rewardEntity);
//        acticleRewardPop.setOnClickListener(new RewardMoreCoinPop.OnClickListener() {
//            @Override
//            public void onclick(View view) {
//                PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.WATCHAD);
//            }
//        });
//        acticleRewardPop.showPopupWindow();
//    }
    public void showRewardPop(double coin) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setCoin(coin);
        rewardEntity.setTitle("时段奖励领取成功");

        acticleRewardPop = new RewardMoreCoinPop_2(mActivity);
        acticleRewardPop.setView(rewardEntity);
        acticleRewardPop.setOnClickListener(new RewardMoreCoinPop_2.OnClickListener() {
            @Override
            public void onclick(View view) {
                PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.WATCHAD);
            }
        });
        if(!acticleRewardPop.isShowing()){
            acticleRewardPop.showPopupWindow();
        }
    }


    /**
     * 获取首页30min奖励
     *
     * @param serializable
     */
    @Override
    public void countDownOf30Min(Serializable serializable) {
        GetCoinCountDownEntity entity = (GetCoinCountDownEntity) serializable;
        startCounter(entity);
    }

    /**
     * 未读消息数量
     *
     * @param serializable
     */
    @Override
    public void getHomeTabUnreadMsgSucess(Serializable serializable) {
//        mViewBinding.activityHomePageTable.showMsg(2,8);
        UnreadMaseggeEntity entity = (UnreadMaseggeEntity) serializable;
        if (entity.getCode() == 0) {
            for (int i = 0; i < entity.getData().size(); i++) {
                unreadMessageMap.put(entity.getData().get(i).getColumn_id(), entity.getData().get(i).getUnread_num());
            }
            for (int j = 0; j < mChannelList.size(); j++) {
                for (int i = 0; i < entity.getData().size(); i++) {
                    if (mChannelList.get(j).getId() == entity.getData().get(i).getColumn_id()) {
                        int finalJ = j;
                        int finalI = i;
                        mViewBinding.activityHomePageTable.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (entity.getData().get(finalI).getUnread_num() > 0) {
                                    mViewBinding.activityHomePageTable.showMsg(finalJ, entity.getData().get(finalI).getUnread_num());
                                }
                            }
                        }, 500);

                    }

                }

            }

        } else {
            ToastUtils.getInstance().show(mActivity, entity.getMsg());
        }

    }

    @Override
    public void getShenmaSearchWord(ShenmaHotWordsEntity entity) {
    }

    //头条热榜列表
    @Override
    public void getHotWordRankSuccess(TodayHotEntity entity) {
        //搜索轮播
        mViewBinding.fragmetNewsFlipper.removeAllViews();

        if(entity != null && entity.getData() != null && entity.getData().size() != 0) {

            for (int i = 0; i < entity.getData().size(); i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_homesearch_flipper, null);
                TextView tvContent = view.findViewById(R.id.model_view_filpper_context);
                TodayHotEntity.DataBean hotWordsBean = entity.getData().get(i);
                tvContent.setLines(1);
                tvContent.setText(hotWordsBean.getTitle());
                mViewBinding.fragmetNewsFlipper.addView(view);
                view.setOnClickListener(v ->
                        HomeSearchActivity.intentMe(mActivity , hotWordsBean)
                );

            }
        }
    }

    //金币领取 开始计时
    void startCounter(GetCoinCountDownEntity data) {
        long remainTime = data.getData().getTime_remaining();
        final String coin = data.getData().getCoin() + "";

        if (remainTime == 0) { //可领取
            mViewBinding.tvCountTime.setText(getString(R.string.lingqu));
            mViewBinding.tvLingCount.setText(coin);
            mViewBinding.lyLing.setBackgroundResource(R.mipmap.bg_ling_anchor);
        } else {               //倒计时
            mViewBinding.tvLingCount.setText("");
            mViewBinding.lyLing.setBackgroundResource(R.mipmap.bg_ling_anchor_2);

            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            mCountDownTimer = new CountDownTimer(remainTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long min = millisUntilFinished / 1000 / 60;
                    long s = millisUntilFinished / 1000 % 60;
                    onCounting = true;
                    mViewBinding.tvCountTime.setText(String.format("%02d:%02d", min, s));
                }

                @Override
                public void onFinish() {
                    mViewBinding.tvCountTime.setText(getString(R.string.lingqu));
                    mViewBinding.tvLingCount.setText(coin);
                    onCounting = false;
                    mViewBinding.lyLing.setBackgroundResource(R.mipmap.bg_ling_anchor);
//                    showLingRewardPop();  //计时完成不用显示
                }
            };
            mCountDownTimer.start();
        }
        mCounterData = null;
    }


    @Override
    public void showError(String msg) {
//        MyToast.show(context, msg);
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.i("zzz", "showError: " + msg);
        mViewBinding.ivNewTaskTriangle.setVisibility(View.GONE);
        mViewBinding.rlNewTask.setVisibility(View.GONE);
    }

    @Override
    public void getUserDataSuccess(Serializable serializable) {
        userDataEntity = (UserDataEntity) serializable;
        if(userDataEntity.getCode()==0){
            //新手任务
            if(userDataEntity.getData().getNewbie_task()!=null && userDataEntity.getData().getNewbie_task().size()!=0){
                mViewBinding.ivNewTaskTriangle.setVisibility(Paper.book().read(PagerCons.NEW_TASK_TRIANGLE, false) ? View.GONE : View.VISIBLE);
                mViewBinding.rlNewTask.setVisibility(View.VISIBLE);
                mViewBinding.tvNewTaskNum.setText(String.format("%s（%s/3）",userDataEntity.getData().getNewbie_task().get(0).getName(), userDataEntity.getData().getNewbie_task().get(0).getLevel()));
                mViewBinding.tvNewTaskName.setText(userDataEntity.getData().getNewbie_task().get(0).getNotice());
                mViewBinding.tvNewTaskGold.setText(String.format("%s 金币", userDataEntity.getData().getNewbie_task().get(0).getValue()));
            }else{
                mViewBinding.ivNewTaskTriangle.setVisibility(View.GONE);
                mViewBinding.rlNewTask.setVisibility(View.GONE);
            }
        }else{
            ToastUtils.getInstance().show(mActivity,userDataEntity.getMsg());
        }
        Paper.book().write(PagerCons.PERSONAL,userDataEntity);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    /**
     * 显示领取奖励popup
     * 登录用户每天提示一次
     * 非登录不提示
     */
    private void showLingRewardPop() {
        try {
            if (System.currentTimeMillis() - SPUtils.getInstance().getLong(PagerCons.KEY_GET_COIN_POP) < 12 * 60 * 60 * 1000) { //先写12小时
                return;
            }
            if (getActivity() == null) {
                return;
            }
            int[] location = new int[2];
            mViewBinding.tvLingCount.getLocationOnScreen(location);

            Integer read = Paper.book().read(PagerCons.KEY_IS_FIRST_OPEN_APP_JUMP_ZCZF, 0);

            if (mPopupWindow == null || !mPopupWindow.isShowing()) {
                if (false) {
                    mPopupWindow = new HomeTipsPopupWindow(mActivity);
                    mPopupWindow.setOutsideTouchable(true);
                    mPopupWindow.showAtLocation(mViewBinding.tvLingCount, Gravity.NO_GRAVITY, location[0] - NotchScreenUtil.dp2px(getActivity(), 70),
                            location[1] + NotchScreenUtil.dp2px(getActivity(), 25));
                    SPUtils.getInstance().put(PagerCons.KEY_GET_COIN_POP, System.currentTimeMillis());

                }
            }

            mPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                doGetCoin();
//                if (LoginEntity.getIsNew()) {  //是否新用户
//                    loadMessageHint();
//                }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //点击领取首页金币
    void doGetCoin() {

        if (onCounting) {
            return;
        }
        loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null || !loginEntity.isLogin()) {
            Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
            getActivity().startActivity(intent);
            return;
        }
        mPresenter.getCoinOf30Min(new HashMap<String, String>(), mActivity);
    }

    //获取领取时间
    private void loadLingTimeApi() {
        if (mCounterData != null) {
            startCounter(mCounterData);
            return;
        }
        if (onCounting) {
            return;
        }

        mPresenter.countDownOf30Min(new HashMap<String, String>(), mActivity);
    }

    /**
     * 更新选中tab位置(event.name做判断)
     *
     * @param event
     */
    @Subscribe()
    public void updateTab(HomeTabSelectEvent event) {
        int index = 0;
        for (int i = 0; i < mChannelList.size(); i++) {
            if (mChannelList.get(i).getName().equals(event.name)) {
                index = i;
            }
        }
        mViewBinding.viewPager.setCurrentItem(index);

    }

    /**
     * 清除首页Tab上的对应频道的未读消息
     */
    @Subscribe()
    public void claerHomeTabUnreadMsg(ClearHomeTabUnreadMsgEvent event) {

        if (LoginEntity.getIsLogin()) {
            mPresenter.getHomeTabUnreadMsg(mActivity);
        }

        for (int i = 0; i < mChannelList.size(); i++) {
            if (mChannelList.get(i).getId() == event.column_id) {
                Log.i("zzz", "claerHomeTabUnreadMsg: " + event.column_id + "隐藏");
                mViewBinding.activityHomePageTable.hideMsg(i);
            }
        }
    }

    /**
     * 登录成功
     *
     * @param event
     */
    @Subscribe()
    public void reloginEvent(LoginEvent event) { //登录后
//        hasShowGiftDialog = LoginEntity.hasGetGiftBag();
        loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (!LoginEntity.getIsGetGiftPacks()) {
            mViewBinding.adsView.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.adsView.setVisibility(View.GONE);
        }
        EventBus.getDefault().post(new RequestUnreadApiEvent());
        loadMessageHint();
        mPresenter.countDownOf30Min(new HashMap<String, String>(), mActivity);
    }


    /**
     * 退出登录监听事件
     *
     * @param settingLoginEvent
     */
    @Subscribe()
    public void loginOutEvent(SettingLoginEvent settingLoginEvent) { //退出登录
        mViewBinding.adsView.setVisibility(View.VISIBLE);

        mViewBinding.tvCountTime.setText("领取");
        onCounting = false;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        //重置文章奖励接口弹框次数
        Paper.book().write(PagerCons.ACTICLE_REWARD_STYLE, 0);
        //重置图集阅读奖励次数
        Paper.book().write(PagerCons.ACTICLE_REWARD_SCANPICTURE_NUMBER, 0);
        //首次弹框
        Paper.book().write(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, false);

    }

    /**
     * 获取未读消息 弹下拉框
     */
    private void loadMessageHint() {
        mPresenter.getMessageHint(new HashMap<String, String>(), mActivity);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mViewBinding.rlHead)
                .init();
    }

    /**
     * 新手任务阅读奖励引导弹窗
     */
    public void showNewTaskReadDialog() {
        NewTaskReadRewardDialog dialog = new NewTaskReadRewardDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
            intent.putExtra("id", "15615301");
            intent.putExtra("from", Constant.FROM_NEW_TASK_INTENT);
            if(userDataEntity!=null && userDataEntity.getData().getNewbie_task().size()>0)
                intent.putExtra("task",userDataEntity.getData().getNewbie_task().get(0));
            mActivity.startActivity(intent);
        });
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Map<String, Object> map = new HashMap<>();
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
                map.put("onLine", System.currentTimeMillis()/1000-(newUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
               // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.HOMEPAGE_NEWUSER_USETIME, map);
            }else{
                map.put("onLine", System.currentTimeMillis()/1000-(oldUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
             //   MobclickAgent.onEventObject(MyApplication.getContext(), EventID.HOMEPAGE_OLDUSER_USETIME, map);
            }
        }else{
            map.put("onLine", System.currentTimeMillis()/1000-(touristsStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
           // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.HOMEPAGE_TOURISTS_USETIME, map);
        }
    }
}

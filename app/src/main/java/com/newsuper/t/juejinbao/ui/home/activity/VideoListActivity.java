package com.newsuper.t.juejinbao.ui.home.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityVideoListBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.adapter.VideoPagerAdapter;
import com.newsuper.t.juejinbao.ui.home.dialog.GuideActicleRewardDialog;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.presenter.VideoListPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.VideoListPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * 沉浸式视频浏览页
 */
public class VideoListActivity extends BaseActivity<VideoListPresenterImpl, ActivityVideoListBinding> implements VideoListPresenter.View {

    VideoPagerAdapter videoPagerAdapter;
    List<Object> mData = new ArrayList<>();
    private String name;

    //是否第一次拉取数据
    boolean isFirstGetData = true;
    ShareDialog mShareDialog;
    /**
     * 每次请求的广告返回个数，最多支持3个
     */
    final int AD_Per_ReqCount = 3;
    int loadAdCount = 1;
  //  private List<TTFeedAd> mTTFeedAds = new ArrayList<>();     //显示的广告
  //  private List<TTFeedAd> mLastRemainAd = new ArrayList<>();       //上次加载未显示的广告

    final int AD_Interval = 3;        //间隔多少行显示一条广告
    private boolean isRefresh;
    private int clickPosition = 0;
    public int firstVisibleItem, lastVisibleItem, visibleCount;
    private static final int STOP_SMART_REFRESH = 10001;
    private HomeListEntity.DataBean bean;

    private int INTERVAL = 60 * 1000;
    private int countDownTime = INTERVAL;
    private PollTask pollTask;
    private Timer pollTimer;
    private long startReadTime; //开始阅读时间
    private HomeListEntity.DataBean dataBean;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void initView() {

        startReadTime = System.currentTimeMillis() / 1000;
        mViewBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewBinding.refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadDate(true);
            }
        });
        mViewBinding.refreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadDate(false);
            }
        });
        mViewBinding.recyclerview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JzvdStd jzvd = view.findViewById(R.id.jzvdPlayer);
                if (jzvd != null && jzvd.jzDataSource != null && Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.jzDataSource != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra("name");
        bean = (HomeListEntity.DataBean) getIntent().getSerializableExtra("data");

        mData.add(0, bean);
        videoPagerAdapter = new VideoPagerAdapter(mActivity, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mViewBinding.recyclerview.setLayoutManager(linearLayoutManager);
        mViewBinding.recyclerview.setAdapter(videoPagerAdapter);
        videoPagerAdapter.setIsDarkMode(1);


        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        loadDate(true);

        dataBean = (HomeListEntity.DataBean) mData.get(0);
        mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
        startCountDown();
        if (NetUtil.isWifi(this)) {  //wifi条件下开启自动播放
            loadListener(linearLayoutManager);
//            autoPlayVideo(mViewBinding.recyclerview);
        }

        ShareInfo shareInfo = new ShareInfo();
        videoPagerAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder) {
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                clickPosition = position;
                HomeListEntity.DataBean model = (HomeListEntity.DataBean) mData.get(position);
                if (view.getId() == R.id.item_paager_share) {
                    //分享
                    if (LoginEntity.getIsLogin()) {
                       // MobclickAgent.onEvent(VideoListActivity.this, EventID.HOMEPAGE_VIDEO_SHARE);   //首页-视频分享-埋点
                        shareInfo.setUrl_type(ShareInfo.TYPE_VIDEO);
                        shareInfo.setUrl_path("/VideoDetail/" + model.getId());

                        shareInfo.setType("video");
                        shareInfo.setId(model.getId() + "");

                        shareInfo.setAid(model.getId() + "");

                        if (model != null && model.getImg_url().size() != 0) {
                            shareInfo.setSharePicUrl(model.getImg_url().get(0));
                        }

                        mShareDialog = new ShareDialog(VideoListActivity.this, shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                        mShareDialog.show();
                    } else {
                        Intent intent = new Intent(VideoListActivity.this, GuideLoginActivity.class);
                        startActivity(intent);
                    }
                } else if (view.getId() == R.id.fl_give_like) {
                    //点赞

                    HashMap param = new HashMap();
                    param.put("vid", model.getId() + "");
                    mPresenter.giveLike(param, mActivity);
                } else {  //跳详情
                    Jzvd.goOnPlayOnPause();
                    VideoDetailActivity.intentMe(mActivity, model);
                }
            }

            @Override
            public void tatistical(int id, int type) {

            }
        });

        videoPagerAdapter.setOnJzvdStateListener(new Jzvd.CommenStateListener() {
            @Override
            public void startPlay(int position) {
                dataBean = (HomeListEntity.DataBean) mData.get(position);
                mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                startCountDown();
            }

            @Override
            public void pause() {
                if (pollTimer != null) {
                    pollTimer.cancel();
                    pollTimer = null;
                }
            }

            @Override
            public void endPlay() {

            }

            @Override
            public void error() {

            }
        });
    }

    public void loadDate(boolean isRef) {
        myHandler.removeMessages(STOP_SMART_REFRESH);
        myHandler.sendEmptyMessageDelayed(STOP_SMART_REFRESH, 10000);
        isRefresh = isRef;

        Map<String, String> param = new HashMap<>();
        param.put("column_id", "0");
        param.put("type", "video");
        param.put("change_uid", "n");
        param.put("ua", "n");

        String str = System.currentTimeMillis() + "";//待处理字符串 取时间戳后四位传给后台

        if (Paper.book().read(PagerCons.HOME_TIME) != null) {
            param.put("ua", Paper.book().read(PagerCons.HOME_TIME));
        } else {
            param.put("ua", str);
            Paper.book().write(PagerCons.HOME_TIME, str);
        }

        mPresenter.getVideoList(param, mActivity);
    }

    /**
     * 自动播放监听
     *
     * @param linearLayoutManager
     */
    public void loadListener(LinearLayoutManager linearLayoutManager) {

        mViewBinding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollState = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE: //滚动停止
                        scrollState = false;
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING: //手指拖动
                        scrollState = true;
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING: //惯性滚动
                        scrollState = true;
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;


                //大于0说明有播放
                if (Jzvd.CURRENT_JZVD != null && (Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PLAYING || Jzvd.CURRENT_JZVD.state == Jzvd.STATE_PREPARING)) {
                    //当前播放的位置
                    //对应的播放列表TAG
                    try {
                        //JzvdStd.goOnPlayOnPause();
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    private void autoPlayVideo(RecyclerView view) {
        try {
            RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

            for (int i = 0; i < visibleCount; i++) {
                if (layoutManager != null && layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(R.id.jzvdPlayer) != null) {
                    JzvdStd jzplayer = (JzvdStd) layoutManager.getChildAt(i).findViewById(R.id.jzvdPlayer);
                    Rect rect = new Rect();
                    jzplayer.getLocalVisibleRect(rect);
                    int videoheight = jzplayer.getHeight();
                    if (rect.top == 0 && rect.bottom == videoheight) {
                        Log.i("autoPlayVideo", "rect.top: " + rect.top + "----" + "rect.bottom" + rect.bottom);

                        if (Jzvd.CURRENT_JZVD == null || Jzvd.CURRENT_JZVD.state != Jzvd.STATE_PLAYING && Jzvd.CURRENT_JZVD.state != Jzvd.STATE_PREPARING) {
                            jzplayer.setVisibility(View.VISIBLE);
                            jzplayer.startVideo();
                            dataBean = (HomeListEntity.DataBean) mData.get(i);
                            mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                            startCountDown();
                        }
                        return;
                    }
//                    else {
//                        jzplayer.goOnPlayOnPause();
//                    }
                }
            }
        } catch (Exception e) {

        }

    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == STOP_SMART_REFRESH) {
                mViewBinding.refreshView.finishRefresh();
                mViewBinding.refreshView.finishLoadMore();
            }
            return false;
        }
    });

    private void scrollToTopAndRefresh() {
        mViewBinding.recyclerview.scrollToPosition(0);
        //loadDate(true);
        mViewBinding.refreshView.autoRefresh();
    }

    @Override
    public void getVideoListSuccess(Serializable serializable) {
        mViewBinding.refreshView.finishRefresh();
        mViewBinding.refreshView.finishLoadMore();
        HomeListEntity homeListEntity = (HomeListEntity) serializable;
        boolean isTop;

        if (homeListEntity.getData() == null || homeListEntity.getData().size() == 0) {
            return;
        }
        if (isRefresh) {
            mData.addAll(homeListEntity.getData());

            isTop = true;
        } else {
            isTop = false;
            mData.addAll(homeListEntity.getData());
        }

        if (mData.size() > AD_Interval && homeListEntity.getData().size() != 0) {
           // loadListAd(homeListEntity.getData().size(), isTop); //加载广告
        }

        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));

        isFirstGetData = false;
        if (homeListEntity.getData() != null) {
            Paper.book().write(PagerCons.KET_HOME_VIDEO_LASTTIME_DATA + name, homeListEntity.getData());
        }
    }

    @Override
    public void getLikeSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;

        if (baseEntity.getCode() == 0) {
            HomeListEntity.DataBean model = (HomeListEntity.DataBean) mData.get(clickPosition);

            model.setDigg_count(model.getDigg_count() + 1);
            //model.is_fabulous = true;
            videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
            model.setIsGiveLike(1);

            if (Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST) == null) {
                List<Integer> likeBeanList = new ArrayList<>();
                likeBeanList.add(model.getId());
                Paper.book().write(PagerCons.KEY_GIVE_LIKE_LIST, likeBeanList);
            } else {
                List<Integer> likeBeanList = Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST);
                if (likeBeanList.size() > 9) {
                    likeBeanList.remove(0);
                }
                likeBeanList.add(model.getId());
                Paper.book().write(PagerCons.KEY_GIVE_LIKE_LIST, likeBeanList);
            }

        } else {
            ToastUtils.getInstance().show(mActivity, baseEntity.getMsg());
        }
    }

    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {
        GetCoinEntity coinEntity = (GetCoinEntity) serializable;
        if (coinEntity.getCode() == 0) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_article_reward_coin);
            drawable.setBounds(0, 0, 32, 32);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("+" + Utils.FormatGold(coinEntity.getData().getCoin()));

            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coinEntity.getData().getCoin(),
                    "观看视频奖励",
                    "用掘金宝App读文章看视\n频，能多赚更多金币！",
                    true
            );
            ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
            acticleRewardPop.setView(entity);
            acticleRewardPop.showPopupWindow();
        } else {
            ToastUtils.getInstance().show(mActivity, coinEntity.getMsg());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startCircleCountDown();
            }
        }, 500);

    }

    @Override
    public void leavePageCommitSuccess(Serializable serializable) {
    }

    @Override
    public void onError(String msg) {
        mViewBinding.refreshView.finishRefresh();
        mViewBinding.refreshView.finishLoadMore();
    }

    //请求点赞
    public void doGiveLike(TextView tv, final HomeListEntity.DataBean model) {
        Map<String, String> param = new HashMap<>();
        param.put("vid", model.getId() + "");
        mPresenter.giveLike(param, mActivity);
    }

    /**
     * 加载穿山甲信息流广告
     *
     * @paramcontentCount 本次加载文章条数
     * @paramsTop        加到头部还是尾部
     */
//    void loadListAd(int contentCount, boolean isTop) {
//        int adPerCount = book().read(PagerCons.KEY_INTERVAL_HOME_PAGE_AD, 2);
//        /**
//         * 头条联盟信息流广告大图的尺寸为（690px*388px）、小图尺寸为（228px*150px）、组图为（228px*150px*3）
//         */
//        //feed广告请求类型参数
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(TTAdManagerHolder.POS_ID_VIDEO)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(690, 388) //加载返回类型是随机的，通过参数也改不了
//                .setAdCount(adPerCount) // 可选参数，针对信息流广告设置每次请求的广告返回个数，最多支持3个
//                .build();
//
//        //调用feed广告异步请求接口
//        MainActivity.mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                //TToast.show(getContext(), "穿山甲异常："+message);
//            }
//
//            @Override
//            public void onFeedAdLoad(List<TTFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
//                    //TToast.show(getContext(), "on FeedAdLoaded: ad is null!");
//                    return;
//                }
//
//                //ads = ADUtils.removeDuplicate(ads, mTTFeedAds);
//                //去除重复为空时，再加载两次
//                if (loadAdCount < 3) {
//                    loadAdCount++;
//                    loadListAd(contentCount, isTop);
//                    return;
//                }
//                loadAdCount = 1;
//
//                //一页多条时循环
//                for (int pos = 0; pos < ads.size(); pos++) {
//                    TTFeedAd ad = ads.get(pos);
//                    //非视频不加载
////                    if (ad.getImageMode() != TTAdConstant.IMAGE_MODE_VIDEO) continue;
//
////                    while (mData.get(adLastPos) instanceof TTFeedAd){
////                        adLastPos += AD_Interval;     //正常往后加载用这个
////                        if (adLastPos >= mData.size()){
//////                            adLastPos = mData.size()-1;
//////                            break;  //加到尾部时
////                            return;
////                        }
////                    }
//
////                    mData.set(adLastPos, ad);
//
//                    float percent = 2.0f / 3;
//
//                    if (isTop) {
//                        //下拉刷新
//                        if (pos == 0) {
//                            percent = 1.0f / 3;
//                        } else if (pos == 1) {
//                            percent = 2.0f / 3; //第二条时
//                        } else if (pos == 2) {
//                            percent = 1; //第二条时
//                        }
//
//                    } else {
//                        //上拉加载
//                        if (pos == 0) {
//                            percent = 0;
//                        } else if (pos == 1) {
//                            percent = 1.0f / 3; //第二条时
//                        } else if (pos == 2) {
//                            percent = 2.0f / 3; //第二条时
//                        }
//                    }
//
//                    if (ads.size() == 1) percent = 0.5f; //只有一条时
//
//                    int insertPosition = (int) (isTop ? contentCount * percent : (mData.size() - contentCount * percent));
////                    mData.set(insertPosition, ad);
////                    mTTFeedAds.add(ad);
//
//                    if (!mData.contains(ad)) {
//                        mData.add(insertPosition, ad); //add加载会出现连续两条广告的情况
//                        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
//                    }
//
//
////                    if (percent == 2.0f / 3) //只有一条或第二条时
////                        break; //
//                }
//
//            }
//        });
//    }

    public static void intentMe(Activity context, HomeListEntity.DataBean data, int requestCode) {
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra("data", data);
        context.startActivityForResult(intent, requestCode);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    /**
     * 转圈奖励蒙层
     */
    public void showGuideDialog() {
        GuideActicleRewardDialog dialog = new GuideActicleRewardDialog(mActivity);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (LoginEntity.getIsLogin()) {
                    mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                    startCountDown();
                } else {
                    mViewBinding.rlCircleReward.setVisibility(View.GONE);
                }

            }
        });
        dialog.show();
        Paper.book().write(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, false);
    }

    /**
     * 开启读秒倒计时
     */
    public void startCountDown() {
        if (pollTask != null) {
            pollTask.cancel();
            pollTask =null;
        }
        pollTask = new PollTask();
        //schedule 计划安排，时间表
        if (pollTimer == null) {
            pollTimer = new Timer();
        }
        if(pollTimer!=null){
            pollTimer.schedule(pollTask, 500, 1);
        }
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
                            pollTask=null;
                        }
                        if (LoginEntity.getIsLogin()) {
                            //上传阅读时间
                            commitReadTime();
                            getRewardOf30S();
                        }
                    } else {
                        mViewBinding.circlePercentProgress.setPercentage((INTERVAL - countDownTime) * 1000 / INTERVAL);
                    }
                }
            });
        }
    }

    /**
     * 上传阅读时间
     */
    public void commitReadTime() {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", dataBean.getId() + "");
        hashMap.put("type", "video");
        hashMap.put("starttime", (System.currentTimeMillis() - INTERVAL )/1000 + "");

        if (mPresenter != null)
            mPresenter.leavePageCommit(hashMap, mActivity);
    }

    /**
     * 阅读30s随机奖励
     */
    public void getRewardOf30S() {
        if (mPresenter == null || this.isDestroyed()) {
            return;
        }
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", dataBean.getId() + "");
        hashMap.put("type", "video");
        hashMap.put("starttime", startReadTime + "");
        hashMap.put("read_time", System.currentTimeMillis() / 1000 + "");

        mPresenter.getRewardOf30second(hashMap, mActivity);
    }

    private void startCircleCountDown() {
        countDownTime = INTERVAL;
        boolean isShowGuide = book().read(PagerCons.KEY_ARTICLE_IS_SHOW_GUIDE, true);
        if (LoginEntity.getIsLogin()) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_arcticle_circle_reward);
            drawable.setBounds(0, 0, 70, 80);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("");

            if (isShowGuide) {
                showGuideDialog();
            } else {
                mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
                startCountDown();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pollTimer!=null){
            pollTimer.cancel();
            pollTimer=null;
        }

    }

    @Override
    public void onBackPressed() {

        if (Jzvd.CURRENT_JZVD != null) {
            if (Jzvd.CURRENT_JZVD.screen == Jzvd.SCREEN_FULLSCREEN) {
                Jzvd.backPress();
            } else {
                Jzvd.releaseAllVideos();
            }
        } else {
            super.onBackPressed();
        }
    }
}

package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.juejinchain.android.R;
import com.juejinchain.android.config.TTAdManagerHolder;
import com.juejinchain.android.databinding.FragmentVideoPagerBinding;
import com.juejinchain.android.event.TabSelectedEvent;
import com.juejinchain.android.event.TextSettingEvent;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.home.activity.VideoDetailActivity;
import com.juejinchain.android.module.home.adapter.VideoPagerAdapter;
import com.juejinchain.android.module.home.entity.ChannelEntity;
import com.juejinchain.android.module.home.entity.HomeListEntity;
import com.juejinchain.android.module.home.interf.OnItemClickListener;
import com.juejinchain.android.module.home.presenter.VideoPagerPresenter;
import com.juejinchain.android.module.home.presenter.impl.VideoPagerPresenterImpl;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.share.dialog.ShareDialog;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.utils.ClickUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;


public class VideoPagerFragment extends BaseFragment<VideoPagerPresenterImpl, FragmentVideoPagerBinding> implements VideoPagerPresenter.View {


    VideoPagerAdapter videoPagerAdapter;
    List<Object> mData = new ArrayList<>();
    private String name;
    private int id;

    //是否第一次拉取数据
    boolean isFirstGetData = true;
    ShareDialog mShareDialog;
    /**
     * 每次请求的广告返回个数，最多支持3个
     */
    final int AD_Per_ReqCount = 3;
    int loadAdCount = 1;
    private List<TTFeedAd> mTTFeedAds = new ArrayList<>();     //显示的广告
    private List<TTFeedAd> mLastRemainAd = new ArrayList<>();       //上次加载未显示的广告

    final int AD_Interval = 3;        //间隔多少行显示一条广告
    int adLastPos = AD_Interval - 1;    //广告插入位置
    private boolean isRefresh;
    private TextView giveLikeTv;
    private int clickPosition = 0;
    public int firstVisibleItem, lastVisibleItem, visibleCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        View view = inflater.inflate(R.layout.fragment_video_pager, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    public static Fragment newInstance(ChannelEntity videoChannelEntity) {

        VideoPagerFragment fragment = new VideoPagerFragment();
        Bundle args = new Bundle();
        args.putString("name", videoChannelEntity.getName());
        args.putInt("id", videoChannelEntity.getId());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        id = bundle.getInt("id");

        videoPagerAdapter = new VideoPagerAdapter(mActivity, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mViewBinding.recyclerview.setLayoutManager(linearLayoutManager);
        mViewBinding.recyclerview.setAdapter(videoPagerAdapter);
        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
//        if(NetUtil.isWifi(context)){  //wifi条件下开启自动播放
//            loadListener(linearLayoutManager);
//        }

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
//                        JzvdStd.goOnPlayOnPause();
                    } catch (Exception e) {

                    }

                }
            }


        });
    }

    private void autoPlayVideo(RecyclerView view) {
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

        for (int i = 0; i < visibleCount; i++) {
            if (layoutManager != null && layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(R.id.jzvdPlayer) != null) {
                JzvdStd jzplayer = (JzvdStd) layoutManager.getChildAt(i).findViewById(R.id.jzvdPlayer);
                Rect rect = new Rect();
                jzplayer.getLocalVisibleRect(rect);
                int videoheight = jzplayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (Jzvd.CURRENT_JZVD == null || Jzvd.CURRENT_JZVD.state != Jzvd.STATE_PLAYING && Jzvd.CURRENT_JZVD.state != Jzvd.STATE_PREPARING) {
                        jzplayer.setVisibility(View.VISIBLE);
                        jzplayer.startVideo();
                    }

                    return;
                }

            }
        }
        Jzvd.releaseAllVideos();
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
    private static final int STOP_SMART_REFRESH = 10001;

    public void loadDate(boolean isRef) {
        myHandler.removeMessages(STOP_SMART_REFRESH);
        myHandler.sendEmptyMessageDelayed(STOP_SMART_REFRESH, 10000);
        isRefresh = isRef;

        Map<String, String> param = new HashMap<>();

        param.put("column_id", id + "");

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
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position == MainActivity.HOME && event.channelName.equals("视频")) {
            isRefresh = true;
            scrollToTopAndRefresh();
        }
    }

    private void scrollToTopAndRefresh() {
        mViewBinding.recyclerview.scrollToPosition(0);
//        loadDate(true);
        mViewBinding.refreshView.autoRefresh();
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        List<HomeListEntity.DataBean> cacheData = Paper.book().read(PagerCons.KET_HOME_VIDEO_LASTTIME_DATA + name);
        mViewBinding.loadingView.showLoading();
        if (cacheData != null) {
            mViewBinding.loadingView.showContent();
            mData.addAll(cacheData);
            videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        } else {
            loadDate(true);
        }
    }

    @Override
    public void initData() {

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

                HomeListEntity.DataBean model = (HomeListEntity.DataBean) mData.get(position);
                if (view.getId() == R.id.ll_video_detail) {

                    clickPosition = position;
                    Jzvd.goOnPlayOnPause();

//                    String url = RetrofitManager.WEB_URL_COMMON + "/VideoDetail/" + model.getId();
//                    BridgeWebViewActivity.intentMe(context, url);
                    VideoDetailActivity.intentMe(mActivity, model);

                } else {
                    if (LoginEntity.getIsLogin()) {
                        MobclickAgent.onEvent(context, EventID.HOMEPAGE_VIDEO_SHARE);   //首页-视频分享-埋点
                        shareInfo.setUrl_type(ShareInfo.TYPE_VIDEO);
                        shareInfo.setUrl_path("/VideoDetail/" + model.getId());

                        shareInfo.setType("video");
                        shareInfo.setId(model.getId() + "");

                        shareInfo.setAid(model.getId() + "");

                        if (model != null && model.getImg_url().size() != 0) {
                            shareInfo.setSharePicUrl(model.getImg_url().get(0));
                        }

                        mShareDialog = new ShareDialog(getActivity(), shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                        mShareDialog.show();
                    } else {
                        Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                        getActivity().startActivity(intent);
                        return;
                    }
                }
            }

            @Override
            public void tatistical(int id, int type) {

            }
        });


    }

    @Override
    public void getVideoListSuccess(Serializable serializable) {
        mViewBinding.refreshView.finishRefresh();
        mViewBinding.refreshView.finishLoadMore();
        HomeListEntity homeListEntity = (HomeListEntity) serializable;
        boolean isTop;

        if (homeListEntity.getData() == null || homeListEntity.getData().size() == 0) {
            mViewBinding.loadingView.showEmpty();
            return;
        }

        for ( HomeListEntity.DataBean bean : homeListEntity.getData()){
            if(bean.getType().equals("ad")){
                homeListEntity.getData().remove(bean);
            }
        }


        mViewBinding.loadingView.showContent();
        if (isRefresh) {
            mData.addAll(0, homeListEntity.getData());
            isTop = true;
        } else {
            isTop = false;
            mData.addAll(homeListEntity.getData());
        }

        if (mData.size() > AD_Interval && homeListEntity.getData().size() != 0) {
            loadListAd(homeListEntity.getData().size(), isTop); //加载广告
        }

        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));

        isFirstGetData = false;
        if (homeListEntity.getData() != null) {
            Paper.book().write(PagerCons.KET_HOME_VIDEO_LASTTIME_DATA + name, homeListEntity.getData());
        }
    }

    @Override
    public void getLikeSuccess(Serializable serializable) {
        HomeListEntity.DataBean model = (HomeListEntity.DataBean) mData.get(clickPosition);

        model.setDigg_count(model.getDigg_count() + 1);
//        model.is_fabulous = true;
        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
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
     * @param contentCount 本次加载文章条数
     * @param isTop        加到头部还是尾部
     */
    void loadListAd(int contentCount, boolean isTop) {
        int adPerCount = book().read(PagerCons.KEY_INTERVAL_HOME_PAGE_AD, 2);
        /**
         * 头条联盟信息流广告大图的尺寸为（690px*388px）、小图尺寸为（228px*150px）、组图为（228px*150px*3）
         */
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(TTAdManagerHolder.POS_ID_VIDEO)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(690, 388) //加载返回类型是随机的，通过参数也改不了
                .setAdCount(adPerCount) // 可选参数，针对信息流广告设置每次请求的广告返回个数，最多支持3个
                .build();

        //调用feed广告异步请求接口
        MainActivity.mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {

//                TToast.show(getContext(), "穿山甲异常："+message);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                if (ads == null || ads.isEmpty()) {
//                    TToast.show(getContext(), "on FeedAdLoaded: ad is null!");
                    return;
                }


//                ads = ADUtils.removeDuplicate(ads, mTTFeedAds);
                //去除重复为空时，再加载两次
                if (ads.size() == 0 && loadAdCount < 3) {
                    loadAdCount++;
                    loadListAd(contentCount, isTop);
                    return;
                }
                loadAdCount = 1;

                //一页多条时循环
                for (int pos = 0; pos < ads.size(); pos++) {
                    TTFeedAd ad = ads.get(pos);
                    //非视频不加载
//                    if (ad.getImageMode() != TTAdConstant.IMAGE_MODE_VIDEO) continue;

//                    while (mData.get(adLastPos) instanceof TTFeedAd){
//                        adLastPos += AD_Interval;     //正常往后加载用这个
//                        if (adLastPos >= mData.size()){
////                            adLastPos = mData.size()-1;
////                            break;  //加到尾部时
//                            return;
//                        }
//                    }

//                    mData.set(adLastPos, ad);

                    float percent = 2.0f / 3;


                    if (isTop) {
                        //下拉刷新
                        if (pos == 0) {
                            percent = 1.0f / 3;
                        } else if (pos == 1) {
                            percent = 2.0f / 3; //第二条时
                        } else if (pos == 2) {
                            percent = 3 / 3; //第二条时
                        }

                    } else {
                        //上拉加载
                        if (pos == 0) {
                            percent = 0;
                        } else if (pos == 1) {
                            percent = 1.0f / 3; //第二条时
                        } else if (pos == 2) {
                            percent = 2.0f / 3; //第二条时
                        }
                    }

                    if (ads.size() == 1) percent = 0.5f; //只有一条时

                    int insertPosition = (int) (isTop ? contentCount * percent : (mData.size() - contentCount * percent));
//                    mData.set(insertPosition, ad);
//                    mTTFeedAds.add(ad);

                    if (!mData.contains(ad)) {
                        mData.add(insertPosition, ad); //add加载会出现连续两条广告的情况
                        videoPagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
                    }


//                    if (percent == 2.0f / 3) //只有一条或第二条时
//                        break; //
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextSizeSetting(TextSettingEvent event) {
        videoPagerAdapter.setTextSizeLevel(event.getLevel());
    }
}

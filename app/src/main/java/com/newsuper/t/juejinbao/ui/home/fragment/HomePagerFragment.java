package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomePagerBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.TabSelectedEvent;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.activity.PictureViewPagerActivity;
import com.newsuper.t.juejinbao.ui.home.activity.VideoListActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeHotPointEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.presenter.HomePagerPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.HomePagerPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.PreLoadUtils;
import com.newsuper.t.juejinbao.utils.AnimUtil;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.TimeUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

public class HomePagerFragment extends BaseFragment<HomePagerPresenterImpl, FragmentHomePagerBinding> implements HomePagerPresenter.HomePagerPresenterView {
    //    private static ChannelEntity mChannel;
    List<Object> mData = new ArrayList<>();
    private HomePagerAdapter homePagerAdapter;

    // 是否下拉刷新
    boolean isRefresh = true;

    //是否第一次拉取数据
    boolean isFirstGetData = true;
    private String name;
    private int id;

    public final static int REQUEST_CODE_VIDEO_LIST = 20001;

    /**
     * 每次请求的广告返回个数，最多支持3个
     */
    final int AD_Per_ReqCount = 2;

    //private List<TTFeedAd> mTTFeedAds = new ArrayList<>();     //显示的广告
  //  private List<TTFeedAd> mLastRemainAd = new ArrayList<>();       //上次加载未显示的广告

    final int AD_Interval = 4;        //间隔多少行显示一条广告
    int adLastPos = AD_Interval - 1;    //广告插入位置
    private String TAG = "zzz";

    //预加载工具
    private PreLoadUtils preLoadUtils;

    private NativeExpressAD mADManager;

    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();

    private List<NativeExpressADView> mAdViewList = new ArrayList<>();
    private List<NativeExpressADView> mShowAdList = new ArrayList<>();

    public static final int AD_COUNT = 3;    // 加载广告的条数，取值范围为[1, 10]
    public static int FIRST_AD_POSITION = 1; // 第一条广告的位置
    public static int ITEMS_PER_AD = 10;     // 每间隔10个条目插入一条广告
    private int adPerCount;
    private Integer adType;

    private int errorTime = 0; //拉取失败次数


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_home_pager, container, false);
        return view;
    }

    public static Fragment newInstance(ChannelEntity model) {

        Bundle args = new Bundle();
        HomePagerFragment fragment = new HomePagerFragment();
        args.putString("name", model.getName());
        args.putInt("id", model.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
//        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);

        Bundle bundle = getArguments();
//        mChannel = (ChannelEntity) bundle.getSerializable("model");
        name = bundle.getString("name");
        id = bundle.getInt("id");

        adPerCount = book().read(PagerCons.KEY_INTERVAL_HOME_PAGE_AD, 2);

        ADConfigEntity adConfigEntity = book().read(PagerCons.KEY_AD_CONFIG);
        if(adConfigEntity!=null){
            adType  = adConfigEntity.getData().getAd_type2().getPlatform();
        }else {
            adType = 1;
        }

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //设置分割线
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line));
        mViewBinding.recy.addItemDecoration(itemDecoration);

        mViewBinding.recy.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);


        mViewBinding.recy.setLayoutManager(linearLayoutManager);

        preLoadUtils = new PreLoadUtils(mViewBinding.recy, mData, 4, new PreLoadUtils.PreLoadMoreListener() {
            @Override
            public void preLoad() {
                loadDate(false);
            }
        });

        homePagerAdapter = new HomePagerAdapter(mActivity, mData);
        mViewBinding.recy.setAdapter(homePagerAdapter);
        homePagerAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        mViewBinding.smallLabel.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                if (errorTime > 2) {
                    errorTime--;
                    mViewBinding.smallLabel.finishRefresh();
                    mViewBinding.tvRecommend.setText("服务器繁忙，请休息会再来~");
                    AnimUtil.animHeightToView(getActivity(), mViewBinding.tvRecommend, true, 500);
                    return;
                }

                Log.e("zy", "启动刷新");
                loadDate(true);
            }
        });

        mViewBinding.smallLabel.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                loadDate(false);
            }
        });
        mViewBinding.loadingView.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(context)) {
                    mViewBinding.loadingView.showLoading();
                    loadDate(true);
                } else {
                    ToastUtils.getInstance().show(mActivity, "请连接网络后重试");
                }

            }
        });

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
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(getActivity(), GuideLoginActivity.class));
                    return;
                }

                if (mData.get(position) instanceof HomeListEntity.DataBean) {

                    HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) mData.get(position);
                    Log.i(TAG, "onItemClick: " + position + "------" + dataBean.toString());
//                    view.findViewById(R.id.tv_title).setEnabled(false);
                    dataBean.setSelected(true);
                    homePagerAdapter.notifyItemChanged(position);
                    if (dataBean.getShowtype() == 5) {
                        scrollToTopAndRefresh();
                    } else if (dataBean.getShowtype() == 8) {  //点击短视频标题
//                        String url = RetrofitManager.WEB_URL_COMMON + "/VideoDetail/" + dataBean.getId();
//                        BridgeWebViewActivity.intentMe(context, url);

//                        VideoDetailActivity.intentMe(mActivity, dataBean);

                        VideoListActivity.intentMe(mActivity, dataBean, REQUEST_CODE_VIDEO_LIST);

                    } else {
                        if (dataBean.getType().equals("picture")) {
//                            Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                            intent.putExtra("id", dataBean.getId());
//                            intent.putExtra("tabId", 0);
//                            getActivity().startActivity(intent);
                            PictureViewPagerActivity.intentMe(getActivity(), dataBean.getId(), 0);
                            return;
                        }
//                        Intent intent = new Intent(mActivity, BridgeWebViewActivity.class);
                        //原生详情
                        /**
                         * 1. 判断redirect_url是否存在，
                         * 存在则判断链接类型是否时http的如果是http的链接
                         * 跳转到  /browser/文章id
                         * 非http链接则判断是否是带#，例如#/burst_rich 跳转到暴富秘籍， 则直接去除#用jsBridge方法处理跳转到对应的路由
                         *
                         * 2. 不存在则跳转到文章详情/ArticleDetails/文章id
                         */
                        String url;
                        if (dataBean.getOther() == null || TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {

//                            url = RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + dataBean.getId();
//                            BridgeWebViewActivity.intentMe(context, url);
                            Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
                            intent.putExtra("id", String.valueOf(dataBean.getId()));
                            getActivity().startActivity(intent);

                        } else {
                            if (dataBean.getOther().getRedirect_url().contains("http")) {


//                                url = RetrofitManager.WEB_URL_COMMON + "/browser/" + dataBean.getId();
                                url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ACTICLE_TOP + dataBean.getId();

                            } else {
//                                if (dataBean.getOther().getRedirect_url().contains("#")) {
//
//                                    url = RetrofitManager.WEB_URL_COMMON + deleteString0(dataBean.getOther().getRedirect_url(), '#');
//
//                                } else {
//
//                                }
                                url = dataBean.getOther().getRedirect_url();
                            }
                            BridgeWebViewActivity.intentMe(context, url);
                        }

                        try {
                            //阅读记录数据缓存
                            ArrayList<HomeListEntity.DataBean> dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                            if (dataBeanArrayList == null) {
                                dataBeanArrayList = new ArrayList<>();
                            }

//                             只缓存最近30条记录
                            if (dataBeanArrayList.size() > 30) {
                              int a =   dataBeanArrayList.size() - 30;
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

            }
        });
        mViewBinding.recy.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.jzvdPlayer);
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
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        List<HomeListEntity.DataBean> cacheData = Paper.book().read(PagerCons.KEY_HOME_CACHE_LAST_TIME + name);
        mViewBinding.loadingView.showLoading();
        if (false) {
            mViewBinding.loadingView.showContent();
            mData.addAll(cacheData);
            homePagerAdapter.notifyDataSetChanged();
        } else {
            loadDate(true);
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == STOP_SMART_REFRESH) {
                mViewBinding.smallLabel.finishRefresh();
                mViewBinding.smallLabel.finishLoadMore();
            }
            return false;
        }
    });
    private static final int STOP_SMART_REFRESH = 10001;

    public void loadDate(boolean isRef) {
        if (myHandler != null) {
            myHandler.removeMessages(STOP_SMART_REFRESH);
            myHandler.sendEmptyMessageDelayed(STOP_SMART_REFRESH, 10000);
        }

        isRefresh = isRef;
        Map<String, String> param = new HashMap<>();

        param.put("column_id", id + "");

        param.put("type", "article");


        String str = System.currentTimeMillis() + "";//待处理字符串 取时间戳后四位传给后台

        if (Paper.book().read(PagerCons.HOME_TIME) != null) {
            param.put("ua", Paper.book().read(PagerCons.HOME_TIME));
        } else {
            param.put("ua", str);
            Paper.book().write(PagerCons.HOME_TIME, str);
        }

        if (isFirstGetData) {
            param.put("is_first", 1 + "");
        } else {
            param.put("isRefresh", isRef ? "1" : "0");
        }
        if (mData.size() == 0) {
//            if(mViewBinding.imgLoadingBg.getVisibility()==View.GONE){
////                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
//            }

        }
        mPresenter.getNewsList(param, mActivity);


    }

    @Override
    public void initData() {

//        MyToast.showToast(entity.getName());
    }


    @Override
    public void getNewsList(Serializable serializable) {
        //预加载的页码
        preLoadUtils.loaded();

        errorTime = 0;
        boolean isTop = false;
//        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        mViewBinding.smallLabel.finishRefresh();
        mViewBinding.smallLabel.finishLoadMore();
        HomeListEntity newsEntity = (HomeListEntity) serializable;
        if (newsEntity.getData() != null) {
            //去掉缓存
//            Paper.book().write(PagerCons.KEY_HOME_CACHE_LAST_TIME + name , newsEntity.getData());
        }
        if (isRefresh) {
            if (newsEntity.getData().size() > 0) {
                if (!isFirstGetData) {
                    //除开第一次拉取以外 都要先移除上次观看位置
                    Iterator<Object> iterator = mData.iterator();
                    while (iterator.hasNext()) {
                        Object next = iterator.next();
                        if (next instanceof HomeListEntity.DataBean) {
                            HomeListEntity.DataBean bean = (HomeListEntity.DataBean) next;
                            if (bean.getShowtype() == 5) {
                                iterator.remove();
                            }
                            //下拉刷新后移除置顶文章
                            if ("置顶".equals(bean.getMark())) {
                                iterator.remove();
                            }
                        }

                        //把今日热点删除掉
                        if (next instanceof HomeHotPointEntity) {
                            iterator.remove();
                        }
                    }
                    //假如位置标记
                    HomeListEntity.DataBean entity = new HomeListEntity.DataBean();
                    entity.setShowtype(5);
                    newsEntity.getData().add(entity);
                }


                mData.addAll(0, newsEntity.getData());
                addHomeHotPoint(newsEntity);
            }

            // 提示更新数量
            if (!isFirstGetData) {
                startRecommend(newsEntity.getData().size() - 1, newsEntity.getData().size() != 0);
            }

            isTop = true;
        } else {
            isTop = false;
            mData.addAll(newsEntity.getData());
        }
        isFirstGetData = false;
        homePagerAdapter.notifyDataSetChanged();
        if (mData.size() > 0) {
            mViewBinding.loadingView.showContent();
        } else {
            mViewBinding.loadingView.showEmpty();
        }

        //adLastPos/AD_Interval < AD_MAX_SIZE  mTTFeedAds.size() < AD_MAX_SIZE

        //关闭信息流广告

        HashMap<String, Integer> auditMap = Paper.book().read(PagerCons.KEY_AUDIT_MAP);
        if (auditMap == null) {
            auditMap = new HashMap();
        }
        int isCloseOpenAD = auditMap.get(Constant.KEY_AD_CONTROL) == null ? 0 : auditMap.get(Constant.KEY_AD_CONTROL);


        if (mData.size() > AD_Interval && id != ChannelEntity.ID_JJB && isCloseOpenAD != 1) {

            if (adType == 0) {
                initCSJExPressAD(isTop,newsEntity);
            } else if(adType==1){
                initNativeExpressAD(newsEntity.getData().size());
            }else {
                initCSJExPressAD(isTop,newsEntity);
            }
        }


    }

   public void initCSJExPressAD(boolean isTop, HomeListEntity newsEntity){
        final boolean isTop1 = isTop;

//        if (mLastRemainAd.size() > 0) {
//            mViewBinding.recy.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    insertAdView(newsEntity.getData().size() - 1, isTop1, mLastRemainAd);
//                    mLastRemainAd.clear();
//                }
//            }, 500);
//        } else {
//            loadListAd(newsEntity.getData().size(), isTop); //加载广告
//        }
    }

    private int mMutiType = 3;

    private int getMutiType() {
        int all = 4;
        int type = mMutiType++ % all;
        if (mMutiType >= all) {
            mMutiType = 0;
        }
        return type;
    }

    /**
     * 清除首页Tab小红点
     *
     * @param serializable
     */
    @Override
    public void clearUnreadMsgSuccess(Serializable serializable) {
//            EventBus.getDefault().post(new ClearHomeTabUnreadMsgEvent(id));
    }

    /**
     * 增加今日热点条目
     */
    private void addHomeHotPoint(HomeListEntity newsEntity) {
        for (int i = 0; i < newsEntity.getData().size(); i++) {
            HomeListEntity.DataBean bean = newsEntity.getData().get(i);
            if (bean.getHot_point_lists() != null && bean.getHot_point_lists().size() > 0) {
                if ("置顶".equals(bean.getMark()) && bean.getHot_point_lists().size() >= 3) {
                    HomeHotPointEntity homeHotPointEntity = new HomeHotPointEntity();
                    long updateTime = newsEntity.getTime() * 1000L;
                    homeHotPointEntity.setUpdateTime(TimeUtils.getTimeString("HH:mm", updateTime));
                    homeHotPointEntity.setId_1(bean.getHot_point_lists().get(0).getId() + "");
                    homeHotPointEntity.setImgUrl_1(bean.getHot_point_lists().get(0).getImg_url().get(0));
                    homeHotPointEntity.setTitle_1(bean.getHot_point_lists().get(0).getTitle());
                    homeHotPointEntity.setComment_1(bean.getHot_point_lists().get(0).getComment_count() + " 评论");
                    homeHotPointEntity.setId_2(bean.getHot_point_lists().get(1).getId() + "");
                    homeHotPointEntity.setTitle_2(bean.getHot_point_lists().get(1).getTitle());
                    homeHotPointEntity.setComment_2(bean.getHot_point_lists().get(1).getComment_count() + " 评论");
                    homeHotPointEntity.setId_3(bean.getHot_point_lists().get(2).getId() + "");
                    homeHotPointEntity.setTitle_3(bean.getHot_point_lists().get(2).getTitle());
                    homeHotPointEntity.setComment_3(bean.getHot_point_lists().get(2).getComment_count() + " 评论");
                    mData.add(i + 1, homeHotPointEntity);
                } else {
                    HomeHotPointEntity homeHotPointEntity = new HomeHotPointEntity();
                    long updateTime = newsEntity.getTime() * 1000L;
                    homeHotPointEntity.setUpdateTime(TimeUtils.getTimeString("HH:mm", updateTime));
                    homeHotPointEntity.setId_1(bean.getHot_point_lists().get(0).getId() + "");
                    homeHotPointEntity.setImgUrl_1(bean.getHot_point_lists().get(0).getImg_url().get(0));
                    homeHotPointEntity.setTitle_1(bean.getHot_point_lists().get(0).getTitle());
                    homeHotPointEntity.setComment_1(bean.getHot_point_lists().get(0).getComment_count() + " 评论");
                    homeHotPointEntity.setTop(true);
                    mData.add(0, homeHotPointEntity);
                }
                break;
            }
        }
    }

    private void scrollToTopAndRefresh() {
        mViewBinding.recy.scrollToPosition(0);
//        loadDate(true);
        mViewBinding.smallLabel.autoRefresh();
    }


    @Override
    public void showError(String msg) {
//        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
//        mViewBinding.loadingView.showError(msg);
        mViewBinding.smallLabel.finishRefresh();
        mViewBinding.smallLabel.finishLoadMore();


    }

    @Override
    public void getNewsListError(String msg) {
        errorTime++;
        mViewBinding.smallLabel.finishRefresh();
        mViewBinding.smallLabel.finishLoadMore();
    }

    private void startRecommend(int count, boolean newest) {
        if (newest)
            //掘金宝推荐引擎有1条更新
            mViewBinding.tvRecommend.setText(new StringBuilder("掘金宝推荐引擎有").append(count).append("条更新"));
        else
            mViewBinding.tvRecommend.setText("暂无更新，请休息会再来~");

        AnimUtil.animHeightToView(getActivity(), mViewBinding.tvRecommend, true, 500);

    }

    /**
     * Reselected Tab 刷新
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        Log.e("zy", "onTabSelectedEvent");
        if (event.position == JunjinBaoMainActivity.HOME && event.channelName.equals(name)) {
            Log.e("zy", "刷新首页");
            isRefresh = true;
            scrollToTopAndRefresh();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        mHandler.removeCallbacksAndMessages(null);
//        mHandler = null;
        preLoadUtils.destory();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }
    }

    /**
     * 加载穿山甲信息流广告
     *
     * @param contentCount 本次加载文章条数
     * @param isTop 加到头部还是尾部
     */
    int loadAdCount = 1;

//    void loadListAd(int contentCount, boolean isTop) {
//        int adPerCount = book().read(PagerCons.KEY_INTERVAL_HOME_PAGE_AD, 2);
//        Log.i(TAG, "loadListAd: 缓存广告次数" + adPerCount);
//        /**
//         * 头条联盟信息流广告大图的尺寸为（690px*388px）、小图尺寸为（228px*150px）、组图为（228px*150px*3）
//         */
//        //feed广告请求类型参数
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(TTAdManagerHolder.POS_ID)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(640, 320) //加载返回类型是随机的，通过参数也改不了
//                .setAdCount(adPerCount) // 可选参数，针对信息流广告设置每次请求的广告返回个数，最多支持3个
//                .build();
//
//        //调用feed广告异步请求接口
//        JunjinBaoMainActivity.mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                Log.w(TAG, "onError: 穿山甲异常：" + message + "，code=" + code);
////                TToast.show(getContext(), "穿山甲异常："+message);
//                if (code == 20001 && loadAdCount < 2) { //没有广告
//                    loadAdCount++;
//                    loadListAd(contentCount, isTop);
//                }
//            }
//
//            @Override
//            public void onFeedAdLoad(List<TTFeedAd> ads) {
//                if (ads == null || ads.isEmpty()) {
////                    TToast.show(getContext(), "on FeedAdLoaded: ad is null!");
//                    Log.w(TAG, "onFeedAdLoad: 穿山甲信息流接口返回为 null");
//                    return;
//                }
////                L.w(TAG, "onFeedAdLoad: 本次信息流返回= "+ads.size() + "条 ,first= "+ads.get(0).getTitle());
////                for (TTFeedAd ad : ads)
////                    Log.w(TAG, "onFeedAdLoad: 未去重前广告= " + ad.getTitle());
////                List<TTFeedAd> availableAds = ADUtils.removeDuplicate(ads, mTTFeedAds);
//                List<TTFeedAd> availableAds = ads;
//                Log.w(TAG, "onFeedAdLoad: 本次拉取广告数量= " + ads.size() + "---" + "文章数量" + contentCount
//                );
//                for (TTFeedAd ad : availableAds)
//
//
//                    //去除重复为空时，再加载两次
//                    if (availableAds.size() == 0 && loadAdCount < 3) {
//                        loadAdCount++;
//                        loadListAd(contentCount, isTop);
//                        return;
//                    }
//                loadAdCount = 1;
//
//                insertAdView(contentCount, isTop, availableAds);
//            }
//        });
//    }
//
//    //插入广告
//    private void insertAdView(int contentCount, boolean isTop, List<TTFeedAd> ads) {
//        List<TTFeedAd> temps = new ArrayList<>();
//        temps.addAll(ads);
//        //一页多条时循环
//        for (int pos = 0; pos < ads.size(); pos++) {
//            TTFeedAd ad = ads.get(pos);
//            //如视频有问题，不加载
////                    if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) continue;
//
////                    while (mData.get(adLastPos) instanceof TTFeedAd){
////                        adLastPos += AD_Interval;     //正常往后加载用这个
////                        if (adLastPos >= mData.size()){
//////                            adLastPos = mData.size()-1;
//////                            break;  //加到尾部时
////                            return;
////                        }
////                    }
////                    mData.set(adLastPos, ad);
//
//            float percent = 2.0f / 3;
//
////            if (isTop) {
//            //下拉刷新
//            if (pos == 0) {
//                percent = 1.0f / 3;
//            } else if (pos == 1) {
//                percent = 2.0f / 3; //第二条时
//            } else if (pos == 2) {
//                percent = 3 / 3; //第二条时
//            }
//
////            } else {
////                //上拉加载
////                if (pos == 0) {
////                    percent = 0;
////                } else if (pos == 1) {
////                    percent = 1.0f / 3; //第二条时
////                } else if (pos == 2) {
////                    percent = 2.0f / 3; //第二条时
////                }
////            }
//
//            if (ads.size() == 1) percent = 0.5f; //只有一条时
//
//
//            int insertPosition = (int) (isTop ? contentCount * percent : (mData.size() - contentCount * percent));
//
//
////            int insertPosition = (int) (contentCount * percent);
////            mData.set(insertPosition, ad);
//            if (!mData.contains(ad)) {
//                mData.add(insertPosition, ad); //add加载会出现连续两条广告的情况
//                homePagerAdapter.notifyDataSetChanged();
//            }
//
//            mTTFeedAds.add(ad);
//            temps.remove(ad);
////            if (percent == 2.0f / 3) //只有一条或第二条时
////                break; //
//        }
//
//        if (temps.size() > 0) {
//            mLastRemainAd = temps; //记录没显示完的
//            //因为ArrayList为无序的，再次去重
//            for (int i = 0; i < mLastRemainAd.size(); i++) {
//                TTFeedAd tf = mLastRemainAd.get(i);
//                for (TTFeedAd ad : mTTFeedAds) {
//                    if (ad.getTitle().equals(tf.getTitle())) {
//                        mLastRemainAd.remove(i);
//                        i--;
//                        break;
//                    }
//                }
//            }
//        }
//
//
//    }

    /**
     * 删除方法一
     *
     * @param str
     * @param delChar
     * @return
     */
    public static String deleteString0(String str, char delChar) {
        String delStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != delChar) {
                delStr += str.charAt(i);
            }
        }
        return delStr;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextSizeSetting(TextSettingEvent event) {
        homePagerAdapter.setTextSizeLevel(event.getLevel());
    }


    private void initNativeExpressAD(int contentCount) {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
//        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, (int) getResources().getDimension(R.dimen.ws100dp)); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(getContext(), adSize, GDTHolder.APPID, GDTHolder.NATIVE_ID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {  //拉取广告成功
                //去除重复广告
                for (int i = 0; i < adList.size(); i++) {
                    NativeExpressADView ad = adList.get(i);
                    //mShowAdList：所有已经显示的广告
                    if (mShowAdList.size() == 0) {
                        mAdViewList.add(ad);
                    } else {
                        int size = mShowAdList.size();
                        //拉取的广告和已经显示的广告都不相同才能加入广告池
                        boolean canAdd = true;
                        for (int j = 0; j < size; j++) {
                            if (ad.getBoundData().equalsAdData(mShowAdList.get(j).getBoundData())) {
                                Log.v("gdt_ad", "相同 - ad:" + ad.getBoundData().getTitle() + ",mAdViewList:" + mShowAdList.get(j).getBoundData().getTitle());
                                canAdd = false;
                                break;
                            }
                        }
                        if (canAdd) {
                            mAdViewList.add(ad);
                        }
                    }
                }

                try {
                    //从广告池取 adPerCount 条广告出来展示
                    List<NativeExpressADView> adListNew = new ArrayList<>();
                    int size = mAdViewList.size() >= adPerCount ? adPerCount : mAdViewList.size();
                    for (int i = 0; i < size; i++) {
                        adListNew.add(mAdViewList.remove(0));
                    }
                    //计算广告位置
                    calcAdPos(adListNew, contentCount);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("gdt_ad", "IndexOutOfBoundsException");
                }
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
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKTIMES);
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKUSERS);
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

        //一次拉取10条广告并保存，如果还有广告池就直接使用广告池
        if (mAdViewList.size() >= adPerCount) {
            List<NativeExpressADView> adList = new ArrayList<>();
            for (int i = 0; i < adPerCount; i++) {
                adList.add(mAdViewList.remove(0));
            }
            calcAdPos(adList, contentCount);
        } else {    //再次拉取10条广告
            mADManager.loadAD(10);
        }
    }

    //计算广告位置
    private void calcAdPos(@NotNull List<NativeExpressADView> adList, int contentCount) {
        for (int i = 0; i < adList.size(); i++) {
            if (i >= adPerCount) {
                break;
            }
            NativeExpressADView ad = adList.get(i);
//            float percent = 1.0f / 5;
//            if (i == 0) {
//                percent = 1.0f / 5;
//            } else if (i == 1) {
//                percent = 1.0f / 2; //第二条时
//            } else if (i == 2) {
//                percent = 4.0f /5; //第二条时
//            }
            float percent = 2.0f / 3;
            if (i == 0) {
                percent = 1.0f / 3;
            } else if (i == 1) {
                percent = 2.0f / 3; //第二条时
            } else if (i == 2) {
                percent = 3 / 3; //第二条时
            }
            if (adList.size() == 1) percent = 0.5f; //只有一条时
            int insertPosition = (int) (isRefresh ? contentCount * percent : (mData.size() - contentCount * percent));
            Log.e("gdt_ad_size", "adList:" + adList.size() + ",contentCount:" + contentCount + ",insertPosition:" + insertPosition);
            if (!mData.contains(ad)) {
                mData.add(insertPosition, ad); //add加载会出现连续两条广告的情况
                homePagerAdapter.notifyDataSetChanged();
                mShowAdList.add(ad); // 把每个广告在列表中位置记录下来
                if (mShowAdList.size() > 10) {
                    mShowAdList.remove(0);
                }
                mAdViewList.remove(ad);
            }
        }
    }

    private String getAdInfo(NativeExpressADView nativeExpressADView) {
        AdData adData = nativeExpressADView.getBoundData();
        if (adData != null) {
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("title:").append(adData.getTitle()).append(",")
                    .append("desc:").append(adData.getDesc()).append(",")
                    .append("patternType:").append(adData.getAdPatternType());
            if (adData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                infoBuilder.append(", video info: ")
                        .append(getVideoInfo(adData.getProperty(AdData.VideoPlayer.class)));
            }
            return infoBuilder.toString();
        }
        return null;
    }

    private String getVideoInfo(AdData.VideoPlayer videoPlayer) {
        if (videoPlayer != null) {
            StringBuilder videoBuilder = new StringBuilder();
            videoBuilder.append("state:").append(videoPlayer.getVideoState()).append(",")
                    .append("duration:").append(videoPlayer.getDuration()).append(",")
                    .append("position:").append(videoPlayer.getCurrentPosition());
            return videoBuilder.toString();
        }
        return null;
    }

    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoInit: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoLoading: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
            Log.i(TAG, "onVideoReady: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoStart: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPause: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoComplete: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
            Log.i(TAG, "onVideoError");
        }

        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageOpen");
        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageClose");
        }
    };



}

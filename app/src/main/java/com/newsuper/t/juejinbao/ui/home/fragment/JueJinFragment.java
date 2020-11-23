package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentJuejinBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.ClearHomeTabUnreadMsgEvent;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.TabSelectedEvent;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.activity.PictureViewPagerActivity;
import com.newsuper.t.juejinbao.ui.home.activity.VideoDetailActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.presenter.HomePagerPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.HomePagerPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.utils.AnimUtil;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.GlideImageLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

public class JueJinFragment extends BaseFragment<HomePagerPresenterImpl, FragmentJuejinBinding> implements HomePagerPresenter.HomePagerPresenterView {


    //    private static ChannelEntity mChannel;
    List<Object> mData = new ArrayList<>();
    //公告列表
    List<Object> noticeList = new ArrayList<>();
    //非公告列表
    List<Object> notNoticeList = new ArrayList<>();
    //全部列表
    List<Object> allList = new ArrayList<>();
    private HomePagerAdapter homePagerAdapter;
    //查看全部
    boolean isAll = true;
    boolean isShow = true; //默认是展示状态

    // 是否下拉刷新
    boolean isRefresh = true;

    //是否第一次拉取数据
    boolean isFirstGetData = true;
    private String name;
    private int id;
    List<HomeListEntity.DataBean> headList = new ArrayList<>();

    /**
     * 每次请求的广告返回个数，最多支持3个
     */
    final int AD_Per_ReqCount = 3;

  //  private List<TTFeedAd> mTTFeedAds = new ArrayList<>();     //显示的广告
  //  private List<TTFeedAd> mLastRemainAd = new ArrayList<>();       //上次加载未显示的广告

    final int AD_Interval = 7;        //间隔多少行显示一条广告
    int adLastPos = AD_Interval - 1;    //广告插入位置
    private String TAG = "zzz";

    public int currentTab = 0;

    private int errorTime = 0; //拉取失败次数


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_juejin, container, false);
        return view;
    }

    public static JueJinFragment newInstance(ChannelEntity model) {

        Bundle args = new Bundle();
        JueJinFragment fragment = new JueJinFragment();
        args.putString("name", model.getName());
        args.putInt("id", model.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {

        mViewBinding.tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab == 0){
                    return;
                }
                updateTab(0);
                homePagerAdapter = new HomePagerAdapter(mActivity,allList);
                homePagerAdapter.setJJBChannel(1);
                mViewBinding.recy.setAdapter(homePagerAdapter);
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
                            onListClickHandle(position,mData.get(position));
                        }

                    }

                    @Override
                    public void tatistical(int id, int type) {

                    }
                });

                if (allList.size() == 0) {
                    mViewBinding.rvLoading.showEmpty();
                } else {
                    mViewBinding.rvLoading.showContent();
                }
            }
        });
        mViewBinding.tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab == 1){
                    return;
                }
                updateTab(1);
                homePagerAdapter = new HomePagerAdapter(mActivity,notNoticeList);
                homePagerAdapter.setJJBChannel(1);
                mViewBinding.recy.setAdapter(homePagerAdapter);
                homePagerAdapter.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                        if (notNoticeList.size() < position || position < 0) {
                            homePagerAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (!ClickUtil.isNotFastClick()) {
                            return;
                        }
                        if (mData.get(position) instanceof HomeListEntity.DataBean) {
                            onListClickHandle(position,notNoticeList.get(position));
                        }

                    }

                    @Override
                    public void tatistical(int id, int type) {

                    }
                });

//                homePagerAdapter.notifyDataSetChanged();
                if (notNoticeList.size() == 0) {

                    mViewBinding.rvLoading.showEmpty();
                } else {
                    mViewBinding.rvLoading.showContent();
                }

            }
        });
        mViewBinding.tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab == 2){
                    return;
                }
                updateTab(2);
                homePagerAdapter = new HomePagerAdapter(mActivity,noticeList);
                homePagerAdapter.setJJBChannel(1);
                mViewBinding.recy.setAdapter(homePagerAdapter);
                homePagerAdapter.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                        if (noticeList.size() < position || position < 0) {
                            homePagerAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (!ClickUtil.isNotFastClick()) {
                            return;
                        }
                        if (mData.get(position) instanceof HomeListEntity.DataBean) {
                            onListClickHandle(position,noticeList.get(position));
                        }

                    }

                    @Override
                    public void tatistical(int id, int type) {

                    }
                });

                if (noticeList.size() == 0) {
                    mViewBinding.rvLoading.showEmpty("暂无公告");
                } else {
                    mViewBinding.rvLoading.showContent();
                }
            }
        });
        Bundle bundle = getArguments();
//        mChannel = (ChannelEntity) bundle.getSerializable("model");
        name = bundle.getString("name");
        id = bundle.getInt("id");

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //设置分割线
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line));
        mViewBinding.recy.addItemDecoration(itemDecoration);

        mViewBinding.recy.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);


        mViewBinding.recy.setLayoutManager(linearLayoutManager);

        homePagerAdapter = new HomePagerAdapter(mActivity, mData);
        homePagerAdapter.setJJBChannel(1);
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

                loadDate(true);
            }
        });

        mViewBinding.smallLabel.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                loadDate(false);
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
                if (mData.get(position) instanceof HomeListEntity.DataBean) {
                    onListClickHandle(position,mData.get(position));
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

        //设置banner样式
        mViewBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mViewBinding.banner.setImageLoader(new GlideImageLoader());


        mViewBinding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                if(!LoginEntity.getIsLogin()){
                    GuideLoginActivity.start(mActivity,false,"");
                    return;
                }

                if (headList.size() == 0) {
                    return;
                }
                HomeListEntity.DataBean dataBean = headList.get(position);
                String url;
                if (TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {


                    Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
                    intent.putExtra("id", String.valueOf(dataBean.getId()));
                    intent.putExtra("entry_type", 1);

                    getActivity().startActivity(intent);
                } else {
                    if (dataBean.getOther().getRedirect_url().contains("http")) {
                        url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ACTICLE_TOP + dataBean.getId();

                    } else {
                        url = dataBean.getOther().getRedirect_url();
                    }
                    BridgeWebViewActivity.intentMe(context, url);
                }
            }
        });

        //设置轮播时间
        mViewBinding.banner.setDelayTime(3000);
    }

    private void showBottomSVGA(int animationStyle) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), animationStyle);
        animation.setFillAfter(true); //动画结束后，保持动画结束的状态
        mViewBinding.llSearch.startAnimation(animation);
        isShow = !isShow; //取反
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
//        List<HomeListEntity.DataBean> cacheData = Paper.book().read(PagerCons.KEY_HOME_CACHE_LAST_TIME + name);
//        List<HomeListEntity.DataBean> handList = Paper.book().read(PagerCons.KEY_HOME_CACHE_BANNER + name);
        mViewBinding.loadingView.showLoading();
        loadDate(true);

        HashMap<String,String> hashMap = new HashMap();
        hashMap.put("column_id",id + "");
        mPresenter.clearUnreadMsg(hashMap,mActivity);

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    public void onListClickHandle(int position, Object o) {
        if(!LoginEntity.getIsLogin()){
            GuideLoginActivity.start(mActivity, false, "");
            return;
        }

        HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) o;
        dataBean.setSelected(true);
        homePagerAdapter.notifyItemChanged(position);
        if (dataBean.getShowtype() == 5) {
            scrollToTopAndRefresh();
        } else if (dataBean.getShowtype() == 8) {  //点击短视频标题
            VideoDetailActivity.intentMe(mActivity, dataBean);
        } else {
            if (dataBean.getType().equals("picture")) {
//                Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                intent.putExtra("id", dataBean.getId());
//                intent.putExtra("tabId", 0);
//                getActivity().startActivity(intent);
                PictureViewPagerActivity.intentMe(getActivity() , dataBean.getId() , 0);

                return;
            }
//                        Intent intent = new Intent(mActivity, BridgeWebViewActivity.class);
            //原生详情
//                        if (1 == 1) {
//                            Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
//                            intent.putExtra("id", String.valueOf(dataBean.getId()));
//                            getActivity().startActivity(intent);
//                            return;
//                        }
            /**
             * 1. 判断redirect_url是否存在，
             * 存在则判断链接类型是否时http的如果是http的链接
             * 跳转到  /browser/文章id
             * 非http链接则判断是否是带#，例如#/burst_rich 跳转到暴富秘籍， 则直接去除#用jsBridge方法处理跳转到对应的路由
             *
             * 2. 不存在则跳转到文章详情/ArticleDetails/文章id
             */
            String url;
            if (TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {

//                url = RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + dataBean.getId();
//                BridgeWebViewActivity.intentMe(context, url);

                Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
                intent.putExtra("id", String.valueOf(dataBean.getId()));
                intent.putExtra("entry_type", 1);
                getActivity().startActivity(intent);
            } else {
                if (dataBean.getOther().getRedirect_url().contains("http")) {
                    url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ACTICLE_TOP + dataBean.getId();

                } else {
                    url = dataBean.getOther().getRedirect_url();
                }
                BridgeWebViewActivity.intentMe(context, url);
            }

            try{
                //阅读记录数据缓存
                ArrayList<HomeListEntity.DataBean> dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                if (dataBeanArrayList == null) {
                    dataBeanArrayList = new ArrayList<>();
                }

                //去重
                for (HomeListEntity.DataBean dataBean1 : dataBeanArrayList) {
                    if (dataBean1.getId() == dataBean.getId()) {
                        return;
                    }
                }

                dataBeanArrayList.add(dataBean);
                book().write(PagerCons.KEY_READOBJECT, dataBeanArrayList);
            }catch (Exception e){

            }



        }
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
        myHandler.removeMessages(STOP_SMART_REFRESH);
        myHandler.sendEmptyMessageDelayed(STOP_SMART_REFRESH, 10000);

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
        }
        mPresenter.getNewsList(param, mActivity);
    }

    @Override
    public void initData() {
    }


    @Override
    public void getNewsList(Serializable serializable) {
        errorTime = 0;
        boolean isTop = false;
        mViewBinding.smallLabel.finishRefresh();
        mViewBinding.smallLabel.finishLoadMore();
        HomeListEntity newsEntity = (HomeListEntity) serializable;

//        updateTab(0);

        if (newsEntity.getData().size() > 2) {
            List<Object> images = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                if (newsEntity.getData().get(i).getImg_url().size() > 0) {
                    images.add(newsEntity.getData().get(i).getImg_url().get(0));
                } else {
                    images.add(R.mipmap.bg_banner_def);
                }
                titles.add(newsEntity.getData().get(i).getTitle());
            }
            //设置图片集合
            mViewBinding.banner.setImages(images);

            //设置标题集合（当banner样式有显示title时）
            mViewBinding.banner.setBannerTitles(titles);

            //banner设置方法全部调用完毕时最后调用
            mViewBinding.banner.start();

            headList.clear();
            for (int i = 0; i < 2; i++) {
                headList.add(newsEntity.getData().get(i));
            }
            for (int i = 0; i < 2; i++) {
                newsEntity.getData().remove(0);
            }

        }
        if (newsEntity.getData() != null && newsEntity.getData().size() != 0) {
//            Paper.book().write(PagerCons.KEY_HOME_CACHE_LAST_TIME + name , newsEntity.getData());
//            Paper.book().write(PagerCons.KEY_HOME_CACHE_BANNER + name , headList);
        } else {
            return;
        }




        allList.clear();
        allList.addAll(newsEntity.getData());
        noticeList.clear();
        notNoticeList.clear();
        for (HomeListEntity.DataBean bean : newsEntity.getData()) {
            if (bean.getOfficial_notice() != 1) {
                notNoticeList.add(bean);
            } else {
                noticeList.add(bean);
            }
        }
        mData.clear();
        if (isAll) {
            mData.addAll(newsEntity.getData());
            mViewBinding.tvSearch.setText("隐藏公告");
        } else {
            mData.addAll(notNoticeList);
            mViewBinding.tvSearch.setText("显示公告");
        }
//        mViewBinding.llSearch.setVisibility(noticeList.size() == 0 ? View.GONE : View.VISIBLE);
        homePagerAdapter.setIsFooter(true);
        mViewBinding.smallLabel.setEnableLoadMore(false);
        homePagerAdapter.notifyDataSetChanged();
        if (mData.size() > 0) {
            mViewBinding.loadingView.showContent();
            mViewBinding.tabLayout.setVisibility(View.VISIBLE);
            mViewBinding.divider.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.loadingView.showEmpty();
        }

        return;

    }

    /**
     * 清除未读消息
     * @param serializable
     */
    @Override
    public void clearUnreadMsgSuccess(Serializable serializable) {
        EventBus.getDefault().post(new ClearHomeTabUnreadMsgEvent(id));
    }

    private void scrollToTopAndRefresh() {
        mViewBinding.nestScroll.smoothScrollTo(0, 0);
//        loadDate(true);
        mViewBinding.smallLabel.autoRefresh();
    }


    @Override
    public void showError(String msg) {
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
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position == JunjinBaoMainActivity.HOME && event.channelName.equals(name)) {
            isRefresh = true;
            scrollToTopAndRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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

    public void updateTab(int positon) {
        currentTab = positon;
        switch (positon) {
            case 0:
                mViewBinding.tv1.setTextColor(mActivity.getResources().getColor(R.color.c_FF5500));
                mViewBinding.tv2.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.tv3.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.indicator1.setVisibility(View.VISIBLE);
                mViewBinding.indicator2.setVisibility(View.INVISIBLE);
                mViewBinding.indicator3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mViewBinding.tv1.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.tv2.setTextColor(mActivity.getResources().getColor(R.color.c_FF5500));
                mViewBinding.tv3.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.indicator1.setVisibility(View.INVISIBLE);
                mViewBinding.indicator2.setVisibility(View.VISIBLE);
                mViewBinding.indicator3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mViewBinding.tv1.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.tv2.setTextColor(mActivity.getResources().getColor(R.color.c_333333));
                mViewBinding.tv3.setTextColor(mActivity.getResources().getColor(R.color.c_FF5500));
                mViewBinding.indicator1.setVisibility(View.INVISIBLE);
                mViewBinding.indicator2.setVisibility(View.INVISIBLE);
                mViewBinding.indicator3.setVisibility(View.VISIBLE);
                break;
        }

    }
}

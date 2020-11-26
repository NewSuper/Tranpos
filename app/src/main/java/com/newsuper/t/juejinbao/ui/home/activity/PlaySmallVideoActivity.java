package com.newsuper.t.juejinbao.ui.home.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityPalySmallVideoBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.PlayVideoStateEvent;
import com.newsuper.t.juejinbao.bean.SmallVideoPlayCompleteEvent;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.PlaySmallvideoMsg;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.AdFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.MeiVideoDragFragment;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.presenter.SmallVideoPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.SmallVideoPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.PreloadVideoUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.view.rewardAnim.RewardAnimManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;

import static io.paperdb.Paper.book;

public class PlaySmallVideoActivity extends BaseActivity<SmallVideoPresenterImpl, ActivityPalySmallVideoBinding> implements SmallVideoPresenter.SmallVideoPresenterView {
    int[] mGlobalRect = new int[5];
    private List<Fragment> mFragments;
    private int position;
    private ArrayList<Object> mList;
    private AdapterFragment adapterFragment;
    private int scollIndex;
    private boolean isStop = true;
    private int clickNum = 0;
    private boolean isTwoShow = false;
    private boolean isThreeShow = false;

    //广告集合（用于去重）
//    private List<TTDrawFeedAd> ttDrawFeedAdList = new ArrayList<>();

//    private TTAdNative mTTAdNative;

    //算数用
    //广告间隔
    private int interval = 3;
    //除不尽数据
    private int exCount = 0;

//    public void addTTDrawFeedAd(TTDrawFeedAd ttDrawFeedAd){
//        ttDrawFeedAdList.add(ttDrawFeedAd);
//    }

//    public List<TTDrawFeedAd> getTtDrawFeedAdList(){
//        return ttDrawFeedAdList;
//    }

    //广告是否可用（能请求到广告）
    private boolean canAD = false;
    //广告储备（防止突然请求不到广告）
//    public List<TTDrawFeedAd> ttDrawFeedAdList = new ArrayList<>();
//    private TTAdNative mTTAdNative;
    private long startReadTime;
    private static final int REQUEST_CODE_LOGIN = 0X02;
    private boolean alreadyGetCoin = false;

    //激励广告弹窗动画效果
    private RewardAnimManager rewardAnimManager;
    //双倍奖励时间戳
    private long doubleRewardTime = 0;
    //当前小视频奖励
   com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean;

    //存储了 需要加载的小视频 里面存放的是 小视频在 mList 的下标 （集合最大值 preloadCount）
    private List<Integer> preloadUrls = new ArrayList<>();


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_paly_small_video;
    }

    @Override
    public void initView() {
        interval = book().read(PagerCons.KEY_INTERVAL_SMALLVIDEO_AD, 8);
//        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        startReadTime = System.currentTimeMillis() / 1000;

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

        perloadTime = getIntent().getIntExtra("perloadTime", 3000);
        addGoldCoinCount = getIntent().getIntExtra("addGoldCoinCount", 10);
        preloadCount = getIntent().getIntExtra("preloadCount", 5);

        urls = book().read(PagerCons.KEY_SMALLVIDEO_COUNT,urls);
        mViewBinding.circlePercentProgress.setPercentage(urls.size() / addGoldCoinCount * 100f);
    }


    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        mViewBinding.viewPager.removeAllViews();
     //   mTTAdNative = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if(rewardAnimManager != null){
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private int operationNum = 0;

    @Override
    public void initData() {
//        if (mTTAdNative == null) {
//            mTTAdNative = TTAdManagerHolder.get().createAdNative(mActivity);
//        }
     //  mPresenter.requestTTDrawFeedAds(mTTAdNative);
    }

    private void initVideoList() {
        //type=smallvideo
        Map<String, String> map = new HashMap<>();
        map.put("type", "smallvideo");
//        map.put("change_uid", "112");
        mPresenter.getSmallVideoList(map, mActivity, 0);
    }

    private HomeListEntity HomeListEntity;
    private int mlistSize = 0;

    //请求数据返回
    @Override
    public void getSmallVideoListSuccess(Serializable serializable, int type) {

//        ThreadPoolManager.execute(new Runnable() {
//            @Override
//            public void run() {


        if (canAD) {

            HomeListEntity = (HomeListEntity) serializable;
            mlistSize = mList.size();
            if (HomeListEntity.getData() != null) {

                List<com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean> listBeans = HomeListEntity.getData().get(0).getOther().getSmallvideo_list();
                List<Object> objects = new ArrayList<>();
                for (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean : listBeans) {
                    objects.add(smallvideoListBean);
                }


                int addCount = exCount;
                int movieindex = 0;
                exCount = (objects.size() + exCount) % interval;
                int originBeanSize = objects.size();
                for (int i = 0; i < originBeanSize; i++) {
                    if ((i + 1 + addCount) % interval == 0) {
                        //超过数组
                        if (i + 1 + movieindex >= objects.size()) {
                            objects.add("");
                        } else {
                            objects.add(i + 1 + movieindex, "");
                        }

                        movieindex++;
                    }
                }

                for (int i = 0; i < objects.size(); i++) {

                    if (objects.get(i) instanceof com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) {
                        com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean =
                                (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) objects.get(i);

                        MeiVideoDragFragment videoDragFragment = new MeiVideoDragFragment();
                        Bundle bundle = new Bundle();
                        bundle.putIntArray("region", mGlobalRect);
                        bundle.putInt("position", scollIndex);
                        bundle.putInt("index", mlistSize + i);
                        bundle.putInt("id", smallvideoListBean.getId());
                        bundle.putString("videoUrl", smallvideoListBean.getImg_url().get(1));
                        bundle.putString("videoImageUrl", smallvideoListBean.getImg_url().get(0));
                        bundle.putString("desc", smallvideoListBean.getDescription());
                        bundle.putString("author", smallvideoListBean.getAuthor());
                        bundle.putString("authorLogo", smallvideoListBean.getAuthor_logo());
                        bundle.putDouble("diggCount", smallvideoListBean.getDigg_count());
                        bundle.putDouble("commentCount", smallvideoListBean.getComment_count());
                        videoDragFragment.setArguments(bundle);
                        mFragments.add(videoDragFragment);

//                if((i + 1 + addCount) % interval == 0){
//                    mFragments.add(new AdFragment());
//                    movieindex++;
//                }
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putIntArray("region", mGlobalRect);
                        bundle.putInt("position", mlistSize + i);
                        AdFragment adFragment = new AdFragment();
                        adFragment.setArguments(bundle);
                        mFragments.add(adFragment);
                    }
                }

                for (int i = 0; i < objects.size(); i++) {
                    mList.add(objects.get(i));
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterFragment.notifyDataSetChanged();
                        preloadVideo(scollIndex);
                    }
                });
            }

        } else {
            HomeListEntity = (HomeListEntity) serializable;
            mlistSize = mList.size();
            if (HomeListEntity.getData() != null) {
                for (int i = 0; i < HomeListEntity.getData().get(0).getOther().getSmallvideo_list().size(); i++) {
                    mList.add(HomeListEntity.getData().get(0).getOther().getSmallvideo_list().get(i));
                }

                for (int i = 0; i < HomeListEntity.getData().get(0).getOther().getSmallvideo_list().size(); i++) {
                    com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean =
                            (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) mList.get(mlistSize + i);


                    MeiVideoDragFragment videoDragFragment = new MeiVideoDragFragment();
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("region", mGlobalRect);
                    bundle.putInt("position", scollIndex);
                    bundle.putInt("index", mlistSize + i);
                    bundle.putInt("id", smallvideoListBean.getId());
                    bundle.putString("videoUrl", smallvideoListBean.getImg_url().get(1));
                    bundle.putString("videoImageUrl", smallvideoListBean.getImg_url().get(0));
                    bundle.putString("desc", smallvideoListBean.getDescription());
                    bundle.putString("author", smallvideoListBean.getAuthor());
                    bundle.putString("authorLogo", smallvideoListBean.getAuthor_logo());
                    bundle.putDouble("diggCount", smallvideoListBean.getDigg_count());
                    bundle.putDouble("commentCount", smallvideoListBean.getComment_count());
                    videoDragFragment.setArguments(bundle);
                    mFragments.add(videoDragFragment);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterFragment.notifyDataSetChanged();
                        preloadVideo(scollIndex);
                    }
                });

            }
        }
//        preloadVideo(scollIndex);


//            }
//        });
    }

    @Override
    public void showError(String msg) {

    }

    //请求广告返回
//    @Override
//    public void requestTTDrawFeedAds(List<TTDrawFeedAd> ads) {
//        if (ads != null) {
//            ttDrawFeedAdList = ads;
//            if (ttDrawFeedAdList.size() == 3) {
//                canAD = true;
//            } else {
//                canAD = false;
//            }
//        }
//        init();
//    }

    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {
        GetCoinEntity coinEntity = (GetCoinEntity) serializable;
        if (coinEntity.getCode() == 0) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_article_reward_coin);
            drawable.setBounds(0, 0, 32, 32);
            mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
            mViewBinding.tvCircleInnerPic.setText("+" + Utils.FormatGold(coinEntity.getData().getCoin()));
//            showRewardDialog(coinEntity.getData().getCoin());

            if(rewardAnimManager != null){
                rewardAnimManager.destory();
                rewardAnimManager = null;
            }

            if(rewardAnimManager == null){
                rewardAnimManager = new RewardAnimManager(this , ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0) , 0 , 270 , coinEntity);
                rewardAnimManager.show();
            }

            urls.clear();
            alreadyGetCoin = true;
            book().write(PagerCons.KEY_SMALLVIDEO_COUNT,urls);
            mViewBinding.circlePercentProgress.setPercentage(0);
        } else {
            ToastUtils.getInstance().show(mActivity, coinEntity.getMsg());
        }
    }

    @Override
    public void getRewardDouble(RewardDoubleEntity rewardDoubleEntity) {
        RewardEntity entity = new RewardEntity(
                "奖励到账",
                rewardDoubleEntity.getData().getCoin(),
                "小视频奖励",
                "用掘金宝App读文章看视\n频，能多赚更多金币！",
                false
        );
        ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.showPopupWindow();
    }

    /**
     *
     */
    public void showRewardDialog(double coin) {
        RewardEntity entity = new RewardEntity(
                "奖励到账",
                coin,
                "看小视频奖励",
                "用掘金宝App看小视\n频，能多赚更多金币！",
                false
        );
        ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.showPopupWindow();
    }

    private void init() {

        Intent intent = getIntent();
        if (intent != null) {
            mGlobalRect = intent.getIntArrayExtra("region");
            mList = (ArrayList<Object>) getIntent().getSerializableExtra("HomeListEntity");
            position = getIntent().getIntExtra("position", 0);
            scollIndex = getIntent().getIntExtra("position", 0);
            position = 0;
            scollIndex = 0;

        }

        if(urls.size() >= addGoldCoinCount) {
            getRewardOf30S();
        }

        if (book().read(PagerCons.KEY_IS_FIRST_VIDEO) == null) {
            mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.VISIBLE);
        }
        mViewBinding.activityPlaySmallVideoGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.activityPlaySmallVideoOperationOne.setVisibility(View.GONE);
                mViewBinding.activityPlaySmallVideoOperationTwo.setVisibility(View.GONE);
                mViewBinding.activityPlaySmallVideoOperationThree.setVisibility(View.GONE);
                mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.GONE);
            }
        });

        mFragments = new ArrayList<>();

        //有广告
        if (canAD) {

            exCount = mList.size() % interval;
            position = position + position / interval;

            int movieindex = 0;
            //先加入列表
            int originListSize = mList.size();
            for (int i = 0; i < originListSize; i++) {
                if ((i + 1) % interval == 0) {
                    mList.add(i + 1 + movieindex, "");
                    movieindex++;
                }
            }

            for (int i = 0; i < mList.size(); i++) {

                if (mList.get(i) instanceof com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) {

                    com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean =
                            (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) mList.get(i);

                    MeiVideoDragFragment videoDragFragment = new MeiVideoDragFragment();
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("region", mGlobalRect);
                    bundle.putInt("position", position);
                    bundle.putInt("index", i);
                    bundle.putInt("id", smallvideoListBean.getId());
                    bundle.putString("videoUrl", smallvideoListBean.getImg_url().get(1));
                    bundle.putString("videoImageUrl", smallvideoListBean.getImg_url().get(0));
                    bundle.putString("desc", smallvideoListBean.getDescription());
                    bundle.putString("author", smallvideoListBean.getAuthor());
                    bundle.putString("authorLogo", smallvideoListBean.getAuthor_logo());
                    bundle.putDouble("diggCount", smallvideoListBean.getDigg_count());
                    bundle.putDouble("commentCount", smallvideoListBean.getComment_count());
                    videoDragFragment.setArguments(bundle);

                    //设置广告数据
//            videoDragFragment.setTTDrawFeedAd(mList.get(i).ttDrawFeedAd);
                    mFragments.add(videoDragFragment);

//            if((i + 1) % interval == 0){
//                mFragments.add(new AdFragment());
//                movieindex++;
//            }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("region", mGlobalRect);
                    bundle.putInt("position", i);
                    AdFragment adFragment = new AdFragment();
                    adFragment.setArguments(bundle);
                    mFragments.add(adFragment);


                }
            }

        }
        //无广告
        else {
            for (int i = 0; i < mList.size(); i++) {
                com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean
                        = (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) mList.get(i);


                MeiVideoDragFragment videoDragFragment = new MeiVideoDragFragment();
                Bundle bundle = new Bundle();
                bundle.putIntArray("region", mGlobalRect);
                bundle.putInt("position", position);
                bundle.putInt("index", i);
                bundle.putInt("id", smallvideoListBean.getId());
                bundle.putString("videoUrl", smallvideoListBean.getImg_url().get(1));
                bundle.putString("videoImageUrl", smallvideoListBean.getImg_url().get(0));
                bundle.putString("desc", smallvideoListBean.getDescription());
                bundle.putString("author", smallvideoListBean.getAuthor());
                bundle.putString("authorLogo", smallvideoListBean.getAuthor_logo());
                bundle.putDouble("diggCount", smallvideoListBean.getDigg_count());
                bundle.putDouble("commentCount", smallvideoListBean.getComment_count());
                videoDragFragment.setArguments(bundle);
                mFragments.add(videoDragFragment);
            }
        }
        adapterFragment = new AdapterFragment(getSupportFragmentManager(), mFragments);
//        mViewBinding.viewPager.setOffscreenPageLimit(mList.size());
        mViewBinding.viewPager.setAdapter(adapterFragment);
        clickNum = position;
        mViewBinding.viewPager.setCurrentItem(position);
        mViewBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(alreadyGetCoin) {
                    setRedPack();
                }

                if (Jzvd.CURRENT_JZVD != null) {
                    Jzvd.releaseAllVideos();
                }

                if(rewardAnimManager != null){
                    rewardAnimManager.destory();
                    rewardAnimManager = null;
                }

                EventBus.getDefault().post(new PlaySmallvideoMsg(i));
                scollIndex = i;
                preloadVideo(scollIndex);
                if (mList.size() - 1 == i) {
                    initVideoList();
                }
                //小视频蒙层判断
                if (book().read(PagerCons.KEY_IS_FIRST_VIDEO) == null) {
                    if (i < clickNum) {
                        clickNum--;
                        return;
                    }
                    clickNum++;
                    Log.e("TAG", "onPageSelected: ============>>>>>>>" + clickNum + "和=" + isTwoShow);
                    if (clickNum == 2 && !isTwoShow) {
                        isTwoShow = true;
                        mViewBinding.activityPlaySmallVideoOperationTwo.setVisibility(View.VISIBLE);
                        mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.VISIBLE);
                    } else if (clickNum == 4 && !isThreeShow) {
                        isThreeShow = true;
                        mViewBinding.activityPlaySmallVideoOperationThree.setVisibility(View.VISIBLE);
                        mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.VISIBLE);
                    } else if (clickNum == 6) {

                        mViewBinding.activityPlaySmallVideoOperationFore.setVisibility(View.VISIBLE);
                        mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.VISIBLE);
                        book().write(PagerCons.KEY_IS_FIRST_VIDEO, "no");
                    } else {
                        mViewBinding.activityPlaySmallVideoOperationOne.setVisibility(View.GONE);
                        mViewBinding.activityPlaySmallVideoOperationTwo.setVisibility(View.GONE);
                        mViewBinding.activityPlaySmallVideoOperationThree.setVisibility(View.GONE);
                        mViewBinding.activityPlaySmallVideoGuide.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        preloadVideo(0);

        if (LoginEntity.getIsLogin()) {
            mViewBinding.rlCircleReward.setVisibility(View.VISIBLE);
//            startCount();
        } else {
            mViewBinding.rlCircleReward.setVisibility(View.GONE);
        }
    }

    public class AdapterFragment extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public AdapterFragment(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {//必须实现
            return mFragments.get(position);
        }

        @Override
        public int getCount() {//必须实现
            return mFragments.size();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: =========>>>>>>" + "走没走");
        if (isStop) {
            Jzvd.releaseAllVideos();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(PlayVideoStateEvent message) {
        isStop = message.isStop();
    }

    @Override
    public void onBackPressed() {
        try {
            if (Jzvd.backPress()) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //最后一条预加载的 下标
    private int lastPreloadIndex = 0;

    /** 几秒后才预加载 */
    private int perloadTime = 2000;

    //一次性 预加载 preloadCount 条 小视频
    private void preloadVideo(int position) {
        //如果 预加载集合中 已经有这个下标了 就说明 当前页面已经不需要预加载
        if(preloadUrls.contains(position)) {
            //删除这个下标 并删除handler消息
            preloadUrls.remove((Object)position);
            mHandler.removeMessages(position);

            //重新发送消息 保证连续性
            sendMessageStartPreload();
        }
        //说明这个 position 已经预加载过了
        if (position < lastPreloadIndex - 1) {
            return;
        }
        //清空一下 预加载 集合
        preloadUrls.clear();
        for (int i = position + 1; i < mList.size(); i++) {
            if (mList.get(i) instanceof com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) {
                //预加载集合数据 超过 preloadCount预加载数量 就退出这个循环
                if (i - position > preloadCount) {
                    lastPreloadIndex = i;
                    break;
                }
                //将下标 添加到集合
                preloadUrls.add(i);
            }
        }

        //先从第一条数据 开始预加载
        sendMessageStartPreload();
    }

    //用于 小视频预加载的 handler
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //如果 预加载小视频数量 大于0
            if(preloadUrls.size() > 0) {
                //取出（并删除） 集合 中的 第一条数据 并开始预加载
                Integer remove = preloadUrls.remove(0);
                PreloadVideoUtils.getInstance().download(mActivity, ((com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) mList.get(remove)).getImg_url().get(1));

                //如果 集合中仍然还有数据 再次发送消息 相当于 循环执行 上面两句代码
                sendMessageStartPreload();
            }
            return false;
        }
    });

    private void sendMessageStartPreload() {
        if (preloadUrls.size() > 0) {
            Message msg1 = Message.obtain();
            msg1.what = preloadUrls.get(0); //这句很重要 当页面滑动超过这个下标 就remove这条消息 removeMessage(what) 这样就不会加载已经滑过视频
            mHandler.sendMessageDelayed(msg1, perloadTime);
        }
    }

    /**
     * 阅读30s随机奖励
     */
    public void getRewardOf30S() {
        if (mPresenter == null || this.isDestroyed()) {
            return;
        }

        smallvideoListBean = (com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean) mList.get(scollIndex);

        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("aid", smallvideoListBean.getId() + "");
        hashMap.put("type", "svideo");
        hashMap.put("starttime", startReadTime + "");
        hashMap.put("read_time", (doubleRewardTime = System.currentTimeMillis()) + "");

        mPresenter.getRewardOf30second(hashMap, mActivity);
    }

    //几条小视频加一次金币
    private float addGoldCoinCount = 10f;

    //预加载几条小视频
    private int preloadCount = 5;

    private List<String> urls = new ArrayList<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSmallVideoPlayComplete(SmallVideoPlayCompleteEvent event) {
        if (LoginEntity.getIsLogin()) {
            String type = event.getType();
            if (!urls.contains(type)) {
                if (urls.size() <= addGoldCoinCount) {
                    urls.add(type);
                    book().write(PagerCons.KEY_SMALLVIDEO_COUNT,urls);
                }
                if(urls.size() >= addGoldCoinCount){
                    mViewBinding.circlePercentProgress.setPercentage(100);
//                    doAnimation((int) ((addGoldCoinCount - 1) / addGoldCoinCount * 100f),100);
                    getRewardOf30S();
                } else {
                    setRedPack();
                    mViewBinding.circlePercentProgress.setPercentage(urls.size() / addGoldCoinCount * 100f);
//                    doAnimation((int) ((urls.size() - 1) / addGoldCoinCount * 100f),(int) (urls.size() / addGoldCoinCount * 100f));
                }
            }
        }
    }

    /** 重新设置未红包状态 */
    private void setRedPack() {
        alreadyGetCoin = false;
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_arcticle_circle_reward);
        drawable.setBounds(0, 0, 70, 80);
        mViewBinding.tvCircleInnerPic.setCompoundDrawables(drawable, null, null, null);
        mViewBinding.tvCircleInnerPic.setText("");
    }

    private void doAnimation(int prePercent,int percent) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prePercent,percent);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mViewBinding.circlePercentProgress.setPercentage((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LoginEntity.getIsLogin() && mPresenter != null) {

            switch (requestCode) {
                //双倍奖励
                case PlayRewardVideoAdActicity.DOUBLEREWARD:

                    if(smallvideoListBean == null){
                        return;
                    }
                    if(resultCode == RESULT_OK) {
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put("aid", smallvideoListBean.getId() + "");
                        hashMap.put("type", "svideo");
                        hashMap.put("read_time", doubleRewardTime + "");
                        mPresenter.getRewardDouble(hashMap, mActivity);
                        doubleRewardTime = 0;
                    }
                    break;
            }
        }

    }

}

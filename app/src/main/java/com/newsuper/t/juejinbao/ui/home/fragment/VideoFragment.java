package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentVideoBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.UpdateVideoChannelEvent;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.activity.VideoChannelManagerActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.VideoPagerFragmentAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.VideoFragmentPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.VideoFragmentPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import io.paperdb.Paper;

public class VideoFragment extends BaseFragment<VideoFragmentPresenterImpl, FragmentVideoBinding> implements VideoFragmentPresenter.View {
    List<ChannelEntity> channelEntities = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        return view;
    }


    public static VideoFragment newInstance(String model) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initView() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "video");
        mPresenter.getVideoChannelList(map,mActivity);
    }

    @Override
    public void initData() {
        mViewBinding.modelFragmentAtlasMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!LoginEntity.getIsLogin()){
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                if (channelEntities.size() > 0) {
                    Intent intent = new Intent(getActivity(), VideoChannelManagerActivity.class);

                    Paper.book().write(PagerCons.VIDEO_CHANNEL_CHCHE, channelEntities);

                    startActivity(intent);
                } else {
                    ToastUtils.getInstance().show(getContext(), "当前频道还未加载完");
                }

            }
        });

    }


    @Override
    public void getVideoChannelListSuccess(Serializable serializable) {
        ChannelInfo entity = (ChannelInfo) serializable;
        channelEntities.clear();
        channelEntities.addAll(entity.getData());
        setTabsAndPage();
    }

    @Override
    public void saveSuccess(Serializable serializable) {
        Log.i("zzz", "saveSuccess: " + "视频频道保存成功");
    }

    @Subscribe
    public void onChannelUpdate(UpdateVideoChannelEvent event) {
//        Log.d(TAG, "onChannelUpdate: ");

        List<ChannelEntity> channelCacheData = Paper.book().read(PagerCons.VIDEO_CHANNEL_CHCHE);
        mViewBinding.slTab.setCurrentTab(0);

        if (channelCacheData != null && channelCacheData.size() > 0) {
            channelEntities = channelCacheData;
            if (LoginEntity.getIsLogin()) {
                saveChannelToService();
            }
            setTabsAndPage();
        }
    }
    //保存频道到服务器
    void saveChannelToService() {
        Map<String, String> param = new HashMap<>();
        String ids = "";
        for (int i = 0; i < channelEntities.size(); i++) {
            ids += channelEntities.get(i).getId();
            if (i < channelEntities.size() - 1) ids += ",";
        }
        param.put("channel_id", ids);
        param.put("type", "video");

        mPresenter.setChannelList(param, mActivity);
    }

    void setTabsAndPage() {

        mViewBinding.viewPagerVideo.setAdapter(new VideoPagerFragmentAdapter(getChildFragmentManager(), channelEntities));

        //重写了adapter的destroyItem, 就不要设置了，否则后面的可能加载不出来
//        mViewPager.setOffscreenPageLimit(2);
        mViewBinding.viewPagerVideo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                UpdateTabUI(position);
                if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.releaseAllVideos();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //   mTabs.setViewPager(mViewPager);
        mViewBinding.slTab.setViewPager(mViewBinding.viewPagerVideo);
        UpdateTabUI(0);
        mViewBinding.slTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                UpdateTabUI(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
//        setTabsValue();
    }

    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.slTab.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white));
        }
    }

    @Override
    public void showErrolr(String str) {
        MyToast.showToast(str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().unregister(this);
        }
    }
}

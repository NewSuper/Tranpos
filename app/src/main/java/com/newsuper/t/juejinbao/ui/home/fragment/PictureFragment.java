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
import com.newsuper.t.databinding.FragmentPictureBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.PicturePageIndexEvent;
import com.newsuper.t.juejinbao.bean.UpdatePictureEvent;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.activity.PictureManagerActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.PicturePagerFragmentAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.PicturePresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PictureFragmentPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class PictureFragment extends BaseFragment<PictureFragmentPresenterImpl, FragmentPictureBinding> implements PicturePresenter.View {

    private PicturePagerFragmentAdapter mAdapter;
    List<ChannelEntity> mListTab = new ArrayList<>();
    // 记录当前频道 用户首页点击tab刷新判断当前频道
    public ChannelEntity currChannel;

    public static PictureFragment newInstance() {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        return view;
    }

    @Override
    public void initView() {
        Map<String, String> map = new HashMap<>();
        //type=[article|picture|video]
        map.put("type", "picture");
        mPresenter.getPictureTabList(map, getActivity());
    }

    @Override
    public void initData() {
        mViewBinding.modelFragmentAtlasMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                if(loginEntity==null){
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
                if (mListTab.size() > 0) {
                    Intent intent = new Intent(getActivity(), PictureManagerActivity.class);
                    Paper.book().write(PagerCons.PICTURE_CHCHE, mListTab);
                    startActivity(intent);
                } else {
                    ToastUtils.getInstance().show(getContext(), "当前频道还未加载完");
                }

            }
        });
    }

    @Override
    public void getPictureTabSuccess(Serializable serializable) {
        ChannelInfo channelEntity = (ChannelInfo) serializable;
        mListTab.clear();
        mListTab = channelEntity.getData();

        setTabsAndPage();
    }

    @Override
    public void saveSuccess(Serializable serializable) {
        Log.i("zzz", "saveSuccess: " + "图集频道保存成功");
    }



    void setTabsAndPage() {
        mViewBinding.fragmentPictureViewPager.removeAllViews(); //要调用此方法，因为修改频道要重置
        if (mAdapter != null) {
            mAdapter.clean();
            mAdapter = null; //置空也无法清除缓存，要用StateFragmentAdapter才行
        }
        mViewBinding.fragmentPictureViewPager.setOffscreenPageLimit(mListTab.size());
        mAdapter = new PicturePagerFragmentAdapter(getChildFragmentManager(), mListTab);
        mViewBinding.fragmentPictureViewPager.setAdapter(mAdapter);
        //重写了adapter的destroyItem, 就不要设置了，否则后面的可能加载不出来
//        mViewPager.setOffscreenPageLimit(2);
        mViewBinding.fragmentPictureViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new PicturePageIndexEvent(position));
                UpdateTabUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currChannel = mListTab.get(0);
        mViewBinding.fragmentPictureTable.setViewPager(mViewBinding.fragmentPictureViewPager);
        mViewBinding.fragmentPictureViewPager.setCurrentItem(0, true);

        UpdateTabUI(0);
        mViewBinding.fragmentPictureTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currChannel = mListTab.get(position);
                UpdateTabUI(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().unregister(this);
        }
    }

    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.fragmentPictureTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
            tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white));
        }
    }

    @Subscribe
    public void onChannelUpdate(UpdatePictureEvent event) {
//        Log.d(TAG, "onChannelUpdate: ");

        List<ChannelEntity> channelCacheData = Paper.book().read(PagerCons.PICTURE_CHCHE);
        mViewBinding.fragmentPictureTable.setCurrentTab(0);

        if (channelCacheData != null && channelCacheData.size() > 0) {
            mListTab = channelCacheData;
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
        for (int i = 0; i < mListTab.size(); i++) {
            ids += mListTab.get(i).getId();
            if (i < mListTab.size() - 1) ids += ",";
        }
        param.put("channel_id", ids);
        param.put("type", "picture");

        mPresenter.setChannelList(param, mActivity);
    }

    @Override
    public void showErrolr(String str) {

    }

}

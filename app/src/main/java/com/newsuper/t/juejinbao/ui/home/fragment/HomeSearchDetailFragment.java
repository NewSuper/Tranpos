package com.newsuper.t.juejinbao.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomesearchdetailBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.home.adapter.HomeSearchDetailPageAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent2;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchDetailImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 全网搜索-搜索结果详情
 */
public class HomeSearchDetailFragment extends BaseFragment<HomeSearchDetailImpl, FragmentHomesearchdetailBinding> implements HomeSearchDetailImpl.MvpView {

    HomeSearchDetailPageAdapter homeSearchDetailPageAdapter;


    private String kw;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesearchdetail, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initView() {
        kw = getArguments().getString("kw");

        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //((AutoHeightViewPager)viewPager).resetHeight(position);
            }

            @Override
            public void onPageSelected(int position) {
                selectUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<String> stringList = new ArrayList<>();
        stringList.add("掘金宝");
        stringList.add("360资讯");
        stringList.add("搜狗新闻");
//        stringList.add("新浪新闻");

        homeSearchDetailPageAdapter = new HomeSearchDetailPageAdapter(getChildFragmentManager(), stringList, kw);
        mViewBinding.vp.setAdapter(homeSearchDetailPageAdapter);
        homeSearchDetailPageAdapter.notifyDataSetChanged();
        mViewBinding.stb.setViewPager(mViewBinding.vp);

        selectUI(0);

    }

    @Override
    public void initData() {
        search(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void search(HomeSearchDetailEvent2 homeSearchResultEvent) {
        if (homeSearchResultEvent != null) {
            kw = homeSearchResultEvent.getKw();
        }


        homeSearchDetailPageAdapter.setKw(kw);

        //通知子界面
        HomeSearchDetailEvent homeSearchDetailEvent = new HomeSearchDetailEvent();
        homeSearchDetailEvent.setKw(kw);
        EventBus.getDefault().post(homeSearchDetailEvent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void selectUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.stb.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 15 : 15);

            TextPaint tp = tv_tab_title.getPaint();
            if (i == position) {
                tp.setFakeBoldText(true);
            } else {
                tp.setFakeBoldText(false);
            }
        }
    }


}

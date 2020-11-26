package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityTwentyFourHourHotBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.home.adapter.TwentyFourHourHotAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HotPointEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.TodayHotPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.TodayHotPresneterImpl;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

import static io.paperdb.Paper.book;
//
//public class TwentyFourHourHotActivity extends BaseActivity<TodayHotPresneterImpl, ActivityTwentyFourHourHotBinding> implements TodayHotPresenter.View{
//
//    private TwentyFourHourHotAdapter todayHotAdapter;
//
//    private int page;
//    private List<HotPointEntity.HotPoint> mData;
//
//    public static void start(Context mActivity) {
//        mActivity.startActivity(new Intent(mActivity, TwentyFourHourHotActivity.class));
//    }
//
//    @Override
//    public boolean setStatusBarColor() {
//        return false;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_twenty_four_hour_hot;
//    }
//
//    @Override
//    public void initView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        mViewBinding.recy.setLayoutManager(linearLayoutManager);
//        mData = new ArrayList<>();
//        todayHotAdapter = new TwentyFourHourHotAdapter(mActivity, mData);
//        todayHotAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE,"middle"));
//        mViewBinding.recy.setAdapter(todayHotAdapter);
//        mViewBinding.smallLabel.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                page = 1;
//                loadDate();
//            }
//        });
//
//        mViewBinding.smallLabel.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                page++;
//                loadDate();
//            }
//        });
//    }
//
//    @Override
//    public void initData() {
//        page = 1;
//        loadDate();
//    }
//
//
//    @OnClick({R.id.module_include_title_bar_return})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.module_include_title_bar_return:
//                finish();
//                break;
//        }
//    }
//
//    private void loadDate() {
//        Map<String, String> param = new HashMap<>();
//        param.put("page",page + "");
//        mPresenter.getHotpoint(param,mActivity);
//    }
//
//    @Override
//    public void getHotpoint(HotPointEntity hotPointEntity) {
//        mViewBinding.smallLabel.finishRefresh();
//        mViewBinding.smallLabel.finishLoadMore();
//
//        if(todayHotAdapter != null && hotPointEntity != null) {
//            todayHotAdapter.setToDay(hotPointEntity.getToday());
//
//            if(page == 1) {
//                mData.clear();
//            }
//
//            mData.addAll(hotPointEntity.getData());
//            todayHotAdapter.notifyDataSetChanged();
//
//            if(page == hotPointEntity.getLast_page()) { //已经最后一页
//                mViewBinding.smallLabel.finishLoadMoreWithNoMoreData();
//                mViewBinding.smallLabel.setNoMoreData(true);
//            }else { //还有下一页
//                mViewBinding.smallLabel.setNoMoreData(false);
//            }
//        }
//    }
//
//    @Override
//    public void onError(String msg) {
//        mViewBinding.smallLabel.finishRefresh();
//        mViewBinding.smallLabel.finishLoadMore();
//
//        ToastUtils.getInstance().show(mActivity,msg);
//    }
//}

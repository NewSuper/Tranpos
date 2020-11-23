package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityTodayHotBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.MyItemClickListener;
import com.newsuper.t.juejinbao.ui.home.adapter.TodayHotListAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.TodayHotPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.TodayHotPresneterImpl;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.OnClick;


public class TodayHotActivity extends BaseActivity<TodayHotPresneterImpl, ActivityTodayHotBinding> implements TodayHotPresenter.View{

    private TodayHotListAdapter todayHotAdapter;

    private int page;
    private List<TodayHotEntity.DataBean> mData;

    public static void start(Context mActivity) {
        mActivity.startActivity(new Intent(mActivity, TodayHotActivity.class));
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_today_hot;
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mViewBinding.recy.setLayoutManager(linearLayoutManager);
        mData = new ArrayList<>();
        todayHotAdapter = new TodayHotListAdapter(mData, mActivity);
        mViewBinding.recy.setAdapter(todayHotAdapter);
        mViewBinding.smallLabel.setOnRefreshListener(refreshLayout -> {
            page = 1;
            loadDate();
        });
        mViewBinding.smallLabel.finishLoadMoreWithNoMoreData();
//        mViewBinding.smallLabel.setOnLoadMoreListener(refreshLayout -> {
//            page++;
//            loadDate();
//        });
        todayHotAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, List list) {
                TodayHotResultActivity.intentMe(mActivity,mData.get(position).getHot_word(),mData.get(position).getEncode_hot_word());
            }

            @Override
            public void onLongClickListener(View view, int postion) {
            }
        });
    }

    @Override
    public void initData() {
        page = 1;
        loadDate();
    }

    @OnClick({R.id.module_include_title_bar_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.module_include_title_bar_return:
                finish();
                break;
        }
    }

    private void loadDate() {
        Map<String, String> param = new HashMap<>();
        //param.put("page",page + "");
        mPresenter.getHotWordRank(param,mActivity);
    }

    @Override
    public void getHotWordRankSuccess(TodayHotEntity entity) {
        if(entity.getCode()==0){
            mViewBinding.smallLabel.finishRefresh();
            mViewBinding.smallLabel.finishLoadMore();
            mData.clear();
            if(entity.getData() != null && entity.getData().size()!=0) {
                mData.addAll(entity.getData());
            }
            if(todayHotAdapter!=null)
                todayHotAdapter.updateList(mData);
        }else{
            ToastUtils.getInstance().show(mActivity,entity.getMsg());
        }
    }

    @Override
    public void onError(String msg) {
        mViewBinding.smallLabel.finishRefresh();
        mViewBinding.smallLabel.finishLoadMore();

        ToastUtils.getInstance().show(mActivity,msg);
    }
}

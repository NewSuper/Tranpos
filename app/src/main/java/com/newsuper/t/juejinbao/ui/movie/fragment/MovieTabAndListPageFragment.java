package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovietabandlistpageBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieNewTabAndListAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.V2PlayListEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieTabAndListPageImpl;

import java.util.List;

/**
 * 电视剧播出时间表子界面
 */
public class MovieTabAndListPageFragment extends BaseFragment<MovieTabAndListPageImpl, FragmentMovietabandlistpageBinding> implements MovieTabAndListPageImpl.MvpView {


    private List<V2PlayListEntity.DataBean.TvsBean> beans;

    MovieNewTabAndListAdapter movieNewTabAndListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movietabandlistpage, container, false);
    }

    @Override
    public void initView() {
        String beanString = getArguments().getString("beanString");
        beans = JSON.parseArray(beanString , V2PlayListEntity.DataBean.TvsBean.class);

    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(movieNewTabAndListAdapter = new MovieNewTabAndListAdapter(getActivity()));

        movieNewTabAndListAdapter.update(beans);

    }



}

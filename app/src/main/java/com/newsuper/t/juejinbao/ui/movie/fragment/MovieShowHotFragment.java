package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovietvhotBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieShowHotAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowHotEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieShowHotImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热门综艺
 */
public class MovieShowHotFragment extends BaseFragment<MovieShowHotImpl, FragmentMovietvhotBinding> implements MovieShowHotImpl.MvpView {

    private String tag;

    private List<MovieShowHotEntity.DataBeanX.DataBean> beans = new ArrayList<>();

    MovieShowHotAdapter adapter;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movietvhot, container, false);
    }

    @Override
    public void initView() {
        tag = getArguments().getString("tag");

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(adapter = new MovieShowHotAdapter(getActivity()));

        adapter.update(beans);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                startRequest();
                mViewBinding.srl.setEnableLoadMore(true);
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                startRequest();
            }
        });

    }

    @Override
    public void initData() {

        mViewBinding.loading.showLoading();
        startRequest();
    }

    public void startRequest(){
        Map<String , String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pre_page" , 10 + "");

        if(tag.equals("综合")){
            map.put("type" , "show_hot_all");
        }
        if(tag.equals("国内综艺")){
            map.put("type" , "show_hot_china");
        }
        if(tag.equals("国外综艺")){
            map.put("type" , "show_hot_foreign");
        }
        mPresenter.requestData(mActivity , map , page);
    }


    @Override
    public void requestData(MovieShowHotEntity entity, int page) {
        mViewBinding.loading.showContent();
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();


        if(page == 1) {
            beans.clear();
            beans.addAll(entity.getData().getData());

            adapter.update(beans);
        }else{
            beans.addAll(entity.getData().getData());
            adapter.update(beans);
        }

        if(entity.getData().getTotal() <= beans.size()){
            MovieShowHotEntity.DataBeanX.DataBean footerBean = new MovieShowHotEntity.DataBeanX.DataBean();
            footerBean.setUiType(EasyAdapter.TypeBean.FOOTER);
            beans.add(footerBean);
            mViewBinding.srl.setEnableLoadMore(false);
        }else{
            mViewBinding.srl.setEnableLoadMore(true);
        }
    }

    @Override
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        MyToast.show(mActivity ,string);
    }
}

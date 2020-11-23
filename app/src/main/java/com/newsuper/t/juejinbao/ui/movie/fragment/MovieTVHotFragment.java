package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.R;
import com.juejinchain.android.base.BaseEntity;
import com.juejinchain.android.databinding.FragmentMovietabandlistpageBinding;
import com.juejinchain.android.databinding.FragmentMovietvhotBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.MovieNewTabAndListAdapter;
import com.juejinchain.android.module.movie.adapter.MovieTVHotAdapter;
import com.juejinchain.android.module.movie.entity.MovieTVHotEntity;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieTVHotImpl;
import com.juejinchain.android.module.movie.presenter.impl.MovieTabAndListPageImpl;
import com.juejinchain.android.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ys.network.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * vip-影视-电视剧-热播新剧-更多-子界面
 */
public class MovieTVHotFragment extends BaseFragment<MovieTVHotImpl, FragmentMovietvhotBinding> implements MovieTVHotImpl.MvpView {

    private String tag;

    private List<MovieTVHotEntity.DataBeanX.DataBean> beans = new ArrayList<>();

    MovieTVHotAdapter adapter;

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
        mViewBinding.rv.setAdapter(adapter = new MovieTVHotAdapter(getActivity()));

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
            map.put("type" , "tv_hot_all");
        }
        if(tag.equals("国产剧")){
            map.put("type" , "tv_hot_china");
        }
        if(tag.equals("英美剧")){
            map.put("type" , "tv_hot_enus");
        }
        if(tag.equals("日剧")){
            map.put("type" , "tv_hot_janpan");
        }
        if(tag.equals("韩剧")){
            map.put("type" , "tv_hot_korea");
        }

        mPresenter.requestMovieTVHot(mActivity , map , page);
    }


    @Override
    public void requestMovieTVHot(MovieTVHotEntity entity, int page) {
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
            MovieTVHotEntity.DataBeanX.DataBean footerBean = new MovieTVHotEntity.DataBeanX.DataBean();
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

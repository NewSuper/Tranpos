package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentMoviecartonhotBinding;
import com.juejinchain.android.databinding.FragmentMovietvhotBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.MovieCartonHotAdapter;
import com.juejinchain.android.module.movie.adapter.MovieShowHotAdapter;
import com.juejinchain.android.module.movie.entity.MovieCartonHotEntity;
import com.juejinchain.android.module.movie.entity.MovieShowHotEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieCartonHotImpl;
import com.juejinchain.android.module.movie.presenter.impl.MovieShowHotImpl;
import com.juejinchain.android.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ys.network.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热播动漫子界面
 */
public class MovieCartonHotFragment extends BaseFragment<MovieCartonHotImpl, FragmentMoviecartonhotBinding> implements MovieCartonHotImpl.MvpView {

    private String tag;

    private List<MovieCartonHotEntity.DataBeanX.DataBean> beans = new ArrayList<>();

    MovieCartonHotAdapter adapter;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moviecartonhot, container, false);
    }

    @Override
    public void initView() {
        tag = getArguments().getString("tag");

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(adapter = new MovieCartonHotAdapter(getActivity()));

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
            map.put("type" , "cartoon_all");
        }
        if(tag.equals("国产")){
            map.put("type" , "cartoon_china");
        }
        if(tag.equals("日漫")){
            map.put("type" , "cartoon_janpan");
        }
        mPresenter.requestData(mActivity , map , page);
    }


    @Override
    public void requestData(MovieCartonHotEntity entity, int page) {
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
            MovieCartonHotEntity.DataBeanX.DataBean footerBean = new MovieCartonHotEntity.DataBeanX.DataBean();
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

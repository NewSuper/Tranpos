package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityMovieShownsoonBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.MovieShownSoonAdapter;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.entity.MovieNewTabRankEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieShownSoonImpl;
import com.juejinchain.android.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 即将上映
 */
public class MovieShownSoonActivity extends BaseActivity<MovieShownSoonImpl , ActivityMovieShownsoonBinding> implements MovieShownSoonImpl.MvpView {


    MovieShownSoonAdapter adapter;
    List<MovieNewTabRankEntity.DataBeanX.DataBean> dataList = new ArrayList<>();
    int page = 1;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_shownsoon;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mActivity);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(adapter = new MovieShownSoonAdapter(mActivity));

        mViewBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    public static void intentMe(Context context){
        Intent intent = new Intent(context , MovieShownSoonActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void requestNewTabRankList(MovieNewTabRankEntity entity, int page) {
        mViewBinding.loading.showContent();
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();


        if(page == 1) {
            dataList.clear();
            dataList.addAll(entity.getData().getData());

            adapter.update(dataList);
        }else{
            dataList.addAll(entity.getData().getData());
            adapter.update(dataList);
        }

        if(entity.getData().getTotal() <= dataList.size()){
            MovieNewTabRankEntity.DataBeanX.DataBean footerBean = new MovieNewTabRankEntity.DataBeanX.DataBean();
            footerBean.setUiType(EasyAdapter.TypeBean.FOOTER);
            dataList.add(footerBean);
            mViewBinding.srl.setEnableLoadMore(false);
        }else{
            mViewBinding.srl.setEnableLoadMore(true);
        }
    }


    public void startRequest(){
        Map<String , String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pre_page" , 10 + "");
        map.put("type" , "movie_soon");
        mPresenter.requestNewTabRankList(mActivity , map , page);
    }

    @Override
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        MyToast.show(mActivity ,string);
    }
}
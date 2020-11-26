package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovieNewtabBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieNewTabListAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieNewTabImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * vip-影视-子界面
 */
public class MovieNewTabFragment extends BaseFragment<MovieNewTabImpl, FragmentMovieNewtabBinding> implements MovieNewTabImpl.MvpView {

//    private PagerFragmentTabAdapter pagerFragmentTabAdapter;

    public String type;

    private MovieNewTabListAdapter movieNewTabListAdapter;
    private List<MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean> items = new ArrayList<>();

    private int page = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_newtab, container, false);
    }

    private String getTypeParam(){
        if(type.equals("电影")){
            return "hot_movie";
        }else if(type.equals("电视剧")){
            return "hot_tv";
        }else if(type.equals("综艺")){
            return "hot_show";
        }else if(type.equals("动漫")){
            return "hot_cartoon";
        }
        return "hot_movie";
    }

    @Override
    public void initView() {
        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.movieIndexRecommend(mActivity , getTypeParam() , page);
                mViewBinding.srl.setEnableLoadMore(true);
//                mViewBinding.srl.finishRefresh();
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.movieIndexRecommend(mActivity , getTypeParam() , page);
//                mViewBinding.srl.finishLoadMore();
            }
        });


        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(movieNewTabListAdapter = new MovieNewTabListAdapter(this));


        mViewBinding.loading.showLoading();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        page = 1;
        mPresenter.movieIndexRecommend(mActivity , getTypeParam() , page);
    }

    @Override
    public void movieIndexRecommend(MovieIndexRecommendEntity mMovieIndexRecommendEntity , int page) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();

        mViewBinding.loading.showContent();
        if(page == 1) {
            items.clear();
            items.addAll(mMovieIndexRecommendEntity.getData().getRecommend().getData());

            MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean headBean = new MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean();
            headBean.setUiType(EasyAdapter.TypeBean.HEADER);
            items.add(0, headBean);

            movieNewTabListAdapter.update(mMovieIndexRecommendEntity.getData(), items);
        }else{
            items.addAll(mMovieIndexRecommendEntity.getData().getRecommend().getData());
            movieNewTabListAdapter.update(mMovieIndexRecommendEntity.getData(), items);
        }

        if(mMovieIndexRecommendEntity.getData().getRecommend().getTotal() <= items.size()){
            mViewBinding.srl.setEnableLoadMore(false);
        }else{
            mViewBinding.srl.setEnableLoadMore(true);
        }

        if(mMovieIndexRecommendEntity.getData().getRecommend().getData().size() == 0){
            MyToast.show(mActivity , "没有更多数据");
        }
    }

    @Override
    public void movieIndexRecommendError(String message) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        MyToast.show(mActivity ,message);
    }


}

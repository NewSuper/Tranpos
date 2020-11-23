package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentMoveRecommendBinding;
import com.juejinchain.android.databinding.FragmentMovieTabRecommendBinding;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.adapter.ConditionAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.MovieMovieFilterAdapter;
import com.juejinchain.android.module.movie.adapter.MovieMovieRecommendAdapter;
import com.juejinchain.android.module.movie.adapter.TVAdapter;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieMovieFilterDataEntity;
import com.juejinchain.android.module.movie.entity.MovieMovieRecommendDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieTabFragmentImpl;
import com.juejinchain.android.module.movie.presenter.impl.MovieTabRecommendImpl;
import com.juejinchain.android.module.movie.utils.OnClickReturnStringListener;
import com.juejinchain.android.module.movie.utils.PreLoadUtils;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.DependentResourceDialog;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ys.network.base.BaseFragment;
import com.ys.network.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 影视非推荐页-------内的推荐页 老版废弃
 */
public class MovieTabRecommendFragment extends BaseFragment<MovieTabRecommendImpl, FragmentMovieTabRecommendBinding> implements MovieTabRecommendImpl.MvpView {
    private static RecyclerView.RecycledViewPool recommendRecyclerViewPool = new RecyclerView.RecycledViewPool();
    public static RecyclerView.RecycledViewPool recommendRecyclerViewPool2 = new RecyclerView.RecycledViewPool();

    private List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> items = new ArrayList<>();


    //电影adapter
    MovieMovieRecommendAdapter movieMovieRecommendAdapter;
    //综艺和动漫adapter
    MovieMovieFilterAdapter movieMovieFilterAdapter;
    //电视剧adapter
    TVAdapter tvAdapter;

    int page = 1;
    String category = "";
    String type = "电影";

    PreLoadUtils preLoadUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString("type");
        LogUtils.e("zy", type + " filter");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tab_recommend, container, false);
    }


    @Override
    public void initView() {
        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);


        if (type.equals("电影")) {
            movieMovieRecommendAdapter = new MovieMovieRecommendAdapter(mActivity, type, new MovieMovieRecommendAdapter.RadioListener() {
                @Override
                public void checkTag(String tag) {
                    category = tag;
                    page = 1;
                    mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                    mPresenter.requestRecommendData(mActivity, page, type, category);
                }
            }, new OnClickReturnStringListener() {
                @Override
                public void onClick(BeanMovieSearchItem movieSearchItem) {
                    if(getActivity() != null) {
//                        if (((MainActivity) getActivity()).dependentResourceDialog == null) {
//                            ((MainActivity) getActivity()).dependentResourceDialog = new DependentResourceDialog(mActivity);
//                        }
//                        ((MainActivity) getActivity()).dependentResourceDialog.show(name);
                        MovieSearchActivity.intentMe(mActivity , movieSearchItem.getTitle() , TextUtils.isEmpty(movieSearchItem.getImg()) ? null : movieSearchItem);
                    }
                }
            });
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
            linearLayoutManager3.setRecycleChildrenOnDetach(true);
            linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
            mViewBinding.rv.setLayoutManager(linearLayoutManager3);
            mViewBinding.rv.setRecycledViewPool(recommendRecyclerViewPool);
            mViewBinding.rv.setAdapter(movieMovieRecommendAdapter);

            preLoadUtils = new PreLoadUtils(mViewBinding.rv, items , 5, new PreLoadUtils.PreLoadMoreListener() {
                @Override
                public void preLoad() {
                    page++;
                    mPresenter.requestRecommendData(mActivity, page, type, category);
                }
            });
        }
        else if(type.equals("电视剧")){
            tvAdapter = new TVAdapter(mActivity, type ,new TVAdapter.RadioListener() {
                @Override
                public void checkTag(String tag) {
                    category = tag;
                    page = 1;
                    mPresenter.requestRecommendData(mActivity, page, type, category);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            //设置布局管理器， 参数gridLayoutManager对象
            mViewBinding.rv.setLayoutManager(linearLayoutManager);
            mViewBinding.rv.setNestedScrollingEnabled(false);
            mViewBinding.rv.setAdapter(tvAdapter);


        }
        //综艺和动漫
        else {
            //主数据界面
            movieMovieFilterAdapter = new MovieMovieFilterAdapter(context, type, new OnClickReturnStringListener() {
                @Override
                public void onClick(BeanMovieSearchItem movieSearchItem) {
//                    if(getActivity() != null) {
//                        if (((MainActivity) getActivity()).dependentResourceDialog == null) {
//                            ((MainActivity) getActivity()).dependentResourceDialog = new DependentResourceDialog(mActivity);
//                        }
//                        ((MainActivity) getActivity()).dependentResourceDialog.show(name);
//                    }
                    MovieSearchActivity.intentMe(mActivity , movieSearchItem.getTitle() , TextUtils.isEmpty(movieSearchItem.getImg()) ? null : movieSearchItem);
                }
            });
            WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
            //设置布局管理器， 参数gridLayoutManager对象
            gridLayoutManager.setRecycleChildrenOnDetach(true);
            mViewBinding.rv.setLayoutManager(gridLayoutManager);
            mViewBinding.rv.setNestedScrollingEnabled(false);
            mViewBinding.rv.setRecycledViewPool(recommendRecyclerViewPool2);
            mViewBinding.rv.setAdapter(movieMovieFilterAdapter);

            preLoadUtils = new PreLoadUtils(mViewBinding.rv, items , 6, new PreLoadUtils.PreLoadMoreListener() {
                @Override
                public void preLoad() {
                    page++;
                    mPresenter.requestRecommendData(mActivity, page, type, category);
                }
            });
        }


        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.requestRecommendData(mActivity, page, type, category);
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.requestRecommendData(mActivity, page, type, category);
            }
        });



    }

    @Override
    public void initData() {

    }

    @Override
    public void requestRecommendData(MovieMovieRecommendDataEntity testBean, Integer page, String category) {
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
//        LogUtils.e("zy" , testBean.getMsg());
//
//        items = testBean.getData().getData();
//
//
//        movieMovieRecommendAdapter.update(items);

        if (page == 1) {
            mViewBinding.rv.smoothScrollToPosition(0);
            items.clear();
            items.addAll(testBean.getData().getData());
            mViewBinding.srl.finishRefresh();

            MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
            dataBean.setUiType(EasyAdapter.TypeBean.HEADER);

            //电影
            if (movieMovieRecommendAdapter != null) {
                items.add(0, dataBean);
            }


            //电视剧
//            if(tvAdapter != null){
//                items.add(0, dataBean);
//            }

//            if (type.equals("电视剧")) {
//                if (movieMovieFilterAdapter != null) {
//                    items.add(0, dataBean);
//                }
//            }
        } else {
            items.addAll(testBean.getData().getData());
            mViewBinding.srl.finishLoadMore();
        }
        //更新数据
        //电影
        if (movieMovieRecommendAdapter != null) {
            preLoadUtils.loaded();
            movieMovieRecommendAdapter.setCategory(category);

            //移除footer
            for(MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean : items){
                if(dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER){
                    items.remove(dataBean);
                    break;
                }
            }

            if(page == 1) {


                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
                    items.add(dataBean);
                    movieMovieRecommendAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(false);

                }else {
                    movieMovieRecommendAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(true);
                }

            }else{
                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
                    items.add(dataBean);
                    movieMovieRecommendAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(false);

                }else {
                    movieMovieRecommendAdapter.updateAdd(items, testBean.getData().getData());
                }


            }


        }
        //电视剧
        else if (tvAdapter != null) {

            //移除footer
            for(MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean : items){
                if(dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER){
                    items.remove(dataBean);
                    break;
                }
            }

            if(page == 1) {

                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
//                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
//                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                    items.add(dataBean);
                    mViewBinding.srl.setEnableLoadMore(false);
                }else {
                    mViewBinding.srl.setEnableLoadMore(true);
                }
            }else{
                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
//                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
//                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                    items.add(dataBean);
                    mViewBinding.srl.setEnableLoadMore(false);
                }else {

                }
            }

            ArrayList<EasyAdapter.TypeBean> tvBeans = new ArrayList<>();

            EasyAdapter.TypeBean headBean = new EasyAdapter.TypeBean();
            headBean.setUiType(EasyAdapter.TypeBean.HEADER);
            tvBeans.add(headBean);

            EasyAdapter.TypeBean objectBean = new EasyAdapter.TypeBean();
            objectBean.setUiType(EasyAdapter.TypeBean.ITEM);
            objectBean.setObject(items);
            tvBeans.add(objectBean);

            tvAdapter.update(tvBeans);
        }
        //综艺动画
        else if (movieMovieFilterAdapter != null) {
            preLoadUtils.loaded();
            //移除footer
            for(MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean : items){
                if(dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER){
                    items.remove(dataBean);
                    break;
                }
            }


            if(page == 1) {


                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
//                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
//                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                    items.add(dataBean);
                    movieMovieFilterAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(false);
                }else{
                    movieMovieFilterAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(true);
                }
            }else{
                //添加footer
                if(testBean.getData().getLast_page() <= page){
//                if(testBean.getData().getData().size() == 0){
//                    MovieMovieRecommendDataEntity.DataBeanX.DataBean dataBean = new MovieMovieRecommendDataEntity.DataBeanX.DataBean();
//                    dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                    items.add(dataBean);
                    movieMovieFilterAdapter.update(items);
                    mViewBinding.srl.setEnableLoadMore(false);
                }else {
                    movieMovieFilterAdapter.updateAdd(items, testBean.getData().getData());
                }
            }

        }

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
        mPresenter.requestRecommendData(mActivity, page, type, category);
    }

    @Override
    public void error() {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(preLoadUtils != null) {
            preLoadUtils.destory();
            preLoadUtils = null;
        }
    }
}

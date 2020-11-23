package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentHhmhkBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.SearchMovieThirdAdapter;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.HHMhkFragmentImpl;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.otto.Subscribe;
import com.ys.network.base.BaseFragment;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 坏坏猫 废弃
 */
public class HHMhkFragment extends BaseFragment<HHMhkFragmentImpl, FragmentHhmhkBinding> implements HHMhkFragmentImpl.MvpView{

    //接口电影数据
    List<DependentResourcesDataEntity.DataBeanX.DataBean> myCinemaItems = new ArrayList<>();
    //我的影院适配
    SearchMovieThirdAdapter searchMovieJJBAdapter;

    private String type = "全部";
    private int page = 1;
    private String kw = "";

    //即将显示的kw
    private String showKw = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        kw = getArguments().getString("kw");
//        showKw = getArguments().getString("kw");
        kw = MovieSearchActivity.kw;
        showKw = MovieSearchActivity.kw;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hhmhk, container, false);
        return view;
    }

    @Override
    public void initView() {
        WrapContentGridViewManager gridLayoutManager2 = new WrapContentGridViewManager(context, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager2.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rvJjbmovie.setLayoutManager(gridLayoutManager2);
        //我的影院适配
        searchMovieJJBAdapter = new SearchMovieThirdAdapter(context);
        mViewBinding.rvJjbmovie.setAdapter(searchMovieJJBAdapter);

        mViewBinding.srl2.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mViewBinding.srl2.setEnableLoadMore(true);
                mViewBinding.rlBlank.setVisibility(View.GONE);
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });

        mViewBinding.srl2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });

        //点击切换影院
        mViewBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_MOVIENEXT));
            }
        });

    }

    @Override
    public void initData() {
        mViewBinding.rlBlank.setVisibility(View.VISIBLE);
        mViewBinding.tvLoadresult.setText("加载中");
        mViewBinding.btNext.setVisibility(View.GONE);
        requestData();
    }


    @Subscribe
    public void showKW(Message message){
        if(message.what == BusConstant.MOVIESEARHC_REFRESH_KW){
            if (mPresenter == null) {
                return;
            }

            page = 1;

            if (!TextUtils.isEmpty((String) message.obj)) {
                this.showKw = (String) message.obj;
            }


            if (getUserVisibleHint()) {
                kw = showKw;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewBinding.rlBlank.setVisibility(View.VISIBLE);
                        mViewBinding.tvLoadresult.setText("加载中");
                        mViewBinding.btNext.setVisibility(View.GONE);
                        requestData();
                    }
                });

            }
        }
    }

    boolean isMovieDetailAlertPullRefresh = false;
    //影视详情页内提醒好看影院下拉刷新
    @Subscribe
    public void movieDetailAlertPullRefresh(Message message){
        if(message.what == BusConstant.MOVIESEARCH_ALERTPULLRERESH){
            isMovieDetailAlertPullRefresh = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisible){
            if(isMovieDetailAlertPullRefresh){
                isMovieDetailAlertPullRefresh = false;
                requestData();
            }
        }
    }

    boolean isVisible = false;
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        this.isVisible = isVisible;
        if (isVisible) {
            if (!TextUtils.isEmpty(showKw)) {
                if (!showKw.equals(kw)) {
                    kw = showKw;

                    requestData();
                }
            }

            if(isMovieDetailAlertPullRefresh){
                isMovieDetailAlertPullRefresh = false;

                requestData();
            }
        }

    }

    //请求数据
    private void requestData(){
        //先清空数据
//        searchMovieJJBAdapter.update(new ArrayList());

        myCinemaItems.clear();
        searchMovieJJBAdapter.update(myCinemaItems);
        mViewBinding.rvJjbmovie.setVisibility(View.VISIBLE);

//        mViewBinding.rlBlank.setVisibility(View.GONE);
        mViewBinding.srl2.setEnableLoadMore(true);
        mPresenter.requestDependentResource(context, page, kw, type);

        //设置关键字
        searchMovieJJBAdapter.setKey(kw);
    }

    @Override
    public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page) {
//        movieLoadingDialog.hide();

        if (page == 1) {
            myCinemaItems.clear();
            myCinemaItems.addAll(dependentResourcesDataEntity.getData().getData());

        } else {
            myCinemaItems.addAll(dependentResourcesDataEntity.getData().getData());

        }

        for (DependentResourcesDataEntity.DataBeanX.DataBean dataBean : myCinemaItems) {
            if (dataBean.getUiType() == EasyAdapter.TypeBean.BLANK) {
                myCinemaItems.remove(dataBean);
                break;
            }
        }

        if (myCinemaItems.size() == 0) {
            mViewBinding.rlBlank.setVisibility(View.VISIBLE);
            mViewBinding.tvLoadresult.setText("未找到相关影视");
            mViewBinding.btNext.setVisibility(View.VISIBLE);

        } else {
            mViewBinding.rlBlank.setVisibility(View.GONE);
        }

        if(dependentResourcesDataEntity.getData().getData().size() < 9){
            mViewBinding.srl2.setEnableLoadMore(false);
        }


        mViewBinding.srl2.finishRefresh();
        mViewBinding.srl2.finishLoadMore();

        searchMovieJJBAdapter.update(myCinemaItems);
    }

    @Override
    public void error() {

    }
}

package com.newsuper.t.juejinbao.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomesearchresultBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailNotifyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEvent;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchResultImpl;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 全网搜索-模糊搜索界面
 */
public class HomeSearchResultFragment extends BaseFragment<HomeSearchResultImpl, FragmentHomesearchresultBinding> implements HomeSearchResultImpl.MvpView {

    EasyAdapter2 adapter;

    Subscription subscription;

    private String kw;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homesearchresult, container, false);
    }



    @Override
    public void initView() {
        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂无数据");

        kw = getArguments().getString("kw");

        EventBus.getDefault().register(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager);
        mViewBinding.rv.setAdapter(adapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {
            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_homesearch_result, viewGroup) {
                    HomeSearchResultEntity.DataBean dataBean;

                    TextView tv;
                    @Override
                    public void setData(Object object, int position) {
                        dataBean = (HomeSearchResultEntity.DataBean) object;
                        tv = itemView.findViewById(R.id.tv);

                        tv.setText(Utils.getSearchTitle2(mActivity , dataBean.getTitle() , kw) );
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);
//                        HomeSearchDetailActivity.intentMe(mActivity , dataBean.getTitle());
                        HomeSearchDetailNotifyEntity homeSearchDetailNotifyEntity = new HomeSearchDetailNotifyEntity();
                        homeSearchDetailNotifyEntity.setKw(dataBean.getTitle());
                        EventBus.getDefault().post(homeSearchDetailNotifyEntity);
                    }
                };
            }
        }));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void result(HomeSearchResultEvent homeSearchResultEvent){
        kw = homeSearchResultEvent.getKw();
        if(subscription != null){
            subscription.unsubscribe();
            subscription = null;
        }

        subscription = Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Map<String, String> params = new HashMap<>(3);
                        params.put("kw", kw);
                        params.put("pagesize", 20 + "");
                        mPresenter.search(params , mActivity);

                        mViewBinding.loading.showLoading();
                    }
                });




    }



    @Override
    public void initData() {
        Map<String, String> params = new HashMap<>(3);
        params.put("kw", kw);
        params.put("pagesize", 20 + "");
        mPresenter.search(params , mActivity);

        mViewBinding.loading.showLoading();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void search(HomeSearchResultEntity homeSearchResultEntity) {
        adapter.update(homeSearchResultEntity.getData());


        if(homeSearchResultEntity.getData().size() == 0){
            mViewBinding.loading.showEmpty();
        }else{
            mViewBinding.loading.showContent();
        }
    }



}

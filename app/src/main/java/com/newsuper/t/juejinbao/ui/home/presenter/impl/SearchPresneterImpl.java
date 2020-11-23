package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HotSearchEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.SearchPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SearchPresneterImpl extends BasePresenter<SearchPresenter.View> implements SearchPresenter {

    //搜索
    @Override
    public void search(Map<String, String> StringMap, Activity activity) {
        final SearchPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                searchByHome(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity channelEntity) {
                baseView.searchSuccess(channelEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //热门搜索
    @Override
    public void hotSearch(Map<String, String> StringMap, Activity activity) {
        final SearchPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HotSearchEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                hotSearch(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HotSearchEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HotSearchEntity>() {
            @Override
            public void next(HotSearchEntity hotSearchEntity) {
                baseView.hotSearchSuccess(hotSearchEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

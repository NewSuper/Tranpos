package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.home.entity.HomeSearchResultEntity;
import com.juejinchain.android.module.home.presenter.HotPointSearchResultPresenter;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class HotPointSearchResultImpl extends BasePresenter<HotPointSearchResultPresenter.MvpView> implements HotPointSearchResultPresenter{


    @Override
    public void search(Map<String, String> StringMap, Activity activity) {
        final HotPointSearchResultImpl.MvpView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeSearchResultEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHomeSearch(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeSearchResultEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeSearchResultEntity>() {
            @Override
            public void next(HomeSearchResultEntity homeSearchResultEntity) {
                if(homeSearchResultEntity.getCode() == 0) {
                    baseView.searchResult(homeSearchResultEntity);
                }
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

package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.HotPointSearchResultPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

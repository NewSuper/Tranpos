package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.TodayHotPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class TodayHotPresneterImpl extends BasePresenter<TodayHotPresenter.View> implements TodayHotPresenter {

    @Override
    public void getHotWordRank(Map<String, String> StringMap, Activity activity) {
        final View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<TodayHotEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHotWordRank(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TodayHotEntity>() {
            @Override
            public void next(TodayHotEntity entity) {
                baseView.getHotWordRankSuccess(entity);
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

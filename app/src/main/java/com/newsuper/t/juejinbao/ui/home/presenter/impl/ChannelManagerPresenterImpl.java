package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.home.NetInfo.ChannelInfo;
import com.juejinchain.android.module.home.presenter.ChannelManagerPresenter;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class ChannelManagerPresenterImpl extends BasePresenter<ChannelManagerPresenter.ChannelManagerView> implements ChannelManagerPresenter{


    @Override
    public void getChannelList(Map<String, String> StringMap, Activity activity) {
        final ChannelManagerView  baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ChannelInfo> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getRecommendChannel(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ChannelInfo>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ChannelInfo>() {
            @Override
            public void next(ChannelInfo loginEntity) {
                baseView.getChennelListSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onerror(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

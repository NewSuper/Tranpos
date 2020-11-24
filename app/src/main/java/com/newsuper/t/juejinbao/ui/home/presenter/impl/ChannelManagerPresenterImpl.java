package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.presenter.ChannelManagerPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

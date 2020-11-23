package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.base.BaseEntity;
import com.juejinchain.android.module.home.NetInfo.ChannelInfo;
import com.juejinchain.android.module.home.presenter.PicturePresenter;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class PictureFragmentPresenterImpl extends BasePresenter<PicturePresenter.View> implements PicturePresenter {

    @Override
    public void getPictureTabList(Map<String, String> map, Activity activity) {
        final PicturePresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ChannelInfo> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPictureList(map).map((new HttpResultFunc<ChannelInfo>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ChannelInfo>() {
            @Override
            public void next(ChannelInfo channelInfo) {
                baseView.getPictureTabSuccess(channelInfo);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showErrolr(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void setChannelList(Map<String, String> StringMap, Activity activity) {
        final PicturePresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                setChennelList(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity channelEntity) {
                baseView.saveSuccess(channelEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showErrolr(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

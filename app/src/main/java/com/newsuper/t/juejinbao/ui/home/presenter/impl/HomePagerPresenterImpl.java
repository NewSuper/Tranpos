package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.ClearUnreadEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.HomePagerPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class HomePagerPresenterImpl extends BasePresenter<HomePagerPresenter.HomePagerPresenterView> implements HomePagerPresenter {


    @Override
    public void getNewsList(Map<String, String> StringMap, Activity activity) {
        final HomePagerPresenter.HomePagerPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHomePageList(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity channelEntity) {
                baseView.getNewsList(channelEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.getNewsListError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void clearUnreadMsg(Map<String, String> StringMap, Activity activity) {
        final HomePagerPresenter.HomePagerPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ClearUnreadEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                clearHomeTabUnreadMsg(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ClearUnreadEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ClearUnreadEntity>() {
            @Override
            public void next(ClearUnreadEntity channelEntity) {
                baseView.clearUnreadMsgSuccess(channelEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


}

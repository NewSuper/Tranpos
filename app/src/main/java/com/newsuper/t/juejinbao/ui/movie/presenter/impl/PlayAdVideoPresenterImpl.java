package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class PlayAdVideoPresenterImpl extends BasePresenter<PlayAdVideoPresenter.View> implements PlayAdVideoPresenter {

    @Override
    public void watchAdVideoReward(Map<String, String> StringMap, Activity activity) {
        final PlayAdVideoPresenter.View baseView = getView();
        if (baseView == null || activity == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                watchAdVideoInTask(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.watchAdVideoRewardSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("zzz", e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

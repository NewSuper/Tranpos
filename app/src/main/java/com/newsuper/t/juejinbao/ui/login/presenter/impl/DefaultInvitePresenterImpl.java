package com.newsuper.t.juejinbao.ui.login.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.login.entity.DefaultIntviteCodeEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.DefaultInvitePresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class DefaultInvitePresenterImpl extends BasePresenter<DefaultInvitePresenter.View> implements DefaultInvitePresenter{

    @Override
    public void getDefaultInviteCode(Map<String, String> StringMap, Activity activity) {
        final DefaultInvitePresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<DefaultIntviteCodeEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getInvitecodeByChannel(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<DefaultIntviteCodeEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<DefaultIntviteCodeEntity>() {
            @Override
            public void next(DefaultIntviteCodeEntity loginEntity) {
                baseView.onDefaultInviteCodeSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }
}

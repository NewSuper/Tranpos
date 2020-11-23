package com.newsuper.t.juejinbao.ui.splash.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.AuditEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class WelcomePresenterImpl extends BasePresenter<WelcomePresenter.View> implements WelcomePresenter {
    @Override
    public void getAuditData(Activity activity) {
        final WelcomePresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<AuditEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getAuditData().map((new HttpResultFunc<AuditEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AuditEntity>() {
            @Override
            public void next(AuditEntity auditEntity) {
                baseView.onAuditDataBack(auditEntity);
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

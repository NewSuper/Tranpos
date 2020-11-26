package com.newsuper.t.juejinbao.ui.splash.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.OwnADEntity;
import com.newsuper.t.juejinbao.ui.home.entity.AuditEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class ADOpenPresenterImpl extends BasePresenter<ADOpenPresenter.View> implements ADOpenPresenter{
    @Override
    public void loadOwnADAndNestTimeOpenType(Activity activity) {
        final ADOpenPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<OwnADEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getOpenADtypeAndData().map((new HttpResultFunc<OwnADEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<OwnADEntity>() {
            @Override
            public void next(OwnADEntity loginEntity) {
                baseView.loadAdDateSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onerror(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void commitAdData(Map<String, String> StringMap, Activity activity) {
        final ADOpenPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                adDataCommitByThird(StringMap).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity loginEntity) {
                baseView.onAdDataCommitSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onerror(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getAuditData(Activity activity) {
        final ADOpenPresenter.View baseView = getView();
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

    @Override
    public void clickAdCount(Map<String, String> StringMap, Activity activity) {
        final ADOpenPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getClickADCount(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity loginEntity) {
                baseView.clickAdCountBack(loginEntity);
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

package com.newsuper.t.juejinbao.ui.ad;

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


class BaoquGamePrensenterImpl extends BasePresenter<BaoquGamePrensenter.BaoquGamePrensenterView> implements BaoquGamePrensenter {


    @Override
    public void playGameGetCoin(Map<String, String> StringMap, Activity activity, OnDataBackLintener lintener) {

        if (lintener == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                playGameGetCoin(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity loginEntity) {
                lintener.onSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                lintener.onEorr(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void commitAdData(Map<String, String> StringMap, Activity activity, OnDataBackLintener lintener){

        if (lintener == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                adDataCommitByThird(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity loginEntity) {
                lintener.onSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                lintener.onEorr(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

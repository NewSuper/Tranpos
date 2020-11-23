package com.newsuper.t.juejinbao.ui.task.presenter;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.task.entity.SleepEarningEntity;
import com.juejinchain.android.module.task.entity.SleepEarningEntity2;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;
import com.ys.network.utils.ToastUtils;

import java.io.Serializable;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SleepEarningPresenterImpl extends BasePresenter<SleepEarningPresenterImpl.MvpView> {

    public void getSleepEarningInfo(Context context) {
        rx.Observable<SleepEarningEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getSleepEarningInfo().map((new HttpResultFunc<SleepEarningEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SleepEarningEntity>() {
            @Override
            public void next(SleepEarningEntity bean) {
                if (bean.getCode() == 0) {
                    getView().getSleepEarningInfo(bean);
                } else if (bean.getCode() == 1) {
                    ToastUtils.getInstance().show(context, bean.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void startSleep(Context context) {
        rx.Observable<SleepEarningEntity2> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .startSleep().map((new HttpResultFunc<SleepEarningEntity2>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SleepEarningEntity2>() {
            @Override
            public void next(SleepEarningEntity2 bean) {
                if (bean.getCode() == 0) {
                    getView().startSleep(bean);
                } else if (bean.getCode() == 1) {
                    ToastUtils.getInstance().show(context, bean.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void getReward(Context context) {
        rx.Observable<SleepEarningEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getReward().map((new HttpResultFunc<SleepEarningEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SleepEarningEntity>() {
            @Override
            public void next(SleepEarningEntity bean) {
                if (bean.getCode() == 0) {
                    getView().getReward(bean);
                } else if (bean.getCode() == 1) {
                    ToastUtils.getInstance().show(context, bean.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void videoReward(Map<String, String> StringMap, Context context) {
        rx.Observable<SleepEarningEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .videoReward(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<SleepEarningEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SleepEarningEntity>() {
            @Override
            public void next(SleepEarningEntity bean) {
                if (bean.getCode() == 0) {
                    getView().videoReward(bean);
                } else if (bean.getCode() == 1) {
                    ToastUtils.getInstance().show(context, bean.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    public interface MvpView {

        void error(String str);

        void getSleepEarningInfo(SleepEarningEntity data);

        void getReward(SleepEarningEntity data);

        void startSleep(SleepEarningEntity2 data);

        void videoReward(Serializable serializable);
    }
}

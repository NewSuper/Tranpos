package com.newsuper.t.juejinbao.ui.my.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserInfoEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.PrivacyPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class PrivacyPresenterImpl extends BasePresenter<PrivacyPresenter.View> implements PrivacyPresenter{

    @Override
    public void settingPrivacy(Map<String, String> StringMap, Activity activity) {
        final PrivacyPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                updateInfo(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.settingPrivacySuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getUserInfo(Activity activity) {
        final PrivacyPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UserInfoEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUserInfo().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserInfoEntity>() {
            @Override
            public void next(UserInfoEntity userProfileEntity) {
                baseView.getUserInfoSuccess(userProfileEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    @Override
    public void setAllSwitch(Activity activity) {
        final PrivacyPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                updateAllSwitch().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinEntity>() {
            @Override
            public void next(GetCoinEntity entity) {
                baseView.setAllSwitchBack(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, true);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }
}

package com.newsuper.t.juejinbao.ui.login.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.BindMobilPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BindMobilPresenterImpl extends BasePresenter<BindMobilPresenter.LoginView> implements BindMobilPresenter {
    //验证码登录
    @Override
    public void Login(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                bindMoble(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showListSuccess(loginEntity);
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

    //获取短信验证码
    @Override
    public void GetCode(Map<String, String> StringMap, Activity activity) {

        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<SetAndChangePswEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPhoneCode(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<SetAndChangePswEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SetAndChangePswEntity>() {
            @Override
            public void next(SetAndChangePswEntity setAndChangePswEntity) {
                baseView.showCode(setAndChangePswEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error==========》》》》》》: " + errResponse);
                Log.i("zzz", "具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //绑定QQ
    @Override
    public void bindQQ(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                bindQQMoble(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.bindQQ(loginEntity);
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
    public void BindCode(Map<String, String> StringMap, Activity activity) {
        final BindMobilPresenter.LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BindInviterEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                bindCode(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BindInviterEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindInviterEntity>() {
            @Override
            public void next(BindInviterEntity loginEntity) {
                baseView.showBindCode(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error==========》》》》》》: " + errResponse);
                Log.i("zzz", "绑定邀请码具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


}

package com.newsuper.t.juejinbao.ui.login.presenter.impl;

import android.app.Activity;
import android.util.Log;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.PhoneIsRegistEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.SetAndChangePresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SetAndChangePresenterImpl extends BasePresenter<SetAndChangePresenter.SetAndChangeView> implements SetAndChangePresenter {
    //获取手机验证码
    @Override
    public void SetPsw(Map<String, String> StringMap, Activity activity) {
        final SetAndChangeView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<SetAndChangePswEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPhoneCode(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<SetAndChangePswEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SetAndChangePswEntity>() {
            @Override
            public void next(SetAndChangePswEntity setAndChangePswEntity) {
                baseView.showSetOrChangePswCode(setAndChangePswEntity);
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


    //提交
    @Override
    public void CommitPsw(Map<String, String> StringMap, Activity activity) {
        final SetAndChangeView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                commitNewPsw(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showSetOrChangePsw(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                Log.i("zzz", "具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void CommitLoginPsw(Map<String, String> StringMap, Activity activity) {
        final SetAndChangeView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                commitLoginNewPsw(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showLoginPsw(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
                Log.i("zzz", "具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void BindCode(Map<String, String> StringMap, Activity activity) {
        final SetAndChangeView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BindInviterEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                bindCode(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BindInviterEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindInviterEntity>() {
            @Override
            public void next(BindInviterEntity loginEntity) {
                baseView.showBindCodeSuccess(loginEntity);
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

    //检测当前账号是否注册
    @Override
    public void PhoneIsRegistCode(Map<String, String> StringMap, Activity activity) {
        final SetAndChangeView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<PhoneIsRegistEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                phoneIsRegistCode(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<PhoneIsRegistEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PhoneIsRegistEntity>() {
            @Override
            public void next(PhoneIsRegistEntity phoneIsRegistEntity) {
                baseView.showPhoneIsRegistCodeSuccess(phoneIsRegistEntity);
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

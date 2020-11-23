package com.newsuper.t.juejinbao.ui.login.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.LoginSetInviteCodePresenter;
import com.newsuper.t.juejinbao.ui.movie.entity.BindThirdEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class LoginSetInviteCodeImpl extends BasePresenter<LoginSetInviteCodePresenter.View> implements LoginSetInviteCodePresenter {
    @Override
    public void BindCode(Map<String, String> StringMap, Activity activity) {
        final LoginSetInviteCodePresenter.View baseView = getView();
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

    //绑定微信
    @Override
    public void BindWeChat(Map<String, String> StringMap, Activity activity) {
        final LoginSetInviteCodePresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BindThirdEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                bindWechat(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BindThirdEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BindThirdEntity>() {
            @Override
            public void next(BindThirdEntity bindThirdEntity) {
                baseView.bindWechatSuccess(bindThirdEntity);
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
}

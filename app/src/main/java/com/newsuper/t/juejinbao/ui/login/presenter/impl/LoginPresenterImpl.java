package com.newsuper.t.juejinbao.ui.login.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.login.presenter.LoginPresenter;
import com.newsuper.t.juejinbao.ui.movie.entity.BindThirdEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class LoginPresenterImpl extends BasePresenter<LoginPresenter.LoginView> implements LoginPresenter {
    //验证码登录
    @Override
    public void Login(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                login(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LoginEntity>()));
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
    public void SetPsw(Map<String, String> StringMap, Activity activity) {

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
                baseView.showSetOrChangePsw(setAndChangePswEntity);
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

    //密码登录
    @Override
    public void LoginPsw(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                loginPsw(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showListSuccess(loginEntity);
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

    //一建登录
    @Override
    public void LoginOnePsw(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                loginOnePsw(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showLoginOnePswSuccess(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error==========》》》》》》: " + errResponse);
                Log.i("zzz", "一键登录error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //微信登录
    @Override
    public void LoginWX(Map<String, String> map, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                loginWX(map).map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showOtherSuccess(loginEntity);
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

    //QQ登录
    @Override
    public void LoginQQ(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                loginQQ(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
                baseView.showOtherSuccess(loginEntity);
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

    //绑定微信
    @Override
    public void BindWeChat(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
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

    @Override
    public void BindCode(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
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

    //是否显示微信或者QQ
    @Override
    public void IsShowWchatorQQ(Map<String, String> StringMap, Activity activity) {
        final LoginView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<IsShowQQEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                isShowWechat(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<IsShowQQEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<IsShowQQEntity>() {
            @Override
            public void next(IsShowQQEntity loginEntity) {
                baseView.showIsShowWchatorQQ(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "绑定邀请码具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

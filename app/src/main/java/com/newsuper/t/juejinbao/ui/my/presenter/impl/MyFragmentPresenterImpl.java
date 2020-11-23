package com.newsuper.t.juejinbao.ui.my.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.my.entity.AdFirstEntity;
import com.newsuper.t.juejinbao.ui.my.entity.AdTwoEntity;
import com.newsuper.t.juejinbao.ui.my.entity.LookOrOnTachEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyUnreadMessageEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.MyFragmentPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class MyFragmentPresenterImpl extends BasePresenter<MyFragmentPresenter.SettingView> implements MyFragmentPresenter {
    //获取用户所有信息
    @Override
    public void getUserData(Map<String, String> StringMap, Activity activity) {

        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UserDataEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUserData(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UserDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserDataEntity>() {
            @Override
            public void next(UserDataEntity userDataEntity) {


                baseView.showLoginOutSuccess(userDataEntity);

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:3==============>>>>> " + errResponse);
                Log.i("zzz", "error:3==============>>>>> " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取轮播的广告
    @Override
    public void getAdFirst(Map<String, String> StringMap, Activity activity) {
        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<AdFirstEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getAdFirst(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<AdFirstEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AdFirstEntity>() {
            @Override
            public void next(AdFirstEntity adFirstEntity) {
                baseView.showAdFirstSuccess(adFirstEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:广告1==============>>>>> " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取带图轮播的广告
    @Override
    public void getAdTwo(Map<String, String> StringMap, Activity activity) {
        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<AdTwoEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getAdTwo(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<AdTwoEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AdTwoEntity>() {
            @Override
            public void next(AdTwoEntity adTwoEntity) {
                baseView.showAdTwoSuccess(adTwoEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:广告2==============>>>>> " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取浏览或者点击统计
    @Override
    public void getLookOrOnTach(Map<String, String> StringMap, Activity activity) {
        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LookOrOnTachEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getLookOrOnTach(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LookOrOnTachEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LookOrOnTachEntity>() {
            @Override
            public void next(LookOrOnTachEntity lookOrOnTachEntity) {
                baseView.showLookOrOnTachSuccess(lookOrOnTachEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:统计==============>>>>> " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //未读消息
    @Override
    public void getMessge(Map<String, String> StringMap, Activity activity) {
        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<MyUnreadMessageEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getMyMessage(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<MyUnreadMessageEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MyUnreadMessageEntity>() {
            @Override
            public void next(MyUnreadMessageEntity myUnreadMessageEntity) {
                baseView.showMessageSuccess(myUnreadMessageEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:未读消息==============>>>>> " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //等级弹窗或者商品弹窗已弹出
    @Override
    public void getGiftTalk(Map<String, String> StringMap, Activity activity) {
        final MyFragmentPresenter.SettingView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LookOrOnTachEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getGiftAndGoods(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LookOrOnTachEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LookOrOnTachEntity>() {
            @Override
            public void next(LookOrOnTachEntity lookOrOnTachEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:等级弹窗或者商品弹窗已弹出==============>>>>> " + errResponse);
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

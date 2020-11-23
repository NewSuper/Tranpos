package com.newsuper.t.juejinbao.ui.my.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserProfileEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.UserInfoPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class UserInfoImpl extends BasePresenter<UserInfoPresenter.View> implements UserInfoPresenter {

    @Override
    public void getUserProfile(Map<String, String> StringMap, Activity activity) {
        final UserInfoPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UserProfileEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUserProfile(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserProfileEntity>() {
            @Override
            public void next(UserProfileEntity userProfileEntity) {
                baseView.getUserProfileSuccess(userProfileEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    //用户信息设置
    public void setUserInfo(Activity activity , Map<String, String> StringMap){
        final UserInfoPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                updateInfo(StringMap).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity baseEntity) {
                baseView.uploadImage(baseEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取隐私设置
//    public void getUserInfo(Activity activity){
//        final View baseView = getView();
//        if (baseView == null) {
//            return;
//        }
//
//        rx.Observable<UserInfoEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
//                getUserInfo().map((new HttpResultFunc<>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserInfoEntity>() {
//            @Override
//            public void next(UserInfoEntity userProfileEntity) {
//                baseView.getUserInfo(userProfileEntity);
//            }
//
//            @Override
//            public void error(String target, Throwable e, String errResponse) {
//                baseView.showError(errResponse);
//            }
//        }, activity, true);
//        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
//        addSubscrebe(rxSubscription);
//    }


}

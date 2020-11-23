package com.newsuper.t.juejinbao.ui.my.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendRewardValueEntity;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendWheelEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyInviteFriendListEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class InviteFriendImpl  extends BasePresenter<InviteFriendImpl.MvpView> {


    //轮播滚动接口
    public void inviteFriendWheel(Context context){
        rx.Observable<InviteFriendWheelEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).inviteFriendWheel(new HashMap<>()).map((new HttpResultFunc<InviteFriendWheelEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<InviteFriendWheelEntity>() {
            @Override
            public void next(InviteFriendWheelEntity inviteFriendWheelEntity) {
                getView().inviteFriendWheel(inviteFriendWheelEntity);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //我的邀请好友列表
    public void myInviteFriendList(Context context){
        rx.Observable<MyInviteFriendListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).myInviteFriendList(new HashMap<>()).map((new HttpResultFunc<MyInviteFriendListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MyInviteFriendListEntity>() {
            @Override
            public void next(MyInviteFriendListEntity myInviteFriendListEntity) {
                getView().myInviteFriendList(myInviteFriendListEntity);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //邀请好友奖励规则
    public void inviteFriendRewardValue(Context context){
        rx.Observable<InviteFriendRewardValueEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).inviteFriendRewardValue(new HashMap<>()).map((new HttpResultFunc<InviteFriendRewardValueEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<InviteFriendRewardValueEntity>() {
            @Override
            public void next(InviteFriendRewardValueEntity inviteFriendRewardValueEntity) {
                getView().inviteFriendRewardValue(inviteFriendRewardValueEntity);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //获取日期
    public String getDateString(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss\nyyyy-MM-dd");
        String date = sdf.format(timeStamp);
        return date;
    }

    public interface MvpView {
        public void inviteFriendWheel(InviteFriendWheelEntity inviteFriendWheelEntity);
        public void myInviteFriendList(MyInviteFriendListEntity myInviteFriendListEntity);
        public void inviteFriendRewardValue(InviteFriendRewardValueEntity mInviteFriendRewardValueEntity);
        public void error(String error);
    }

}
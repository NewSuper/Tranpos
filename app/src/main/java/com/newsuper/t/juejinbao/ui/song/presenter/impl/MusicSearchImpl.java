package com.newsuper.t.juejinbao.ui.song.presenter.impl;


import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.AddSongEntity;
import com.juejinchain.android.module.song.entity.MusicHotListEntity;
import com.juejinchain.android.module.song.entity.MusicSearchFromEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MusicSearchImpl extends BasePresenter<MusicSearchImpl.MvpView> {

    public void getSearchFrom(Context context) {
        rx.Observable<MusicSearchFromEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getSearchFrom().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicSearchFromEntity>() {
            @Override
            public void next(MusicSearchFromEntity bean) {
                getView().getSearchFrom(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void getSearchHotList(Context context) {
        rx.Observable<MusicHotListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getHotList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicHotListEntity>() {
            @Override
            public void next(MusicHotListEntity bean) {
                getView().getSearchHotList(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void addSongs(Context context,Map<String, Object> map) {
        rx.Observable<AddSongEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .addSongs(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<AddSongEntity>() {
            @Override
            public void next(AddSongEntity bean) {
                getView().addSongs(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView {

        void error(String str);

        void getSearchFrom(MusicSearchFromEntity data);

        void getSearchHotList(MusicHotListEntity data);

        void addSongs(AddSongEntity data);
    }
}

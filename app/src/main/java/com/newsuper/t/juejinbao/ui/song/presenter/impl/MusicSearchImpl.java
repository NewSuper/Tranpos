package com.newsuper.t.juejinbao.ui.song.presenter.impl;


import android.content.Context;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.song.entity.AddSongEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHotListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicSearchFromEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

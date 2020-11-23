package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import rx.Subscriber;
import rx.Subscription;

public class MovieTabAndListImpl extends BasePresenter<MovieTabAndListImpl.MvpView> {


    public void getMovieNewPlaylist(Context context ){
        rx.Observable<V2PlayListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieNewPlaylist().map((new HttpResultFunc<V2PlayListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<V2PlayListEntity>() {
            @Override
            public void next(V2PlayListEntity testBean) {
                getView().getMovieNewPlaylist(testBean);
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
        void getMovieNewPlaylist(V2PlayListEntity entity);
        void error(String string);
    }
}

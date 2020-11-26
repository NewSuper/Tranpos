package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.V2PlayListEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

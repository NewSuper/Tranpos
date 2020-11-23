package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieShowHotEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieShowHotImpl extends BasePresenter<MovieShowHotImpl.MvpView> {

    public void requestData(Context context, Map<String , String> map , int page){
        rx.Observable<MovieShowHotEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieShowHot(map).map((new HttpResultFunc<MovieShowHotEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieShowHotEntity>() {
            @Override
            public void next(MovieShowHotEntity testBean) {
                getView().requestData(testBean , page);
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
        void requestData(MovieShowHotEntity entity, int page);
        void error(String string);
    }
}

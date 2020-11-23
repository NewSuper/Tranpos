package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieCartonHotEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieCartonHotImpl extends BasePresenter<MovieCartonHotImpl.MvpView> {

    public void requestData(Context context, Map<String , String> map , int page){
        rx.Observable<MovieCartonHotEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieCartonHot(map).map((new HttpResultFunc<MovieCartonHotEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieCartonHotEntity>() {
            @Override
            public void next(MovieCartonHotEntity testBean) {
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
        void requestData(MovieCartonHotEntity entity, int page);
        void error(String string);
    }
}

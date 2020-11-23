package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieNewTabRankEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieShownSoonImpl extends BasePresenter<MovieShownSoonImpl.MvpView> {

    public void requestNewTabRankList(Context context, Map<String , String> map , int page){
        rx.Observable<MovieNewTabRankEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieNewTabRankList(map).map((new HttpResultFunc<MovieNewTabRankEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieNewTabRankEntity>() {
            @Override
            public void next(MovieNewTabRankEntity testBean) {
                getView().requestNewTabRankList(testBean , page);
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
        void requestNewTabRankList(MovieNewTabRankEntity entity, int page);
        void error(String string);
    }
}

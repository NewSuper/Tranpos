package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewTabRankEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieNewTabRankImpl extends BasePresenter<MovieNewTabRankImpl.MvpView> {


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

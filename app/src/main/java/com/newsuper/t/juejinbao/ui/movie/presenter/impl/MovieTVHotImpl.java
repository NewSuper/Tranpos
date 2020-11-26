package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTVHotEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieTVHotImpl extends BasePresenter<MovieTVHotImpl.MvpView> {

    public void requestMovieTVHot(Context context, Map<String , String> map , int page){
        rx.Observable<MovieTVHotEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieTVHot(map).map((new HttpResultFunc<MovieTVHotEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieTVHotEntity>() {
            @Override
            public void next(MovieTVHotEntity testBean) {
                getView().requestMovieTVHot(testBean , page);
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
        void requestMovieTVHot(MovieTVHotEntity entity, int page);
        void error(String string);
    }
}

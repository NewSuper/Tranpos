package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowHotEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

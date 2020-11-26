package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCartonHotEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieNewFilterEntity;
import com.juejinchain.android.module.movie.entity.MovieNewFilterTagEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieNewFilterImpl extends BasePresenter<MovieNewFilterImpl.MvpView> {

    //请求筛选数据
    public void requestFilterTag(Context context){
        rx.Observable<MovieNewFilterTagEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieNewFilterTag().map((new HttpResultFunc<MovieNewFilterTagEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieNewFilterTagEntity>() {
            @Override
            public void next(MovieNewFilterTagEntity testBean) {
                getView().requestFilterTag(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
//                baseView.error(errResponse);
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }


    //请求筛选数据
    public void requestFilterData(Context context , Map<String , String> map , int page){
        rx.Observable<MovieNewFilterEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieNewFilter(map).map((new HttpResultFunc<MovieNewFilterEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieNewFilterEntity>() {
            @Override
            public void next(MovieNewFilterEntity testBean) {
                getView().requestFilterData(testBean , page);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
//                baseView.error(errResponse);
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    public interface MvpView {
        void requestFilterTag(MovieNewFilterTagEntity entity);
        void requestFilterData(MovieNewFilterEntity entity, int page);
        void error(String response);

    }

}
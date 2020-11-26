package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieRecommendDataEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class MovieTabRecommendImpl extends BasePresenter<MovieTabRecommendImpl.MvpView> {


    //请求电影-推荐数据
    public void requestRecommendData(Context context , Integer page , String type , final String category ){
        rx.Observable<MovieMovieRecommendDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieMovieRecommend(page , type , category).map((new HttpResultFunc<MovieMovieRecommendDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieMovieRecommendDataEntity>() {
            @Override
            public void next(MovieMovieRecommendDataEntity testBean) {
                getView().requestRecommendData(testBean , page , category);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
//                baseView.error(errResponse);
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    public interface MvpView {
        void requestRecommendData(MovieMovieRecommendDataEntity testBean, Integer page, String category);
        void error();
    }
}

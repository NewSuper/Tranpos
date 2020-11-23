package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieInfoEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.RecommendRankingEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.UploadMovieDetailBean;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.io.Serializable;
import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class RecommentVideoListImpl extends BasePresenter<RecommentVideoListImpl.MvpView> {


    //影视推荐排行榜单
    public void requetRankingList(Context context, HashMap hashMap){
        rx.Observable<RecommendRankingEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieRankingList(hashMap).map((new HttpResultFunc<RecommendRankingEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<RecommendRankingEntity>() {
            @Override
            public void next(RecommendRankingEntity testBean) {
                getView().requetRankingListSuccess(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //影视推荐排行榜单
    public void getMovieInfo(Context context, HashMap hashMap){
        rx.Observable<MovieInfoEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieInfo(hashMap).map((new HttpResultFunc<MovieInfoEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieInfoEntity>() {
            @Override
            public void next(MovieInfoEntity testBean) {
                getView().getMovieInfoSuccess(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //影视推荐排行榜单
    public void uploadMovieDetail(Context context, HashMap hashMap){


        rx.Observable<UploadMovieDetailBean> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getUploadMovieDetail(HttpRequestBody.generateRequestQuery(hashMap)).map((new HttpResultFunc<UploadMovieDetailBean>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UploadMovieDetailBean>() {
            @Override
            public void next(UploadMovieDetailBean uploadMovieDetailBean) {
                    getView().uploadMovieDetail(uploadMovieDetailBean);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().onUploadMovieDetail(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }




    public interface MvpView {
        void requetRankingListSuccess(Serializable entity);
        void getMovieInfoSuccess(Serializable entity);
        void uploadMovieDetail(UploadMovieDetailBean uploadMovieDetailBean);
        void onUploadMovieDetail(String errorString);
        void error(String string);
    }
}

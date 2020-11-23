package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieLooklDataEntity;
import com.juejinchain.android.module.movie.entity.MovieNewRecommendEntity;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity2;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class RecommendFragmentImpl extends BasePresenter<RecommendFragmentImpl.MvpView> {

    //请求海报数据
    public void requestPosterData2(Context context){
        HashMap<String , Object> map = new HashMap<>();
        map.put("type" , "电影");

        rx.Observable<MoviePostDataEntity2> observable = RetrofitManager.getInstance(context).create(ApiService.class).moviePostData2(map).map((new HttpResultFunc<MoviePostDataEntity2>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MoviePostDataEntity2>() {
            @Override
            public void next(MoviePostDataEntity2 testBean) {
                getView().requestPosterData2(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //请求推荐数据
    public void requestPosterData(Context context){
        rx.Observable<MoviePostDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).moviePostData().map((new HttpResultFunc<MoviePostDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MoviePostDataEntity>() {
            @Override
            public void next(MoviePostDataEntity testBean) {
                getView().requestPosterData(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
//                baseView.error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //请求新推荐数据
    public void requestNewRecommendData(Context context){
        rx.Observable<MovieNewRecommendEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).movieNewRecommendData().map((new HttpResultFunc<MovieNewRecommendEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieNewRecommendEntity>() {
            @Override
            public void next(MovieNewRecommendEntity movieNewRecommendEntity) {
                if(movieNewRecommendEntity.getCode() == 0) {
                    getView().requestNewRecommendData(movieNewRecommendEntity);
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
//                baseView.error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //观影用户数据
    public void requestLookData(Context context){
        rx.Observable<MovieLooklDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getLookData().map((new HttpResultFunc<MovieLooklDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieLooklDataEntity>() {
            @Override
            public void next(MovieLooklDataEntity testBean) {
                getView().requestLookData(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
//                baseView.error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //影视推荐排行榜单
//    public void requetMovieRotation(Context context){
//        rx.Observable<MovieRotationListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieRotationList().map((new HttpResultFunc<MovieRotationListEntity>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieRotationListEntity>() {
//            @Override
//            public void next(MovieRotationListEntity testBean) {
//                getView().requestMovieRotationList(testBean);
//            }
//
//            @Override
//            public void error(String target ,Throwable e,String errResponse) {
//                getView().error(errResponse);
//            }
//        }, context, false);
//        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
//        addSubscrebe(rxSubscription);
//    }





    public interface MvpView {
        void requestPosterData2(MoviePostDataEntity2 moviePostDataEntity2);
        void requestPosterData(MoviePostDataEntity moviePostDataEntity);
        void requestLookData(MovieLooklDataEntity movieLooklDataEntity);

//        void requestMovieRotationList(MovieRotationListEntity entity);
        void requestNewRecommendData(MovieNewRecommendEntity movieNewRecommendEntity);

        void error(String string);
//        public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity);
    }

}

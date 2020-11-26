package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewTabRankEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SearchHistoryImpl extends BasePresenter<SearchHistoryImpl.MvpView> {

    //请求热搜榜
//    public void requestHotSearch(Context context){
//        rx.Observable<HotSearchDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getHotSearch().map((new HttpResultFunc<HotSearchDataEntity>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HotSearchDataEntity>() {
//            @Override
//            public void next(HotSearchDataEntity testBean) {
//                getView().requestHotSearch(testBean);
//            }
//
//            @Override
//            public void error(String target ,Throwable e,String errResponse) {
//                getView().error();
//            }
//        }, context, false);
//        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
//        addSubscrebe(rxSubscription);
//    }

    //热搜榜V2
    public void requestHotSearchList(Context context){
        Map<String , String> map = new HashMap<>();
        map.put("page" , 1 + "");
        map.put("pre_page" , 10 + "");
        map.put("type" , "movie_real_time_hotest");

        rx.Observable<MovieNewTabRankEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieNewTabRankList(map).map((new HttpResultFunc<MovieNewTabRankEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieNewTabRankEntity>() {
            @Override
            public void next(MovieNewTabRankEntity testBean) {
                getView().requestHotSearchList(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    public interface MvpView{

        void requestHotSearchList(MovieNewTabRankEntity movieNewTabRankEntity);
        void error();

    }
}

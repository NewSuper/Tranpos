package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.bean.V2MovieSearchEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class SearchResultImpl extends BasePresenter<SearchResultImpl.MvpView> {

    //请求热搜榜
//    public void requestSearchResult(Context context , String kw){
//        rx.Observable<SearchResultDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieSearch(kw).map((new HttpResultFunc<SearchResultDataEntity>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SearchResultDataEntity>() {
//            @Override
//            public void next(SearchResultDataEntity testBean) {
//                getView().requestSearchResult(testBean , kw);
//            }
//
//            @Override
//            public void error(String target ,Throwable e,String errResponse) {
//                if(getView() != null) {
//                    getView().error();
//                }
//            }
//        }, context, false);
//        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
//        addSubscrebe(rxSubscription);
//    }

    public void requestSearchResult(Context context , String keyword){
        rx.Observable<V2MovieSearchEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getDoubanSearch(keyword).map((new HttpResultFunc<V2MovieSearchEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<V2MovieSearchEntity>() {
            @Override
            public void next(V2MovieSearchEntity testBean) {
                getView().requestSearchResult(testBean , keyword);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                if(getView() != null) {
                    getView().error();
                }
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView{
        void requestSearchResult(V2MovieSearchEntity testBean, String kw);
        void error();

    }
}

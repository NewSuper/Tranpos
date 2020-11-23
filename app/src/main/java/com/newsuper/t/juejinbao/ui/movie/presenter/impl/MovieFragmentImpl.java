package com.newsuper.t.juejinbao.ui.movie.presenter.impl;


import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;


public class MovieFragmentImpl extends BasePresenter<MovieFragmentImpl.MvpView> {


    /**
     * 请求的tab栏数据
     * @param context
     */
    public void requestTabData(Context context){
        rx.Observable<MovieTabDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).movieTabData().map((new HttpResultFunc<MovieTabDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieTabDataEntity>() {
            @Override
            public void next(MovieTabDataEntity testBean) {
                getView().requestTabData(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }



//    /**
//     * 小说tab动态显示
//     * @param context
//     */
//    public void readbookData(Context context){
//        rx.Observable<MovieShowBookEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).readbookShow(new HashMap<>()).map((new HttpResultFunc<MovieShowBookEntity>()));
//        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieShowBookEntity>() {
//            @Override
//            public void next(MovieShowBookEntity readBookData) {
//                getView().readBookData(readBookData);
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
        void requestTabData(MovieTabDataEntity testBean);
//        void readBookData(MovieShowBookEntity movieShowBookEntity);
        public void error(String string);
    }
}

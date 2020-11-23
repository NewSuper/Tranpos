package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieShowBookEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;

import rx.Subscriber;
import rx.Subscription;

public class VipFragmentImpl extends BasePresenter<VipFragmentImpl.MvpView> {

    /**
     * 小说tab动态显示
     * @param context
     */
    public void readbookData(Context context){
        rx.Observable<MovieShowBookEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).readbookShow(new HashMap<>()).map((new HttpResultFunc<MovieShowBookEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieShowBookEntity>() {
            @Override
            public void next(MovieShowBookEntity readBookData) {
                getView().readBookData(readBookData);
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
        void readBookData(MovieShowBookEntity movieShowBookEntity);
        public void error(String string);
    }
}
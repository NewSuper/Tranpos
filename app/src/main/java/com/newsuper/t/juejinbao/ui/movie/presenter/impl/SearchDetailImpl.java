package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieSearchthirdWebEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;


public class SearchDetailImpl extends BasePresenter<SearchDetailImpl.MvpView> {

    //影视相关资源
    public void requestThirdWeb(Context context){

        rx.Observable<MovieSearchthirdWebEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).MovieSearchthirdWeb().map((new HttpResultFunc<MovieSearchthirdWebEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieSearchthirdWebEntity>() {
            @Override
            public void next(MovieSearchthirdWebEntity testBean) {
//                getView().requestDependentResource(testBean , page);
                getView().requestThirdWeb(testBean);
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
        void requestThirdWeb(MovieSearchthirdWebEntity movieSearchthirdWebEntity);
        void error();
    }


}

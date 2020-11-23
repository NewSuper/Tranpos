package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.MovieSearchthirdWebEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

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

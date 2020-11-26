package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieFilterDataEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class MovieTabFilterImpl extends BasePresenter<MovieTabFilterImpl.MvpView> {


    //请求筛选数据
    public void requestFilterData(Context context , Integer page ,Integer pre_page , String type , String type_name , String  area, String year ){
        rx.Observable<MovieMovieFilterDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getMovieMovieFilter(page , pre_page , type , type_name , area , year).map((new HttpResultFunc<MovieMovieFilterDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieMovieFilterDataEntity>() {
            @Override
            public void next(MovieMovieFilterDataEntity testBean) {
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
        void requestFilterData(MovieMovieFilterDataEntity entity, Integer page);
        void error(String response);

    }
}

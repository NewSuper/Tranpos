package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieDetailEntity;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class PlayerExFuncImpl extends BasePresenter<PlayerExFuncImpl.MvpView> {


    //请求影视详情
    public void requestMovieDetail(Activity activity , Map<String, String> StringMap) {
        rx.Observable<MovieDetailEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                movieDetail(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<MovieDetailEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieDetailEntity>() {
            @Override
            public void next(MovieDetailEntity movieDetailEntity) {
                getView().requestMovieDetail(movieDetailEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                MyToast.show(activity , errResponse);
//                getView().onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    public interface MvpView{
        void requestMovieDetail(MovieDetailEntity movieDetailEntity);
    }
}

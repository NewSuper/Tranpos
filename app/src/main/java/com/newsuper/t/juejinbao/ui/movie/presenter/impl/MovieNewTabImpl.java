package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieNewTabImpl extends BasePresenter<MovieNewTabImpl.MvpView> {

    //请求板块首页
    public void movieIndexRecommend(Activity activity, String type , int page) {

        Map<String ,String> map = new HashMap<>();
//        map.put("type" , "hot_tv");
//        map.put("page" , "1");
        map.put("type" , type);
        map.put("page" , page + "");

            rx.Observable<MovieIndexRecommendEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).movieIndexRecommend(map).map((new HttpResultFunc<MovieIndexRecommendEntity>()));
            Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieIndexRecommendEntity>() {
                @Override
                public void next(MovieIndexRecommendEntity mMovieIndexRecommendEntity) {
                    if(getView() != null) {
                        if(mMovieIndexRecommendEntity.getCode() == 0) {
                            getView().movieIndexRecommend(mMovieIndexRecommendEntity , page);
                        }else{
                            getView().movieIndexRecommendError("");
                        }
                    }
                }

                @Override
                public void error(String target, Throwable e, String errResponse) {
                    if(getView() != null) {
                        getView().movieIndexRecommendError(errResponse);
                    }
                }
            }, activity, false);
            RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
            addSubscrebe(rxSubscription);

    }

    public interface MvpView {
        public void movieIndexRecommend(MovieIndexRecommendEntity mMovieIndexRecommendEntity, int page);
        public void movieIndexRecommendError(String message);
    }

}

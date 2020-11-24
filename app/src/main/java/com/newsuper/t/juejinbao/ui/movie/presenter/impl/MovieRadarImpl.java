package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.movie.craw.MovieSearchManager;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarSearchListEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class MovieRadarImpl extends BasePresenter<MovieRadarImpl.MvpView> {

    public void getCinemaSearchList(final String key , List<String> jsons , String tag) {
        String tempkey = key.trim();

        tempkey = tempkey
                .replace("~" , " ")
                .replace("`" , " ")
                .replace("!" , " ")
                .replace("（" , " ")
                .replace("）" , " ")
                .replace("(" , " ")
                .replace(")" , " ")
                .replace("@" , " ")
                .replace("#" , " ")
                .replace("$" , " ")
                .replace("%" , " ")
                .replace("^" , " ")
                .replace("&" , " ")
                .replace("*" , " ")
                .replace("[" , " ")
                .replace("]" , " ")
                .replace("{" , " ")
                .replace("}" , " ")
                .replace("," , " ")
                .replace("." , " ")
                .replace("/" , " ")
                .replace(";" , " ")
                .replace(":" , " ")
                .replace("：" , " ")
                .replace("+" , " ")
                .replace("-" , " ")
                .replace("_" , " ")
                .replace("=" , " ")
                .replace("|" , " ")
                .replace("*" , " ")
                .replace("?" , " ").trim();


        if(TextUtils.isEmpty(key)){
            return;
        }
        String[] strings = tempkey.split(" ");

        new MovieSearchManager().crawCinemaSearch(strings[0] , jsons, 20 , tag);
    }


    //请求影院爬取规则
    public void movieRadarCrawListValue(Context context ){
        if(JunjinBaoMainActivity.movieRadarSearchListEntity != null) {

            getView().movieRadarCrawListValue(JunjinBaoMainActivity.movieRadarSearchListEntity);
            return;
        }

        rx.Observable<MovieRadarSearchListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).movieRadarCrawListValue().map((new HttpResultFunc<MovieRadarSearchListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieRadarSearchListEntity>() {
            @Override
            public void next(MovieRadarSearchListEntity movieRadarSearchListEntity) {
                if(movieRadarSearchListEntity.getCode() == 0){
                    JunjinBaoMainActivity.movieRadarSearchListEntity = movieRadarSearchListEntity;
                    getView().movieRadarCrawListValue(movieRadarSearchListEntity);
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {

            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }


    //爬取错误上报
    public void movieCrawError(Context context , String host){
        Map<String , String> map = new HashMap<>();
        map.put("host" , host);

        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).movieCrawError(map).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity movieRadarSearchListEntity) {

            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {

            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    public interface MvpView{
        public void movieRadarCrawListValue(MovieRadarSearchListEntity movieRadarSearchListEntity);
    }

}

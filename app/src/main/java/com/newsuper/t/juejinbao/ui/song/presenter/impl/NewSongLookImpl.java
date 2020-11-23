package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.LatestMusicTagEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class NewSongLookImpl extends BasePresenter<NewSongLookImpl.MvpView>{

    //新歌速递标签
    public void latestSongTag(Context context) {

        Map<String , String> map = new HashMap<>();
        rx.Observable<LatestMusicTagEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .latestSongTag(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LatestMusicTagEntity>() {
            @Override
            public void next(LatestMusicTagEntity bean) {
                getView().latestSongTag(bean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }




    public interface MvpView{
        public void latestSongTag(LatestMusicTagEntity latestMusicTagEntity);
        public void error(String errResponse);
    }

}

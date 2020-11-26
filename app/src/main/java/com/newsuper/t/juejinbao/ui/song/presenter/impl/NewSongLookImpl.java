package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.song.entity.LatestMusicTagEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class NewSongLookImpl extends BasePresenter<NewSongLookImpl.MvpView> {

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

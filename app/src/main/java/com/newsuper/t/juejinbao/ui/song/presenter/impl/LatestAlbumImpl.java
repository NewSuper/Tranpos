package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.LatestAlbumTagEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class LatestAlbumImpl extends BasePresenter<LatestAlbumImpl.MvpView>{

    //最新专辑标签
    public void albumTag(Context context) {

        Map<String , String> map = new HashMap<>();
        rx.Observable<LatestAlbumTagEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .albumTag(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LatestAlbumTagEntity>() {
            @Override
            public void next(LatestAlbumTagEntity bean) {
                getView().albumTag(bean);
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
        public void albumTag(LatestAlbumTagEntity latestAlbumTagEntity);
        public void error(String errResponse);
    }

}

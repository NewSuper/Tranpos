package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.LatestAlbumListEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class LatestAlbumListImpl extends BasePresenter<LatestAlbumListImpl.MvpView> {

    //最新专辑列表
    public void albumList(Context context , String en) {

        Map<String , String> map = new HashMap<>();
        map.put("tag" , en);
        rx.Observable<LatestAlbumListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .albumList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LatestAlbumListEntity>() {
            @Override
            public void next(LatestAlbumListEntity bean) {

                getView().albumList(bean);
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
        public void albumList(LatestAlbumListEntity latestAlbumListEntity);
        public void error(String errResponse);
    }

}

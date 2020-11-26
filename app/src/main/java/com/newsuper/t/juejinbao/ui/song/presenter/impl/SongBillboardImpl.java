package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.song.entity.SongBillBoardEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SongBillboardImpl extends BasePresenter<SongBillboardImpl.MvpView> {

    //榜单列表
    public void allRankList(Context context) {
        Map<String , String> map = new HashMap<>();

        rx.Observable<SongBillBoardEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .allRankList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SongBillBoardEntity>() {
            @Override
            public void next(SongBillBoardEntity bean) {
                getView().allRankList(bean);
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
        public void allRankList(SongBillBoardEntity songBillBoardEntity);
        public void error(String errResponse);
    }


}

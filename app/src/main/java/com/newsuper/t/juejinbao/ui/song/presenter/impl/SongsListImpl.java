package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.ClassAreaDetailEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SongsListImpl extends BasePresenter<SongsListImpl.MvpView> {

    //歌单分类详情
    public void classAreaDetail(Context context , String id) {

        Map<String , String> map = new HashMap<>();
        map.put("id" , id);
        rx.Observable<ClassAreaDetailEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .classAreaDetail(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ClassAreaDetailEntity>() {
            @Override
            public void next(ClassAreaDetailEntity bean) {
                if(bean.getCode() == 0){
                    getView().classAreaDetail(bean);
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
//                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView{
        public void classAreaDetail(ClassAreaDetailEntity bean);
    }
}

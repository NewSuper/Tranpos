package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.HotWordSearchEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class TodayHotResultImpl extends BasePresenter<TodayHotResultImpl.MvpView> {

    public void getHotWordSearch(Context context, String word){
        Map<String,String> map = new HashMap<>();
        map.put("encode_hot_word",word);
        rx.Observable<HotWordSearchEntity> observable = RetrofitManager.getInstance(context)
                .create(ApiService.class).getHotWordSearch(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HotWordSearchEntity>() {
            @Override
            public void next(HotWordSearchEntity entity) {
                getView().getHotWordSearchSuccess(entity);
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

        void getHotWordSearchSuccess(HotWordSearchEntity entity);

        void error(String str);
    }
}

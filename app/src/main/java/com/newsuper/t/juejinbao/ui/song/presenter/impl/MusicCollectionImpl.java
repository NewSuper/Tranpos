package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.song.entity.MusicCollectionEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.base.PagerCons;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class MusicCollectionImpl extends BasePresenter<MusicCollectionImpl.MvpView>{


    //歌单列表
    public void gedanCollection(Context context) {

        Map<String , String> map = new HashMap<>();
        rx.Observable<MusicCollectionEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .gedanCollect(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MusicCollectionEntity>() {
            @Override
            public void next(MusicCollectionEntity bean) {
                if(bean.getCode() == 0) {
                    //缓存
                    List<String> ids = new ArrayList<>();
                    for (MusicCollectionEntity.DataBean dataBean : bean.getData()) {
                        ids.add(dataBean.getId() + "");
                    }
                    book().write(PagerCons.KEY_GEDAN_LIKE_LIST, ids);

                    getView().gedanCollection(bean);
                }else{
                    getView().error(bean.getMsg());
                }
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
        public void gedanCollection(MusicCollectionEntity musicCollectionEntity);
        public void error(String string);
    }

}

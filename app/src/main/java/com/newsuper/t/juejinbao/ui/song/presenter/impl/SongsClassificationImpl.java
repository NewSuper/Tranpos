package com.newsuper.t.juejinbao.ui.song.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.song.entity.ClassAreaEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class SongsClassificationImpl extends BasePresenter<SongsClassificationImpl.MvpView> {


    //歌单分类
    public void classArea(Context context) {

        Map<String , String> map = new HashMap<>();
        rx.Observable<ClassAreaEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .classArea(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ClassAreaEntity>() {
            @Override
            public void next(ClassAreaEntity bean) {
                if(bean.getCode() == 0){
                    getView().classArea(bean);
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
        public void classArea(ClassAreaEntity bean);
    }
}

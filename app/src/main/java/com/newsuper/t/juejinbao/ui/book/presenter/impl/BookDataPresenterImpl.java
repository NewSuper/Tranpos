package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BooKDataEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class BookDataPresenterImpl extends BasePresenter<BookDataPresenterImpl.MvpView> {

    public void getBookData(Context context ) {
        rx.Observable<BooKDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getBookData().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BooKDataEntity>() {
            @Override
            public void next(BooKDataEntity bean) {
                getView().getBooKData(bean);
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

        void error(String str);

        void getBooKData(BooKDataEntity data);
    }
}

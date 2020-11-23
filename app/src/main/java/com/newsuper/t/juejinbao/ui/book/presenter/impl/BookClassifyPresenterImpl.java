package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import rx.Subscriber;
import rx.Subscription;

public class BookClassifyPresenterImpl extends BasePresenter<BookClassifyPresenterImpl.MvpView> {

    public void getCategoryList(Context context ) {
        rx.Observable<BookCategoryEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCategoryList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookCategoryEntity>() {
            @Override
            public void next(BookCategoryEntity bean) {
                if(bean.getCode() == 1){
                   getView().getCategoryList(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
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

        void error(String str);

        void getCategoryList(BookCategoryEntity data);
    }
}

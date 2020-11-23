package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookSearchEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookSearchPresenterImpl extends BasePresenter<BookSearchPresenterImpl.MvpView> {

    public void getSearchRecordList(Context context ) {
        rx.Observable<BookSearchEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getSearchRecordList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookSearchEntity>() {
            @Override
            public void next(BookSearchEntity bean) {
                if(bean.getCode() == 1){
                    getView().getSearchRecordList(bean);
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

    public void getNovelList(Context context, String keyword, String page) {
        //type: search; //搜索
        Map<String, String> map = new HashMap<>();
        map.put("type","search");
        map.put("keyword",keyword);
        map.put("page",page);
        rx.Observable<BookListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getNovelList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookListEntity>() {
            @Override
            public void next(BookListEntity bean) {
                if(bean.getCode() == 1){
                    getView().getNovelList(bean);
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

        void getSearchRecordList(BookSearchEntity data);

        void getNovelList(BookListEntity entity);
    }
}

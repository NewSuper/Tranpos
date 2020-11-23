package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookshelfEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookshelfPresenterImpl extends BasePresenter<BookshelfPresenterImpl.MvpView> {

    public void getMyNovelList(Context context) {
        rx.Observable<BookshelfEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getMyNovelList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookshelfEntity>() {
            @Override
            public void next(BookshelfEntity bean) {
                if(bean.getCode() == 1){
                    getView().getMyNovelList(bean);
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

    public void getMyNovelRecordList(Context context) {
        rx.Observable<BookshelfEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getMyNovelRecordList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookshelfEntity>() {
            @Override
            public void next(BookshelfEntity bean) {
                if(bean.getCode() == 1){
                    getView().getMyNovelRecordList(bean);
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

    public void delFromMyNovel(Context context, String novelId) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .delFromMyNovel(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                if(bean.getCode() == 1){
                    getView().delFromMyNovel(bean);
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

    public void delFromMyNovelRecord(Context context,String novelId) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .delFromMyNovelRecord(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                if(bean.getCode() == 1){
                    getView().delFromMyNovelRecord(bean);
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

        void getMyNovelList(BookshelfEntity data);

        void getMyNovelRecordList(BookshelfEntity data);

        void delFromMyNovel(BaseEntity data);

        void delFromMyNovelRecord(BaseEntity data);
    }
}

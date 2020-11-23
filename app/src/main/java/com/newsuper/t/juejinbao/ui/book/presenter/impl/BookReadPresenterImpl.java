package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BooKInfoEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookReadPresenterImpl extends BasePresenter<BookReadPresenterImpl.MvpView> {

    public void getBookData(Context context, String novelId) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        rx.Observable<BooKInfoEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getBookInfo(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BooKInfoEntity>() {
            @Override
            public void next(BooKInfoEntity bean) {
                if(bean.getCode() == 1){
                    getView().getBooKInfo(bean);
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

    public void getChapterList(Context context, String novelId, String page) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        map.put("sort","asc");
        map.put("page",page);
        map.put("pagesize", String.valueOf(10000));
        rx.Observable<BookChapterEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getChapterList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookChapterEntity>() {
            @Override
            public void next(BookChapterEntity bean) {
                if(bean.getCode() == 1){
                    getView().getChapterList(bean);
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

    public void getChapterRead(Context context,String novelId, String chapterId,int chapter) {
        if(!NetUtil.isNetworkAvailable(context)){
            ToastUtils.getInstance().show(context,"网络连接失败，请检查网络");
        }
        Map<String, String> map = new HashMap<>();
        map.put("is_ajax","1");
        map.put("source","api");
        map.put("novel_id",novelId);
        map.put("chapterid",chapterId);
        rx.Observable<BookChapterDetailEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getChapterRead(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookChapterDetailEntity>() {
            @Override
            public void next(BookChapterDetailEntity bean) {
                if(bean.getCode() == 1){
                    getView().showChapterRead(bean,chapter);
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

    public void addToMyNovel(Context context, String novelId, String chapterId) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        map.put("chapter_id",chapterId);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .addToMyNovel(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity bean) {
                if(bean.getCode() == 1){
                    getView().addToMyNovel();
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

        void error(String error);

        void getBooKInfo(BooKInfoEntity data);

        void getChapterList(BookChapterEntity data);

        void showChapterRead(BookChapterDetailEntity data, int chapter);

        void addToMyNovel();
    }
}

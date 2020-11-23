package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookClassifyDetailPresenterImpl extends BasePresenter<BookClassifyDetailPresenterImpl.MvpView> {

    public void getCategoryInfo(Context context, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id",id);
        rx.Observable<BookCategoryDetailEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCategoryInfo(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookCategoryDetailEntity>() {
            @Override
            public void next(BookCategoryDetailEntity bean) {
                if(bean.getCode() == 1){
                    getView().getCategoryInfo(bean);
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

    public void getCommendList(Context context, String id) {

        // 1为猜你喜欢，2为好书推荐，3点击排行，4为收藏排行，5为最近入库，6为各分类下的热门小说
        // novel_id 小说ID，type=1时必填
        // categoryId 小说的分类ID，type=1或type=6时必填
        Map<String, String> map = new HashMap<>();
        map.put("type","6");
        map.put("category_id", id);
        rx.Observable<BookCommendEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCommendList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookCommendEntity>() {
            @Override
            public void next(BookCommendEntity bean) {
                if(bean.getCode() == 1){
                    getView().getCommendList(bean);
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

    public void getNovelList(Context context, String id, String page) {
        //type: 推荐数为 votenum、总点击 allvisit、收藏数 marknum、入库时间 postdate、最近更新 lastupdate、全本 over、分类 category
        //category_id 分类ID，当type=category时必填
        Map<String, String> map = new HashMap<>();
        map.put("type","category");
        map.put("category_id",id);
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

        void getCategoryInfo(BookCategoryDetailEntity data);

        void getCommendList(BookCommendEntity data);

        void getNovelList(BookListEntity entity);
    }
}

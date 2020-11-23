package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookForAllPresenterImpl extends BasePresenter<BookForAllPresenterImpl.MvpView> {

    public void getNovelList(Context context, String type, String category_id, String page) {
        //type: 推荐数为 votenum、总点击 allvisit、收藏数 marknum、入库时间 postdate、最近更新 lastupdate、全本 over、分类 category
        //category_id 分类ID，当type=category时必填
        Map<String, String> map = new HashMap<>();
        map.put("type",type);
        if(!TextUtils.isEmpty(category_id))
            map.put("category_id",category_id);
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

        void getNovelList(BookListEntity entity);
    }
}

package com.newsuper.t.juejinbao.ui.book.presenter.impl;


import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookRankingPresenterImpl extends BasePresenter<BookRankingPresenterImpl.MvpView> {

    public void getCommendList(Context context, int type) {
        // 1为猜你喜欢，2为好书推荐，3点击排行，4为收藏排行，5为最近入库，6为各分类下的热门小说
        // novel_id 小说ID，type=1时必填
        // categoryId 小说的分类ID，type=1或type=6时必填
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        rx.Observable<BookCommendEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getCommendList(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BookCommendEntity>() {
            @Override
            public void next(BookCommendEntity bean) {
                if(bean.getCode() == 1){
                    getView().getCommendList(bean,type);
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

        void getCommendList(BookCommendEntity data, int type);
    }
}

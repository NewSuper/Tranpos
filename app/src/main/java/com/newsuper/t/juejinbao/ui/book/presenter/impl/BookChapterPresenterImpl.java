package com.newsuper.t.juejinbao.ui.book.presenter.impl;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class BookChapterPresenterImpl extends BasePresenter<BookChapterPresenterImpl.MvpView> {

    public void getChapterList(Context context, String novelId, String sort,int page, int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("novel_id",novelId);
        map.put("sort",sort);
        map.put("page", String.valueOf(page));
        map.put("pagesize",String.valueOf(pageSize));
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

    public interface MvpView{

        void error(String str);

        void getChapterList(BookChapterEntity data);
    }
}

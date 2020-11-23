package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import rx.Subscriber;
import rx.Subscription;

public class DependentResourceImpl extends BasePresenter<DependentResourceImpl.MvpView> {

    //影视相关资源
    public void requestDependentResource(Context context , Integer page , String kw , String type){
        rx.Observable<DependentResourcesDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getDependentResources(page , kw , type).map((new HttpResultFunc<DependentResourcesDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<DependentResourcesDataEntity>() {
            @Override
            public void next(DependentResourcesDataEntity testBean) {
                getView().requestDependentResource(testBean , page);
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
        void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page);
        void error(String str);
    }

}

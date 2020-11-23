package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.home.entity.HomeListSearchEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class HomeSearchDetailJjbImpl extends BasePresenter<HomeSearchDetailJjbImpl.MvpView> {


    public void search(Map<String, String> StringMap, Activity activity , final int aid) {
        final MvpView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListSearchEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                searchNews(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeListSearchEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListSearchEntity>() {
            @Override
            public void next(HomeListSearchEntity channelEntity) {
                baseView.search(channelEntity , aid);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
//                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView{
        public void search(HomeListSearchEntity channelEntity, int aid);
    }
}

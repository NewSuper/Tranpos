package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListSearchEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

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

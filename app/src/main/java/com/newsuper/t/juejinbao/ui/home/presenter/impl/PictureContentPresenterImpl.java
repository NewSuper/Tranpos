package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.PictureContentEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.PictureContentPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class PictureContentPresenterImpl extends BasePresenter<PictureContentPresenter.View> implements PictureContentPresenter {

    @Override
    public void getPictureContentList(Map<String, String> map, Activity activity) {
        final PictureContentPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<PictureContentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPictureContent(map).map((new HttpResultFunc<PictureContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PictureContentEntity>() {
            @Override
            public void next(PictureContentEntity pictureContentEntity) {
                baseView.getPictureContentSuccess(pictureContentEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 错误信息=======》》》》》"+e.toString() );
                baseView.showErrolr(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

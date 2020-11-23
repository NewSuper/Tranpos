package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.home.entity.PictureContentEntity;
import com.juejinchain.android.module.home.presenter.PictureContentPresenter;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

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

package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.IsCollectEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.PictureBigPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class PictureBigPresenterImpl extends BasePresenter<PictureBigPresenter.pictureBigPresenterView> implements PictureBigPresenter {
    @Override
    public void getIsCollect(Map<String, String> StringMap, Activity activity, int is_collect) {
        final PictureBigPresenter.pictureBigPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<IsCollectEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPictureIsCollect(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<IsCollectEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<IsCollectEntity>() {
            @Override
            public void next(IsCollectEntity isCollectEntity) {
                baseView.getIsCollectSuccess(isCollectEntity, is_collect);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 点赞=====》》》》》" + e.toString());
                baseView.showError(e.toString());
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

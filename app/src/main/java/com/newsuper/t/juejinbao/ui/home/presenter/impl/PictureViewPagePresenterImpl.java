package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.IsCollectEntity;
import com.newsuper.t.juejinbao.ui.home.entity.PictureViewPageEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.PictureViewPagePresenter;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class PictureViewPagePresenterImpl extends BasePresenter<PictureViewPagePresenter.pictureViewPagePresenterView> implements PictureViewPagePresenter {

    @Override
    public void getPictureContentList(Map<String, String> map, Activity activity) {

        final PictureViewPagePresenter.pictureViewPagePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<PictureViewPageEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPictureBigImageList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<PictureViewPageEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PictureViewPageEntity>() {
            @Override
            public void next(PictureViewPageEntity pictureViewPageEntity) {
                baseView.getBigPictureSuccess(pictureViewPageEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getIsCollect(Map<String, String> StringMap, Activity activity, int is_collect) {
        final PictureViewPagePresenter.pictureViewPagePresenterView baseView = getView();
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
                baseView.showError(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //用户兴趣接口
    @Override
    public void getIsLike(Map<String, String> StringMap, Activity activity) {
        final PictureViewPagePresenter.pictureViewPagePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<IsCollectEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPictureLike(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<IsCollectEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<IsCollectEntity>() {
            @Override
            public void next(IsCollectEntity isCollectEntity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 图集兴趣=====》》》》》" + e.toString());
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //30秒阅读奖励
    public void getRewardOf30second(Map<String, String> StringMap, Activity activity) {
        final PictureViewPagePresenter.pictureViewPagePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getRewardOf30Second(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GetCoinEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinEntity>() {
            @Override
            public void next(GetCoinEntity lookOrOnTachEntity) {
                if(lookOrOnTachEntity.getCode() == 0) {
                    baseView.getRewardOf30secondSuccess(lookOrOnTachEntity);
                }else{
                    if(activity != null){
                        MyToast.show(activity , lookOrOnTachEntity.getMsg());
                    }
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //双倍阅读奖励
    public void getRewardDouble(Map<String, String> StringMap, Activity activity) {
        final PictureViewPagePresenter.pictureViewPagePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<RewardDoubleEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getRewardDouble(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<RewardDoubleEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<RewardDoubleEntity>() {
            @Override
            public void next(RewardDoubleEntity rewardDoubleEntity) {
                if(rewardDoubleEntity.getCode() == 0) {
                    baseView.getRewardDouble(rewardDoubleEntity);
                }else{
                    if(activity != null) {
                        MyToast.show(activity, rewardDoubleEntity.getMsg());
                    }
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

}

package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.home.entity.GetCoinEntity;
import com.juejinchain.android.module.home.entity.HomeListEntity;
import com.juejinchain.android.module.home.entity.RewardDoubleEntity;
import com.juejinchain.android.module.home.presenter.SmallVideoPresenter;
import com.juejinchain.android.utils.MyToast;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class SmallVideoPresenterImpl extends BasePresenter<SmallVideoPresenter.SmallVideoPresenterView> implements SmallVideoPresenter {
    //小视频列表
    @Override
    public void getSmallVideoList(Map<String, String> StringMap, Activity activity,int type) {
        final SmallVideoPresenter.SmallVideoPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getSmallVideoList(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity HomeListEntity) {
                baseView.getSmallVideoListSuccess(HomeListEntity,type);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 小视频错误详细信息=====>>>>>>" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //请求广告
    public void requestTTDrawFeedAds(TTAdNative mTTAdNative){
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("920793078")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setAdCount(3) //请求广告数量为1到3条
                .build();


        //step4:请求广告,对请求回调的广告作渲染处理
        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e("zy" , message);
                getView().requestTTDrawFeedAds(null);
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                if (ads == null || ads.isEmpty()) {
                    return;
                }
                getView().requestTTDrawFeedAds(ads);
            }
        });
    }

    @Override
    public void getRewardOf30second(Map<String, String> StringMap, Activity activity) {
        final SmallVideoPresenter.SmallVideoPresenterView baseView = getView();
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
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRewardDouble(Map<String, String> StringMap, Activity activity) {
        final SmallVideoPresenter.SmallVideoPresenterView baseView = getView();
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
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

}

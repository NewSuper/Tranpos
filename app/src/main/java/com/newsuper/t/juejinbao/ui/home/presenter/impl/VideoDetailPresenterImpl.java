package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.VideoDetailEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.VideoDetailPresenter;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class VideoDetailPresenterImpl extends BasePresenter<VideoDetailPresenter.View> implements VideoDetailPresenter{


    @Override
    public void getVideoDetail(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<VideoDetailEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getVideoDetail(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<VideoDetailEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<VideoDetailEntity>() {
            @Override
            public void next(VideoDetailEntity entity) {
                baseView.getVideoDetailSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 小视频错误详细信息=====>>>>>>" + e.toString());
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getVideoList(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHomePageList(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity HomeListEntity) {
                baseView.getVideoListSuccess(HomeListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 小视频错误详细信息=====>>>>>>" + e.toString());
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void giveLike(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                giveLike(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity HomeListEntity) {
                baseView.giveLikeSuccess(HomeListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRewardByShare(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                shareGetReward(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GetCoinEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinEntity>() {
            @Override
            public void next(GetCoinEntity lookOrOnTachEntity) {
                baseView.getRewardByShareSuccess(lookOrOnTachEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(target);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getVideoCommentList(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<DetailRecomentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getVideoCommentList(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<DetailRecomentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<DetailRecomentEntity>() {
            @Override
            public void next(DetailRecomentEntity lookOrOnTachEntity) {
                baseView.getVideoCommentListSuccess(lookOrOnTachEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(target);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRewardOf30second(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
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
                baseView.onError(target);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRewardDouble(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
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
                }else {
                    if(activity != null) {
                        MyToast.show(activity, rewardDoubleEntity.getMsg());
                    }
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void leavePageCommit(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null || activity==null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                leaveActicleDetail(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.leavePageCommitSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error:ShareDomainEntity ============>>>>" + e.toString());
                baseView.onError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void postComment(Map<String, String> StringMap, Activity activity) {
        final VideoDetailPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                postVideoComment(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                baseView.postCommentSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.onError(target);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

}

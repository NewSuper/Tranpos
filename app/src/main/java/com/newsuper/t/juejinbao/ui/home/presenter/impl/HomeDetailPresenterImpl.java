package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleDetailEntity;
import com.newsuper.t.juejinbao.ui.home.entity.CommentCommitEntity;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeDetailPresenter;
import com.newsuper.t.juejinbao.ui.my.entity.BaseDefferentEntity;
import com.newsuper.t.juejinbao.ui.my.entity.LookOrOnTachEntity;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class HomeDetailPresenterImpl extends BasePresenter<HomeDetailPresenter.HomeDetailView> implements HomeDetailPresenter {
    //文章详情富文本
    @Override
    public void getHomeDetail(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ArticleDetailEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleDetail(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ArticleDetailEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ArticleDetailEntity>() {
            @Override
            public void next(ArticleDetailEntity articleDetailEntity) {
                baseView.getHomeDetailSuccess(articleDetailEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //文章详情推荐列表
    @Override
    public void getArticleList(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleListDetail(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity homeListEntity) {
                baseView.getArticleListSuccess(homeListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //关注
    @Override
    public void getArticleAuthorFcous(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeListEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleAuthorFcous(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeListEntity>() {
            @Override
            public void next(HomeListEntity homeListEntity) {
                baseView.getArticleAuthorFcousSuccess(homeListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //收藏
    @Override
    public void getArticleCollect(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseDefferentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleAuthorCollect(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseDefferentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseDefferentEntity>() {
            @Override
            public void next(BaseDefferentEntity baseEntity) {
                baseView.getArticleCollectSuccess(baseEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //发布评论
    @Override
    public void getArticleCollectCommit(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<CommentCommitEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleSubmit(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<CommentCommitEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<CommentCommitEntity>() {
            @Override
            public void next(CommentCommitEntity giveLikeEnty) {
                baseView.getArticleCollectCommitSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //评论详情
    @Override
    public void getArticleComment(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<DetailRecomentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getArticleComment(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<DetailRecomentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<DetailRecomentEntity>() {
            @Override
            public void next(DetailRecomentEntity detailRecomentEntity) {
                baseView.getArticleCommentSuccess(detailRecomentEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //广告统计
    @Override
    public void getLookOrOnTach(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<LookOrOnTachEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getLookOrOnTach(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LookOrOnTachEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LookOrOnTachEntity>() {
            @Override
            public void next(LookOrOnTachEntity lookOrOnTachEntity) {
                baseView.showLookOrOnTachSuccess(lookOrOnTachEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error:统计==============>>>>> " + errResponse);
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //30秒阅读奖励
    @Override
    public void getRewardOf30second(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getRewardOf30Second(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GetCoinEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinEntity>() {
            @Override
            public void next(GetCoinEntity lookOrOnTachEntity) {
                baseView.getRewardOf30secondSuccess(lookOrOnTachEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //双倍阅读奖励
    @Override
    public void getRewardDouble(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
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
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getRewardByShare(Map<String, String> StringMap, Activity activity) {
        final HomeDetailView baseView = getView();
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
                baseView.error(e);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void leavePageCommit(Map<String, String> StringMap, Activity activity) {
        final HomeDetailPresenter.HomeDetailView baseView = getView();
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
    public void getNewTaskReward(Map<String, String> StringMap, Activity activity) {
        final HomeDetailPresenter.HomeDetailView baseView = getView();
        if (baseView == null || activity==null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getNewTaskReward(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.getNewTaskRewardSuccess(entity);
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

}

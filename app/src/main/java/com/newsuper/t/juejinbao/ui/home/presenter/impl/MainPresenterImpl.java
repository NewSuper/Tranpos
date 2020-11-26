package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.ad.HomeAdDialogEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;
import com.newsuper.t.juejinbao.ui.home.entity.BackCardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeWalkMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeBottomTabEntity;
import com.newsuper.t.juejinbao.ui.home.entity.Read60Reword;
import com.newsuper.t.juejinbao.ui.home.entity.UnReadEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelFareRewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelfareEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.MainPresenter;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.login.entity.PostAliasEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarSearchListEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDomainEntity;
import com.newsuper.t.juejinbao.ui.share.entity.SharePicsEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class MainPresenterImpl extends BasePresenter<MainPresenter.View> implements MainPresenter {

    @Override
    public void getExitCardInfo(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BackCardEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getBackCardInfo(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BackCardEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BackCardEntity>() {
            @Override
            public void next(BackCardEntity giveLikeEnty) {
                baseView.getExitCardInfoSuccess(giveLikeEnty);
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
    public void getHomeBottomTab(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeBottomTabEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHomeTab(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeBottomTabEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeBottomTabEntity>() {
            @Override
            public void next(HomeBottomTabEntity giveLikeEnty) {
                baseView.getHomeBottomTabSuccess(giveLikeEnty);
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
    public void getUnReadMessage(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UnReadEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUnReadMessage(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UnReadEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UnReadEntity>() {
            @Override
            public void next(UnReadEntity entity) {
                baseView.getUnReadMessageSuccess(entity);
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
    public void getShareConfig(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ShareConfigEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getShareConfig(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ShareConfigEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ShareConfigEntity>() {
            @Override
            public void next(ShareConfigEntity entity) {
                baseView.getShareConfigSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //c
    @Override
    public void getShareAndAppUpHeader(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ShareDomainEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getDomainApi(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ShareDomainEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ShareDomainEntity>() {
            @Override
            public void next(ShareDomainEntity entity) {
                baseView.getShareAndAppUpHeaderSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error:ShareDomainEntity ============>>>>" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //上传Alias
    @Override
    public void postAdAlias(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<PostAliasEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                postAlias(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<PostAliasEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<PostAliasEntity>() {
            @Override
            public void next(PostAliasEntity entity) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error:ShareDomainEntity ============>>>>" + e.toString());
                baseView.onPostAliasError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getCoinOf60Min(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<Read60Reword> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getRead60Reword(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<Read60Reword>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<Read60Reword>() {
            @Override
            public void next(Read60Reword entity) {
                baseView.getCoinOf60MinSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error:ShareDomainEntity ============>>>>" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void leavePageCommit(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null || activity == null) {
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
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void watchAdVideoInTask(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null || activity == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                watchAdVideoInTask(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                baseView.watchAdVideoInTaskSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("zzz", e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getShareFullPics(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<SharePicsEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getSharePics(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<SharePicsEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SharePicsEntity>() {
            @Override
            public void next(SharePicsEntity entity) {
                baseView.getShareFullPicsSuccess(entity);
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
    public void getBaseConfig(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseConfigEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getBaseConfig(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseConfigEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseConfigEntity>() {
            @Override
            public void next(BaseConfigEntity entity) {
                baseView.getBaseConfigSuccess(entity);
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
    public void getADConfig(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ADConfigEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getADConfig(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ADConfigEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ADConfigEntity>() {
            @Override
            public void next(ADConfigEntity entity) {
                baseView.getADConfigSuccess(entity);
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
    public void getAdDataDialog(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<HomeAdDialogEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getAdDialogData(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<HomeAdDialogEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<HomeAdDialogEntity>() {
            @Override
            public void next(HomeAdDialogEntity entity) {
                baseView.getAdDialogDataSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //是否显示微信或者QQ
    public void IsShowWchatorQQ(Map<String, String> StringMap, Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<IsShowQQEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                isShowWechat(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<IsShowQQEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<IsShowQQEntity>() {
            @Override
            public void next(IsShowQQEntity loginEntity) {
                baseView.showIsShowWchatorQQ(loginEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "绑定邀请码具体错误error==========》》》》》》: " + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //请求影院爬取规则
    public void movieRadarCrawListValue(Context context) {
        rx.Observable<MovieRadarSearchListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).movieRadarCrawListValue().map((new HttpResultFunc<MovieRadarSearchListEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieRadarSearchListEntity>() {
            @Override
            public void next(MovieRadarSearchListEntity movieRadarSearchListEntity) {
                if (movieRadarSearchListEntity.getCode() == 0) {
                    JunjinBaoMainActivity.movieRadarSearchListEntity = movieRadarSearchListEntity;
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {

            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    //小程序兑换奖励弹框
    @Override
    public void exchangeMiniProgramToJJB(Activity context) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }

        rx.Observable<ExChangeMiniProgramEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).exChangeMiniProgramToJJB().map((new HttpResultFunc<ExChangeMiniProgramEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ExChangeMiniProgramEntity>() {
            @Override
            public void next(ExChangeMiniProgramEntity movieRadarSearchListEntity) {
                baseView.onExchangeSuccess(movieRadarSearchListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }
    //小程序兑换奖励弹框
    @Override
    public void exchangeWalkMiniProgramToJJB(Activity context) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }

        rx.Observable<ExChangeWalkMiniProgramEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).exChangeWalkMiniProgramToJJB().map((new HttpResultFunc<ExChangeWalkMiniProgramEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ExChangeWalkMiniProgramEntity>() {
            @Override
            public void next(ExChangeWalkMiniProgramEntity movieRadarSearchListEntity) {
                baseView.onWalkExchangeSuccess(movieRadarSearchListEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    @Override
    public void getWelFareData(Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }

        rx.Observable<WelFareRewardEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).getWelFareReward().map((new HttpResultFunc<WelFareRewardEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<WelFareRewardEntity>() {
            @Override
            public void next(WelFareRewardEntity movieRadarSearchListEntity) {
                baseView.onWelFareDataSuccess(movieRadarSearchListEntity);
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
    public void getEggsWelfare(Activity activity) {
        final MainPresenter.View baseView = getView();
        if (baseView == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("tag","open_app_reward");
        rx.Observable<WelfareEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class)
                .welfare(map).map((new HttpResultFunc<WelfareEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<WelfareEntity>() {
            @Override
            public void next(WelfareEntity entity) {
                baseView.onEggsWelfareSuccess(entity);
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

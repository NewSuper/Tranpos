package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;

import com.lzx.starrysky.utils.MD5;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.presenter.HomePresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;


import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class HomePresenterImpl extends BasePresenter<HomePresenter.HomePresenterView> implements HomePresenter{

    /**
     * 获取频道列表
     * @param StringMap
     * @param activity
     */
    @Override
    public void getChannelList(Map<String, String> StringMap, Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<ChannelInfo> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getChennelList(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<ChannelInfo>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ChannelInfo>() {
            @Override
            public void next(ChannelInfo channelEntity) {
                baseView.getChennelListSuccess(channelEntity);
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
    public void setChannelList(Map<String, String> StringMap, Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                setChennelList(HttpRequestBody.generateRequestQuery(StringMap)).
                map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity channelEntity) {
                baseView.setChennelListSuccess(channelEntity);
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
    public void getMessageHint(Map<String, String> StringMap, Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<MessageHintEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getMessageHint(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<MessageHintEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MessageHintEntity>() {
            @Override
            public void next(MessageHintEntity channelEntity) {
                baseView.getMessageHintSuccess(channelEntity);
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
    public void getCoinOf30Min(Map<String, String> StringMap,Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getCoinOf30Min(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GetCoinEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinEntity>() {
            @Override
            public void next(GetCoinEntity channelEntity) {
                baseView.getCoinOf30MinSuccess(channelEntity);
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
    public void countDownOf30Min(Map<String, String> StringMap, Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GetCoinCountDownEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                countDownOf30Min(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GetCoinCountDownEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GetCoinCountDownEntity>() {
            @Override
            public void next(GetCoinCountDownEntity channelEntity) {
                baseView.countDownOf30Min(channelEntity);
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
    public void getHomeTabUnreadMsg(Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UnreadMaseggeEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHomeTabUnreadMsg().map((new HttpResultFunc<UnreadMaseggeEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UnreadMaseggeEntity>() {
            @Override
            public void next(UnreadMaseggeEntity entity) {
                baseView.getHomeTabUnreadMsgSucess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取神马热门搜索数据
    public void getShenmaSearchWord(Activity activity){
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }

        Map<String , String> map = new HashMap<>();
        map.put("appId" , "c4oe3pl9q1");
        map.put("timeStamp" , (System.currentTimeMillis() / 1000L) + "");
        map.put("apiToken" , MD5.hexdigest(map.get("appId") + "efgQRSTZhijKopqr2345" + map.get("timeStamp")) );

//        map.put("appId" , "c4oe3pl9q1");
//        map.put("timeStamp" , "1582629838904");
//        map.put("apiToken" , "ad6aba0d74c675d01518729f255d5246");


        rx.Observable<ShenmaHotWordsEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                shenmaHotWords("http://api1.mangolm.com/api/ad/hotwords" , map).map((new HttpResultFunc<ShenmaHotWordsEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ShenmaHotWordsEntity>() {
            @Override
            public void next(ShenmaHotWordsEntity entity) {
                baseView.getShenmaSearchWord(entity);
//                baseView.getHomeTabUnreadMsgSucess(entity);
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
    public void getHotWordRank(Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        Map<String, String> StringMap = new HashMap<>();
        rx.Observable<TodayHotEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHotWordRank(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TodayHotEntity>() {
            @Override
            public void next(TodayHotEntity entity) {
                baseView.getHotWordRankSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取用户所有信息
    @Override
    public void getUserData(Map<String, String> StringMap, Activity activity) {
        final HomePresenter.HomePresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<UserDataEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUserData(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<UserDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserDataEntity>() {
            @Override
            public void next(UserDataEntity userDataEntity) {
                baseView.getUserDataSuccess(userDataEntity);
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

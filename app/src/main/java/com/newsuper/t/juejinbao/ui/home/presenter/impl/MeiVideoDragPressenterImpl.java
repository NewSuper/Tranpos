package com.newsuper.t.juejinbao.ui.home.presenter.impl;

import android.app.Activity;
import android.util.Log;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GiveLikeEnty;
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoContentEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.MeiVideoDragPresenter;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;


public class MeiVideoDragPressenterImpl extends BasePresenter<MeiVideoDragPresenter.meiVideoDragPresenterView> implements MeiVideoDragPresenter {
    // 点赞
    @Override
    public void gieGiveLike(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getGiveLike(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                baseView.getGiveLikeSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 点赞=====》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //提交评论
    @Override
    public void postContent(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                postCotent(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                baseView.getPostContent(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 提交评论=====》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //评论列表
    @Override
    public void getContentList(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getSmallVideoContentList(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                baseView.getContentListSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 评论列表=====》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //是否已点赞
    @Override
    public void isGiveLike(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getIsGiveLike(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                baseView.getisGiveLikeSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 是否已点赞======》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取小视频当前人的评论列表
    @Override
    public void getReplyContentList(Map<String, String> map, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getPiopleVideoContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                baseView.getReplyContentListSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 回复小视频当前人的评论列表======》》》》》" + e.toString());
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //回复小视频点赞
    @Override
    public void getReplyGiveLike(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getGiveLike(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                baseView.getReplyGiveLikeSuccess(giveLikeEnty);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 回复小视频点赞=======》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //回复小视频评论提交
    @Override
    public void getReplyPostContent(Map<String, String> StringMap, Activity activity, int cid) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                postSmallVideoCotentReply(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                baseView.getReplyPostContent(giveLikeEnty, cid);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 回复小视频评论提交=======》》》》》" + e.toString());
                baseView.showError(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //用户兴趣接口
    @Override
    public void getIsLikeMovied(Map<String, String> StringMap, Activity activity) {
        final MeiVideoDragPresenter.meiVideoDragPresenterView baseView = getView();
        if (baseView == null) {
            return;
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                postSmallVideoLike(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {

            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: 用户兴趣接口小视频=======》》》》》" + e.toString());
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}

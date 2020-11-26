package com.newsuper.t.juejinbao.ui.task.presenter;

import android.app.Activity;
import android.content.Context;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.home.entity.FinishTaskEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserInfoEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BoxInfoEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BoxTimeEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SignEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskListEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskMsgEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class TaskDetailPresenterImpl extends BasePresenter<TaskDetailPresenterImpl.MvpView> {

    //任务签到接口
    public void signIn(Context context){
        Map<String, String> map = new HashMap<>();
        rx.Observable<SignEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .signIn(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SignEntity>() {
            @Override
            public void next(SignEntity bean) {
                if(bean.getCode() == 0){
                    getView().signIn(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //任务左上角滚动轮播图
    public void getTaskMessage(Context context){
        Map<String, String> map = new HashMap<>();
        rx.Observable<TaskMsgEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getTaskMessage(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TaskMsgEntity>() {
            @Override
            public void next(TaskMsgEntity bean) {
                if(bean.getCode() == 0){
                    getView().getTaskMessage(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    // 任务列表
    public void getTaskList(Context context){
        rx.Observable<TaskListEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getTaskList().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TaskListEntity>() {
            @Override
            public void next(TaskListEntity bean) {
                if(bean.getCode() == 0){
                    getView().getTaskList(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    // 获取开宝箱剩余时间 0可开启宝箱，返回秒数，时间还有就显示倒计时
    public void getTreasureBoxTime(Context context){
        Map<String, String> map = new HashMap<>();
        rx.Observable<BoxTimeEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getTreasureBoxTime(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BoxTimeEntity>() {
            @Override
            public void next(BoxTimeEntity bean) {
                if(bean.getCode() == 0){
                    getView().getTreasureBoxTime(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //开宝箱
    public void treasureBoxSave(Context context){
        Map<String, String> map = new HashMap<>();
        rx.Observable<BoxInfoEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .treasureBoxSave(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BoxInfoEntity>() {
            @Override
            public void next(BoxInfoEntity bean) {
                if(bean.getCode() == 0){
                    getView().treasureBoxSave(bean);
                }else{
                    ToastUtils.getInstance().show(context,bean.getMsg());
                }
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error(errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //获取用户信息判断手机号微信是否绑定
    public void getUserInfo(Activity activity) {
        rx.Observable<UserInfoEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getUserInfo().map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<UserInfoEntity>() {
            @Override
            public void next(UserInfoEntity userProfileEntity) {
                getView().getUserInfoSuccess(userProfileEntity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //新手奖励
    public void getNewTaskReward(Map<String, String> StringMap, Activity activity) {
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getNewTaskReward(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity entity) {
                getView().getNewTaskRewardSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    //领鸡蛋
    public void getEggsWelfare(Activity activity) {
        Map<String, String> map = new HashMap<>();
        rx.Observable<FinishTaskEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class)
                .finishTask(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<FinishTaskEntity>() {
            @Override
            public void next(FinishTaskEntity entity) {
                getView().onEggsWelfareSuccess(entity);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                getView().error(errResponse);
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public interface MvpView{

        void error(String str);

        void signIn(SignEntity data);

        void getTaskMessage(TaskMsgEntity data);

        void getTaskList(TaskListEntity data);

        void getTreasureBoxTime(BoxTimeEntity data);

        void treasureBoxSave(BoxInfoEntity data);

        void getUserInfoSuccess(UserInfoEntity data);

        void getNewTaskRewardSuccess(BaseEntity data);

        void onEggsWelfareSuccess(FinishTaskEntity data);
    }
}

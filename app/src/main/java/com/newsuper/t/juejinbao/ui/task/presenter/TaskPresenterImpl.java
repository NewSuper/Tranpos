package com.newsuper.t.juejinbao.ui.task.presenter;

import android.content.Context;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.TaskADEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class TaskPresenterImpl extends BasePresenter<TaskPresenterImpl.MvpView> {

    //请求广告
    public void getTaskAD(Context context){
        Map<String, String> map = new HashMap<>();
        rx.Observable<TaskADEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class)
                .getTaskAD(map).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TaskADEntity>() {
            @Override
            public void next(TaskADEntity bean) {
                if(bean.getCode() == 0){
                    getView().getTaskAD(bean);
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

    public interface MvpView{

        void error(String str);

        void getTaskAD(TaskADEntity data);
    }
}

package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;

import com.google.gson.JsonObject;
import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.movie.bean.YXL;
import com.ys.network.base.BasePresenter;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

public class VipPlayImpl extends BasePresenter<VipPlayImpl.MvpView> {


    public void requestYXL(Context context){
        rx.Observable<JsonObject> observable = RetrofitManager.getInstance(context).create(ApiService.class).vipparse(new HashMap<>()).map((new HttpResultFunc<JsonObject>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<JsonObject>() {
            @Override
            public void next(JsonObject testBean) {

                List<YXL> yxls = new ArrayList<>();

                String str = testBean.toString();

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    if(jsonObject.optString("code").equals("0")){
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                        boolean isFirst = true;
                        Iterator iterator = jsonObject1.keys();
                        while(iterator.hasNext()){
                            String key = (String) iterator.next();
                            String value = jsonObject1.optString(key);

                            YXL yxl = new YXL();
                            yxl.name = key;
                            yxl.url = value;
                            if(isFirst){
                                yxl.select = true;
                                isFirst = false;
                            }else{
                                yxl.select = false;
                            }
                            yxls.add(yxl);
                        }

                    }

                }catch (JSONException e){}


                getView().requestYXL(yxls);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }






    public interface MvpView{
        void requestYXL(List<YXL> yxls);
        void error();
    }




}

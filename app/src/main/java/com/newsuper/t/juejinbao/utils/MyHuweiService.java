package com.newsuper.t.juejinbao.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
//
//
//public class MyHuweiService extends PushReceiver {
//    public static final String TAG = "HuaweiPushRevicer";
//
//    public static final String ACTION_UPDATEUI = "action.updateUI";
//    public static final String ACTION_TOKEN = "action.updateToken";
//
//    private static List<IPushCallback> pushCallbacks = new ArrayList<IPushCallback>();
//    private static final Object CALLBACK_LOCK = new Object();
//
//    public interface IPushCallback {
//        void onReceive(Intent intent);
//    }
//
//    public static void registerPushCallback(IPushCallback callback) {
//        synchronized (CALLBACK_LOCK) {
//            pushCallbacks.add(callback);
//        }
//    }
//
//    public static void unRegisterPushCallback(IPushCallback callback) {
//        synchronized (CALLBACK_LOCK) {
//            pushCallbacks.remove(callback);
//        }
//    }
//
//    @Override
//    public void onToken(Context context, String tokenIn, Bundle extras) {
//        Log.e("onToken：", "获取的token+===========>>>>>>>>>>" + tokenIn);
//        Map<String, String> map = new HashMap<>();
//        map.put("alias", tokenIn);
//        map.put("type", "huawei");
//        rx.Observable<PostAliasEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
//                postAlias(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<PostAliasEntity>()));
//        RetrofitManager.getInstance(context).toSubscribe(observable, new Subscriber<PostAliasEntity>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(PostAliasEntity postAliasEntity) {
//
//            }
//        });
//    }
//
//    @Override
//    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
//
//        try {
//            String content = new String(msg, StandardCharsets.UTF_8);
//            Log.e("onPushMsg：", "未转换 接收消息=======>>>>>" + content);
//            HuaweiPushEntity huaweiPushEntity = JSON.parseObject(content, HuaweiPushEntity.class);
//            Log.e("onPushMsg：", "接收消息=======>>>>>" + huaweiPushEntity.getJsonData());
//            //透传消息
//            EventBus.getDefault().post(new PushDataEvent(1, huaweiPushEntity.getJsonData()));
//
//        } catch (Exception e) {
//            Log.e("onPushMsg：", "接收消息=======>>>>>" + e.toString());
//        }
//        return false;
//    }
//
//    public void onEvent(Context context, Event event, Bundle extras) {
//
//        super.onEvent(context, event, extras);
//    }
//
//    @Override
//    public void onPushState(Context context, boolean pushState) {
//        Log.e("onPushState：", "接收状态");
//    }
//
//    private static void callBack(Intent intent) {
//        synchronized (CALLBACK_LOCK) {
//            for (IPushCallback callback : pushCallbacks) {
//                if (callback != null) {
//                    callback.onReceive(intent);
//                }
//            }
//        }
//    }
//
//}

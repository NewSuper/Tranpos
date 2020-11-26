package com.newsuper.t.juejinbao.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


import org.greenrobot.eventbus.EventBus;
//
//public class MyHuaWeiNotifyReceiver extends PushReceiver {
//    private static final String TAG = "另外的service";
//
//    @Override
//    public void onEvent(Context context, Event event, Bundle bundle) {
//        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
//            int notifyId = bundle.getInt(BOUND_KEY.pushNotifyId, 0);
//            Log.i(TAG, "收到通知栏消息点击事件,notifyId:" + notifyId);
//            if (0 != notifyId) {
//                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.cancel(notifyId);
//            }
//        }
//
//        String message = bundle.getString(BOUND_KEY.pushMsgKey, "UTF-8");
//        try {
//
//            JSONArray objects = JSONArray.parseArray(message);
//            String content = objects.getString(0);
//            HuaweiPushEntity huaweiPushEntity = JSON.parseObject(content, HuaweiPushEntity.class);
//            //华为单独处理
//            EventBus.getDefault().post(new PushDataEvent(2, huaweiPushEntity.getJsonData()));
//        } catch (Exception e) {
//            Log.e(TAG, "onEvent: 事件===========" + e.toString());
//        }
//        super.onEvent(context, event, bundle);
//    }
//}

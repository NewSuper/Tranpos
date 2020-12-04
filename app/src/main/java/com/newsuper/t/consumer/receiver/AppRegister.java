package com.newsuper.t.consumer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.newsuper.t.consumer.wxapi.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.APP_ID);
    }
}
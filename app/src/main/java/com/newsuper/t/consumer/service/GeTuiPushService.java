
package com.newsuper.t.consumer.service;

import com.igexin.sdk.PushService;


/**
 * 核心服务, 继承 android.app.Service, 必须实现以下几个接口, 并在 AndroidManifest 声明该服务并配置成
 * android:process=":pushservice", 具体参考 开发文档
 * PushManager.getInstance().initialize(this.getApplicationContext(), userPushService), 其中
 * userPushService 为 用户自定义服务 即 DemoPushService.
 */


public class GeTuiPushService extends PushService {


}



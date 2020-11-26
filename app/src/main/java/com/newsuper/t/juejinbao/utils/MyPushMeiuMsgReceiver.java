package com.newsuper.t.juejinbao.utils;

//
//import android.content.Context;
//import android.content.Intent;
//
//import com.juejinchain.android.R;
//import com.juejinchain.android.module.MainActivity;
//import com.meizu.cloud.pushinternal.DebugLogger;
//import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
//import com.meizu.cloud.pushsdk.handler.MzPushMessage;
//import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
//import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
//import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
//import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
//import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
//import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
//
//public class MyPushMeiuMsgReceiver extends MzPushMessageReceiver {
//    Intent privateIntent;
//
//    @Override
//    public void onMessage(Context context, Intent intent) {
//        super.onMessage(context, intent);
//        privateIntent = intent;
//    }
//
//    @Override
//    @Deprecated
//    public void onRegister(Context context, String s) {
//        // 调用旧版的订阅 PushManager.register(context) 方法后，
//        // 会在此回调订阅状态（已废弃）
//    }
//
//    @Override
//    @Deprecated
//    public void onUnRegister(Context context, boolean b) {
//        // 调用旧版的反订阅 PushManager.unRegister(context) 方法后，
//        // 会在此回调反订阅状态（已废弃）
//    }
//
//    @Override
//    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
//        // 调用新版的订阅 PushManager.register(context,appId,appKey) 方法后，
//        // 会在此回调订阅状态
//        if (privateIntent == null) {
//            privateIntent = new Intent(context, MainActivity.class);
//        }
//        if (privateIntent != null) {
//            privateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            try {
//                context.startActivity(privateIntent);
//            } catch (Exception e) {
//                DebugLogger.e(TAG, "Click message StartActivity error " + e.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
//        // 调用新版的反订阅 PushManager.unRegister(context,appId,appKey) 方法后，
//        // 会在此回调订阅状态
//    }
//
//    @Override
//    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
//        // 调用 PushManager.switchPush/checkPush 方法后，
//        // 会在此回调通知栏和透传消息的开关状态
//    }
//
//    @Override
//    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
//        // 调用 PushManager.subScribeTags/unSubScribeTags/unSubScribeAllTags
//        // /checkSubScribeTags 方法后，会在此回调标签相关信息
//    }
//
//    @Override
//    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
//        // 调用 PushManager.subScribeAlias/unSubScribeAlias/checkSubScribeAlias
//        // /checkSubScribeTags 方法后，会在此回调别名相关信息
//    }
//
//    @Override
//    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
//        // 兼容旧版本 Flyme 系统中设置消息弹出后状态栏中的小图标
//        // 同时请在相应的 drawable 不同分辨率文件夹下放置一张名称务必为
//        // mz_push_notification_small_icon 的图片
//        pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_push_notification_small_icon);
//    }
//
//    @Override
//    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
//        // 当用户点击通知栏消息后会在此方法回调
//    }
//
//    @Override
//    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
//        // 当推送的通知栏消息展示后且应用进程存在时会在此方法回调
//    }
//}

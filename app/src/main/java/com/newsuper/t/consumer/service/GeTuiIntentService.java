package com.newsuper.t.consumer.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GetuiBean;
import com.newsuper.t.consumer.function.TopActivity3;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.person.activity.MessageCenterActivity;
import com.newsuper.t.consumer.function.person.activity.MyCouponActivity;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.List;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */

public class GeTuiIntentService extends GTIntentService {
    private static final String TAG = "GetuiSdkDemo";
    public static final String channel_id = "channel_1";
    public static final String channel_name = "channel_name_1";

    /**
     * 为了观察透传数据变化.
     */
    private static int cnt;
    public GeTuiIntentService() {

    }
    @Override
    public void onReceiveServicePid(Context context, int pid) {
        LogUtil.log(TAG, "onReceiveServicePid -> " + pid);
    }
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        LogUtil.log(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            LogUtil.log(TAG, "receiver payload = " + data);
            LogUtil.log(TAG, "receiver  = " + SharedPreferencesUtil.getNotification());
            //是否接受消息通知
            if (!SharedPreferencesUtil.getNotification()){
                LogUtil.log(TAG, "receiver  = close");
                return;
            }
            LogUtil.log(TAG, "receiver  = open ");
            if (StringUtils.isEmpty(data)){
                return;
            }
            LogUtil.log(TAG, "receiver  = data ");
            GetuiBean getuiBean = new Gson().fromJson(data,GetuiBean.class);
            String type = getuiBean.type;
            LogUtil.log(TAG, "type = " + type);
            if (!StringUtils.isEmpty(type)){
                if ("order".equals(type) && getuiBean.data.orderData != null){
                    String order_id = getuiBean.data.orderData.order_id;
                    String title = getuiBean.data.orderData.title;
                    String content = getuiBean.data.orderData.content;
                    String order_no = getuiBean.data.orderData.order_no;
                    LogUtil.log(TAG, "order_id = " + order_id);
                    if (isBackground(context)){
                        LogUtil.log(TAG, "--------------------------------------   is  Background  ----");
                        showNotification(getApplicationContext(),title,content,order_id,order_no);
                    }else {
                        LogUtil.log(TAG, "--------------------------------------   is not  Background  ----");
                        Intent intent = new Intent();
                        intent.putExtra("title",title);
                        intent.putExtra("content",content);
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("order_no",order_no);
                        intent.setAction(Const.BROADCAST_ACTION_ORDER);
                        sendBroadcast(intent);
                    }
                }else  if ("coupon".equals(type) && getuiBean.data.couponData != null){
                    String title = getuiBean.data.couponData.title;
                    String content = getuiBean.data.couponData.content;
                    if (isBackground(context)){
                        LogUtil.log(TAG, "--------------------------------------   is  Background  ----");
                        showCouponNotification(getApplicationContext(),title,content);
                    }else {
                        LogUtil.log(TAG, "--------------------------------------   is not  Background  ----");
                        Intent intent = new Intent();
                        intent.putExtra("title",title);
                        intent.putExtra("content",content);
                        intent.setAction(Const.BROADCAST_ACTION_COUPON);
                        sendBroadcast(intent);
                    }
                }
            }else {
                String order_id = getuiBean.order_id;
                String title = getuiBean.title;
                String content =getuiBean.content;
                String order_no = getuiBean.order_no;
                if (!StringUtils.isEmpty(order_id)){
                    if (isBackground(context)){
                        LogUtil.log(TAG, "--------------------------------------   is  Background  ----");
                        showNotification(getApplicationContext(),title,content,order_id,order_no);
                    }else {
                        LogUtil.log(TAG, "--------------------------------------   is not  Background  ----");
                        Intent intent = new Intent();
                        intent.putExtra("title",title);
                        intent.putExtra("content",content);
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("order_no",order_no);
                        intent.setAction(Const.BROADCAST_ACTION_ORDER);
                        sendBroadcast(intent);
                    }
                }
            }
        }

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);

    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        LogUtil.log(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        LogUtil.log(TAG, "onReceiveCommandResult -> " + cmdMessage.getAction());

        int action = cmdMessage.getAction();

        if (action == PushConsts.SET_TAG_RESULT) {
            setTagResult((SetTagCmdMessage) cmdMessage);
        } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }else if (action == PushConsts.GET_MSG_DATA){

        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtil.log(TAG, "onNotificationMessageArrived -> " );
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        LogUtil.log(TAG, "onNotificationMessageClicked -> " );
    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();

        String text = "设置标签失败, 未知异常";
        switch (Integer.valueOf(code)) {
            case PushConsts.SETTAG_SUCCESS:
                text = "设置标签成功";
                break;

            case PushConsts.SETTAG_ERROR_COUNT:
                text = "设置标签失败, tag数量过大, 最大不能超过200个";
                break;

            case PushConsts.SETTAG_ERROR_FREQUENCY:
                text = "设置标签失败, 频率过快, 两次间隔应大于1s且一天只能成功调用一次";
                break;

            case PushConsts.SETTAG_ERROR_REPEAT:
                text = "设置标签失败, 标签重复";
                break;

            case PushConsts.SETTAG_ERROR_UNBIND:
                text = "设置标签失败, 服务未初始化成功";
                break;

            case PushConsts.SETTAG_ERROR_EXCEPTION:
                text = "设置标签失败, 未知异常";
                break;

            case PushConsts.SETTAG_ERROR_NULL:
                text = "设置标签失败, tag 为空";
                break;

            case PushConsts.SETTAG_NOTONLINE:
                text = "还未登陆成功";
                break;

            case PushConsts.SETTAG_IN_BLACKLIST:
                text = "该应用已经在黑名单中,请联系售后支持!";
                break;

            case PushConsts.SETTAG_NUM_EXCEED:
                text = "已存 tag 超过限制";
                break;

            default:
                break;
        }

        LogUtil.log(TAG, "settag result sn = " + sn + ", code = " + code + ", text = " + text);
    }

    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

        LogUtil.log(TAG, "onReceiveCommandResult -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nactionid = " + actionid + "\nresult = " + result
                + "\ncid = " + cid + "\ntimestamp = " + timestamp);
    }


    private void showNotification(Context context,String title,String content,String order_id,String order_no){
     /*   DEFAULT_ALL                  使用所有默认值，比如声音，震动，闪屏等等
        DEFAULT_LIGHTS            使用默认闪光提示
        DEFAULT_SOUNDS         使用默认提示声音
        DEFAULT_VIBRATE         使用默认手机震动*/
        Intent []in = makeIntentStack(context,order_id,order_no);
        PendingIntent contentIntent = PendingIntent.getActivities(context, 0 ,in ,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notice = null ;
        if (Build.VERSION.SDK_INT >= 26){
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                // 声音
                Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
                channel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                channel.enableVibration(true);
                channel.enableLights(true);
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                // 振动
                channel.enableVibration(true);
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                channel.enableLights(true);// 认闪光提示
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            manager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(context,channel_id);
            builder.setSmallIcon(R.mipmap.app_logo);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setTicker(title);
            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);
            notice = builder.build();
        }else {
            NotificationCompat.Builder builder1 = null;
            builder1 = new NotificationCompat.Builder(context);
            builder1.setSmallIcon(R.mipmap.app_logo);
            builder1.setWhen(System.currentTimeMillis());
            builder1.setContentTitle(title);
            builder1.setContentText(content);
            builder1.setTicker(title);
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                builder1.setDefaults( Notification.DEFAULT_SOUND );// 声音
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                builder1.setDefaults( Notification.DEFAULT_VIBRATE );// 振动
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                builder1.setDefaults( Notification.DEFAULT_LIGHTS );// 认闪光提示
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            builder1.setContentIntent(contentIntent);
            builder1.setAutoCancel(true);
            notice = builder1.build();
        }

        manager.notify(1,notice);

    }
    private void showCouponNotification(Context context,String title,String content){
        Intent []in = makeCouponIntentStack(context);
        PendingIntent contentIntent = PendingIntent.getActivities(context, 0 ,in ,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notice = null ;

        if (Build.VERSION.SDK_INT >= 26){

            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                // 声音
                Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
                channel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                channel.enableVibration(true);
                channel.enableLights(true);
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                // 振动
                channel.enableVibration(true);
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                channel.enableLights(true);// 认闪光提示
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            manager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(context,channel_id);
            builder.setSmallIcon(R.mipmap.app_logo);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setTicker(title);

            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);
            notice = builder.build();
        }else {
            NotificationCompat.Builder builder1 =  new NotificationCompat.Builder(context);
            builder1.setSmallIcon(R.mipmap.app_logo);
            builder1.setWhen(System.currentTimeMillis());
            builder1.setContentTitle(title);
            builder1.setContentText(content);
            builder1.setTicker(title);

         /*   DEFAULT_ALL                  使用所有默认值，比如声音，震动，闪屏等等
        DEFAULT_LIGHTS            使用默认闪光提示
        DEFAULT_SOUNDS         使用默认提示声音
        DEFAULT_VIBRATE         使用默认手机震动*/
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                builder1.setDefaults( Notification.DEFAULT_SOUND );// 声音
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                builder1.setDefaults( Notification.DEFAULT_VIBRATE );// 振动
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                builder1.setDefaults( Notification.DEFAULT_LIGHTS );// 认闪光提示
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            builder1.setContentIntent(contentIntent);
            builder1.setAutoCancel(true);
            notice = builder1.build();
        }
        manager.notify(1,notice);
    }
    private void showMsgCenterNotification(Context context,String title,String content,String id){
        Intent []in = makeMsgCenterIntentStack(context,id);
        PendingIntent contentIntent = PendingIntent.getActivities(context, 0 ,in ,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notice = null ;

        if (Build.VERSION.SDK_INT >= 26){

            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                // 声音
                Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
                channel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                channel.enableVibration(true);
                channel.enableLights(true);
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                // 振动
                channel.enableVibration(true);
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                channel.enableLights(true);// 认闪光提示
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            manager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(context,channel_id);
            builder.setSmallIcon(R.mipmap.app_logo);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setTicker(title);

            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);
            notice = builder.build();
        }else {
            NotificationCompat.Builder builder1 =  new NotificationCompat.Builder(context);
            builder1.setSmallIcon(R.mipmap.app_logo);
            builder1.setWhen(System.currentTimeMillis());
            builder1.setContentTitle(title);
            builder1.setContentText(content);
            builder1.setTicker(title);

         /*   DEFAULT_ALL                  使用所有默认值，比如声音，震动，闪屏等等
        DEFAULT_LIGHTS            使用默认闪光提示
        DEFAULT_SOUNDS         使用默认提示声音
        DEFAULT_VIBRATE         使用默认手机震动*/
            // 设置显示通知时的默认的发声、振动、Light效果
            if (SharedPreferencesUtil.getShake() && SharedPreferencesUtil.getVoice()){
                LogUtil.log("GetuiSdkDemo","使用所有默认值，比如声音，震动，闪屏 ");
                builder1.setDefaults( Notification.DEFAULT_SOUND );// 声音
                LogUtil.log("GetuiSdkDemo","声音提醒 + ");
            }else if (!SharedPreferencesUtil.getVoice() && SharedPreferencesUtil.getShake()){
                builder1.setDefaults( Notification.DEFAULT_VIBRATE );// 振动
                LogUtil.log("GetuiSdkDemo","振动提醒 + ");
            }else {
                builder1.setDefaults( Notification.DEFAULT_LIGHTS );// 认闪光提示
                LogUtil.log("GetuiSdkDemo","认闪光提示 ");
            }
            builder1.setContentIntent(contentIntent);
            builder1.setAutoCancel(true);
            notice = builder1.build();
        }
        manager.notify(1,notice);
    }

    //任务栈
    Intent[] makeMsgCenterIntentStack(Context context,String msg_id) {
        Intent[] intents = new Intent[2];
        intents[0] = new Intent(context, TopActivity3.class);
        intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[1] = new Intent(context,MessageCenterActivity.class);
        intents[1].putExtra("msg_id",msg_id);
        return intents;
    }
    //任务栈
    Intent[] makeIntentStack(Context context,String order_id,String order_no) {
        Intent[] intents = new Intent[2];
        intents[0] = new Intent(context, TopActivity3.class);
        intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[1] = new Intent(context,MyOrderActivity.class);
        intents[1].putExtra("order_id",order_id);
        intents[1].putExtra("order_no",order_no);
        return intents;
    }

    //任务栈
    Intent[] makeCouponIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = new Intent(context, TopActivity3.class);
        intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[1] = new Intent(context, MyCouponActivity.class);
        return intents;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                //处于后台
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
                //处于前台
                else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        LogUtil.log(TAG, "onCreate -> " );
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogUtil.log(TAG, "onDestroy -> " );
        super.onDestroy();
    }

}

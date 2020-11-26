package com.newsuper.t.juejinbao.utils;

//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.juejinchain.android.event.PushDataEvent;
//import com.juejinchain.android.module.MainActivity;
//import com.xiaomi.mipush.sdk.ErrorCode;
//import com.xiaomi.mipush.sdk.MiPushClient;
//import com.xiaomi.mipush.sdk.MiPushCommandMessage;
//import com.xiaomi.mipush.sdk.MiPushMessage;
//import com.xiaomi.mipush.sdk.PushMessageReceiver;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.List;
//
//
//public class MiPushReceiver extends PushMessageReceiver {
//    private String mRegId;
//    private long mResultCode = -1;
//    private String mReason;
//    private String mCommand;
//    private String mMessage;
//    private String mTopic;
//    private String mAlias;
//    private String mUserAccount;
//    private String mStartTime;
//    private String mEndTime;
//
//    @Override
//    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        Log.e("TAG", "onNotificationMessageClicked: 000========>>>>>>" + mMessage);
//        //透传消息
//        EventBus.getDefault().post(new PushDataEvent(1, mMessage));
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
//    }
//
//    @Override
//    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        //通知栏点击跳转推送消息
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("pushType", 1);
//        intent.putExtra("pushMsg", mMessage);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
//
//    }
////    action_type  点击执行动作
////    {
////        "1": "点击领豪车-首页",
////            "2": "180天赚房赚车计划-发财计划",
////            "3": "查看我的奖励 - 奖励明细页面",
////            "7": "开启宝箱 - 开宝箱页面",
////            "8": "如何极速上手 - 手把手教你赚掘金宝",
////            "10": "点击进入任务页面（自动签到）",
////            "11": "我的页面",
////            "12": "文章详情页",
////            "15": "暴富秘籍",
////            "17": "手把手教你如何邀请好友",
////            "18": "实物奖励表页面",
////            "19":"我的消息",
////            "20":"内置浏览器打开网页",
////            "21":"外部浏览器打开网页"
////    }
//
////    show_type:展示方式
////    {
////        "1":"通知栏",
////            "2":"首页栏下方跑马灯",
////            "3":"我的页面上方跑马灯",
////            "4":"任务页面跑马灯",
////            "5":"首页弹窗广告"
////    }
//
//    //    推送json参数：
////    {"action_type":"1",
////            "action_url":"http://dev.wechat.juejinchain.cn/#/",
////            "show_title":"内部浏览器展示标题",
////            "imgurl":"http://p1-tt.byteimg.com/large/pgc-image/RXrOdURDrloHTt?from=detail",
////            "show_type":"2"}
//    @Override
//    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
//
//        //通知栏消息
//        mMessage = message.getContent();
//        Log.e("TAG", "onNotificationMessageClicked: 222========>>>>>>" + mMessage);
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
//    }
//
//    @Override
//    public void onCommandResult(Context context, MiPushCommandMessage message) {
//        String command = message.getCommand();
//        Log.e("TAG", "onNotificationMessageClicked: 333========>>>>>>" + command);
//
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mStartTime = cmdArg1;
//                mEndTime = cmdArg2;
//            }
//        }
//    }
//
//    @Override
//    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
//        String command = message.getCommand();
//        Log.e("TAG", "onNotificationMessageClicked: 444========>>>>>>" + command);
//
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        }
//    }
//}

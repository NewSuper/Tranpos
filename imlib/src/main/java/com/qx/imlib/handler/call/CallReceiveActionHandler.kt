package com.qx.imlib.handler.call

import android.annotation.SuppressLint
import android.util.Log
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CVideoAnswered
import io.netty.channel.ChannelHandlerContext

//音视频对方用户相关操作，比如挂断，接听，取消，音视频切换，呼叫超时等
class CallReceiveActionHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        val data = S2CVideoAnswered.VideoAnswered.parseFrom(recMessage!!.contents)
        // 收到音视频消息这时候没有入本地数据库的，
        // 服务端会通话挂单或者取消后发送一条p2p消息入本地数据库作为本次通话的消息
        // 因为多进程需要将本消息转发到qximclient中处理
        // ModuleManager.routeMessage(recMessage)
        EventBusUtil.post(CallEvent(recMessage.cmd,data.roomId,"",
            "","",data.userId, mutableListOf()))
        Log.e("CallReceiveActionHandler","音视频对方用户相关操作：cmd:${recMessage.cmd}")
    }
}
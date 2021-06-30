package com.qx.imlib.handler.call

import android.util.Log
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CVideoCall
import io.netty.channel.ChannelHandlerContext

//收到音视频呼叫
class CallIncomeHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        val data = S2CVideoCall.VideoCall.parseFrom(recMessage!!.contents)
        //收到音视频消息这时没有存入db
        //服务端会挂单或取消后发送一条p2p消息到db作为本次通话的消息
        //因为多进程需要将本消息转发到qximclient中处理
        EventBusUtil.post(CallEvent(recMessage.cmd,data.roomId,data.sendType,
            data.targetId,data.type,data.userId,data.membersList))
        Log.e("CallIncomeHandler","收到音视频消息呼叫：${data.roomId},：${data.type}")
    }
}
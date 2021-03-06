package com.qx.imlib.handler.call

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.C2SVideoParam
import io.netty.channel.ChannelHandlerContext

class CallVideoParamHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        val data = C2SVideoParam.VideoParam.parseFrom(recMessage!!.contents)
        // 收到音视频消息这时候没有入本地数据库的，
        // 服务端会通话挂单或者取消后发送一条p2p消息入本地数据库作为本次通话的消息
        // 因为多进程需要将本消息转发到qximclient中处理
        // ModuleManager.routeMessage(recMessage)
        val event = CallEvent(recMessage.cmd,data.roomId,"",
            "","",data.userId, mutableListOf())
        event.data = data.param
        EventBusUtil.post(event)
    }
}
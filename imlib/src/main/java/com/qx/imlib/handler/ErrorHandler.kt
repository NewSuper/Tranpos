package com.qx.imlib.handler

import com.qx.imlib.netty.*
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CCommon
import io.netty.channel.ChannelHandlerContext

class ErrorHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val error = S2CCommon.ErrorResponse.parseFrom(recMessage.contents)
        QLog.i("ErrorHandler",
            "channelRead 错误：cmd=" + recMessage.cmd + " getCode" + "=" + error.code + " message:" + error.message
        )
        HeartBeatTimeCheck.getInstance().cancelTimer()
        NettyConnectionStateManager.getInstance().state= NettyConnectionState.STATE_SERVER_REFUSE
        val status = ConnectionStatusListener.Status.SERVER_INVALID
        status.message = error.message
        EventBusUtil.post(status)
        if(error.code == 301) {
            //非法请求，拒绝服务，服务端需强制断开TCP连接，不可重连，协议文档：S2C_ErrorResponse
        } else {
            //可重连
        }
    }
}
package com.qx.imlib.handler.call

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.it.protos.S2CVideoCallResult
import io.netty.channel.ChannelHandlerContext

class CallVideoResultHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val data = S2CVideoCallResult.VideoCallResult.parseFrom(recMessage.contents)
        QLog.d("CallVideoResultHandler","$data")
    }
}

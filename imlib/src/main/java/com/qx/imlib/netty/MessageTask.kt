package com.qx.imlib.netty

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.qlog.QLog
import io.netty.channel.ChannelHandlerContext

class MessageTask(var ctx:ChannelHandlerContext,var msg:S2CRecMessage) {
    fun handle(handler: BaseCmdHandler){
        QLog.d("MessageTask","并发测试 消费任务")
        handler.handle(ctx, msg)
    }
}
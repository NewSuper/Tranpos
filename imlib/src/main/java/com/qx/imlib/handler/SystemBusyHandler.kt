package com.qx.imlib.handler

import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import io.netty.channel.ChannelHandlerContext

class SystemBusyHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        QLog.i("SystemBusyHandler",  "cmd=" + recMessage!!.cmd + "系统繁忙")
    }
}
package com.qx.imlib.handler

import com.qx.imlib.netty.HeartBeatTimeCheck
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import io.netty.channel.ChannelHandlerContext

class HeartBeatHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        QLog.i("HeartBeatHandler", "收到心跳包回复:" + recMessage!!.sequence)
        HeartBeatTimeCheck.getInstance().cancelTimer()
    }
}
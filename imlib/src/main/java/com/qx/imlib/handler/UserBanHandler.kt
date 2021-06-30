package com.qx.imlib.handler

import com.qx.imlib.netty.ConnectionStatusListener
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import io.netty.channel.ChannelHandlerContext
import org.greenrobot.eventbus.EventBus

class UserBanHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        QLog.i("UserBanHandler", "cmd=" + recMessage!!.cmd + "用户被封禁")
        EventBus.getDefault().post(ConnectionStatusListener.Status.CONN_USER_BLOCKED)
    }
}
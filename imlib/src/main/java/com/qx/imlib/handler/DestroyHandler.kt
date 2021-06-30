package com.qx.imlib.handler

import com.qx.im.model.UserInfoCache
import com.qx.imlib.job.JobManagerUtil
import com.qx.imlib.netty.ConnectionStatusListener
import com.qx.imlib.netty.NettyConnectionState
import com.qx.imlib.netty.NettyConnectionStateManager
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import io.netty.channel.ChannelHandlerContext

class DestroyHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        QLog.i("DestroyHandler", "注销成功");
        JobManagerUtil.instance.logout()
        UserInfoCache.setUserId("")
        UserInfoCache.setToken("")
        NettyConnectionStateManager.getInstance().state = NettyConnectionState.STATE_DISCONNECTED
        EventBusUtil.post(ConnectionStatusListener.STATUS_LOGOUT)
        ctx?.close()
    }
}
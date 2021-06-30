package com.qx.imlib.handler

import com.qx.im.model.UserInfoCache
import com.qx.imlib.job.JobManagerUtil
import com.qx.imlib.netty.ConnectionStatusListener
import com.qx.imlib.netty.NettyConnectionState
import com.qx.imlib.netty.NettyConnectionStateManager
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import io.netty.channel.ChannelHandlerContext

class KickHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        JobManagerUtil.instance.logout()
        UserInfoCache.setUserId("")
        UserInfoCache.setToken("")
        NettyConnectionStateManager.getInstance().state = NettyConnectionState.STATE_KICKED
        EventBusUtil.post(ConnectionStatusListener.STATUS_KICKED)
    }
}
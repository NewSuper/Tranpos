package com.qx.imlib.handler.chatroom

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import io.netty.channel.ChannelHandlerContext

class ChatRoomDestroyHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        QLog.i("ChatRoomDestroyHandler", "cmd=" + recMessage!!.cmd + "聊天室已销毁")
        EventBusUtil.postChatRoomDestroy()
    }
}
package com.qx.imlib.handler.chatroom

import com.qx.im.model.ConversationType
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.ChatNotice
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CGlobalOperation
import io.netty.channel.ChannelHandlerContext

class ChatRoomGlobalMuteHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var opr = S2CGlobalOperation.GlobalOperation.parseFrom(recMessage!!.contents)
        QLog.i("ChatRoomGlobalMuteHandler", "cmd=" + recMessage!!.cmd + "聊天室【全局禁言】："+" userId="+opr.userId +" type="+opr.type)
        var notice = ChatNotice()
        notice.isGlobal = true
        notice.sendType = ConversationType.TYPE_CHAT_ROOM
        notice.userId = opr.userId
        notice.type = opr.type
        EventBusUtil.postChatNotice(notice)
    }
}
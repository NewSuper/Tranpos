package com.qx.imlib.handler.chatroom

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.ChatNotice
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CSpecialOperation
import io.netty.channel.ChannelHandlerContext

class ChatRoomMemberBanHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var opr = S2CSpecialOperation.SpecialOperation.parseFrom(recMessage!!.contents)
        QLog.i("ChatRoomMemberBanHandler", "cmd=" + recMessage!!.cmd
                + "聊天室成员【封禁】： sendType="+opr.sendType+" userId="+opr.userId +" targetId="+opr
            .targetId+" type="+opr.type)
        var notice = ChatNotice()
        notice.sendType = opr.sendType
        notice.targetId = opr.targetId
        notice.userId = opr.userId
        notice.type = opr.type
        notice.isBan = true
        EventBusUtil.postChatNotice(notice)
    }
}
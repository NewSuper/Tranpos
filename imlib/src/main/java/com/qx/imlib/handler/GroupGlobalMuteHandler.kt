package com.qx.imlib.handler

import com.qx.im.model.ConversationType
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.ChatNotice
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CGlobalOperation
import io.netty.channel.ChannelHandlerContext

class GroupGlobalMuteHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var opr = S2CGlobalOperation.GlobalOperation.parseFrom(recMessage!!.contents)

        QLog.i("GroupGlobalMuteHandler",  "cmd=" + recMessage!!.cmd + " 全局群组禁言 userId=" + opr.userId + " type=" + opr.type)

        var notice = ChatNotice()
        notice.isGlobal = true
        notice.sendType = ConversationType.TYPE_GROUP
        notice.userId = opr.userId
        notice.type = opr.type
        EventBusUtil.postChatNotice(notice)
    }
}
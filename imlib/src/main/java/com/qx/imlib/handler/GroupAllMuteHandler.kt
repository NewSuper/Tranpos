package com.qx.imlib.handler

import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.ChatNotice
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CSpecialOperation
import io.netty.channel.ChannelHandlerContext

class GroupAllMuteHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var opr = S2CSpecialOperation.SpecialOperation.parseFrom(recMessage!!.contents)
        QLog.i("GroupAllMuteHandler", "cmd=" + recMessage!!.cmd + "群组【整体禁言】： sendType="+opr.sendType+" userId="+opr.userId +" targetId="+opr
            .targetId+" type="+opr.type)
        var notice = ChatNotice()
        notice.sendType = opr.sendType
        notice.targetId = opr.targetId
        notice.type = opr.type
        notice.isAll = true
        EventBusUtil.postChatNotice(notice)
    }
}
package com.qx.imlib.handler.conversation

import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CSpecialOperation
import io.netty.channel.ChannelHandlerContext

class ConversationNoDisturbingHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
      val specialOperation = S2CSpecialOperation.SpecialOperation.parseFrom(recMessage!!.contents)

        QLog.i("ConversationNoDisturbingHandler", "收到免打扰消息，sendType：" + specialOperation.sendType + " userId=" + specialOperation
            .userId + " targetId=" + specialOperation.targetId + " type=" + specialOperation.type)
        var isNoDisturbing = if(specialOperation.type == "set"){
            1
        }else{
            0
        }
        IMDatabaseRepository.instance.updateConversationNoDisturbing(specialOperation.sendType, specialOperation.targetId, isNoDisturbing)
        var conversation = IMDatabaseRepository.instance.getConversation(specialOperation.sendType, specialOperation.targetId)
        if (conversation != null) {
            //通知UI
            EventBusUtil.postConversationUpdate(arrayListOf(conversation))
        }
    }
}
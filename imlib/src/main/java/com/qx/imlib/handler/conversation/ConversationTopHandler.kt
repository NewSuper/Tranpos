package com.qx.imlib.handler.conversation

import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CSpecialOperation
import io.netty.channel.ChannelHandlerContext

class ConversationTopHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var specialOperation = S2CSpecialOperation.SpecialOperation.parseFrom(recMessage!!.contents)

        QLog.i("ConversationTopHandler",
            "收到置顶消息，sendType：" + specialOperation.sendType + " userId=" + specialOperation.userId + " targetId=" + specialOperation.targetId + " type=" + specialOperation.type
        )
        var isTop = if (specialOperation.type == "set") {
            1
        } else {
            0
        }
        //更新置顶状态
        IMDatabaseRepository.instance.updateConversationTop(
            specialOperation.sendType, specialOperation.targetId, isTop
        )
        var conversation = IMDatabaseRepository.instance.getConversation(specialOperation.sendType,
            specialOperation.targetId)
        if(conversation != null){
            //通知UI
            EventBusUtil.postConversationUpdate(arrayListOf(conversation))
        }
    }
}
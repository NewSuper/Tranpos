package com.qx.imlib.handler.chatroom

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CCustomMessage
import io.netty.channel.ChannelHandlerContext

class ReceiveChatRoomMessageHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        var msg = S2CCustomMessage.Msg.parseFrom(recMessage.contents)
        QLog.i("ReceiveChatRoomMessageHandler", "sendType=" + msg.sendType + " messageType=" + msg.messageType)
        var messageEntity = MessageConvertUtil.instance.convertToMessageEntity(msg)
        EventBusUtil.postNewChatRoomMessageEntity(messageEntity)
    }
}
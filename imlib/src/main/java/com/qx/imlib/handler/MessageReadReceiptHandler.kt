package com.qx.imlib.handler

import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CMessageRead
import io.netty.channel.ChannelHandlerContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
///消息已读回执
class MessageReadReceiptHandler:BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        var readState = S2CMessageRead.MessageRead.parseFrom(recMessage.contents)
        GlobalScope.launch {
            IMDatabaseRepository.instance.updateAllMessageReadState(readState.sendType,readState.targetId)
           EventBusUtil.postMessageRead()
        }
    }
}
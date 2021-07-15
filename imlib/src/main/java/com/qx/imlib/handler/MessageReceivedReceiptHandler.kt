package com.qx.imlib.handler

import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CMessageStatus
import io.netty.channel.ChannelHandlerContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//消息已送达回执
class MessageReceivedReceiptHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val status = S2CMessageStatus.MessageStatus.parseFrom(recMessage.contents)
        QLog.i("MessageReceivedReceiptHandler",
            "state=" + status.state + " from=" + status.from + " to=" + status.to + " " + "sendType=" + status.sendType)
        //使用Android协程实现异步
        GlobalScope.launch {
            //更新到数据库
            for( messageId in status.messageIdsList) {
                if (IMDatabaseRepository.instance.updateMessageState(messageId, status.state, "") > 0) {
                    //获取消息数据，并使用EventBus发送
                    EventBusUtil.postMessageStateChanged(IMDatabaseRepository.instance.getMessageById(messageId))
                }
            }
        }
    }
}
package com.qx.imlib.handler.conversation

import com.qx.im.model.ConversationModel
import com.qx.im.model.UserInfoCache
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.ConversationEntity
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CSessionUnReadCount
import com.qx.message.MessageType
import io.netty.channel.ChannelHandlerContext

class UnReadCountHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var unReadCount = S2CSessionUnReadCount.SessionUnReadCount.parseFrom(recMessage!!.contents)
        QLog.i("UnReadCountHandler", "消息未读会话：" + unReadCount.unReadsList.size)
        var list = arrayListOf<ConversationEntity>()
        for (data in unReadCount.unReadsList) {
            var messageEntity = MessageConvertUtil.instance.convertToMessageEntity(data.lastMsg)
            if (messageEntity.messageType == MessageType.TYPE_RECALL) {
                messageEntity.messageId = data.lastMsg.recall.targetMessageId
            }
            messageEntity.state = MessageEntity.State.STATE_RECEIVED
            //确保from为本人，to为对方
            if (messageEntity.to == UserInfoCache.getUserId()
                || messageEntity.to.isEmpty()
            ) {
                messageEntity.to = data.from
            }
            var conversationEntity = ConversationModel.instance.generateConversation(messageEntity)
            messageEntity.conversationId = conversationEntity.conversationId
            var result = IMDatabaseRepository.instance.insertMessage(messageEntity)
            QLog.d(
                "UnReadCountHandler",
                "insertMessage result:${result.newMessageCount}, conversationid:${conversationEntity.conversationId},message $messageEntity"
            )

            conversationEntity.unReadCount = data.count
            if (IMDatabaseRepository.instance.insertConversation(conversationEntity) > 0) {
                //处理会话最新一条消息时间
                IMDatabaseRepository.instance.refreshConversationInfo(
                    MessageConvertUtil.instance.convertToMessageEntity(
                        data.lastMsg
                    )
                )
                if (conversationEntity.isNew) {
                    ConversationModel.instance.getConversationProperty(conversationEntity)
                }
            }
            var c =
                IMDatabaseRepository.instance.getConversationById(conversationEntity.conversationId)
            list.add(c!!)
        }
        if (list.isNotEmpty()) {
            EventBusUtil.postConversationUpdate(list)
        }
    }
}
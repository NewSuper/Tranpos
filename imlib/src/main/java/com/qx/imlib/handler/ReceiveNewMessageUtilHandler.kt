package com.qx.imlib.handler

import android.util.Log
import com.qx.im.model.InsertMessageResult
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CCustomMessage
import com.qx.message.MessageType

abstract class ReceiveNewMessageUtilHandler:CustomMessageHandler() {
    private  val TAG = "ReceiveNewMessageUtilHa"
    override fun notifyUiUpdate(result: InsertMessageResult) {
        Log.e(TAG, "notifyUiUpdate: 更新ui")
        if (result.messages.size>0){
            //在此叠加消息未读
            IMDatabaseRepository.instance.addConversationUnReadCount(
                result.messages[0].sendType,result.messages[0].from,
                result.messages[0].to,result.newMessageCount
            )
            EventBusUtil.postNewMessageEntity(result.messages)
        }
    }
    override fun getMessages(recMessage: S2CRecMessage): List<MessageEntity> {
        val msg = S2CCustomMessage.Msg.parseFrom(recMessage.contents)
        var entity = MessageConvertUtil.instance.convertToMessageEntity(msg)
        if(msg.messageType == MessageType.TYPE_RECALL) {
            entity.messageId = msg.recall.targetMessageId
        }
        entity.state = MessageEntity.State.STATE_RECEIVED
        return arrayListOf(entity)
    }

    override fun getLatestMessage(sendType: String, from: String, to: String): MessageEntity {
        TODO("Not yet implemented")
    }

    override fun isNeedCheckDeleteTime(): Boolean {
        return false
    }
}
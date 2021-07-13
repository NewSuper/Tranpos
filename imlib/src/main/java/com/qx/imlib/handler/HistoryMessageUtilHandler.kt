package com.qx.imlib.handler

import android.util.Log
import com.qx.im.model.InsertMessageResult
import com.qx.im.model.UserInfoCache
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CMessageRead
import com.qx.it.protos.S2CMessageRecord
import com.qx.message.MessageType

open class HistoryMessageUtilHandler : CustomMessageHandler() {
    private val TAG = "HistoryMessageUtilHandl"
    override fun notifyUiUpdate(result: InsertMessageResult) {
        Log.e(TAG, "notifyUiUpdate: 发送EventBus更新UI:" + result.messages.size)
        EventBusUtil.postHistoryMessage(result.messages)
    }

    override fun setProperties(recMessage: S2CRecMessage) {
        var record = S2CMessageRead.MessageRead.parseFrom(recMessage.contents)
        super.setProperties(record.sendType, record.targetId)
    }

    override fun getLatestMessage(sendType: String, from: String, to: String): MessageEntity? {
        return IMDatabaseRepository.instance.getLatestMessage(
            sendType,
            from,
            to,
            UserInfoCache.getUserId()
        )
    }

    override fun isNeedCheckDeleteTime(): Boolean {
        return true
    }

    override fun getMessages(recMessage: S2CRecMessage): List<Any> {
        var list = arrayListOf<MessageEntity>()
        var record = S2CMessageRecord.MessageRecord.parseFrom(recMessage.contents)
        for (msg in record.msgsList) {
            var entity = MessageConvertUtil.instance.convertToMessageEntity(msg)
            if (msg.messageType == MessageType.TYPE_RECALL) {
                entity.messageId = msg.recall.targetMessageId
            }
            entity.state = MessageEntity.State.STATE_RECEIVED
            list.add(entity)
        }
        return list
    }
}
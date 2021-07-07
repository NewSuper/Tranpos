package com.qx.imlib

import com.qx.im.model.UserInfoCache
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.ConversationEntity
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.message.Conversation
import com.qx.message.Message

object ConversationUtil {

    fun toConversationEntity(conversation: Conversation): ConversationEntity {
        var conversationEntity = ConversationEntity()
        conversationEntity.conversationId = conversation.conversationId
        conversationEntity.ownerId = conversation.ownerId
        conversationEntity.conversationType = conversation.conversationType
        conversationEntity.targetId = conversation.targetId
        conversationEntity.icon = conversation.icon
        conversationEntity.targetName = conversation.targetName
        conversationEntity.draft = conversation.draft
        conversationEntity.atTo = conversation.atTo
        conversationEntity.draft = conversation.draft
        conversationEntity.unReadCount = conversation.unReadCount
        conversationEntity.noDisturbing = conversation.noDisturbing
        conversationEntity.top = conversation.top
        conversationEntity.deleted = conversation.deleted
        conversationEntity.timestamp = conversation.timestamp
        conversationEntity.deleteTime = conversation.deleteTime
        conversationEntity.timeIndicator = conversation.timeIndicator
        conversationEntity.topTime = conversation.topTime
        conversationEntity.isNew = conversation.isNew
        conversationEntity.background = conversation.background!!
        return conversationEntity
    }

    fun toConversation(conversationEntity: ConversationEntity?): Conversation? {
        if (conversationEntity == null) {
            return null
        }
        var conversation = Conversation()
        conversation.conversationId = conversationEntity.conversationId
        conversation.ownerId = conversationEntity.ownerId
        conversation.conversationType = conversationEntity.conversationType
        conversation.targetId = conversationEntity.targetId
        conversation.icon = conversationEntity.icon
        conversation.targetName = conversationEntity.targetName
        var message = getLatestMessage(conversationEntity.conversationId)
        conversation.lastMessage = message
        conversation.draft = conversationEntity.draft
        conversation.atTo = conversationEntity.atTo
        conversation.draft = conversationEntity.draft
        conversation.unReadCount = conversationEntity.unReadCount
        conversation.noDisturbing = conversationEntity.noDisturbing
        conversation.top = conversationEntity.top
        conversation.deleted = conversationEntity.deleted
        conversation.timestamp = conversationEntity.timestamp
        conversation.deleteTime = conversationEntity.deleteTime
        conversation.timeIndicator = conversationEntity.timeIndicator
        conversation.topTime = conversationEntity.topTime
        conversation.isNew = conversationEntity.isNew
        conversation.background = conversationEntity.background
        return conversation
    }


    fun getLatestMessage(conversationId: String): Message? {
        var messageEntity = IMDatabaseRepository.instance.getLatestMessageByConversationId(conversationId, UserInfoCache.getUserId())
        if (messageEntity != null) {
            return MessageConvertUtil.instance.convertToMessage(messageEntity)
        }
        return null
    }
}
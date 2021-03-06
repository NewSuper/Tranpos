package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.im.model.UserInfoCache

import com.qx.imlib.utils.UUIDUtil

@Entity(tableName = "text_message")
class TBTextMessage {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "owner_id")
    var ownerId: String = ""

    @ColumnInfo(name = "content")
    var content: String? = ""

    @ColumnInfo(name = "extra")
    var extra: String? = ""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0


    companion object {
        fun obtain(conversationId : String, messageId: String, content: String, extra: String): TBTextMessage {
            var textMessage = TBTextMessage()
            textMessage.conversationId = conversationId
            textMessage.messageId = messageId
            textMessage.content = content
            textMessage.ownerId = UserInfoCache.getUserId()
            textMessage.extra = extra
            return textMessage
        }
    }
}
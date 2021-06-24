package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "custom_message")
class TBCustomMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name="content")
    var content : String = ""

    @ColumnInfo(name = "extra")
    var extra : String=""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String, content: String, extra: String): TBCustomMessage {
            var message = TBCustomMessage()
            message.conversationId = conversationId
            message.messageId = messageId
            message.content = content
            message.extra = extra
            return message
        }
    }
}
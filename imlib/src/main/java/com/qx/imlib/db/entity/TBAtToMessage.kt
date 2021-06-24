package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil

@Entity(tableName = "at_to_message", primaryKeys = ["id"])
class TBAtToMessage {

    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "at_to")
    var atTo: String = ""

    /**
     * 阅读状态，0：未读，1已读
     */
    @ColumnInfo(name = "read")
    var read: Int = ReadState.STATE_UN_READ

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String, at: String, read : Int, timestamp : Long): TBAtToMessage {
            var atTo = TBAtToMessage()
            atTo.conversationId = conversationId
            atTo.messageId = messageId
            atTo.read = read
            atTo.atTo = at
            atTo.timestamp = timestamp
            return atTo
        }
    }

    object ReadState {
        val STATE_UN_READ = 0
        val STATE_READ = 1
    }
}
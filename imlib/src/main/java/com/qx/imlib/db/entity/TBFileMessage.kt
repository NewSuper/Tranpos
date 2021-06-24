package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil

@Entity(tableName = "file_message")
class TBFileMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "file_name")
    var fileName: String = ""

    @ColumnInfo(name = "origin_url")
    var originUrl: String = ""

    @ColumnInfo(name = "local_path")
    var localPath: String = ""//本地路径

    @ColumnInfo(name = "type")
    var type: String = ""

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "extra")
    var extra: String = ""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String, fileName: String, originUrl: String, localPath: String,
                   type: String, size: Long, extra: String): TBFileMessage {
            var message = TBFileMessage()
            message.conversationId = conversationId
            message.messageId = messageId
            message.fileName = fileName
            message.originUrl = originUrl
            message.localPath = localPath
            message.type = type
            message.size = size
            message.extra = extra
            return message
        }
    }
}
package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "image_message")
class TBImageMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "origin_url")
    var originUrl: String = ""

    @ColumnInfo(name = "breviary_url")
    var breviaryUrl: String = ""

    @ColumnInfo(name = "width")
    var width: Int = 0

    @ColumnInfo(name = "height")
    var height: Int = 0

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "extra")
    var extra: String = ""

    @ColumnInfo(name = "local_path")
    var localPath: String = ""//本地路径

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId: String,
                   messageId: String,
                   originUrl: String,
                   breviaryUrl: String,
                   localPath: String,
                   width: Int,
                   height: Int,
                   size: Long,
                   extra: String): TBImageMessage {
            var imageMessage = TBImageMessage()
            imageMessage.conversationId = conversationId
            imageMessage.messageId = messageId
            imageMessage.originUrl = originUrl
            imageMessage.localPath = localPath
            imageMessage.breviaryUrl = breviaryUrl
            imageMessage.size = size
            imageMessage.width = width
            imageMessage.height = height
            imageMessage.extra = extra
            return imageMessage
        }
    }
}
package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "video_message")
class TBVideoMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "head_url")
    var headUrl: String = ""// 视频首图地址

    @ColumnInfo(name = "origin_url")
    var originUrl: String = ""// 视频资源地址

    @ColumnInfo(name = "width")
    var width: Int = 0 // 视频宽度

    @ColumnInfo(name = "height")
    var height: Int = 0 // 视频高度

    @ColumnInfo(name = "duration")
    var duration: Int = 0 // 视频时长

    @ColumnInfo(name = "extra")
    var extra: String = "" // 扩展消息

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "local_path")
    var localPath: String = ""//本地路径

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String,
                   headUrl: String,
                   originUrl: String,
                   localPath : String,
                   width: Int,
                   height: Int,
                   size: Long,
                   duration: Int,
                   extra: String): TBVideoMessage {
            var message = TBVideoMessage()
            message.conversationId = conversationId
            message.messageId = messageId
            message.headUrl = headUrl
            message.localPath = localPath
            message.originUrl = originUrl
            message.width = width
            message.height = height
            message.size = size
            message.duration = duration
            message.extra = extra
            return message
        }
    }
}
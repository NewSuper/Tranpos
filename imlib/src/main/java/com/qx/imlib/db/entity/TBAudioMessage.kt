package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "audio_message")
class TBAudioMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "origin_url")
    var originUrl: String = ""

    @ColumnInfo(name = "extra")
    var extra: String = ""

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "duration")
    var duration: Int = 0 // 音频时长

    @ColumnInfo(name = "local_path")
    var localPath: String = ""//本地路径

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String, localPath: String,originUrl: String, size : Long, duration: Int, extra: String): TBAudioMessage {
            var audioMessage = TBAudioMessage()
            audioMessage.conversationId = conversationId
            audioMessage.messageId = messageId
            audioMessage.localPath = localPath
            audioMessage.originUrl = originUrl
            audioMessage.size = size
            audioMessage.duration = duration
            audioMessage.extra = extra
            return audioMessage
        }
    }
}
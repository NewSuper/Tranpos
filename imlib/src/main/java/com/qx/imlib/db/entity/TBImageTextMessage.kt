package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "image_text_message")
class TBImageTextMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "title")
    var title: String = "" // 标题

    @ColumnInfo(name = "content")
    var content: String = ""// 内容

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = ""// 图片地址

    @ColumnInfo(name = "redirect_url")
    var redirectUrl: String = "" // 链接跳转地址

    @ColumnInfo(name = "tag")
    var tag: String = "" // 来源

    @ColumnInfo(name = "extra")
    var extra: String = ""// 扩展消息

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String,
                   title: String,
                   content: String,
                   imageUrl: String,
                   redirectUrl: String,
                   tag: String,
                   extra: String): TBImageTextMessage {
            var audioMessage = TBImageTextMessage()
            audioMessage.conversationId = conversationId
            audioMessage.messageId = messageId
            audioMessage.title = title
            audioMessage.content = content
            audioMessage.imageUrl = imageUrl
            audioMessage.redirectUrl = redirectUrl
            audioMessage.tag = tag
            audioMessage.extra = extra
            return audioMessage
        }
    }
}
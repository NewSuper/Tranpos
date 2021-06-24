package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil

@Entity(tableName = "notice_message")
class TBNoticeMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "content")
    var content : String=""

    /**
     * 被操作的用户
     */
    @ColumnInfo(name = "users")
    var users : String=""

    /**
     * 操作的用户
     */
    @ColumnInfo(name = "operate_user")
    var operateUser : String=""

    /**
     * 操作类型：通知消息类型 1),//加入聊天室	2),//退出聊天室	3),//群组解散4),//群组同步5),//群组创建
     * 6),//加入群组7),//退出群组8),//群组成员禁言（添加）9),//群组成员禁言（移除）10),//群组整体禁言（添加）11);//群组整体禁言（移除）
     */
    @ColumnInfo(name = "type")
    var type : Int = 0

    @ColumnInfo(name = "extra")
    var extra : String=""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId : String, messageId: String, operateUser : String, users : String, type : Int, content: String, extra: String): TBNoticeMessage {
            var textMessage = TBNoticeMessage()
            textMessage.conversationId = conversationId
            textMessage.messageId = messageId
            textMessage.content = content
            textMessage.operateUser = operateUser
            textMessage.users = users
            textMessage.type = type
            textMessage.extra = extra
            return textMessage
        }
    }
}
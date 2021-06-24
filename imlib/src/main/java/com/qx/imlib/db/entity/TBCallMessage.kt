package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "call_message")
class TBCallMessage {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "room_id")
    var roomId: String = ""

    // 通话类型
    @ColumnInfo(name = "call_type")
    var callType: String = ""

    // 通话状态
    @ColumnInfo(name = "call_state")
    var callState: Int = 0

    @ColumnInfo(name = "userIds")
    var userIds: String = ""

    // 通话时长
    @ColumnInfo(name = "duration")
    var duration: Long = 0

    //通话结束类型 0正常结束，1已取消，2已拒接，3连接异常，4连接断开，5对方无响应
    @ColumnInfo(name = "end_type")
    var endType: Int = 0

    //
    @ColumnInfo(name = "content")
    var content: String = ""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    @ColumnInfo(name = "extra")
    var extra: String = ""

    companion object {

        fun obtain(messageId: String,content:String,duration:Long,endType:Int): TBCallMessage {
            var message = TBCallMessage()
            message.messageId = messageId
            message.duration = duration
            message.content = content
            message.endType = endType
            return message
        }

        fun obtain(conversationId: String, messageId: String,roomId:String, callType: String,endType:Int): TBCallMessage {
            var message = TBCallMessage()
            message.conversationId = conversationId
            message.messageId = messageId
            message.callType = callType
            message.endType = endType
            return message
        }

        fun obtain(conversationId: String, messageId: String, roomId:String,callType: String,userIds:String,endType:Int): TBCallMessage {
            var message = obtain(conversationId,messageId,roomId,callType,endType)
            message.userIds = userIds
            return message
        }

        const val CALL_TYPE_AUDIO = "1"
        const val CALL_TYPE_VIDEO = "2"
    }
}
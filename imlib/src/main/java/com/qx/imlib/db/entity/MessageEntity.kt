package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.im.model.UserInfoCache
import com.qx.imlib.utils.UUIDUtil
import com.qx.message.EventMessage

@Entity(tableName = "message", primaryKeys = ["message_id", "owner_id"])
class MessageEntity : Comparable<MessageEntity> {

    /**
     * 消息id
     */
    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    /**
     * 外键，会话id
     */
    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    /**
     * 拥有者id
     */
    @ColumnInfo(name = "owner_id")
    var ownerId: String = UserInfoCache.getUserId()

    /**
     * @see com.qx.im.message.ConversationType
     */
    @ColumnInfo(name = "send_type")
    var sendType: String = ""

    /**
     * 发送方id，可能是用户id、群id、群里id
     */
    @ColumnInfo(name = "from")
    var from: String = ""

    /**
     * 接收方的id，可能是用户id、群id、群里id
     */
    @ColumnInfo(name = "to")
    var to: String = ""

    /**
     * @see com.qx.im.message.MessageType 消息类型
     */
    @ColumnInfo(name = "message_type")
    var messageType: String = ""

    /**
     * @see State 消息状态
     */
    @ColumnInfo(name = "state")
    var state = 0

    /**
     * 发送失败原因
     */
    @ColumnInfo(name = "failed_reason")
    var failedReason : String =""

    /**
     * 消息阅读数，-1为所有人已阅读
     */
    @ColumnInfo(name = "read_count")
    var readCount = 0

    /**
     * @param deleted 是否已删除，0未删除，1已删除，调用删除单条消息时，不是真正的删除消息，而是标记而已，只有清空或者按时间删除本地消息，才真正的删除消息
     */
    @ColumnInfo(name = "deleted")
    var deleted = 0

    /**
     * 消息方向，0：发送（自己发送的消息），1：接收（别人发送的消息）
     */
    @ColumnInfo(name = "direction")
    var direction = 0

    /**
     * 发送成功时间，服务器返回，本地不赋值
     */
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = System.currentTimeMillis()

    @Ignore
    var textMessage: TBTextMessage? = null

    @Ignore
    var atToMessage: List<TBAtToMessage>? = null

    @Ignore
    var imageMessage: TBImageMessage? = null

    @Ignore
    var imageTextMessage: TBImageTextMessage? = null

    @Ignore
    var videoMessage: TBVideoMessage? = null

    @Ignore
    var audioMessage: TBAudioMessage? = null

    @Ignore
    var fileMessage: TBFileMessage? = null

    @Ignore
    var geoMessage: TBGeoMessage? = null

    @Ignore
    var noticeMessage: TBNoticeMessage? = null

    @Ignore
    var inputStatusMessage: TBInputStatusMessage? = null

    @Ignore
    var customMessage: TBCustomMessage? = null

    @Ignore
    var callMessage: TBCallMessage? = null

    @Ignore
    var replyMessage: TBReplyMessage? = null

    @Ignore
    var eventMessage: EventMessage? = null

    @Ignore
    var retransmissionMessage: TBRetransmissionMessage? = null

    @Ignore
    var thumb : String? = null//图片以文件形式发送时返回的缩略图id

    @Ignore
    var fileLoading : Boolean? = false//是否在下载

    @Ignore
    var currProgress : Int? = 0//进度

    override fun hashCode(): Int {
        return messageId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return messageId == (other as MessageEntity).messageId
    }

    companion object {

        fun obtain(sendType: String, messageType: String, from:String, to: String): MessageEntity {
            var messageEntity = MessageEntity()
            messageEntity.messageId = UUIDUtil.getUUID()
            messageEntity.sendType = sendType
            messageEntity.messageType = messageType
            messageEntity.from = from
            messageEntity.to = to
            return messageEntity
        }
    }

    class State {
        companion object {
            /**
             * 发送中
             */
            val STATE_SENDING = 0

            /**
             * 发送成功
             */
            val STATE_SENT = STATE_SENDING +1

            /**
             * 消息送达
             */
            val STATE_RECEIVED = STATE_SENT +1

            /**
             * 消息已读
             */
            val STATE_READ = STATE_RECEIVED +1

            /**
             * 发送失败
             */
            val STATE_FAILED = STATE_READ +1

        }
    }

    class Direction {
        companion object {
            val DIRECTION_SEND = 0
            val DIRECTION_RECEIVED = 1
        }
    }

    override fun compareTo(other: MessageEntity): Int {
        return when {
            this.timestamp - other.timestamp > 0 -> {
                1
            }
            this.timestamp - other.timestamp < 0 -> {
                -1
            }
            else                                 -> {
                0
            }
        }
    }

    override fun toString(): String {
        return "MessageEntity(messageId='$messageId', conversationId='$conversationId', ownerId='$ownerId', sendType='$sendType', from='$from', to='$to', messageType='$messageType', state=$state, failedReason='$failedReason', readCount=$readCount, deleted=$deleted, direction=$direction, timestamp=$timestamp, textMessage=$textMessage, atToMessage=$atToMessage, imageMessage=$imageMessage, imageTextMessage=$imageTextMessage, videoMessage=$videoMessage, audioMessage=$audioMessage, fileMessage=$fileMessage, geoMessage=$geoMessage, noticeMessage=$noticeMessage, inputStatusMessage=$inputStatusMessage, customMessage=$customMessage, callMessage=$callMessage, replyMessage=$replyMessage, eventMessage=$eventMessage, retransmissionMessage=$retransmissionMessage, thumb=$thumb, fileLoading=$fileLoading, currProgress=$currProgress)"
    }


}
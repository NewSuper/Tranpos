package com.qx.imlib.handler

import android.util.Log
import com.qx.im.model.ConversationModel
import com.qx.im.model.InsertMessageResult
import com.qx.im.model.UserInfoCache
import com.qx.imlib.SystemCmd
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.ConversationEntity
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.utils.TargetIdUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.C2SMessageContext
import io.netty.channel.ChannelHandlerContext

/**
 * 消息连续性计算类
 */
abstract class MessageContinuityCalcHandler : BaseCmdHandler() {
    private val TAG = "MessageContinuityCalcHandler"

    var timeIndicator = -1L;
    var topTime = -1L;
    var localHasConversationOriginally = false//本地db是否有会话
    private var messageBlock: List<MessageEntity> = arrayListOf()//消息列表块，包含n个操作的消息列表
    private lateinit var messages: List<MessageEntity> //当前操作的消息列表
    private lateinit var sendType: String
    private lateinit var from: String
    private lateinit var to: String

    fun handle(
        ctx: ChannelHandlerContext?,
        recMessage: S2CRecMessage?,
        block: List<MessageEntity>
    ) {
        messages = block
        when (recMessage?.cmd) {
            //当收到离线消息，新消息（单聊，群聊，聊天室，系统消息）自动回复已收到cmd
            SystemCmd.C2S_SEND_P2P_MESSAGE, SystemCmd.C2S_SEND_GROUP_MESSAGE, SystemCmd.C2S_SEND_CHATROOM_MESSAGE, SystemCmd.C2S_SEND_SYSTEM_MESSAGE, SystemCmd.S2C_MESSAGE_LIST_OFFLINE -> {
                sendReceivedConfirm(messages, ctx)
            }
        }
        //设置sendType,from .to
        if (messageBlock.isNotEmpty()) {
            setProperties(messages.first().sendType, messages.first().to)
        } else {
            setProperties(recMessage!!)
        }
        localHasConversationOriginally = localHasConversation()//本地DB“原本”是否有会话
        if (!messages.isNullOrEmpty()) {
            ccalcData()
        } else {
            Log.e(TAG, "handle: 收到了空消息")
        }
    }

    private fun ccalcData() {
        var conversation = ConversationModel.instance.generateConversation(messages.first())
        saveToDb(conversation)
    }

    private fun saveToDb(conversation: ConversationEntity) {
        var messageEntity: MessageEntity? = null
        messageEntity = if (messages.isNotEmpty()) {
            messages.first()
        } else {
            IMDatabaseRepository.instance.getLatestMessage(
                sendType,
                from,
                to,
                UserInfoCache.getUserId()
            )
        }
        if (messageEntity == null) {
            Log.e(TAG, "saveToDb: messageEntity 为空")
            //说明本地和网络上都没有数据，则不用往下处理了
            return
        }
        //插入或更新会话
        if (IMDatabaseRepository.instance.insertConversation(conversation) > 0) {  //处理不信任时间区域
//            IMDatabaseRepository.instance.calcUnTrustTime(conversation.conversationType,
//                    conversation.targetId, messages.last().timestamp, messages.first().timestamp)
            IMDatabaseRepository.instance.calcUnTrustTime2(
                conversation.conversationType,
                conversation.targetId, messages.last().timestamp, messages.first().timestamp
            )
            val result = insertMessage(conversation, messages)
            IMDatabaseRepository.instance.refreshConversationInfo(
                messageEntity.sendType,
                messageEntity.from,
                messageEntity.to
            )
            if (conversation.isNew) {
                ConversationModel.instance.generateConversation(messageEntity)
            }
            Log.e(TAG, "saveToDb: 去更新UI")
            notifyUiUpdate(result)
            var conversation =
                IMDatabaseRepository.instance.getConversationById(conversation.conversationId)
            if (conversation != null) {
                EventBusUtil.postConversationUpdate(arrayListOf(conversation))
            }
        } else {
            Log.e(TAG, "saveToDb: 插入或更新会话失败")
        }
    }

    private fun insertMessage(
        conversationEntity: ConversationEntity,
        list: List<MessageEntity>
    ): InsertMessageResult {
        var result = InsertMessageResult()
        for (msg in list) {
            Log.e(
                TAG,
                "insertMessage: 去插入消息：" + msg.timestamp + " | " + conversationEntity.deleteTime + " | " + isNeedCheckDeleteTime()
            )
            //加此条件过滤消息时间<=deleteTime的消息，满足此条件的消息不插入数据库
            if (isNeedCheckDeleteTime() && msg.timestamp <= conversationEntity.deleteTime) {
                continue
            }
            msg.conversationId = conversationEntity.conversationId
            var r = IMDatabaseRepository.instance.insertMessage(msg)
            result.newMessageCount += r.newMessageCount
            result.messages.addAll(r.messages)
        }
        return result
    }

    //本地是否有会话
    private fun localHasConversation(): Boolean {
        var targetId = TargetIdUtil.getTargetId(messages.first())
        var conversationEntity =
            IMDatabaseRepository.instance.getConversation(messages.first()!!.sendType, targetId)
        return if (conversationEntity != null) {
            Log.e(TAG, "calcData: 本地有会话")
            true
        } else {
            Log.e(TAG, "calcData: 本地无会话")
            false
        }
    }

    open fun sendReceivedConfirm(messages: List<MessageEntity>, ctx: ChannelHandlerContext?) {
        var ids = arrayListOf<String>()
        for (message in messages) {
            ids.add(message.messageId)
        }
        var body = C2SMessageContext.MessageContext.newBuilder().addAllMessageIds(ids)
            .setFrom(messages[0].from)
            .setTo(messages[0].to).setSendType(messages[0].sendType).build()
        var msg = S2CSndMessage()
        msg.body = body
        ctx!!.channel().writeAndFlush(msg)

    }

    open fun setProperties(sendType: String, to: String) {
        this.sendType = sendType
        from = UserInfoCache.getUserId()
        this.to = to
    }

    open fun isPreSetMessage(messageType: String) {}

    abstract fun notifyUiUpdate(result: InsertMessageResult)
    abstract fun getMessages(recMessage: S2CRecMessage): List<Any>
    abstract fun getLatestMessage(sendType: String, from: String, to: String): MessageEntity?
    abstract fun isNeedCheckDeleteTime(): Boolean

    open fun setProperties(recMessage: S2CRecMessage) {}
}
package com.qx.imlib.netty

import android.util.Log
import com.qx.imlib.SystemCmd
import com.qx.imlib.handler.*
import com.qx.imlib.handler.call.*
import com.qx.imlib.handler.chatroom.*
import com.qx.imlib.handler.conversation.ConversationNoDisturbingHandler
import com.qx.imlib.handler.conversation.ConversationTopHandler
import com.qx.imlib.handler.conversation.UnReadCountHandler
import com.qx.imlib.job.QueueManager
import com.qx.imlib.qlog.QLog
import io.netty.channel.ChannelHandlerContext
import java.lang.Exception
import java.util.HashMap

class CmdDispatcher {
    private val TAG = "CmdDispatcher"

    private val mHandlerMap: HashMap<Short, BaseCmdHandler?> =
        HashMap()
    private val mClassMap: HashMap<Short, Class<*>> = HashMap()

    init {
        initClassMap()
    }

    internal object Holder {
        var instance = CmdDispatcher()
    }

    companion object {
        @JvmStatic
        val instance: CmdDispatcher
            get() = Holder.instance
    }

    /**
     * 初始化cmd和handler的映射关系
     */
    private fun initClassMap() {
        //如果需要添加Cmd处理的Handler，请在此添加
        mClassMap.apply {
            this[SystemCmd.S2C_AES_KEY] = AesKeyHandler::class.java
            this[SystemCmd.S2C_RSA_KEY] = RsaKeyHandler::class.java
            this[SystemCmd.S2C_AUTH_SUCCESS] = AuthHandler::class.java
            this[SystemCmd.S2C_ACK] = ACKHandler::class.java
            this[SystemCmd.S2C_DESTORY_SUCCESS] = DestroyHandler::class.java
            this[SystemCmd.S2C_ERROR] = ErrorHandler::class.java
            this[SystemCmd.S2C_HEARTBEAT] = HeartBeatHandler::class.java
            this[SystemCmd.S2C_KICK] = KickHandler::class.java
            this[SystemCmd.S2C_SEND_MESSAGE_STATE] = MessageReceivedReceiptHandler::class.java
            this[SystemCmd.S2C_SEND_READ_COUNT] = MessageReadReceiptHandler::class.java
            this[SystemCmd.C2S_SEND_P2P_MESSAGE] = ReceiveP2PMessageHandler::class.java
            this[SystemCmd.C2S_SEND_GROUP_MESSAGE] = ReceiveGroupMessageHandler::class.java
            this[SystemCmd.C2S_SEND_SYSTEM_MESSAGE] = ReceiveSystemMessageHandler::class.java
            this[SystemCmd.S2C_SESSION_UNREAD_COUNT] = UnReadCountHandler::class.java
            this[SystemCmd.S2C_MESSAGE_MUTED] = ConversationNoDisturbingHandler::class.java
            this[SystemCmd.S2C_SESSION_TOP] = ConversationTopHandler::class.java
            this[SystemCmd.S2C_MESSAGE_LIST_PAGED] = HistoryMessageHandler::class.java
            this[SystemCmd.S2C_PROPERTY] = PropertyHandler::class.java
            //room
            this[SystemCmd.S2C_MESSAGE_LIST_OFFLINE] = OfflineMessageHandler::class.java
            this[SystemCmd.C2S_SEND_CHATROOM_MESSAGE] = ReceiveChatRoomMessageHandler::class.java
            this[SystemCmd.S2C_CHATROOM_GET_PROP] = ChatRoomAttributeHandler::class.java

            //聊天室成员禁言
            this[SystemCmd.S2C_CHATROOM_MEMBER_FORBIDDEN] = ChatRoomMemberMuteHandler::class.java
            //聊天室成员封禁
            this[SystemCmd.S2C_CHATROOM_MEMBER_CLOSURE] = ChatRoomMemberBanHandler::class.java
            //聊天室销毁
            this[SystemCmd.S2C_CHATROOM_DESTORY] = ChatRoomDestroyHandler::class.java
            //聊天室全局禁言
            this[SystemCmd.S2C_GLOBAL_CHATROOM_FORBIDDEN] = ChatRoomGlobalMuteHandler::class.java
            //群组全局禁言
            this[SystemCmd.S2C_GLOBAL_GROUP_FORBIDDEN] = GroupGlobalMuteHandler::class.java
            //群组整体禁言
            this[SystemCmd.S2C_GROUP_ALL_FORBIDDEN] = GroupAllMuteHandler::class.java
            //群组禁言
            this[SystemCmd.S2C_GROUP_FORBIDDEN] = GroupMuteHandler::class.java
            //用户封禁
            this[SystemCmd.S2C_USER_CLOSURE] = UserBanHandler::class.java

            //获取群组@列表：用于离线接收@消息
            this[SystemCmd.S2C_AT_MESSAGES] = OfflineAtToMessageHandler::class.java

            //系统繁忙
            this[SystemCmd.S2C_BUSY] = SystemBusyHandler::class.java
            //系统维护
            this[SystemCmd.S2C_MAINTENANCE] = SystemMaintenanceHandler::class.java

            //RTC模块
            this[SystemCmd.S2C_VIDEO_CALL] = CallIncomeHandler::class.java
            this[SystemCmd.S2C_VIDEO_ANSWER] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_OTHER_ANSWER] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_REFUSE] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_CANCEL] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_OUT_TIME] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_RING_OFF] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_VIDEO_SWITCH] = CallReceiveActionHandler::class.java
            this[SystemCmd.S2C_RTC_SIGNAL_JOINED] = RTCJoinedHandler::class.java
            this[SystemCmd.S2C_RTC_SIGNAL_JOIN] = RTCJoinedHandler::class.java
            this[SystemCmd.S2C_RTC_SIGNAL_OFFER] = RTCOfferHandler::class.java
            this[SystemCmd.S2C_RTC_SIGNAL_ANSWER] = RTCOfferHandler::class.java
            this[SystemCmd.S2C_RTC_SIGNAL_CANDIDATE] = RTCCandidateHandler::class.java
            this[SystemCmd.S2C_VIDEO_CALL_RESULT] = CallVideoResultHandler::class.java
            this[SystemCmd.S2C_VIDEO_GROUP] = CallGroupHandler::class.java
            this[SystemCmd.C2S_VIDEO_MEMBER_MODIFY] = CallVideoMemberModifyHandler::class.java
            this[SystemCmd.C2S_VIDEO_PARAM] = CallVideoParamHandler::class.java
        }

    }

    fun dispatch(ctx: ChannelHandlerContext, data: Any) {
        try {
            var msg = data as S2CRecMessage
            QLog.i(
                TAG,
                "接收消息 cmd：" + msg.cmd + " sequence:" + msg.sequence + " threadId=" + Thread.currentThread().id
            )
            answerAck(msg.cmd, msg, ctx)
            //在此做群聊消息高并发处理
            if (msg.cmd == SystemCmd.C2S_SEND_GROUP_MESSAGE) {
                var task = MessageTask(ctx, msg)
                QueueManager.instance.receive(task)
            } else {
                var handler = getHandler(msg.cmd)
                handler.handle(ctx, msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getHandler(cmd: Short): BaseCmdHandler {
        return if (cmd == SystemCmd.C2S_SEND_GROUP_MESSAGE) {
            ReceiveGroupMessageHandler()
        } else {
            var handler = mHandlerMap[cmd]
            if (handler == null) {
                val clazz = mClassMap[cmd]
                handler = if (clazz == null) {
                    DefaultHandler()
                } else {
                    clazz.newInstance() as BaseCmdHandler
                }
                mHandlerMap[cmd] = handler
            }
            handler
        }
    }

    private fun answerAck(cmd: Short, recMessage: S2CRecMessage, ctx: ChannelHandlerContext) {
        //如果cmd为acl或认证成功，则不需要回ack,否则 需要
        if (cmd !== SystemCmd.S2C_ACK && cmd != SystemCmd.S2C_AUTH_SUCCESS) {
            val ack = S2CSndMessage()
            ack.cmd = SystemCmd.S2C_ACK
            ack.sequence = recMessage.sequence
            ctx.channel().writeAndFlush(ack)
            Log.e(TAG, "给服务器回复ack sequence:" + recMessage.sequence)
        }
    }

}
package com.qx.imlib.handler

import com.qx.im.model.InsertMessageResult
import com.qx.im.model.UserInfoCache
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.S2CCustomMessage
import com.qx.message.MessageType
import io.netty.channel.ChannelHandlerContext

//群聊接收消息处理类，继承于BaseReceiveMessageHandler
open class ReceiveGroupMessageHandler : ReceiveNewMessageUtilHandler() {
    private val TAG = "ReceiveGroupMessageHand";
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        val msg = S2CCustomMessage.Msg.parseFrom(recMessage!!.contents)
        QLog.i(
            "ReceiveGroupMessageHandler",
            "msg sendType=" + msg.sendType + " from=" + msg.from + " to=" + msg.to + " msgid=" + msg.messageId
        )
        when (msg.messageType) {
            MessageType.TYPE_REPLY, MessageType.TYPE_RECORD, MessageType.TYPE_NOTICE, MessageType.TYPE_TEXT, MessageType.TYPE_IMAGE, MessageType.TYPE_IMAGE_AND_TEXT, MessageType.TYPE_AUDIO, MessageType.TYPE_VIDEO, MessageType.TYPE_FILE, MessageType.TYPE_GEO, MessageType.TYPE_AUDIO_CALL, MessageType.TYPE_VIDEO_CALL, MessageType.TYPE_CARD, MessageType.TYPE_RPP -> {
                super.handle(ctx, recMessage)
            }
            MessageType.TYPE_RECALL -> {
                //如果是撤回类型，则更新消息类型为撤回类型
                var row =
                    IMDatabaseRepository.instance.updateMessageToRecallType(msg.recall.targetMessageId)
                //如果存在在于数据库
                if (row > 0) {
                    var messageEntity =
                        IMDatabaseRepository.instance.getMessageById(msg.recall.targetMessageId)
                    if (messageEntity != null) {
                        IMDatabaseRepository.instance.refreshConversationInfo(messageEntity)
                        EventBusUtil.postRecallMessage(messageEntity)
                    }
                } else {
                    //如果找不到该记录，则应该插入数据库，这时调用基类方法，走插入消息流程
                    //这种情况一般出现于：当服务器向客户端发送撤回消息时，这时客户端刚好不在线，没收到，当客户端换一台设备登录时，
                    // 这时客户端发一条撤回消息过来，但是被撤回的原本消息在本地找不到，服务器也不会通过历史消息传过来，
                    // 所以此时要插入一下recall的消息
                    //   super.handle(ctx, recMessage)
                }
            }
            else -> {
                super.handle(ctx, recMessage)
            }
        }
    }

    override fun notifyUiUpdate(result: InsertMessageResult) {
        //在此处理会话中@内容
        for(msg in result.messages){
            if (msg.messageType == MessageType.TYPE_TEXT){
                if (!msg.atToMessage.isNullOrEmpty()){
                    var atTo = ""
                    for (at in msg.atToMessage!!){
                        if (at.atTo == "-1"){
                            atTo =
                                "-1"
                            break
                        }else{
                            if (at.atTo == UserInfoCache.getUserId()){
                                atTo = "0"
                                break
                            }
                        }
                    }
                    IMDatabaseRepository.instance.updateConversationAtTO(msg.to,atTo)
                }
            }
        }
        super.notifyUiUpdate(result)
    }
}
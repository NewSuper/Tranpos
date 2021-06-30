package com.qx.imlib.handler

import android.util.Log
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.message.EventMessage
import com.qx.message.MessageType
import io.netty.channel.ChannelHandlerContext

abstract class CustomMessageHandler : MessageContinuityCalcHandler() {
    private val TAG = "CustomMessageHandler"

    //历史消息、在线消息
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var messageBlock = getMessages(recMessage!!)
//        Log.e(TAG, "handle: ++++++++++数量为;" + messageBlock.size)
        if (messageBlock.isNullOrEmpty()) {
            return
        }
        var first = messageBlock.first()
        if (first is List<*>) {
            //多个会话，如:离线@功能
//            Log.e(TAG, "handle: 多个会话，如:离线@功能：" + first.size)
            for (block in messageBlock) {
                super.handle(ctx, recMessage, block as List<MessageEntity>)
            }
        } else if (first is MessageEntity) {
            //单个会话
            if (MessageType.isPreSetMessage(first.messageType)) {
//                Log.e(TAG, "handle: ==== 单个会话" + messageBlock.size)
                super.handle(ctx, recMessage, messageBlock as List<MessageEntity>)
            } else {
                //否则为Event消息或自定义消息
//                Log.e(TAG, "handle: 否则为Event消息或自定义消息")
                for (msg in messageBlock) {
                    handleCustomMessage(ctx, recMessage, msg as MessageEntity)
                }
            }
        }
    }

    private fun handleCustomMessage(
        ctx: ChannelHandlerContext?,
        recMessage: S2CRecMessage?,
        msg: MessageEntity
    ) {
       if (msg.messageType == MessageType.TYPE_EVENT){
           //自定义事件，不做存储，只做消息转发到UI层
           var message = MessageConvertUtil.instance.convertToMessage(msg)
           sendReceivedConfirm(arrayListOf(msg),ctx)//发送消息确认接收
           if (message?.messageContent != null && message?.messageContent is EventMessage) {
               var event = message?.messageContent as EventMessage
               var provider = CustomEventManager.getCustomEventProvider(event.event)
               Log.e(TAG, "handleCustomMessage: run to onReceiveCustomEvent method:" + event.event + " | " + (provider == null))
               provider?.onReceiveCustomEvent(message)
           } else {
               Log.e(TAG, "handleCustomMessage: 数据为空：" + (message?.messageContent != null) + " | " + (message?.messageContent is EventMessage) + " | " + message.toString())
           }
       }else{
           //自定义消息
           super.handle(ctx,recMessage, arrayListOf(msg))
       }
    }
    /**
     * 是否为自定义事件
     */
    private fun isCustomEvent(messageType: String): Boolean {
        return CustomEventManager.isExist(messageType)
    }

    /**
     * 是否为自定义消息
     */
    private fun isCustomMessage(messageType: String): Boolean {
        return CustomEventManager.isExist(messageType)
    }
}
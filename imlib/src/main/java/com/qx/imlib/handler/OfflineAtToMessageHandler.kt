package com.qx.imlib.handler

import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.it.protos.S2CAtMessages

class OfflineAtToMessageHandler :GroupOfflineMessageHandler() {
    override fun getMessages(recMessage: S2CRecMessage): List<Any> {

        var messsageBlock = arrayListOf<List<MessageEntity>>()
        var messageList = arrayListOf<MessageEntity>()
        var atMessages = S2CAtMessages.AtMessages.parseFrom(recMessage.contents)
        var tagsList  = atMessages.tagsList
        if (tagsList != null && tagsList.isNotEmpty()){
            for(tag in tagsList){
                for(at in  tag.atsList){
                    for (msg in at.atMsgsList){
                        var entity = MessageConvertUtil.instance.convertToMessageEntity(msg)
                        messageList.add(entity)
                    }
                }
                messsageBlock.add(messageList)
            }
        }
        return messsageBlock
    }
}
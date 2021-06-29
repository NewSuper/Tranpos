package com.qx.im.model

import com.qx.imlib.SystemCmd
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.ConversationEntity
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.job.JobManagerUtil
import com.qx.imlib.job.ResultCallback
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.utils.TargetIdUtil
import com.qx.it.protos.S2CSpecialOperation
import com.qx.message.QXError
import java.util.concurrent.ConcurrentHashMap

class ConversationModel {

    internal object Holder{
        val holder = ConversationModel()
    }
    companion object{
        val instance = Holder.holder
        val mMap = ConcurrentHashMap<String,ConversationEntity>()
    }
    //创建会话实体，不要每次从数据库中获取，应该增加缓存会话的机制
    fun generateConversation(messageEntity: MessageEntity?): ConversationEntity {
        //先根据消息信息获取会话是否存在，如果存在直接返回
        var targetId = TargetIdUtil.getTargetId(messageEntity)
        var conversationEntity = IMDatabaseRepository.instance.getConversation(
            messageEntity!!.sendType,targetId
        )
        if (conversationEntity!=null){
            conversationEntity .deleted=0 //重置为非删除的状态
            conversationEntity.timestamp = messageEntity.timestamp
            return conversationEntity
        }
        //否则创建一个新的会话
        conversationEntity = ConversationEntity()
        conversationEntity.deleted = 0
        conversationEntity.isNew = true
        conversationEntity.timestamp = messageEntity.timestamp
        conversationEntity.conversationType = messageEntity!!.sendType
        conversationEntity.ownerId = UserInfoCache.getUserId()
        conversationEntity.targetId = targetId
        return conversationEntity
    }
    //获取会话属性，置顶或免打扰
    fun getConversationProperty(conversationEntity: ConversationEntity){
        var top = createGetTopPropertyMsg(conversationEntity)
        var nodisturb = createGetNoDisturbPropertyMsg(conversationEntity)
        JobManagerUtil.instance.postMessage(top,object :ResultCallback{
            override fun onSuccess() {
            }

            override fun onFailed(error: QXError) {
            }
        })
        JobManagerUtil.instance.postMessage(nodisturb,object :ResultCallback{
            override fun onSuccess() {
            }

            override fun onFailed(error: QXError) {
            }
        })
    }
    private fun createGetTopPropertyMsg(conversationEntity: ConversationEntity): S2CSndMessage {
    var targetId= conversationEntity.targetId
        var body = S2CSpecialOperation.SpecialOperation.newBuilder().setSendType(conversationEntity.conversationType)
            .setTargetId(targetId).setUserId(UserInfoCache.getUserId()).build()

        var msg = S2CSndMessage()
        msg.cmd = SystemCmd.C2S_GET_TOP_PROPERTY
        msg.body = body
        return msg
    }
    private fun createGetNoDisturbPropertyMsg(conversationEntity: ConversationEntity): S2CSndMessage {
        var targetId = conversationEntity.targetId
        var body = S2CSpecialOperation.SpecialOperation.newBuilder().setSendType(conversationEntity.conversationType)
            .setTargetId(targetId).setUserId(UserInfoCache.getUserId()).build()

        var msg = S2CSndMessage()
        msg.cmd = SystemCmd.C2S_GET_NO_DISTURB_PROPERTY
        msg.body = body
        return msg
    }
}
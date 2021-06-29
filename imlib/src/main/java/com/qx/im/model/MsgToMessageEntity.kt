package com.qx.im.model

import com.google.gson.Gson
import com.qx.im.model.json.JSonMessage
import com.qx.imlib.db.entity.*
import com.qx.imlib.qlog.QLog
import com.qx.it.protos.S2CCustomMessage
import com.qx.message.EventMessage
import java.lang.Exception

class MsgToMessageEntity {

    private val TAG = "MsgToMessageEntity"
    companion object {
        val instance = Holder.holder
    }

    object Holder {
        val holder = MsgToMessageEntity()
    }

    fun fillTextMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        //1.构建TextMessage
        var textMessage = TBTextMessage.obtain(entity.conversationId, msg.messageId, msg.text.content, msg.text.extra)
        //2.构建AtToMessage
        var atTos = arrayListOf<TBAtToMessage>()
        for (at in msg.text.atTosList) {
            atTos.add(TBAtToMessage.obtain(entity.conversationId, msg.messageId, at, TBAtToMessage.ReadState.STATE_UN_READ, entity.timestamp))
        }
        //3.将TextMessage和AtToMessage设置到TextMessage中
        entity.textMessage = textMessage
        entity.atToMessage = atTos
        return entity
    }

    fun fillImageMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var imageMessage = TBImageMessage.obtain(
            entity.conversationId,
            msg.messageId,
            msg.image.originUrl,
            msg.image.breviaryUrl,
            "",
            msg.image.width,
            msg.image.height,
            msg.image.size,
            msg.image.extra
        )
        entity.imageMessage = imageMessage
        return entity
    }

    fun fillCustomMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBCustomMessage.obtain(entity.conversationId, msg.messageId, msg.custom.content, msg.custom.extra)
        entity.customMessage = message
        return entity
    }

    fun fillFileMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBFileMessage.obtain(
            entity.conversationId,
            msg.messageId,
            msg.file.fileName,
            msg.file.originUrl,
            "",
            msg.file.type,
            msg.file.size,
            msg.file.extra
        )
        entity.fileMessage = message
        return entity
    }

    fun fillGeoMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBGeoMessage.obtain(
            entity.conversationId, msg.messageId, msg.geo.title, msg.geo.address, msg.geo.previewUrl, "", msg.geo.lon, msg.geo.lat, msg.geo.extra
        )
        entity.geoMessage = message
        return entity
    }

    fun fillImageTextMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBImageTextMessage.obtain(
            entity.conversationId,
            msg.messageId,
            msg.imageText.title,
            msg.imageText.content,
            msg.imageText.imageUrl,
            msg.imageText.redirectUrl,
            msg.imageText.tag,
            msg.imageText.extra
        )
        entity.imageTextMessage = message
        return entity
    }

    fun fillAudioMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBAudioMessage.obtain(
            entity.conversationId,
            msg.messageId,
            "",
            msg.audio.originUrl,
            msg.audio.size,
            msg.audio.duration,
            msg.audio.extra
        )
        entity.audioMessage = message
        return entity
    }

    fun fillVideoMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBVideoMessage.obtain(
            entity.conversationId,
            msg.messageId,
            msg.video.headUrl,
            msg.video.originUrl,
            "",
            msg.video.width,
            msg.video.height,
            msg.video.size,
            msg.video.duration,
            msg.video.extra
        )
        entity.videoMessage = message
        return entity
    }

    fun fillNoticeMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {

        var operateUser = Gson().toJson(User(msg.notice.operateUser.userId, msg.notice.operateUser.name))
        var userList = ArrayList<User>()
        for(userInfo in msg.notice.usersList) {
            userList.add(User(userInfo.userId, userInfo.name))
        }
        var users = Gson().toJson(userList)
        var type = msg.notice.type
        var message = TBNoticeMessage.obtain(entity.conversationId, msg.messageId, operateUser, users, type, msg.notice.content, msg.notice.extra)
        entity.noticeMessage = message
        return entity
    }

    class User(var userId : String, var name :String)

    fun fillInputStatusMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBInputStatusMessage.obtain(msg.messageId, msg.stus.content, msg.stus.extra)
        entity.inputStatusMessage = message
        return entity
    }

    fun fillCallAudioMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBCallMessage.obtain(msg.messageId, msg.act.content, msg.act.duration.toLong(), msg.act.endType)
        message.conversationId = entity.conversationId
        message.callType = "1"
        message.timestamp = entity.timestamp
        message.callState = if (msg.act.duration == 0) 0 else 1
        entity.callMessage = message
        return entity
    }

    fun fillCallVideoMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBCallMessage.obtain(msg.messageId, msg.vct.content, msg.vct.duration.toLong(), msg.vct.endType)
        message.conversationId = entity.conversationId
        message.callType = "2"
        message.timestamp = entity.timestamp
        message.callState = if (msg.vct.duration == 0) 0 else 1
        entity.callMessage = message
        return entity
    }

    fun fillReplyMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = TBReplyMessage.obtain(entity.conversationId, msg.messageId, msg.reply.reply, msg.reply.msg, msg.reply.extra)
        entity.replyMessage = message
        return entity
    }

    fun fillRetransmissionMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var gson = Gson()
        try {

            var list = arrayListOf<JSonMessage>()
            for (index in 0 until msg.record.recordsList.size) {
                var jsonMessage = gson.fromJson(msg.record.recordsList[index], JSonMessage::class.java)
                list.add(jsonMessage)
            }
            var message = TBRetransmissionMessage.obtain(entity.conversationId, msg.messageId, gson.toJson(list), msg.record.extra)
            entity.retransmissionMessage = message
        } catch (e: Exception) {
            QLog.e(TAG, "json字符串转为JsonMessage格式异常：" + e.cause + " >> " + e.message)

            for (index in 0 until msg.record.recordsList.size) {
                var record = msg.record.recordsList[index]
                QLog.e(TAG, " records=$record")
            }
        }
        return entity
    }

    fun fillEventMessage(entity: MessageEntity, msg: S2CCustomMessage.Msg): MessageEntity {
        var message = EventMessage(msg.event.event, msg.event.extra)
        entity.eventMessage = message
        return entity
    }

}
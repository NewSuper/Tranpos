package com.qx.imlib.utils

import com.google.gson.Gson
import com.qx.im.core.bean.json.JSonVideo
import com.qx.im.model.ConversationType
import com.qx.im.model.MessageEntityToMessage
import com.qx.im.model.MsgToMessageEntity
import com.qx.im.model.UserInfoCache
import com.qx.im.model.json.*
import com.qx.imlib.SystemCmd
import com.qx.imlib.db.entity.*
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.qlog.QLog
import com.qx.it.protos.S2CCustomMessage
import com.qx.it.protos.S2CCustomMessage.Msg
import com.qx.message.*

class MessageConvertUtil {
    private val TAG = "MessageConvertUtil"
    internal object Holder {
        val instance = MessageConvertUtil()
    }

    companion object {
        val instance = Holder.instance
    }
    fun convertToMessageList(origin:List<MessageEntity>):List<Message>{
        var list = arrayListOf<Message>()
        for (msg in origin){
            val m = convertToMessage(msg)
            if (m != null){
                list.add(m)
            }
        }
        return list
    }

    fun convertToMessage(entity: MessageEntity): Message? {
        when (entity.messageType) {
            MessageType.TYPE_TEXT -> {
                return MessageEntityToMessage.instance.createTextMsg(entity)
            }
            MessageType.TYPE_AUDIO -> {
                return MessageEntityToMessage.instance.createAudioMsg(entity)
            }
            MessageType.TYPE_VIDEO -> {
                return MessageEntityToMessage.instance.createVideoMsg(entity)
            }
            MessageType.TYPE_IMAGE -> {
                return MessageEntityToMessage.instance.createImageMsg(entity)
            }
            MessageType.TYPE_IMAGE_AND_TEXT -> {
                return MessageEntityToMessage.instance.createImageTextMsg(entity)
            }
            MessageType.TYPE_GEO -> {
                return MessageEntityToMessage.instance.createGeoMsg(entity)
            }
            MessageType.TYPE_FILE -> {
                return MessageEntityToMessage.instance.createFileMsg(entity)
            }
            MessageType.TYPE_NOTICE -> {
                return MessageEntityToMessage.instance.createNoticeMsg(entity)
            }
            MessageType.TYPE_STATUS -> {
                return MessageEntityToMessage.instance.createInputStatusMsg(entity)
            }
            MessageType.TYPE_RECALL -> {
                return MessageEntityToMessage.instance.createRecallMsg(entity)
            }
            MessageType.TYPE_AUDIO_CALL, MessageType.TYPE_VIDEO_CALL -> {
                return MessageEntityToMessage.instance.createCallMsg(entity)
            }
            MessageType.TYPE_REPLY -> {
                return MessageEntityToMessage.instance.createReplyMsg(entity)
            }
            MessageType.TYPE_RECORD -> {
                return MessageEntityToMessage.instance.createRetransmissionMsg(entity)
            }
            MessageType.TYPE_EVENT -> {
                return MessageEntityToMessage.instance.createEventMsg(entity)
            }
            else -> {
                return MessageEntityToMessage.instance.createCustomMsg(entity)
            }
        }
        return null
    }

    fun  convertToMessageEntity(msg:Msg):MessageEntity{
        var entity = MessageEntity.obtain(msg.sendType,msg.messageType,msg.from,msg.to)
        entity.messageId = msg.messageId
        entity.from = msg.from
        entity.timestamp  = msg.timestamp
        if (entity.from == UserInfoCache.getUserId()){
            entity.direction = MessageEntity.Direction.DIRECTION_SEND
        }else{
            entity.direction = MessageEntity.Direction.DIRECTION_SEND
        }
        parseMessageContent(entity,msg)
        return entity
    }

    private fun parseMessageContent(entity: MessageEntity, msg: Msg): MessageEntity {
        when (msg.messageType) {
            MessageType.TYPE_TEXT -> {
                return MsgToMessageEntity.instance.fillTextMessage(entity, msg)
            }
            MessageType.TYPE_AUDIO -> {
                return MsgToMessageEntity.instance.fillAudioMessage(entity, msg)
            }
            MessageType.TYPE_VIDEO -> {
                return MsgToMessageEntity.instance.fillVideoMessage(entity, msg)
            }
            MessageType.TYPE_IMAGE -> {
                return MsgToMessageEntity.instance.fillImageMessage(entity, msg)
            }
            MessageType.TYPE_IMAGE_AND_TEXT -> {
                return MsgToMessageEntity.instance.fillImageTextMessage(entity, msg)
            }
            MessageType.TYPE_GEO -> {
                return MsgToMessageEntity.instance.fillGeoMessage(entity, msg)
            }
            MessageType.TYPE_FILE -> {
                return MsgToMessageEntity.instance.fillFileMessage(entity, msg)
            }
            MessageType.TYPE_NOTICE -> {
                return MsgToMessageEntity.instance.fillNoticeMessage(entity, msg)
            }
            MessageType.TYPE_STATUS -> {
                return MsgToMessageEntity.instance.fillInputStatusMessage(entity, msg)
            }
            MessageType.TYPE_AUDIO_CALL -> {
                return MsgToMessageEntity.instance.fillCallAudioMessage(entity, msg)
            }

            MessageType.TYPE_VIDEO_CALL -> {
                return MsgToMessageEntity.instance.fillCallVideoMessage(entity, msg)
            }
            MessageType.TYPE_REPLY -> {
                return MsgToMessageEntity.instance.fillReplyMessage(entity, msg)
            }
            MessageType.TYPE_RECORD -> {
                return MsgToMessageEntity.instance.fillRetransmissionMessage(entity, msg)
            }
            MessageType.TYPE_EVENT -> {
                return MsgToMessageEntity.instance.fillEventMessage(entity, msg)
            }
            else -> {
                return MsgToMessageEntity.instance.fillCustomMessage(entity, msg)
            }
        }
        return entity
    }
    fun jsonMessageToMessage(jsonMessage: JSonMessage): Message? {
        var message = Message()
        message.messageId = jsonMessage.messageId
        message.targetId = jsonMessage.to
        message.senderUserId = jsonMessage.from
        message.conversationType = jsonMessage.sendType
        message.messageType = jsonMessage.messageType
        message.timestamp = jsonMessage.timestamp

        when (jsonMessage.messageType) {
            com.qx.message.MessageType.TYPE_TEXT -> {
                if (jsonMessage.text != null) {
                    var jsonText = jsonMessage.text
                    var text = TextMessage.obtain(jsonText!!.content)
                    for (index in 0 until jsonText.atTos.size) {
                        var atto = AtToMessage(jsonText.atTos[index], AtToMessage.ReadState.STATE_UN_READ)
                        text.atToMessageList.add(atto)
                    }
                    text.extra = jsonText.extra
                    message.messageContent = text
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_IMAGE -> {
                if (jsonMessage.image != null) {
                    var jsonImage = jsonMessage.image
                    var image = ImageMessage("", jsonImage!!.breviaryUrl, jsonImage.size, jsonImage.originUrl, jsonImage.width, jsonImage.height)
                    image.extra = jsonImage!!.extra
                    message.messageContent = image
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_IMAGE_AND_TEXT -> {
                if (jsonMessage.imageText != null) {
                    var jsonImageText = jsonMessage.imageText
                    var imageText = ImageTextMessage(jsonImageText!!.title, jsonImageText.content,
                        jsonImageText.imageUrl, jsonImageText.redirectUrl, jsonImageText.tag)

                    imageText.extra = jsonImageText.extra
                    message.messageContent = imageText
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_AUDIO -> {
                if (jsonMessage.audio != null) {
                    var jsonAudio = jsonMessage.audio
                    var audio = AudioMessage("", jsonAudio!!.duration, jsonAudio.size, jsonAudio.originUrl)

                    audio.extra = jsonAudio.extra
                    message.messageContent = audio
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_VIDEO -> {
                if (jsonMessage.video != null) {
                    var jsonVideo = jsonMessage.video
                    var video = VideoMessage("", jsonVideo!!.duration, jsonVideo.size, jsonVideo.headUrl,
                        jsonVideo.originUrl, jsonVideo.width, jsonVideo.height)

                    video.extra = jsonVideo.extra
                    message.messageContent = video
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_FILE -> {
                if (jsonMessage.file != null) {
                    var jsonFile = jsonMessage.file
                    var file = FileMessage("", jsonFile!!.size, jsonFile.fileName, jsonFile.originUrl, jsonFile.type)

                    file.extra = jsonFile.extra
                    message.messageContent = file
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_GEO -> {
                if (jsonMessage.geo != null) {
                    var jsonGeo = jsonMessage.geo
                    var geo = GeoMessage(jsonGeo!!.title, jsonGeo!!.address, jsonGeo!!.previewUrl,"", jsonGeo.lon, jsonGeo.lat)

                    geo.extra = jsonGeo.extra
                    message.messageContent = geo
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_AUDIO_CALL -> {
                if (jsonMessage.act != null) {
                    var jsonAct = jsonMessage.act
                    var audio = CallMessage("", CallMessage.CallType.CALL_TYPE_AUDIO,
                        0, "", jsonAct!!.content, jsonAct.duration.toLong(), jsonAct.endType)
                    audio.extra = jsonAct.extra
                    message.messageContent = audio
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_VIDEO_CALL -> {
                if (jsonMessage.vct != null) {
                    var jsonVct = jsonMessage.vct
                    var video = CallMessage("", CallMessage.CallType.CALL_TYPE_VIDEO,
                        0, "", jsonVct!!.content, jsonVct.duration.toLong(), jsonVct.endType)
                    video.extra = jsonVct.extra
                    message.messageContent = video
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_NOTICE -> {
                if (jsonMessage.notice != null) {
                    var jsonNotice = jsonMessage.notice
                    var notice = NoticeMessage(jsonNotice!!.content, jsonNotice!!.operateUser, jsonNotice!!.users, jsonNotice!!.type)
                    notice.extra = jsonNotice.extra
                    message.messageContent = notice
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_REPLY -> {
                if (jsonMessage.reply != null) {
                    var jsonReply = jsonMessage.reply
                    var jr = Gson().fromJson(jsonReply!!.reply, JSonMessage::class.java)
                    var jm = Gson().fromJson(jsonReply!!.msg, JSonMessage::class.java)
                    var r = jsonMessageToMessage(jr)
                    var m = jsonMessageToMessage(jm)
                    var reply = ReplyMessage(r, m, jsonReply.extra)
                    reply.extra = jsonReply.extra
                    message.messageContent = reply
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
            com.qx.message.MessageType.TYPE_RECORD -> {
                if (jsonMessage.record != null) {
                    var jsonRecord = jsonMessage.record
                    var list = arrayListOf<Message>()
                    for (index in 0 until jsonRecord!!.records.size) {
                        var jm = Gson().fromJson(jsonRecord.records[index], JSonMessage::class.java)
                        list.add(jsonMessageToMessage(jm)!!)
                    }
                    var record = RecordMessage(list, jsonRecord!!.extra)

                    record.extra = jsonRecord!!.extra
                    message.messageContent = record
                } else {
                    QLog.e(TAG, "jsonMessageToMessage > " + jsonMessage.messageType + "类型的消息为空")
                }
            }
        }
        return message
    }

    fun s2cSndMsg(msg:Msg):Message?{
        var entity =  convertToMessageEntity(msg)
        return convertToMessage(entity)
    }
    fun messageToS2CSndMsg(message:Message):S2CSndMessage{
        val s2CSndMessage = S2CSndMessage()
        when(message.conversationType){
            ConversationType.TYPE_PRIVATE -> {
                s2CSndMessage.cmd = SystemCmd.C2S_SEND_P2P_MESSAGE
            }
            ConversationType.TYPE_GROUP -> {
                s2CSndMessage.cmd = SystemCmd.C2S_SEND_GROUP_MESSAGE
            }
            ConversationType.TYPE_CHAT_ROOM -> {
                s2CSndMessage.cmd = SystemCmd.C2S_SEND_CHATROOM_MESSAGE
            }
            ConversationType.TYPE_SYSTEM -> {
                s2CSndMessage.cmd = SystemCmd.C2S_SEND_SYSTEM_MESSAGE
            }
        }
        setBody(s2CSndMessage,message)
        return s2CSndMessage
    }

    private fun setBody(s2CSndMessage: S2CSndMessage,message:Message){
        val builder = Msg.newBuilder()
        builder.setSendType(message.conversationType).setMessageId(message.messageId)
            .setFrom(message.senderUserId)
            .setTo(message.targetId)
            .setTimestamp(System.currentTimeMillis()).build()
        when(message.messageContent){
            is TextMessage->{
                builder.messageType = MessageType.TYPE_TEXT
                val textMessage = message.messageContent as  TextMessage
                val atToList :MutableList<String> = ArrayList()
                if (textMessage .atToMessageList!= null &&
                        textMessage.atToMessageList.size>0){
                    for (at in textMessage.atToMessageList){
                        atToList.add(at.atTo)
                    }
                }
                val text = S2CCustomMessage.Text.newBuilder().setContent(textMessage.content).addAllAtTos(atToList)
                    .setExtra(textMessage.extra).build()
                builder.text = text
            }
            is AudioMessage->{
                builder.messageType =MessageType.TYPE_AUDIO
                val audioMessage = message.messageContent as AudioMessage
                val audio = S2CCustomMessage.Audio.newBuilder().setOriginUrl(audioMessage.originUrl)
                    .setSize(audioMessage.size).setDuration(audioMessage.duration).setExtra(audioMessage.extra).build()
                builder.audio = audio
            }
            is VideoMessage -> {
                builder.messageType = MessageType.TYPE_VIDEO
                val videoMessage = message.messageContent as VideoMessage
                val video =
                    S2CCustomMessage.Video.newBuilder().setDuration(videoMessage.duration).setWidth(videoMessage.width)
                        .setHeight(videoMessage.height).setOriginUrl(videoMessage.originUrl)
                        .setHeadUrl(videoMessage.headUrl).setSize(videoMessage.size).setExtra(videoMessage.extra).build()
                builder.video = video
            }
            is GeoMessage -> {
                builder.messageType = MessageType.TYPE_GEO
                val geoMessage = message.messageContent as GeoMessage
                val geo = S2CCustomMessage.Geo.newBuilder().setTitle(geoMessage.title).setAddress(geoMessage.address).setLat(geoMessage.lat)
                    .setLon(geoMessage.lon).setPreviewUrl(geoMessage.previewUrl).setExtra(geoMessage.extra).build()
                builder.geo = geo
            }
            is ImageMessage -> {
                builder.messageType = MessageType.TYPE_IMAGE
                val imageMessage = message.messageContent as ImageMessage
                val image =
                    S2CCustomMessage.Image.newBuilder().setOriginUrl(imageMessage.originUrl).setWidth(imageMessage.width)
                        .setHeight(imageMessage.height).setSize(imageMessage.size).setBreviaryUrl(imageMessage.breviary_url)
                        .setExtra(imageMessage.extra).build()
                builder.image = image
            }
            is ImageTextMessage -> {
                builder.messageType = MessageType.TYPE_IMAGE_AND_TEXT
                val imageTextMessage = message.messageContent as ImageTextMessage
                val imageText = S2CCustomMessage.ImageText.newBuilder().setTitle(imageTextMessage.title).setContent(imageTextMessage.content)
                    .setImageUrl(imageTextMessage.imageUrl).setRedirectUrl(imageTextMessage.redirectUrl)
                    .setExtra(imageTextMessage.extra).build()
                builder.imageText = imageText
            }
            is FileMessage -> {
                builder.messageType = MessageType.TYPE_FILE
                val fileMessage = message.messageContent as FileMessage
                val file =
                    S2CCustomMessage.File.newBuilder().setFileName(fileMessage.fileName).setOriginUrl(fileMessage.originUrl)
                        .setType(fileMessage.type).setSize(fileMessage.size).setExtra(fileMessage.extra).build()
                builder.file = file
            }
            is NoticeMessage -> {
                builder.messageType = MessageType.TYPE_NOTICE
                val noticeMessage = message.messageContent as NoticeMessage
                val notice = S2CCustomMessage.Notice.newBuilder().setContent(noticeMessage.content).setExtra(noticeMessage.extra).build()
                builder.notice = notice
            }
            is InputStatusMessage -> {
                builder.messageType = MessageType.TYPE_STATUS
                val inputStatusMessage = message.messageContent as InputStatusMessage
                val stus = S2CCustomMessage.Stus.newBuilder().setContent(inputStatusMessage.content).setExtra(inputStatusMessage.extra).build()
                builder.stus = stus
            }
            is ReplyMessage -> {
                builder.messageType = MessageType.TYPE_REPLY
                val replyMessage = message.messageContent as ReplyMessage
                val origin = Gson().toJson(MessageConvertUtil.instance.messageToJSonMessage(replyMessage.origin))
                val answer = Gson().toJson(MessageConvertUtil.instance.messageToJSonMessage(replyMessage.answer))

                val reply = S2CCustomMessage.Reply.newBuilder().setReply(origin).setMsg(answer).setExtra(replyMessage.extra).build()
                builder.reply = reply
            }
            is RecordMessage -> {
                builder.messageType = MessageType.TYPE_RECORD
                val recordMessage = message.messageContent as RecordMessage
                var list = arrayListOf<String>()
                var gson = Gson()
                for (index in 0 until recordMessage.messages.size) {
                    list.add(gson.toJson(MessageConvertUtil.instance.messageToJSonMessage(recordMessage.messages[index])))
                }
                val record = S2CCustomMessage.Record.newBuilder().addAllRecords(list).setExtra(recordMessage.extra).build()
                builder.record = record
            }

            is CustomMessage -> {
                builder.messageType = message.messageType
                var customMessage = message.messageContent as CustomMessage

                var custom = S2CCustomMessage.Custom.newBuilder().setContent(customMessage.content).setExtra(customMessage.extra).build()
                builder.custom = custom
            }
        }
        s2CSndMessage.body = builder.build()
    }
    fun messageToJSonMessage(message: Message): JSonMessage {
        var jsonMessage = JSonMessage(message.messageId, message.targetId, message.senderUserId,
            message.conversationType, message.messageType, message.timestamp)

        when (message.messageType) {
            MessageType.TYPE_TEXT -> {
                if (message.messageContent != null && message.messageContent is TextMessage) {
                    var text = message.messageContent as TextMessage
                    var jsonText = JSonText()
                    jsonText.content = text.content
                    var atto = arrayListOf<String>()
                    for (index in 0 until text.atToMessageList.size) {
                        atto.add(text.atToMessageList[index].atTo)
                    }
                    jsonText.atTos = atto
                    jsonText.extra = text.extra

                    jsonMessage.text = jsonText
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_IMAGE -> {
                if (message.messageContent != null && message.messageContent is ImageMessage) {
                    var image = message.messageContent as ImageMessage
                    var jsonImage = JSonImage()
                    jsonImage.originUrl = image.originUrl
                    jsonImage.breviaryUrl = image.breviary_url
                    jsonImage.width = image.width
                    jsonImage.height = image.height
                    jsonImage.size = image.size
                    jsonImage.extra = image.extra
                    jsonMessage.image = jsonImage
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_IMAGE_AND_TEXT -> {
                if (message.messageContent != null && message.messageContent is ImageTextMessage) {
                    var imageText = message.messageContent as ImageTextMessage
                    var jsonImageText = JSonImageText()
                    jsonImageText.title = imageText.title
                    jsonImageText.content = imageText.content
                    jsonImageText.imageUrl = imageText.imageUrl
                    jsonImageText.redirectUrl = imageText.redirectUrl
                    jsonImageText.tag = imageText.tag
                    jsonImageText.extra = imageText.extra
                    jsonMessage.imageText = jsonImageText
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_VIDEO -> {
                if (message.messageContent != null && message.messageContent is VideoMessage) {
                    var video = message.messageContent as VideoMessage
                    var jsonVideo = JSonVideo()
                    jsonVideo.originUrl = video.originUrl
                    jsonVideo.headUrl = video.headUrl
                    jsonVideo.width = video.width
                    jsonVideo.height = video.height
                    jsonVideo.duration = video.duration
                    jsonVideo.size = video.size
                    jsonVideo.extra = video.extra
                    jsonMessage.video = jsonVideo
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_AUDIO -> {
                if (message.messageContent != null && message.messageContent is AudioMessage) {
                    var audio = message.messageContent as AudioMessage
                    var jsonAudio = JSonAudio()
                    jsonAudio.originUrl = audio.originUrl
                    jsonAudio.duration = audio.duration
                    jsonAudio.size = audio.size
                    jsonAudio.extra = audio.extra
                    jsonMessage.audio = jsonAudio
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_FILE -> {
                if (message.messageContent != null && message.messageContent is FileMessage) {
                    var file = message.messageContent as FileMessage
                    var jsonFile = JSonFile()
                    jsonFile.fileName = file.fileName
                    jsonFile.originUrl = file.originUrl
                    jsonFile.type = file.type
                    jsonFile.size = file.size
                    jsonFile.extra = file.extra
                    jsonMessage.file = jsonFile
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_GEO -> {
                if (message.messageContent != null && message.messageContent is GeoMessage) {
                    var geo = message.messageContent as GeoMessage
                    var jsonGeo = JSonGeo()
                    jsonGeo.title = geo.title
                    jsonGeo.address = geo.address
                    jsonGeo.previewUrl = geo.previewUrl
                    jsonGeo.lon = geo.lon
                    jsonGeo.lat = geo.lat
                    jsonGeo.extra = geo.extra
                    jsonMessage.geo = jsonGeo
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_AUDIO_CALL, MessageType.TYPE_VIDEO_CALL -> {
                if (message.messageContent != null && message.messageContent is CallMessage) {
                    var call = message.messageContent as CallMessage
                    var jsonAct = JSonAct()
                    var jsonVct = JSonVct()
                    if (call.callType == CallMessage.CallType.CALL_TYPE_VIDEO) {
                        jsonAct.duration = call.duration.toInt()
                        jsonAct.content = call.content
                        jsonAct.endType = call.endType
                        jsonAct.extra = call.extra
                        jsonMessage.act = jsonAct
                    } else {
                        jsonVct.duration = call.duration.toInt()
                        jsonVct.content = call.content
                        jsonVct.endType = call.endType
                        jsonVct.extra = call.extra
                        jsonMessage.vct = jsonVct
                    }
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_REPLY -> {
                if (message.messageContent != null && message.messageContent is ReplyMessage) {
                    var reply = message.messageContent as ReplyMessage
                    var jsonReply = JSonReply()
                    jsonReply.reply = Gson().toJson(messageToJSonMessage(reply.origin))//递归转换类型
                    jsonReply.msg = Gson().toJson(messageToJSonMessage(reply.answer))//递归转换类型
                    jsonReply.extra = reply.extra
                    jsonMessage.reply = jsonReply
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
            MessageType.TYPE_RECORD -> {
                if (message.messageContent != null && message.messageContent is RecordMessage) {
                    var retransmission = message.messageContent as RecordMessage
                    var jsonRecord = JSonRecord()

                    for (index in 0 until retransmission.messages.size) {
                        var element = Gson().toJson(messageToJSonMessage(retransmission.messages[index]))//递归转换类型
                        jsonRecord.records.add(element)
                    }
                    jsonRecord.extra = retransmission.extra
                    jsonMessage.record = jsonRecord
                } else {
                    QLog.e(TAG, "messageToJSonMessage " + message.messageType + " messageContent 为空或类型错误")
                }
            }
        }
        return jsonMessage
    }
    fun messageToMessageEntity(message: Message): MessageEntity {
        val messageEntity = MessageEntity.obtain(
            message.conversationType, message.messageType, message.senderUserId, message.targetId
        )
        messageEntity.messageId = message.messageId
        messageEntity.state = message.state

        when (message.messageContent) {
            is TextMessage -> {
                messageEntity.messageType = MessageType.TYPE_TEXT
                val textMessage = message.messageContent as TextMessage
                val tm = TBTextMessage.obtain(
                    "", message.messageId, textMessage.content, textMessage.extra
                )
                val list: MutableList<TBAtToMessage> = java.util.ArrayList()
                for (atm in textMessage.atToMessageList) {
                    val at = TBAtToMessage.obtain(
                        "", message.messageId, atm.atTo, TBAtToMessage.ReadState.STATE_READ, message.timestamp
                    )
                    list.add(at)
                }
                messageEntity.textMessage = tm
                messageEntity.atToMessage = list
            }
            is AudioMessage -> {
                messageEntity.messageType = MessageType.TYPE_AUDIO
                val audioMessage = message.messageContent as AudioMessage
                val audio = TBAudioMessage.obtain(
                    "", message.messageId, audioMessage.localPath, audioMessage.originUrl, audioMessage.size,
                    audioMessage.duration, audioMessage.extra
                )
                messageEntity.audioMessage = audio
            }
            is VideoMessage -> {
                messageEntity.messageType = MessageType.TYPE_VIDEO
                val videoMessage = message.messageContent as VideoMessage
                val video = TBVideoMessage.obtain(
                    "",
                    message.messageId,
                    videoMessage.headUrl,
                    videoMessage.originUrl,
                    videoMessage.localPath,
                    videoMessage.width,
                    videoMessage.height,
                    videoMessage.size,
                    videoMessage.duration,
                    videoMessage.extra
                )
                messageEntity.videoMessage = video
            }
            is GeoMessage -> {
                messageEntity.messageType = MessageType.TYPE_GEO
                val geoMessage = message.messageContent as GeoMessage
                val geo = TBGeoMessage.obtain(
                    "",
                    message.messageId,
                    geoMessage.title,
                    geoMessage.address,
                    geoMessage.previewUrl,
                    geoMessage.localPath,
                    geoMessage.lon.toFloat(),
                    geoMessage.lat.toFloat(),
                    geoMessage.extra
                )
                messageEntity.geoMessage = geo
            }
            is ImageMessage -> {
                messageEntity.messageType = MessageType.TYPE_IMAGE
                val imageMessage = message.messageContent as ImageMessage
                val image = TBImageMessage.obtain(
                    "",
                    message.messageId,
                    imageMessage.originUrl,
                    imageMessage.breviary_url,
                    imageMessage.localPath,
                    imageMessage.width,
                    imageMessage.height,
                    imageMessage.size,
                    imageMessage.extra
                )
                messageEntity.imageMessage = image
            }
            is ImageTextMessage -> {
                messageEntity.messageType = MessageType.TYPE_IMAGE_AND_TEXT
                val imageTextMessage = message.messageContent as ImageTextMessage
                val imageText = TBImageTextMessage.obtain(
                    "",
                    message.messageId,
                    imageTextMessage.title,
                    imageTextMessage.content,
                    imageTextMessage.imageUrl,
                    imageTextMessage.redirectUrl,
                    imageTextMessage.tag,
                    imageTextMessage.extra
                )
                messageEntity.imageTextMessage = imageText
            }
            is FileMessage -> {
                messageEntity.messageType = MessageType.TYPE_FILE
                val fileMessage = message.messageContent as FileMessage
                val file = TBFileMessage.obtain(
                    "",
                    message.messageId,
                    fileMessage.fileName,
                    fileMessage.originUrl,
                    fileMessage.localPath,
                    fileMessage.type,
                    fileMessage.size,
                    fileMessage.extra
                )
                messageEntity.fileMessage = file
            }
            is NoticeMessage -> {
                messageEntity.messageType = MessageType.TYPE_NOTICE
                val noticeMessage = message.messageContent as NoticeMessage
                val notice = TBNoticeMessage.obtain(
                    "", message.messageId, noticeMessage.operateUser, noticeMessage.users,
                    noticeMessage.type, noticeMessage.content, noticeMessage.extra
                )
                messageEntity.noticeMessage = notice
            }
            is InputStatusMessage -> {
                messageEntity.messageType = MessageType.TYPE_STATUS
                val inputStatusMessage = message.messageContent as InputStatusMessage
                val stus = TBInputStatusMessage.obtain(
                    message.messageId, inputStatusMessage.content, inputStatusMessage.extra
                )
                messageEntity.inputStatusMessage = stus
            }
            //回复消息
            is ReplyMessage -> {
                messageEntity.messageType = MessageType.TYPE_REPLY
                val replyMessage = message.messageContent as ReplyMessage

                var orgin = messageToJSonMessage(replyMessage.origin)
                var answer = messageToJSonMessage(replyMessage.answer)

                val reply = TBReplyMessage.obtain(message.conversationId, message.messageId,
                    Gson().toJson(orgin), Gson().toJson(answer), replyMessage.extra)
                messageEntity.replyMessage = reply
            }
            //转发消息
            is RecordMessage -> {
                messageEntity.messageType = MessageType.TYPE_RECORD
                val retransmissionMessage = message.messageContent as RecordMessage

                var list = arrayListOf<JSonMessage>()
                for (index in 0 until retransmissionMessage.messages.size) {
                    var jsonMessage = messageToJSonMessage(retransmissionMessage.messages[index])
                    list.add(jsonMessage)
                }
                val retransmission = TBRetransmissionMessage.obtain(message.conversationId,
                    message.messageId, Gson().toJson(list), retransmissionMessage.extra)
                messageEntity.retransmissionMessage = retransmission
            }
            //自定义消息
            is CustomMessage -> {
                messageEntity.messageType = message.messageType
                var customMessage = message.messageContent as CustomMessage

                var custom = TBCustomMessage.obtain(message.conversationId, message.messageId,
                    customMessage.content, customMessage.extra)
                messageEntity.customMessage = custom
            }
        }
        return messageEntity
    }
    /*fun getSendMessage(body: Msg?, message: MessageEntity): S2CSndMessage {
    val msg = com.qx.im.core.network.tcp.message.S2CSndMessage()
    msg.cmd = ChatType.getSystemCmd(message.sendType)
    msg.body = body
    return msg
    }
    */

}
package com.qx.imlib.handler.chatroom

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.BaseEvent
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.C2SChatroomProperty
import io.netty.channel.ChannelHandlerContext

//聊天室属性获取
class ChatRoomAttributeHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var getPropertyResult = C2SChatroomProperty.GetPropertyResult.parseFrom(recMessage!!.contents)
        var data = HashMap<String,String>()
        data.put("propName",getPropertyResult.propName)
        data.put("propValue",getPropertyResult.propValue)
        EventBusUtil.postUi(data,BaseEvent.EventName.EVENT_NAME_CHATROOM_ATTRIBUTE)
    }
}
package com.qx.imlib.handler.call

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.it.protos.C2SVideoRtcOffer
import io.netty.channel.ChannelHandlerContext

class RTCOfferHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val resp =  C2SVideoRtcOffer.VideoRtcOffer.parseFrom(recMessage.contents)
        val event = RTCEvent(recMessage.cmd,resp)
        EventBusUtil.post(event)
    }
}
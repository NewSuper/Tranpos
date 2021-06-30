package com.qx.imlib.handler

import com.qx.im.model.UserInfoCache
import com.qx.imlib.SystemCmd
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.utils.encry.Key
import com.qx.it.protos.C2SKey
import com.qx.it.protos.C2SRSAKey
import io.netty.channel.ChannelHandlerContext

class RsaKeyHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage?) {
        var key = C2SRSAKey.RSAKeyRes.parseFrom(recMessage!!.contents)
        Key.TCP_SERVER_PUB_KEY = key.serverPubKey
        ctx!!.writeAndFlush(createAesKeyMsg())
    }

    private fun createAesKeyMsg(): S2CSndMessage {
        val req = C2SKey.KeyReq.newBuilder().setToken(UserInfoCache.getToken()).build()
        val message = S2CSndMessage()
        message.cmd = SystemCmd.C2S_AES_KEY
        message.body = req
        return message
    }
}
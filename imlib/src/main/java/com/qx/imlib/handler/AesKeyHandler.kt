package com.qx.imlib.handler

import com.qx.im.model.UserInfoCache
import com.qx.imlib.SystemCmd
import com.qx.imlib.netty.C2SRouter
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.utils.SystemUtil
import com.qx.it.protos.C2SAuth
import com.qx.it.protos.C2SKey
import io.netty.channel.ChannelHandlerContext

class AesKeyHandler : BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val key = C2SKey.KeyRes.parseFrom(recMessage.contents)
        C2SRouter.aesKey = key.key
        if (key.key.isNotEmpty()) {
            val msg = createAuthMessage()
        }
    }

    private fun createAuthMessage(): S2CSndMessage {
        val deviceNo: String = UserInfoCache.getToken() + System.currentTimeMillis()
        var systemVersion = SystemUtil.getSystemVersion()
        var deviceCode = SystemUtil.getDeviceBrand()
        val auth = C2SAuth.Auth.newBuilder().setAppId(UserInfoCache.appKey)
            .setToken(UserInfoCache.getToken()).setDeviceNo(deviceNo)
            .setDeviceType("1").setDeviceCode(deviceCode).setSystemVersion(systemVersion).build()
        val message = S2CSndMessage()
        message.cmd = SystemCmd.C2S_AUTH
        message.body = auth
        return message
    }

    companion object {
        var deviceNo: String = ""
    }
}
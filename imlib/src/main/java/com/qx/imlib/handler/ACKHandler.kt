package com.qx.imlib.handler

import com.qx.imlib.job.JobManagerUtil
import com.qx.imlib.netty.S2CRecMessage
import com.qx.imlib.qlog.QLog
import com.qx.it.protos.S2CAckStatus
import io.netty.channel.ChannelHandlerContext

class ACKHandler :BaseCmdHandler() {
    override fun handle(ctx: ChannelHandlerContext?, recMessage: S2CRecMessage) {
        val status = S2CAckStatus.AckStatus.parseFrom(recMessage.contents)
        QLog.i("ACKHandler",
            "收到ACK回复 sequence：" + recMessage.sequence + " code=" + status.code + " message=" + status.message
        )
        if(status.code == 1){
            JobManagerUtil.instance.callbackSuccess(recMessage.sequence,status)
        }else{
            JobManagerUtil.instance.callbackFailed(recMessage.sequence,status.code,status.message)
        }
        JobManagerUtil.instance.removeMessageTimer(recMessage.sequence)
    }
}
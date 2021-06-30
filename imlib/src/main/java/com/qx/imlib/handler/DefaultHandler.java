package com.qx.imlib.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qx.imlib.netty.S2CRecMessage;
import com.qx.imlib.qlog.QLog;

import io.netty.channel.ChannelHandlerContext;

public class DefaultHandler extends BaseCmdHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, S2CRecMessage recMessage) throws InvalidProtocolBufferException {
        QLog.i("DefaultHandler", "未知指令：cmd=" + recMessage.getCmd());
    }
}

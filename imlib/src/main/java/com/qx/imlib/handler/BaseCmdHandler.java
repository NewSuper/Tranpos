package com.qx.imlib.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qx.imlib.netty.S2CRecMessage;

import io.netty.channel.ChannelHandlerContext;

public abstract class BaseCmdHandler {
    public abstract void handle(ChannelHandlerContext ctx, S2CRecMessage recMessage)throws InvalidProtocolBufferException;
}

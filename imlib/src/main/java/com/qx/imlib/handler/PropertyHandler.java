package com.qx.imlib.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qx.im.model.RTCServerConfig;
import com.qx.imlib.netty.S2CRecMessage;
import com.qx.imlib.utils.ContextUtils;
import com.qx.imlib.utils.SharePreferencesUtil;
import com.qx.imlib.utils.event.RTCConfigEvent;
import com.qx.it.protos.S2CProperty;

import org.greenrobot.eventbus.EventBus;

import io.netty.channel.ChannelHandlerContext;

public class PropertyHandler extends BaseCmdHandler{
    @Override
    public void handle(ChannelHandlerContext ctx, S2CRecMessage recMessage) throws InvalidProtocolBufferException {
        S2CProperty.Property propertyData = S2CProperty.Property.parseFrom(recMessage.getContents());
        //缓存特殊字符
        SharePreferencesUtil.Companion.getInstance(ContextUtils.getInstance().getContext()).saveSpecialCharacters(propertyData.getSpecialCharacters());
        // ice servce,设备硬编码白名单
        RTCServerConfig rtcConfig = new RTCServerConfig();
        rtcConfig.setIceServers(propertyData.getIceServicesList());
        rtcConfig.setWhiteDeveice(propertyData.getWebrtcWhitelist());
        EventBus.getDefault().post(new RTCConfigEvent(rtcConfig));
    }
}


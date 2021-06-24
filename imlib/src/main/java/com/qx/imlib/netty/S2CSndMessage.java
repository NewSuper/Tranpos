package com.qx.imlib.netty;


import com.google.protobuf.GeneratedMessageV3;

/**
 * 发送消息结构
 * @since	JDK 1.8
 * @author	hechuan
 */
public class S2CSndMessage extends NettyMessage {

    /**
     * 消息体，Protobuf对象
     */
    private GeneratedMessageV3 body;

    public GeneratedMessageV3 getBody() {
        return body;
    }

    public void setBody(GeneratedMessageV3 body) {
        this.body = body;
    }

}

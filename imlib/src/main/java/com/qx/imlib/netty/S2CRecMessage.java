package com.qx.imlib.netty;

/**
 * 接收消息结构
 * @since	JDK 1.8
 * @author	hechuan
 */
public class S2CRecMessage extends NettyMessage {
    /**
     * 消息内容，Protobuf编码的byte[]
     */
    private byte[] contents;

    private Object data;

    public byte[] getContents() {

        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }


}

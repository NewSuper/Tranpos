package com.qx.imlib.netty;


import java.io.Serializable;

public abstract class NettyMessage implements Serializable {

    protected long sequence = System.currentTimeMillis(); // 消息序号

    private short cmd;

    public short getCmd() {

        return cmd;
    }

    public void setCmd(short cmd) {

        this.cmd = cmd;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}

package com.qx.imlib.netty;

import com.qx.imlib.qlog.QLog;

import static com.qx.imlib.netty.NettyConnectionState.STATE_CONNECTED;

public class NettyConnectionStateManager {
    private NettyConnectionState state = NettyConnectionState.STATE_IDLE;

    public static NettyConnectionStateManager getInstance() {
        return Holder.instance;
    }

    public void setState(NettyConnectionState state) {
        QLog.d("NettyConnectionStateManager", "setState="+state);
        this.state = state;
    }

    public NettyConnectionState getState() {
        return this.state;
    }

    public boolean isConnected() {
        return this.state == STATE_CONNECTED;
    }

    static class Holder {
        static final NettyConnectionStateManager instance = new NettyConnectionStateManager();
    }
}

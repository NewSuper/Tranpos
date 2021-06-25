package com.qx.imlib.netty;

public abstract class BaseNettyClient {
    public abstract void initBootstrap();

    public abstract void disconnect();

    public abstract void connect(String host, int port, String userToken);

    abstract void send(S2CSndMessage message);
}

package com.qx.imlib.netty;

public enum NettyConnectionState {
    /**
     * 默认空闲状态
     */
    STATE_IDLE,
    /**
     * 正在连接
     */
    STATE_CONNECTING,
    /**
     * 已连接
     */
    STATE_CONNECTED,
    /**
     * 正在断开连接（正常主动断开）
     */
    STATE_DISCONNECTING,
    /**
     * 已断开连接（正常主动断开）
     */
    STATE_DISCONNECTED,
    /**
     * 被踢下线（其他终端登录逼下线）
     */
    STATE_KICKED,
    /**
     * 心跳超时（需重连）
     */
    STATE_HEARTBEAT_TIMEOUT,
    /**
     * 非法请求，服务器强制断开连接
     */
    STATE_SERVER_REFUSE
}

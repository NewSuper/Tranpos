package com.qx.imlib.netty;


import com.qx.imlib.qlog.QLog;
import com.qx.imlib.utils.event.EventBusUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class C2SRouter extends ChannelInboundHandlerAdapter {

    public static final short VERSION = 1;
    public static String aesKey = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //刷新tcp最新一次回复时间
            HeartBeatHolder.getInstance().updateLatestTime();
            HeartBeatTimeCheck.getInstance().cancelTimer();
            // 关闭补充心跳
            HeartBeatHolder.getInstance().stopReplenishHeartBeat();
            CmdDispatcher.getInstance().dispatch(ctx, msg);
            super.channelRead(ctx, msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        //关闭连接
        ctx.channel().closeFuture();
        ctx.close();
        HeartBeatTimeCheck.getInstance().cancelTimer();
        // 关闭补充心跳
        HeartBeatHolder.getInstance().stopReplenishHeartBeat();
        QLog.e("C2SRouter", "exceptionCaught >> 连接异常：" + cause.getMessage());
        NettyConnectionStateManager.getInstance().setState(NettyConnectionState.STATE_DISCONNECTED);
//        //TODO 此处还需思考是返回disconnected还是networkerror
        EventBusUtil.post(ConnectionStatusListener.Status.NETWORK_ERROR);
        // 统一交给imclient负责重连
//        EventBus.getDefault().post(ReconnectEvent.EVENT_RECONNECT);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        /*if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    LogUtil.debug(this.getClass(), "userEventTriggered state="+e.state()+"发送心跳包");
                    ReconnectHolder.getInstance().startTimer();
                    S2CSndMessage msg = new S2CSndMessage();
                    msg.setCmd(SystemCmd.C2S_HEARTBEAT);
                    ctx.writeAndFlush(msg);
                    break;
            }
        }*/
    }

    //这里是断线要进行的操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        QLog.e("C2SRouter", "channelInactive");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        QLog.e("C2SRouter", "channelActive");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        QLog.e("C2SRouter", "channelUnregistered");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        QLog.e("C2SRouter", "channelRegistered");
    }
}
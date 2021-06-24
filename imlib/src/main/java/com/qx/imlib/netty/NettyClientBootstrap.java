package com.qx.imlib.netty;


import android.util.Log;

import com.qx.imlib.qlog.QLog;
import com.qx.it.protos.C2SAuth;
import com.qx.it.protos.C2SKey;
import com.qx.it.protos.C2SRSAKey;

import java.util.Map;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientBootstrap {
    private static final String TAG = "NettyClientBootstrap";

    private Channel mChannel;
    private ChannelFuture mFuture;
    private String userToken;
    private NioEventLoopGroup mGroup;

    public NettyClientBootstrap() {
    }

    public void sendMessage(S2CSndMessage message) {
        try {
            if (message.getBody() != null) {
                Log.e(TAG, " 发送消息:" + message.getBody().toString());
            }

            if (!isConnected()) {
                Log.e(TAG,
                        "sendMessage >> Channel通道已不可用 open=" + mChannel.isOpen() + " active=" + mChannel.isActive() + " " +
                                " writable=" + mChannel.isWritable());
                EventBusUtil.post(ConnectionStatusListener.Status.DISCONNECTED);
                return;
            }
            mChannel.writeAndFlush(message);
            Log.e(TAG, "sendMessage: 发送了消息给后端" + mChannel.isActive() + " " + mChannel.isWritable() + " " + mChannel.isOpen());
            mChannel.closeFuture().sync();
        } catch (Exception e) {
            Log.e(TAG, "sendMessage error(发送消息错误):" + e.getMessage() + " cause:" + e.getCause());
            EventBusUtil.post(ConnectionStatusListener.Status.DISCONNECTED);
            e.printStackTrace();
        }
    }

    public void connectNetty(String serverHost, int serverPort) {
        if (mGroup == null || mGroup.isShutdown()) {
            mGroup = new NioEventLoopGroup();
        }
        try {
            Bootstrap b = new Bootstrap();
            b.group(mGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 65535, 65535)).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("C2SMessageEncoder", new C2SMessageEncoder()); // 编码器
                    pipeline.addLast("C2SMessageDecoder", new C2SMessageDecoder()); // 解码器
                    //pipeline.addLast(new IdleStateHandler(60, 60, 60, TimeUnit.SECONDS)); // 空闲处理
                    pipeline.addLast("C2SRouter", new C2SRouter()); // 服务路由
                }
            });
            // 发起异步连接操作
            mFuture = b.connect(serverHost, serverPort).sync(); // 指定服务地址和端口，并绑定本地地址和端口
            mFuture.addListener(mChannelFutureListener);
            mChannel = mFuture.channel();
            QLog.d(TAG,
                    "尝试建立tcp长连接 mChannel >" + mChannel.toString() + " mChannel" + mChannel.metadata().toString());
            //连接中，设置状态为STATE_CONNECTING
            EventBusUtil.post(ConnectionStatusListener.Status.CONNECTING);
            NettyConnectionStateManager.getInstance().setState(NettyConnectionState.STATE_CONNECTING);
            mChannel.closeFuture().sync();
        } catch (Exception e) {
            QLog.e(TAG,
                    "连接异常：connect cause:" + e.getCause() + " message:" + e.getMessage());
            e.printStackTrace();
            EventBusUtil.post(ConnectionStatusListener.Status.TIMEOUT);
            // 统一交给imsdk 连接状态处理重连
//            //发送重连请求
//            EventBusUtil.post(ReconnectEvent.EVENT_RECONNECT);
        } finally {
            //  group.shutdownGracefully();
        }
    }

    public void send(final S2CSndMessage message) {
//        if (message.getCmd() != 11) {
//            Log.e(TAG, "发送消息 cmd：" + message.getCmd() + " sequence=" + message.getSequence() + Log.getStackTraceString(new Throwable()));
//        }
        ThreadPoolUtils.run(() -> sendMessage(message));
    }

    public void connect(final String serverHost, final int serverPort, final String userToken) {
        UserInfoCache.INSTANCE.setToken(userToken);
        this.userToken = userToken;
        disconnect();
        QLog.d(TAG,
                "尝试建立tcp长连接 connect > serverHost=" + serverHost + " serverPort=" + serverPort + " userToken=" + this.userToken);
        ThreadPoolUtils.run(() -> connectNetty(serverHost, serverPort));
    }

    public void disconnect() {
        try {
            if (mChannel != null) {
                mChannel.disconnect();
                mChannel.close();
                mChannel.closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (mGroup != null) {
                mGroup.shutdownGracefully();
                mChannel = null;
                mGroup = null;
            }
        }
    }

    public boolean isConnected() {
        if (mChannel == null || !mChannel.isOpen() || !mChannel.isActive() || !mChannel.isWritable()) {
            return false;
        }
        return true;
    }

    /**
     * 获取Rsa key
     *
     * @param pubKey
     */
    private void getRsaKey(String pubKey) {
        C2SRSAKey.RSAKeyReq req = C2SRSAKey.RSAKeyReq.newBuilder().setToken(userToken).setClientPubKey(pubKey).build();
        S2CSndMessage message = new S2CSndMessage();
        message.setCmd(SystemCmd.C2S_RSA_KEY);
        message.setBody(req);
        send(message);
    }

    private void getAesKey() {
        C2SKey.KeyReq req = C2SKey.KeyReq.newBuilder().setToken(userToken).build();
        S2CSndMessage message = new S2CSndMessage();
        message.setCmd(SystemCmd.C2S_AES_KEY);
        message.setBody(req);
        send(message);
    }

    private void sendAuth() {
        String deviceNo = userToken + System.currentTimeMillis();
        C2SAuth.Auth auth =
                C2SAuth.Auth.newBuilder().setToken(userToken).setDeviceNo(deviceNo).setDeviceType("1").setDeviceCode(
                        "1.1.1").setSystemVersion("2.2.2").build();
        S2CSndMessage message = new S2CSndMessage();
        message.setCmd(SystemCmd.C2S_AUTH);
        message.setBody(auth);
        send(message);
    }

    ChannelFutureListener mChannelFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            Log.e(TAG, "operationComplete 建立tcp长连接：" + future.isSuccess());
            if (future.isSuccess()) {
                Log.e(TAG, "operationComplete: 生成公私钥匙对");
//                getAesKey();
                //生成公私钥对
                Map map = CryptUtil.genRsaKeyPair();
                String pubkey = (String) map.get(CryptUtil.PUBLIC_KEY);
                Key.TCP_CLIENT_PRI_KEY = (String) map.get(CryptUtil.PRIVATE_KEY);
                getRsaKey(pubkey);
            }
        }
    };
}

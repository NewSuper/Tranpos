package com.qx.imlib.netty;


import android.util.Log;

import com.qx.imlib.utils.event.EventBusUtil;
import com.qx.imlib.utils.event.SendMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 用于处理Netty业务
 */
public class NettyClient extends BaseNettyClient {

    private static final String TAG = "NettyClient";

    private NettyClientBootstrap mBootstrap;

    public static NettyClient getInstance() {
        return ClientHolder.client;
    }

    @Override
    public void connect(String host, int port, String userToken) {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mBootstrap.connect(host, port, userToken);
    }

    @Override
    public void initBootstrap() {
        if (mBootstrap == null) {
            mBootstrap = new NettyClientBootstrap();
        }
    }

    @Override
    public void disconnect() {
        EventBus.getDefault().unregister(this);
        if (mBootstrap != null) {
            mBootstrap.disconnect();
        }
    }

    @Override
    void send(S2CSndMessage message) {
        //判断网络连接状态，如果不为CONNECTED则不能发送消息
        if (NettyConnectionStateManager.getInstance().isConnected()) {
            mBootstrap.send(message);
        } else {
            Log.e(TAG,  "send >>Netty长连接已断开");
            //通知app层，网络未连接
            EventBusUtil.post(ConnectionStatusListener.Status.DISCONNECTED);
            // 统一交给imsdk 连接状态处理重连
//            LogUtil.warning(this.getClass(), "send >> 发起重连");
//            EventBus.getDefault().post(ReconnectEvent.EVENT_RECONNECT);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void HandleSendMessage(SendMessageEvent event) {
        send(event.getMessage());
    }

    static class ClientHolder {
        static final NettyClient client = new NettyClient();
    }

}

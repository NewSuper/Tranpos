package com.qx.imlib.netty

import android.content.Context
import android.util.Log
import com.qx.imlib.utils.http.TcpServer

class NettyClientModel(context: Context) {

    private var mNettyClient: NettyClient? = null
    fun initNettyClient(userToken: String) {
        mNettyClient = NettyClient.getInstance()
        mNettyClient?.initBootstrap()
        connect(userToken)
    }

    fun getNettyClient(): NettyClient? {
        return mNettyClient
    }

    private fun connect(userToken: String) {
        Log.e("TAG", "connect: 连接用的Ip:" + TcpServer.host + "   " + TcpServer.port)
        mNettyClient!!.connect(TcpServer.host, TcpServer.port, userToken)
    }
}
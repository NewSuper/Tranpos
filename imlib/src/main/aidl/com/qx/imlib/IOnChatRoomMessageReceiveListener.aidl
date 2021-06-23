// IOnChatRoomMessageReceiveListener.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.Message;

interface IOnChatRoomMessageReceiveListener {
    //聊天室消息
    void onReceiveNewChatRoomMessage(inout Message message);

    //聊天室属性获取回调
//    void onReceiveGetAttribute(out HashMap<String, String> data);
}
// IOnReceiveMessageListener.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.Message;

interface IOnReceiveMessageListener {
    void onReceiveNewMessage(inout List<Message> message);
    void onReceiveRecallMessage(inout Message message);
    void onReceiveInputStatusMessage(String from);
    void onReceiveHistoryMessage(inout List<Message> message);
    void onReceiveP2POfflineMessage(inout List<Message> message);
    void onReceiveGroupOfflineMessage(inout List<Message> message);
    void onReceiveSystemOfflineMessage(inout List<Message> message);
}
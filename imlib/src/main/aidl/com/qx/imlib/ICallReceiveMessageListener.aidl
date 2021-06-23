// ICallReceiveMessageListener.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.CallReceiveMessage;

interface ICallReceiveMessageListener {
    void onReceive(in CallReceiveMessage receiveMessage);
}
// IMessageReceiptListener.aidl
package com.qx.imlib;

import com.qx.message.Message;

// Declare any non-default types here with import statements

interface IMessageReceiptListener {
    /**
     * 收到消息回执：已送达
     * @param message
     */
    void onMessageReceiptReceived(inout Message message);

    /**
     * 收到消息已阅读回执
     */
    void onMessageReceiptRead();
}
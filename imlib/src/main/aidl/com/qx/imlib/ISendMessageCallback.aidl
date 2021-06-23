// ISendMessageCallback.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.Message;
import com.qx.message.QXError;

interface ISendMessageCallback {
    void onAttached(inout Message message);
    void onSuccess();
    void onError(int errorOrdinal, inout Message message);
}
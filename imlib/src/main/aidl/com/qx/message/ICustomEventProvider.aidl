// QXError.aidl
package com.qx.message;

// Declare any non-default types here with import statements
import com.qx.message.Message;

interface ICustomEventProvider {
    String getCustomEventTag();
    void onReceiveCustomEvent(inout Message message);
}
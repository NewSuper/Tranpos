// IConversationListener.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.Conversation;

interface IConversationListener {
    void onChanged(inout List<Conversation> list);
}
// IConversationListener.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IConversationListener {
      void onChanged(inout List<Conversation> list);
}
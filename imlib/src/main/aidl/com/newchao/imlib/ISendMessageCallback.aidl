// ISendMessageCallback.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface ISendMessageCallback {
     void onAttached(inout Message message);
     void onSuccess();
     void onError(int errorOrdinal, inout Message message);
}
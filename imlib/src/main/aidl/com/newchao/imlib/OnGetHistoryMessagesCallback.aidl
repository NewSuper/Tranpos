// OnGetHistoryMessagesCallback.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface OnGetHistoryMessagesCallback {
     void onComplete(inout Message message);
}
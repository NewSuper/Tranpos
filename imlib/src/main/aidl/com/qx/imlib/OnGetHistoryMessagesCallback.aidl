// OnGetHistoryMessagesCallback.aidl
package com.qx.imlib;
// Declare any non-default types here with import statements
import com.qx.message.Message;
interface OnGetHistoryMessagesCallback {
  void onComplete(inout Message message);
}
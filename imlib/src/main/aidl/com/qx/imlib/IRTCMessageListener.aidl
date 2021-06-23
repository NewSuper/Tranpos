// IRTCMessageListener.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements
import com.qx.message.rtc.RTCSignalData;

interface IRTCMessageListener {
  void onReceive(in RTCSignalData signalData);
}
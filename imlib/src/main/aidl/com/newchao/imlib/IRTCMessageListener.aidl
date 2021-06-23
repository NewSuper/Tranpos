// IRTCMessageListener.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IRTCMessageListener {
  void onReceive(in RTCSignalData signalData);
}
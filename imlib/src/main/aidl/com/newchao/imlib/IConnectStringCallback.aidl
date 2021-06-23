// IConnectStringCallback.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IConnectStringCallback {
      void onComplete();
      void onFailure(int errorCode, String failure);
      void onDatabaseOpened(int state);
}
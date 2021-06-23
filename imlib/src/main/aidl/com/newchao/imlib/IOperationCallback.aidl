// IOperationCallback.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IOperationCallback {
      void onComplete();
      void onFailure(int code);
}
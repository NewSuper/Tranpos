// IOperationCallback.aidl
package com.qx.imlib;

// Declare any non-default types here with import statements

interface IOperationCallback {
    void onComplete();
    void onFailure(int code);
}
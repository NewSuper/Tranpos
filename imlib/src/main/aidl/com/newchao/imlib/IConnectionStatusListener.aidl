// IConnectionStatusListener.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IConnectionStatusListener {
    void onChanged(int code);
}
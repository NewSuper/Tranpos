// ILongCallback.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface ILongCallback {
   void onComplete(long result);
     void onFailure(int code) ;
}
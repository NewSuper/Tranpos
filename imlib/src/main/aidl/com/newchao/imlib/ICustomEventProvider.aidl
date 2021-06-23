// ICustomEventProvider.aidl
package com.newchao.imlib;


interface ICustomEventProvider {
     String getCustomEventTag();
     void onReceiveCustomEvent(inout Message message);
}
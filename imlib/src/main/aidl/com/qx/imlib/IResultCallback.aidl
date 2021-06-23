// IResultCallback.aidl
package com.qx.imlib;

import com.qx.message.Data;
import com.qx.message.QXError;
// Declare any non-default types here with import statements

interface IResultCallback {

        /**
         * 操作成功（指网络操作成功）
         */
        void onSuccess();

        /**IResultCallback
         * 操作失败（可能是数据库或网络操作失败
         */
        void onFailed(int errorOrdinal);
}
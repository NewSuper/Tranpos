package com.qx.imlib.job

import com.qx.message.QXError

interface ResultCallback {

    /**
     * 操作成功（指网络操作成功）
     */
    fun onSuccess()

    /**
     * 操作失败（可能是数据库或网络操作失败
     */
    fun onFailed(error: QXError)
}
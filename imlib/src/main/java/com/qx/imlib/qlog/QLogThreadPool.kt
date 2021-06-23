package com.qx.imlib.qlog

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

class QLogThreadPool(threadSize: Int) {

    private var executorService: ScheduledThreadPoolExecutor

    init {
        executorService = ScheduledThreadPoolExecutor(threadSize, threadFactory("Upload Dispatcher", false))
        executorService.maximumPoolSize = 5
        executorService.setKeepAliveTime(60L, TimeUnit.SECONDS)
        executorService.allowCoreThreadTimeOut(true)
    }

    fun getExecutorService(): ScheduledThreadPoolExecutor {
        return executorService
    }

    private fun threadFactory(name: String, daemon: Boolean): ThreadFactory {
        return ThreadFactory { runnable ->
            val result = Thread(runnable, name)
            result.isDaemon = daemon
            result
        }
    }
}
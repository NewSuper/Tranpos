package com.qx.imlib.job

class JobManagerUtil {
    companion object {
        @JvmStatic
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        var holder = JobManagerUtil()
    }
}
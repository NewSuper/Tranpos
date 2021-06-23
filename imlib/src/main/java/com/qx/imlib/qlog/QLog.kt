package com.qx.imlib.qlog

import android.content.Context
import android.util.Log

object QLog {

    const val TAG = "QLog"
    private const val NONE = 0
    private const val F = 1
    private const val E = 2
    private const val W = 3
    private const val I = 4
    private const val D = 5
    private const val V = 6
    private var isReleaseMode = true
    private var logConfig: QLogConfig? = null
    private var logReport: QLogReport? = null

    @JvmStatic
    fun openDebug() {
        isReleaseMode = false
    }

    @JvmStatic
    fun init(context: Context, appKey: String, sdkVersion: String) {
        val info = context.applicationInfo
        isReleaseMode = info == null || info.flags and 2 == 0
        logConfig = QLogConfig(context, appKey, sdkVersion)
        logReport = QLogReport()
    }

    @JvmStatic
    fun v(tag: String, msg: String): Int {
        return write(6, TAG, "[ $tag ] $msg")
    }

    @JvmStatic
    fun d(tag: String, msg: String): Int {
        return write(5, TAG, "[ $tag ] $msg")
    }

    @JvmStatic
    fun i(tag: String, msg: String): Int {
        return write(4, TAG, "[ $tag ] $msg")
    }

    @JvmStatic
    fun w(tag: String, msg: String): Int {
        return write(3, TAG, "[ $tag ] $msg")
    }

    @JvmStatic
    fun e(tag: String, msg: String): Int {
        return write(2, TAG, "[ $tag ] $msg")
    }

    @JvmStatic
    fun e(tag: String, msg: String, tr: Throwable?): Int {
        return write(2, TAG, "[ $tag ] $msg", tr)
    }

    @JvmStatic
    fun f(tag: String, msg: String): Int {
        return write(1, TAG, "[ $tag ] $msg")
    }


    private fun write(level: Int, tag: String, msg: String): Int {
        return write(level, tag, msg, null as Throwable?)
    }

    private fun write(level: Int, tag: String, msg: String, tr: Throwable?): Int {
        var result = -1
        return if (logConfig == null) {
            result
        } else {
            if (isReleaseMode) {
                result
            } else {
                when (level) {
                    1 -> result = Log.e(TAG, "[ $tag ] $msg")
                    2 -> result = if (tr == null) {
                        Log.e(TAG, "[ $tag ] $msg")
                    } else {
                        Log.e(TAG, "[ $tag ] $msg", tr)
                    }
                    3 -> result = Log.w(TAG, "[ $tag ] $msg")
                    4 -> result = Log.i(TAG, "[ $tag ] $msg")
                    5 -> result = Log.d(TAG, "[ $tag ] $msg")
                    6 -> result = Log.v(TAG, "[ $tag ] $msg")
                }
                result
            }
        }
    }
}
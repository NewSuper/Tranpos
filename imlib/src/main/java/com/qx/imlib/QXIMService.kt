package com.qx.imlib

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.text.TextUtils
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.GlobalContextManager

class QXIMService :Service(),Thread.UncaughtExceptionHandler {

    private var defaultExceptionHandler:Thread.UncaughtExceptionHandler?= null

    companion object{
        val TAG = QXIMService::class.java.simpleName
    }
    override fun onBind(intent: Intent): IBinder? {
        val appKey = intent.getStringExtra("appKey")
        QLog.init(this,appKey!!,"1.0")
        return LibHandlerStub(this,appKey!!)
    }

    override fun onCreate() {
        super.onCreate()
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        GlobalContextManager.getInstance().cacheApplicationContext(this)
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
       var reason = e.toString()
        if (!TextUtils.isEmpty(reason) && reason.contains(":")){
            reason = reason.substring(0,reason.indexOf(":"))
        }
        this.defaultExceptionHandler?.uncaughtException(t,e)
    }

    override fun onDestroy() {
        QLog.d(TAG, "onDestroy, pid=" + Process.myPid())
        Process.killProcess(Process.myPid())
    }
}
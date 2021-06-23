package com.newchao.imlib

import android.os.RemoteException

class QXIMClient private constructor(){
    private object Holder {
        var instance = QXIMClient()
    }

    companion object{
        private val TAG = QXIMClient::class.java.simpleName

        @JvmStatic
        val instance: QXIMClient
            get() = Holder.instance
    }
    fun getCurUserId(): String? {
//        try {
//            if (mLibHandler != null) {
//                instance.mCurrentUserId = mLibHandler!!.curUserId
//            }
//        } catch (e: RemoteException) {
//            e.printStackTrace()
//        }
//
//        return instance.mCurrentUserId
        return null
    }

}
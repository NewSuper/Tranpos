package com.qx.imlib

import android.content.Context
import com.qx.imlib.qlog.QLog
import com.qx.message.Data
import com.qx.message.rtc.RTCSignalData

class ModuleManager private constructor() {
    private val messageRoutes = arrayListOf<MessageRouter>()
    private val rtcMessageRoutes = arrayListOf<RTCSingalMessageRouter>()

    companion object {

        private val TAG = "ModuleManager"

        @JvmStatic
        val instance : ModuleManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy ModuleManager()
        }

        @JvmStatic
        fun init(context: Context) {
        }

        fun addMessageRouter(router: MessageRouter) {
            instance.messageRoutes.add(router)
        }

        fun addRTCMessageRouter(router: RTCSingalMessageRouter) {
            instance.rtcMessageRoutes.add(router)
        }

        fun removeMessageRouter(router: MessageRouter) {
            instance.messageRoutes.remove(router)
        }

        fun getMessageSize() : Int {
            return instance.messageRoutes.size
        }

        fun routeMessage(message: Data) {
            QLog.e(TAG,"routeMessage messageRoutes size:${getMessageSize()}")
            for(router in instance.messageRoutes) {
                router.onReceived(message)
            }
        }

        fun rtcSignalRouterMessage(signalData: RTCSignalData) {
            for(router in instance.rtcMessageRoutes) {
                router.onReceived(signalData)
            }
        }
    }

    interface MessageRouter {
        fun onReceived(message: Data)
    }

    interface RTCSingalMessageRouter {
        fun onReceived(signalData: RTCSignalData)
    }

}
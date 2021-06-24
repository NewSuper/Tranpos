package com.qx.imlib

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.text.TextUtils
import com.qx.imlib.qlog.QLog
import com.qx.imlib.qlog.QLogTrace
import com.qx.imlib.utils.ContextUtils
import com.qx.imlib.utils.SystemUtil
import com.qx.imlib.utils.TimeUtil
import com.qx.imlib.utils.net.NetStateUtils
import com.qx.imlib.utils.net.NetWorkMonitorManager
import com.qx.message.CallReceiveMessage
import com.qx.message.Conversation
import com.qx.message.Message
import com.qx.message.QXError
import com.qx.message.rtc.RTCSignalData
import java.lang.Exception

class QXIMClient private constructor() {
    @Volatile
    var mToken: String? = null
    private var mImServerUrl: String = ""
    private var mCheckSendingMessageListener: CheckSendingMessageListener? = null
    private var mMessageReceiptListener: MessageReceiptListener? = null
    private var mOnMessageReceiveListener: OnMessageReceiveListener? = null
    private var mOnChatNoticeReceivedListenerList = arrayListOf<OnChatNoticeReceivedListener>()
    private var mConversationListener: ConversationListener? = null
    private var mOnChatRoomMessageReceiveListener: OnChatRoomMessageReceiveListener? = null
    private var mLibHandler: IHandler? = null
    private var mContext: Context? = null
    private var mAppKey: String? = null
    private val mAidlConnection: AidlConnection
    private val mWorkHandler: Handler
    private var mConnectRunnable: ConnectRunnable? = null
    private var mConnectCallBack: ConnectCallBack? = null
    private var isNetworkAvailable = false
    private var topForegroundActivity: Activity? = null
    private var mCurrentUserId: String? = null
    private var isDeadNotConnect = false
    var mConnectStatus: ConnectionStatusListener.Status? = null


    private object Holder {
        var instance = QXIMClient()
    }

    companion object {
        private val TAG = QXIMClient::class.java.simpleName
        private var mHandler: Handler? = null

        @Volatile
        private var needCallBackDBOpen = false
        private var isInForeground = false
        private var mConnectionStatusListener: ConnectionStatusListener? = null

        @JvmStatic
        val instance: QXIMClient
            get() = Holder.instance

        @JvmStatic
        fun init(context: Context, appKey: String, imServerUrl: String) {
            Holder.instance.initSdk(context, appKey, imServerUrl)
        }

        @JvmStatic
        fun connect(token: String, callBack: ConnectCallBack?) {
            Holder.instance.mToken = token
            if (token.isNullOrEmpty()) {
                QLog.e(TAG, "token 不能为空")
            } else {
                needCallBackDBOpen = true
                Holder.instance.mConnectCallBack = object : ConnectCallBack() {
                    override fun onSuccess(result: String?) {
                        callBack?.onSuccess(result)
                    }

                    override fun onError(errorCode: String?) {
                        callBack?.onError(errorCode)
                    }

                    override fun onDatabaseOpened(code: Int) {
                        callBack?.onDatabaseOpened(code)
                        needCallBackDBOpen = false
                    }
                }
                instance.connectServer(token)
            }
        }
    }

    init {
        mAidlConnection = AidlConnection()
        mHandler = Handler(Looper.getMainLooper())
        val workThread = HandlerThread("IPC_WORK")
        workThread.start()
        mWorkHandler = Handler(workThread.looper)
    }

    private fun initSdk(context: Context, appkey: String, imServerUrl: String) {
        mImServerUrl = imServerUrl
        val currentProcess = SystemUtil.getCurrentProcessName(context)
        val mainProcess = context.packageName
        mAppKey = appkey
        QLog.d(TAG, " initSdk appKey:$appkey")
        if (!TextUtils.isEmpty(currentProcess) && !TextUtils.isEmpty(mainProcess)) {
            mConnectStatus = ConnectionStatusListener.Status.UNCONNECT
            mContext = context.applicationContext
            QLog.init(context, appkey, "1.0")
            QLogTrace.instance.initLogThread(appkey)
            QLog.e(TAG, " mainProcess initSdk appkey:$mAppKey start initBindService")
            initBindService()
            if (context is Application) {
                NetWorkMonitorManager.getInstance().init(context as Application)
                NetWorkMonitorManager.getInstance().register(this)
            }
            //  QXPushClient.init(context, appkey)
            ContextUtils.getInstance().cacheApplicationContext(mContext)
            isNetworkAvailable = NetStateUtils.isNetworkAvailable()
            if (context is Application) {
                context.registerActivityLifecycleCallbacks(object :
                    Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(
                        activity: Activity,
                        savedInstanceState: Bundle?
                    ) {
                    }

                    override fun onActivityStarted(activity: Activity) {
                    }

                    override fun onActivityResumed(activity: Activity) {
                        if (topForegroundActivity == null) {
                            onAppBackgroundChanged(true)
                        }
                        topForegroundActivity = activity
                    }

                    override fun onActivityPaused(activity: Activity) {
                    }

                    override fun onActivityStopped(activity: Activity) {
                        if (topForegroundActivity === activity) {
                            onAppBackgroundChanged(false)
                            topForegroundActivity = null
                        }
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                    }
                })
            }
        }
    }


    interface ConnectionStatusListener {
        fun onChanged(code: Int)
        enum class Status(var value: Int, var message: String) {
            UNKNOWN(STATUS_UNKNOWN, "未知状态"), CONN_USER_BLOCKED(
                STATUS_CONN_USER_BLOCKED,
                "用户被禁用"
            ),
            CONNECTED(
                STATUS_CONNECTED, "已连接"
            ),
            CONNECTING(STATUS_CONNECTING, "连接中"), DISCONNECTED(
                STATUS_DISCONNECTED,
                "连接已断开"
            ),
            KICKED(
                STATUS_KICKED, "被踢下线"
            ),
            NETWORK_UNAVAILABLE(STATUS_NETWORK_UNAVAILABLE, "网络不可用"), NETWORK_ERROR(
                STATUS_NETWORK_ERROR, "网络错误"
            ),
            INIT_ERROR(STATUS_INIT_ERROR, "初始化错误，请先调用init方法"), SERVER_INVALID(
                STATUS_SERVER_INVALID, "服务器异常"
            ),
            TOKEN_INCORRECT(STATUS_TOKEN_INCORRECT, "token错误"), REFUSE(
                STATUS_REFUSE,
                "非法访问"
            ),
            LOGOUT(
                STATUS_LOGOUT, "注销成功"
            ),
            TIMEOUT(STATUS_TIME_OUT, "连接超时"),
            UNCONNECT(STATUS_UNCONNECTED, "未连接"),
            REMOTE_SERVICE_CONNECTED(STATUS_REMOTE_SERVICE_CONNECTED, "远程服务已连接");

            companion object {
                @JvmStatic
                fun getStatus(code: Int): Status? {
                    for (status in Status.values()) {
                        if (status.value == code) {
                            return status
                        }
                    }
                    return null
                }
            }
        }

        companion object {
            //未知状态
            const val STATUS_UNKNOWN = 0

            //用户被封禁
            const val STATUS_CONN_USER_BLOCKED = 1

            //已连接
            const val STATUS_CONNECTED = 2

            //连接中
            const val STATUS_CONNECTING = 3

            //断开连接
            const val STATUS_DISCONNECTED = 4

            //被踢下线
            const val STATUS_KICKED = 5

            //网络不可用
            const val STATUS_NETWORK_UNAVAILABLE = 6

            //服务器不可用
            const val STATUS_SERVER_INVALID = 7

            //token错误
            const val STATUS_TOKEN_INCORRECT = 8

            //服务器拒绝登录
            const val STATUS_REFUSE = 9

            //已登出
            const val STATUS_LOGOUT = 10

            //网络错误
            const val STATUS_NETWORK_ERROR = 11

            //初始化错误
            const val STATUS_INIT_ERROR = 12

            //连接超时
            const val STATUS_TIME_OUT = 13

            //未连接
            const val STATUS_UNCONNECTED = 14

            //远程服务已连接
            const val STATUS_REMOTE_SERVICE_CONNECTED = 15
        }
    }
    interface OnChatNoticeReceivedListener {


        /**
         * 群全局禁言/解禁
         * @param isEnabled 是否启动，true为禁言，false为解禁
         */
        fun onGroupGlobalMute(isEnabled: Boolean)

        /**
         * 群组成员禁言
         *@param groupId 群组Id
         * @param isEnabled 是否启动，true为禁言，false为解禁
         *
         */

        fun onGroupMute(groupId: String, isEnabled: Boolean)

        /**
         * 群整体禁言。即：该群所有人都不能发消息
         * @param groupId 群组Id
         * @param isEnabled 是否启动，true为禁言，false为解禁
         */
        fun onGroupAllMute(groupId: String, isEnabled: Boolean)

        /**
         * 聊天室全局禁言/解禁
         * @param isEnabled 是否启动，true为禁言，false为解禁
         */
        fun onChatRoomGlobalMute(isEnabled: Boolean)

        /**
         * 聊天室成员封禁，即：封禁后会被踢出聊天室，并无法再次进入聊天室，直到解禁
         * @param isEnabled 是否启动，true为封禁，false为解封
         */
        fun onChatRoomBan(chatRoomId: String, isEnabled: Boolean)

        /**
         * 聊天室成员禁言
         *@param chatRoomId 聊天室Id
         * @param isEnabled 是否启动，true为禁言，false为解禁
         *
         */
        fun onChatRoomMute(chatRoomId: String, isEnabled: Boolean)

        /**
         * 聊天室销毁
         */
        fun onChatRoomDestroy()
    }

    interface ConversationListener {

        fun onChanged(list: List<Conversation>)
    }

    interface MessageReceiptListener {
        /**
         * 收到消息回执：已送达
         * @param message
         */
        fun onMessageReceiptReceived(message: Message?)

        /**
         * 收到消息已阅读回执
         */
        fun onMessageReceiptRead()
    }

    interface OnChatRoomMessageReceiveListener {
        //聊天室消息
        fun onReceiveNewChatRoomMessage(message: Message)

        //聊天室属性获取回调
        fun onReceiveGetAttribute(data: HashMap<String, String>)
    }

    interface CheckSendingMessageListener {
        fun onChecked(messages: List<Message>)
    }

    interface OnMessageReceiveListener {
        fun onReceiveNewMessage(message: List<Message>)
        fun onReceiveRecallMessage(message: Message)
        fun onReceiveInputStatusMessage(from: String)
        fun onReceiveHistoryMessage(message: List<Message>)
        fun onReceiveP2POfflineMessage(message: List<Message>)
        fun onReceiveGroupOfflineMessage(message: List<Message>)
        fun onReceiveSystemOfflineMessage(message: List<Message>)
    }


    /**
     * 连接回调
     */
    abstract class ConnectCallBack {
        /**
         * 成功
         */
        abstract fun onSuccess(result: String?)

        /**
         * 失败
         */
        abstract fun onError(errorCode: String?)

        /**
         * 数据库打开
         */
        abstract fun onDatabaseOpened(code: Int)
        fun onFail(message: String?) {
            mHandler?.post {
                onError(
                    message
                )
            }
        }

        fun onCallBack(result: String?) {
            mHandler?.post {
                this@ConnectCallBack.onSuccess(
                    result
                )
            }
        }
    }


    interface ErrorCallback {

        fun onError(error: QXError)
    }

    abstract class ResultCallback<T> : ErrorCallback {

        /**
         * 操作成功
         * @param data 泛型数据
         */
        abstract fun onSuccess(data: T)

        /**
         * 操作失败
         * @param errorCode 错误码
         * @param msg 错误信息
         */
        abstract fun onFailed(error: QXError)

        fun onCallback(data: T) {
            mHandler?.post {
                onSuccess(data)
            }
        }

        override fun onError(error: QXError) {
            mHandler?.post {
                onFailed(error)
            }
        }

    }

    /**
     * 操作回调，用于无参数返回的操作
     */
    abstract class OperationCallback : ErrorCallback {
        /**
         * 操作成功，回调到主线程
         */
        abstract fun onSuccess()

        /**
         * 操作失败，回调到主线程
         */
        abstract fun onFailed(error: QXError)

        fun onCallback() {
            mHandler?.post {
                onSuccess()
            }
        }

        override fun onError(error: QXError) {
            mHandler?.post {
                onFailed(error)
            }
        }
    }

    /**
     * 发送消息回调
     */
    abstract class SendMessageCallback : ErrorCallback {
        /**
         * 插入数据库成功
         */
        abstract fun onAttached(message: Message?)

        /**
         * 发送成功
         */
        abstract fun onSuccess()

        /**
         * 发送失败
         */
        override fun onError(error: QXError) {
            onError(error, null)
        }

        abstract fun onError(error: QXError, message: Message?)

    }


    fun getCurUserId(): String? {
        try {
            if (mLibHandler != null) {
                instance.mCurrentUserId = mLibHandler!!.curUserId
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        return instance.mCurrentUserId

    }

    fun getHttpHost(): String? {
        try {
            return mLibHandler?.httpHost
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return ""
    }

    private inner class AidlConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            QLog.e(TAG, "onServiceConnected ${TimeUtil.getTime(System.currentTimeMillis())}")
            QLog.e(TAG, "onServiceConnected token:$mToken")
            mLibHandler = IHandler.Stub.asInterface(service)
            mConnectionStatusListener?.onChanged(ConnectionStatusListener.STATUS_REMOTE_SERVICE_CONNECTED)
            initRemoteListener()
            try {
                mLibHandler!!.asBinder().linkToDeath(mDeathReceipient, 0)
                QLog.d(TAG, "remove sevice linkToDeath")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (isDeadNotConnect && !mToken.isNullOrEmpty()) {
                connectServer(mToken!!)
            }
            isDeadNotConnect = false
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mLibHandler = null
            QLog.e(TAG, "onServiceDisconnected")
            initBindService()
        }
    }

    private val mDeathReceipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            QLog.d(TAG, "DeathRecipient binderDied")
            if (mLibHandler != null) {
                mLibHandler!!.asBinder().unlinkToDeath(this, 0)
                QLog.d(TAG, "DeathRecipient unlinkToDeath")
                mLibHandler = null
                isDeadNotConnect = true
                initBindService()
                QLog.d(TAG, "DeathRecipient restart bind service")
            }
        }
    }

    @Synchronized
    private fun connectServer(token: String?) {
        if (!TextUtils.isEmpty(token)) {
            if (mLibHandler == null) {
                QLog.e(TAG, "connectServer mLibHandler is Null")
                mConnectRunnable = ConnectRunnable(token)
                initBindService()
            } else {
                mWorkHandler.post {
                    try {
                        if (mLibHandler == null || token.isNullOrEmpty() || mImServerUrl.isNullOrEmpty()) {
                            QLogTrace.instance.log("a is $mLibHandler,b:$token,c:$mImServerUrl")
                            return@post
                        }
                        mLibHandler?.connectServer(token,mImServerUrl,object :IConnectStringCallback.Stub(){
                            override fun onComplete() {
                                if(mConnectCallBack!=null){
                                    mConnectCallBack!!.onCallBack("")
                                    mConnectCallBack = null
                                }
                                //检查数据库中正在发送的消息
                                checkSendingMessage()
                               // QXPushClient.updateIMToken(mContext, mToken, mLibHandler!!.httpHost, mLibHandler!!.rsaKey)
                            }

                            override fun onFailure(errorCode: Int, failure: String?) {
                                if (mConnectCallBack != null) {
                                    mConnectCallBack!!.onFail(failure)
                                    mConnectCallBack = null
                                }
                            }

                            override fun onDatabaseOpened(state: Int) {
                                if (mConnectCallBack != null) {
                                    mConnectCallBack!!.onDatabaseOpened(state)
                                }
                            }
                        })
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    private fun  checkSendingMessage(){
        val messages = mLibHandler?.checkSendingMessage()
        if (!messages.isNullOrEmpty()){
            QLog.d(TAG, "messages size=" + messages.size)
            mCheckSendingMessageListener?.onChecked(messages)
        }
    }
    private fun onAppBackgroundChanged(inForeground:Boolean){
        isInForeground = inForeground
        try {
            if (mLibHandler!=null){
                if (mConnectStatus == ConnectionStatusListener.Status.CONNECTED){

                }else if (inForeground){
                    connectServer(mToken)
                }
            }else if (inForeground){
                initBindService()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private inner class ConnectRunnable(var token: String?) : Runnable {
        override fun run() {
            connectServer(token)
        }
    }

    private fun runOnUiThread(runnable: Runnable) {
        mHandler?.post(runnable)
    }

    private fun runOnUiThread(runnable: () -> Unit) {
        mHandler?.post(runnable)
    }

    private fun initBindService() {
        if (mLibHandler != null) {
            QLog.d(TAG, "initBindService mLibHandler is not null")
        } else {
            if (mContext != null) {
                QLog.d(TAG, "initBindService mAppKey：$mAppKey")
                QLog.d(TAG, "start bindService ${TimeUtil.getTime(System.currentTimeMillis())}")
                val intent = Intent(mContext, QXIMService::class.java)
                intent.putExtra("appKey", mAppKey)
                mContext!!.bindService(intent, mAidlConnection, Context.BIND_AUTO_CREATE)
            } else {
                QLog.d(TAG, "mContext 为空")
            }
        }
    }

    private fun initRemoteListener() {
        try {
            mLibHandler!!.setOnChatRoomMessageReceiveListener(object :
                IOnChatRoomMessageReceiveListener.Stub() {
                override fun onReceiveNewChatRoomMessage(message: Message?) {
                    runOnUiThread(Runnable {
                        mOnChatRoomMessageReceiveListener?.onReceiveNewChatRoomMessage(message!!)
                    })
                }

            })
            mLibHandler!!.setMessageReceiptListener(object : IMessageReceiptListener.Stub() {
                override fun onMessageReceiptReceived(message: Message?) {
                    runOnUiThread(Runnable {
                        mMessageReceiptListener?.onMessageReceiptReceived(message)
                    })
                }

                override fun onMessageReceiptRead() {
                    runOnUiThread(Runnable {
                        mMessageReceiptListener?.onMessageReceiptRead()
                    })
                }

            })
            mLibHandler!!.setReceiveMessageListener(object : IOnReceiveMessageListener.Stub() {
                override fun onReceiveNewMessage(message: MutableList<Message>?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveNewMessage(message!!)
                    })
                }

                override fun onReceiveRecallMessage(message: Message?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveRecallMessage(message!!)
                    })
                }

                override fun onReceiveInputStatusMessage(from: String?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveInputStatusMessage(from!!)
                    })
                }

                override fun onReceiveHistoryMessage(message: MutableList<Message>?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveHistoryMessage(message!!)
                    })
                }

                override fun onReceiveP2POfflineMessage(message: MutableList<Message>?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveP2POfflineMessage(message!!)
                    })
                }

                override fun onReceiveGroupOfflineMessage(message: MutableList<Message>?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveGroupOfflineMessage(message!!)
                    })
                }

                override fun onReceiveSystemOfflineMessage(message: MutableList<Message>?) {
                    runOnUiThread(Runnable {
                        mOnMessageReceiveListener?.onReceiveSystemOfflineMessage(message!!)
                    })
                }


            })
            mLibHandler!!.setConversationListener(object : IConversationListener.Stub() {

                override fun onChanged(list: MutableList<Conversation>?) {
                    runOnUiThread(Runnable {
                        mConversationListener?.onChanged(list!!)
                    })
                }

            })
            mLibHandler!!.addOnChatNoticeReceivedListener(object :
                IOnChatNoticeReceivedListener.Stub() {
                override fun onGroupGlobalMute(isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onGroupGlobalMute(isEnabled)
                        }
                    })

                }

                override fun onGroupMute(groupId: String?, isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onGroupMute(groupId!!, isEnabled)
                        }
                    })

                }

                override fun onGroupAllMute(groupId: String?, isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onGroupAllMute(groupId!!, isEnabled)
                        }
                    })

                }

                override fun onChatRoomGlobalMute(isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onChatRoomGlobalMute(isEnabled)
                        }
                    })


                }

                override fun onChatRoomBan(chatRoomId: String?, isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onChatRoomBan(chatRoomId!!, isEnabled)
                        }
                    })

                }

                override fun onChatRoomMute(chatRoomId: String?, isEnabled: Boolean) {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onChatRoomMute(chatRoomId!!, isEnabled)
                        }
                    })

                }

                override fun onChatRoomDestroy() {
                    runOnUiThread(Runnable {
                        for (listener in mOnChatNoticeReceivedListenerList) {
                            listener.onChatRoomDestroy()
                        }
                    })
                }

            })

            mLibHandler!!.setConnectionStatusListener(object : IConnectionStatusListener.Stub() {
                override fun onChanged(code: Int) {
                    onConnectStatusChange(code)
                }

                @Synchronized
                fun onConnectStatusChange(code: Int) {
                    mConnectStatus = ConnectionStatusListener.Status.getStatus(code)
                    if (code === ConnectionStatusListener.STATUS_KICKED) {
                        clearToken()
                    }
                    runOnUiThread {
                        mConnectionStatusListener?.onChanged(code)
                    }

                    QLog.e(TAG, " onConnectStatusChange ${mConnectStatus?.message}")
                }

            })

            mLibHandler?.setCallReceiveMessageListener(object : ICallReceiveMessageListener.Stub() {
                override fun onReceive(receiveMessage: CallReceiveMessage) {
                    QLog.e(TAG, "ICallReceiveMessageListener onReceive")
                    ModuleManager.routeMessage(receiveMessage)
                }

            })
            mLibHandler?.setRTCSignalMessageListener(object : IRTCMessageListener.Stub() {
                override fun onReceive(signalData: RTCSignalData) {
                    QLog.e(TAG, "IRTCMessageListener onReceive")
                    ModuleManager.rtcSignalRouterMessage(signalData)
                }

            })
        } catch (remoteExption: RemoteException) {
            remoteExption.printStackTrace()
        }
    }
    private fun clearToken() {
        mToken = ""
       // PushManager.getInstance().clearCache(mContext)
    }
}
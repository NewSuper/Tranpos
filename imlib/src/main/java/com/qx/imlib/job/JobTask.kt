package com.qx.imlib.job

import com.qx.im.model.UserInfoCache
import com.qx.imlib.QXIMClient
import com.qx.imlib.SystemCmd
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.db.entity.MessageEntity
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.MessageConvertUtil
import com.qx.imlib.utils.event.EventBusUtil
import com.qx.imlib.utils.event.SendMessageEvent
import com.qx.it.protos.C2SMessageRecall
import com.qx.it.protos.S2CAckStatus
import com.qx.it.protos.S2CCustomMessage
import com.qx.message.QXError
import java.util.*

class JobTask {

    /**
     * 任务id，一般取messageId，用于发送消息，其它场景传空字符串
     */
    val taskId: String? = ""
    var message: S2CSndMessage? = null
    var callback: ResultCallback? = null
    var isHeartBeat: Boolean = false
    var isNeedRetry: Boolean = false

    @Volatile
    private var startTime: Long = 0

    /**
     * 超时时间
     */
    private val EXP_TIME: Long = 5000

    /**
     * 检测频率
     */
    val INTERVAL_CHECK: Long = 5000

    /**
     * 最大超时次数
     */
    var timeOutMaxCount = 2

    /**
     * 超时次数
     */
    var timeOutCount = 0

    private var timer: Timer? = null

    constructor(taskId: String, message: S2CSndMessage, callback: ResultCallback?) {
        this.message = message
        this.callback = callback
        initRetryFlay(message)
        timer = Timer()
    }

    constructor(taskId: String, message: S2CSndMessage) : this(taskId, message, null) {
    }

    constructor(taskId: String, message: S2CSndMessage, isHeartBeat: Boolean) : this(
        taskId,
        message,
        null
    ) {
        this.isHeartBeat = isHeartBeat
    }

    private fun initRetryFlay(message: S2CSndMessage) {
        if (message.cmd == SystemCmd.C2S_SEND_GROUP_MESSAGE
            || message.cmd == SystemCmd.C2S_SEND_SYSTEM_MESSAGE
            || message.cmd == SystemCmd.C2S_SEND_P2P_MESSAGE
            || message.cmd == SystemCmd.C2S_SEND_CHATROOM_MESSAGE
            || message.cmd == SystemCmd.C2S_RECV_CONFIRM
            || message.cmd == SystemCmd.C2S_MESSAGE_READ
        ) {
            isNeedRetry = true
        }
    }

    private var timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            if (isTimeOut()) {
                //如果需要重发，则执行重发
                if (isNeedRetry) {
                    if (timeOutCount < timeOutMaxCount) {
                        QLog.i(
                            "JOBTask",
                            "发送超时，第${timeOutCount}次重试 threadId=" + Thread.currentThread().id
                        )
                        timeOutCount++
                        //重设时间
                        startTime = System.currentTimeMillis()
                        //发送消息
                        EventBusUtil.post(SendMessageEvent(message!!))
                    } else {
                        QLog.i(
                            "JOBTask",
                            "重试${timeOutCount}次，仍然失败 threadId=" + Thread.currentThread().id
                        )
                        handleRetryTimeOut()
                        timeOutCount = 0
                        isNeedRetry = false
                    }
                } else {
                    handleSendTimeOut()//如果重发次数已满，则回调发送超时错误
                }
            }
        }
    }

    fun startTimer() {
        EventBusUtil.post(SendMessageEvent(message!!))
        if (!isHeartBeat) {
            startTime = System.currentTimeMillis()
            timer?.schedule(timerTask, 0, INTERVAL_CHECK)
        }
    }

    fun cancelTimer() {
        timer!!.cancel()
    }

    fun getTimer(): Timer {
        return timer!!
    }

    fun updateSendTime() {
        startTime = System.currentTimeMillis()
    }

    fun callbackSuccess() {
        if (callback != null) {
            callback?.onSuccess()
        }
        cancelTimer()
    }

    fun callbackSuccess(status: S2CAckStatus.AckStatus) {
        when (message?.cmd) {
            //消息撤回
            SystemCmd.C2S_MESSAGE_RECALL -> {
                //如果为自己撤回的消息，则在这里更新撤回消息的状态
                var msg = message!!.body as C2SMessageRecall.MessageRecall
                IMDatabaseRepository.instance.updateMessageToRecallType(msg.messageId)
                var row = IMDatabaseRepository.instance.refreshConversationInfo(
                    msg.sendType,
                    UserInfoCache.getUserId(),
                    msg.targetId
                )
                if (row > 0) {
                    if (callback != null) {
                        callback?.onSuccess()
                    }
                } else {
                    if (callback != null) {
                        callback?.onFailed(QXError.OPERATE_RECALL_FAILED)
                    }
                }
                if (callback != null) {
                    callback?.onSuccess()
                }
            }
            //消息发送成功
            SystemCmd.C2S_SEND_P2P_MESSAGE, SystemCmd.C2S_SEND_GROUP_MESSAGE, SystemCmd.C2S_SEND_SYSTEM_MESSAGE -> {
                var msg = message!!.body as S2CCustomMessage.Msg
                //更新消息状态为已发送
                if (IMDatabaseRepository.instance.updateMessageStateAndTime(
                        MessageEntity.State.STATE_SENT,
                        status.timestamp,
                        msg.messageId
                    ) > 0
                ) {
                    //更新会话最新时间
                    IMDatabaseRepository.instance.refreshConversationInfo(
                        msg.sendType,
                        msg.from,
                        msg.to
                    )
                    //更新消息数据，并使用eventbus 发送
                    var msg = IMDatabaseRepository.instance.getMessageById(msg.messageId)
                    EventBusUtil.postMessageStateChanged(msg)
                    callback?.onSuccess()
                }
            }
            SystemCmd.C2S_SEND_CHATROOM_MESSAGE -> {
                var msg = message!!.body as S2CCustomMessage.Msg
                var messageEntity = MessageConvertUtil.instance.convertToMessageEntity(msg)
                messageEntity.state = MessageEntity.State.STATE_SENT
                messageEntity.timestamp = status.timestamp
                EventBusUtil.postMessageStateChanged(messageEntity)
                callback?.onSuccess()
            }
            else -> {
                callback?.onSuccess()
            }
        }
    }

    //处理服务器超时
    private fun handleSendTimeOut() {
        timer?.cancel()
        timerTask.cancel()
        callback?.onFailed(QXError.MESSAGE_SEND_TIME_OUT)
    }

    //处理重试超时
    private fun handleRetryTimeOut() {
        callback?.onFailed(QXError.MESSAGE_RETRY_TIME_OUT)
    }

    //acl回调
    fun callbackFailed(errorCode: Int, msg: String) {
        var error = getError(errorCode)
        error.extra = msg
        callback?.onFailed(error)
    }

    private fun getError(code: Int): QXError {
        for (error in QXError.values()) {
            if (code == error.code) {
                return error
            }
        }
        return QXError.UNKNOWN
    }
    private fun isTimeOut(): Boolean {
        return System.currentTimeMillis() - startTime > EXP_TIME
    }


}
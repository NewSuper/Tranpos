package com.qx.imlib.job

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.qx.imlib.SystemCmd
import com.qx.imlib.netty.S2CSndMessage
import com.qx.imlib.qlog.QLog
import com.qx.imlib.utils.ContextUtils
import com.qx.it.protos.S2CAckStatus

class JobManagerUtil {
    companion object {
        @JvmStatic
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        var holder = JobManagerUtil()
    }

    private var mSendMsgJobManager: JobManager? = null
    private var mNotifyUiJobManager: JobManager? = null
    private var mJobTaskMap = HashMap<Long,JobTask>()

    private fun getConfiguration():Configuration{
        return Configuration.Builder(ContextUtils.getInstance().context).customLogger(object :CustomLogger{
            override fun isDebugEnabled(): Boolean {
                return false
            }

            override fun d(text: String, vararg args: Any) {
            }

            override fun e(t: Throwable, text: String, vararg args: Any) {
            }

            override fun e(text: String, vararg args: Any) {
            }

            override fun v(text: String, vararg args: Any) {
            }
        }).minConsumerCount(1)  //最小工作线程数
            .maxConsumerCount(30) //最大工作线程数
            .loadFactor(2) //每个工作线程最大任务数
            .consumerKeepAlive(10) //每个工作线程闲置后保活10 s
            .build()
    }
    fun postMessage(message: S2CSndMessage, callback: ResultCallback, taskId: String = "") {
        var jobTask = JobTask(taskId, message, callback)
        postMsg(message.sequence, jobTask)
    }

    fun postMessage(message: S2CSndMessage, taskId: String = "") {
        var jobTask = JobTask(taskId, message)
        postMsg(message.sequence, jobTask)
    }

    fun postHeatBeat(message: S2CSndMessage, taskId: String = "") {
        var jobTask = JobTask(taskId, message, true)
        postMsg(message.sequence, jobTask)
    }

    fun isExist(taskId:String):Boolean{
        for(task in mJobTaskMap.values){
            if (task.taskId == taskId){
                return true
            }
        }
        return false
    }

    private fun postMsg(sequence:Long,jobTask:JobTask){
        if (mSendMsgJobManager == null){
            mSendMsgJobManager = JobManager(getConfiguration())
        }
        mJobTaskMap[sequence]  = jobTask
        var messageJob = MessageJob(jobTask)
        mSendMsgJobManager!!.addJobInBackground(messageJob)
    }

    fun postUiNotify(data:Any?){
        if (mNotifyUiJobManager==null){
            mNotifyUiJobManager  = JobManager(getConfiguration())
        }
        var uiJob = UiJob(data)
        mNotifyUiJobManager!!.addJobInBackground(uiJob)
    }

    fun removeMessageTimer(sequence: Long){
        QLog.i("JOBManager", "移除timer sequence = $sequence")
        var jobTask = mJobTaskMap[sequence]
        jobTask?.getTimer()?.cancel()
        mJobTaskMap.remove(sequence)
    }

    fun getSentMessage(sequence: Long):S2CSndMessage?{
        return mJobTaskMap[sequence]?.message
    }

    fun callbackSuccess(sequence: Long, status: S2CAckStatus.AckStatus) {
        mJobTaskMap[sequence]?.callbackSuccess(status)
    }

    /**
     * 此处由ack回调
     */
    fun callbackFailed(sequence: Long, errorCode: Int, message: String) {
        mJobTaskMap[sequence]?.callbackFailed(errorCode, message)
    }

    fun logout() {
        for ((key, value) in mJobTaskMap) {
            if (value.message?.cmd == SystemCmd.C2S_LOGOUT) {
                value.callbackSuccess()
                value.cancelTimer()
            }
        }
        cancelAllJob()
    }

    private fun cancelAllJob() {
        for ((key, value) in mJobTaskMap) {
            value.cancelTimer()
        }
    }
}
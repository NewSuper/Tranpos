package com.qx.imlib.job

import com.qx.imlib.handler.BaseCmdHandler
import com.qx.imlib.handler.ReceiveGroupMessageHandler
import com.qx.imlib.netty.MessageTask
import com.qx.imlib.qlog.QLog
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QueueHolder {
    @Volatile
    var isStart = false
    private val max = 300
    private val duration = 100L
    var handler:BaseCmdHandler?= null
    @Volatile
    var mMessageQueue:MessageQueue<MessageTask>?=null
    var mPool :ExecutorService? = null
    constructor(){
        mMessageQueue = MessageQueue(max)
    }
    fun take(task:MessageTask){
        QLog.d("QueueHolder", "并发测试 take 取任务放入队列")
        mMessageQueue!!.add(task)
        //如果线程池空或已终止，则重新启动
        if (mPool==null||mPool!!.isShutdown||mPool!!.isTerminated){
            QLog.d("QueueHolder", "并发测试 启动线程池")
            handler = ReceiveGroupMessageHandler()
            mPool = Executors.newSingleThreadExecutor()
            isStart = true
        }
        isStart  = true
        mPool!!.execute(Runnable {
            try {
                while (isStart){
                    if (mMessageQueue!!.size<1){
                        isStart = false
                        break
                    }
                    mMessageQueue!!.poll()?.handle(handler!!)
                    if (mPool!!.isTerminated||mPool!!.isShutdown){
                        isStart = false
                        break
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        })
    }

    /*private var mRunnable = Runnable {
        try {
            Log.e("QueueHolder",  "启动消费者线程 threadId="+Thread.currentThread().id)

            while (!mMessageQueue.isNullOrEmpty()) {
                if(mMessageQueue!!.size < 1) {
                    QLog.d("QueueHolder",  "消息队列为空，停止轮询")
                    isStart = false
                    break
                }
                //消费任务
                var task = mMessageQueue!!.poll()
                task.handle(handler!!)
                if (mPool!!.isShutdown || mPool!!.isTerminated) {
                    isStart = false
                    break
                }
                Thread.sleep(duration)
            }
            Log.e("QueueHolder",  "终止消费者线程")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}
package com.newsuper.t.sale.service

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import com.newsuper.t.sale.service.model.UploadTask
import com.newsuper.t.sale.utils.KeyConstrant
import java.util.concurrent.ScheduledExecutorService

/**
 * 兼容8.0后的服务，不会显示通知栏，运行在子线程
 */
class UploadService : JobIntentService() {

    private var mExecutor : ScheduledExecutorService? = null
    private val mTask = UploadTask()

    init {

    }

    companion object{
        private const val JOB_ID = 1
        private const val INTERNAL_TIME = 1L
        private const val INIT_DELAY = 1000L
        fun enqueueWork(context: Context,work: Intent){
            enqueueWork(context, UploadService::class.java, JOB_ID,work)
        }
    }

    /**
     * 处理耗时任务
     *
     */
    override fun onHandleWork(intent : Intent) {
        var startLoop = intent.getBooleanExtra(KeyConstrant.KEY_START_LOOP, false)
        var stopLoop = intent.getBooleanExtra(KeyConstrant.KEY_STOP_LOOP, false)
//        if(startLoop){
//            val threadFactory: ThreadFactory = BgThreadFactory()
//            mExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory)
//            mExecutor?.scheduleAtFixedRate(mTask,INIT_DELAY,INTERNAL_TIME,TimeUnit.MINUTES)
//        } else if(stopLoop){
//            mExecutor?.shutdown()
//            mExecutor = null
//        }

    }
}
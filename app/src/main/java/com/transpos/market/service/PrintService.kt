package com.transpos.market.service

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import android.util.Log

class PrintService : JobIntentService() {

    override fun onHandleWork(p0: Intent) {
        Log.e("debug","onHandleWork start print service....")
        val tradeNo = p0.getStringExtra("tradeNo")
        val task = PrintTask(this,tradeNo)
        task.run()
    }

    companion object{
        private const val JOB_ID = 2
        fun enqueueWork(context: Context, work: Intent){
            enqueueWork(context,PrintService::class.java, JOB_ID,work)
        }
    }
}
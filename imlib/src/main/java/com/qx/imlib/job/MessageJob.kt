package com.qx.imlib.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint

class MessageJob constructor(private val jobTask: JobTask) : Job(Params(1)) {
    override fun onAdded() {
    }

    override fun onRun() {
        jobTask.startTimer()
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        jobTask.cancelTimer()
    }

    override fun shouldReRunOnThrowable(
        throwable: Throwable,
        runCount: Int,
        maxRunCount: Int
    ): RetryConstraint {
        jobTask.cancelTimer()
        return RetryConstraint.CANCEL
    }
}
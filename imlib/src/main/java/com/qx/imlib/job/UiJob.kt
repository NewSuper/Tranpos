package com.qx.imlib.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.qx.imlib.utils.event.EventBusUtil

class UiJob constructor(private val data: Any?)  :  Job(Params(1)) {
    override fun onAdded() {

    }

    override fun onRun() {
        //notify ui to update
        EventBusUtil.post(data)
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.CANCEL
    }

}
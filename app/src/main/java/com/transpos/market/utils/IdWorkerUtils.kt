package com.transpos.market.utils

class IdWorkerUtils {

    companion object{
        private val worker : IdWorker = IdWorker(0,0,0)

        fun nextId() : String = worker.id.toString()
    }
}
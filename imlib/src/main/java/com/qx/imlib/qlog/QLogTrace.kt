package com.qx.imlib.qlog

import com.qx.imlib.QXIMClient
import com.qx.imlib.utils.SystemUtil
import java.lang.StringBuilder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class QLogTrace {

    companion object {
        @JvmStatic
        val instance: QLogTrace by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            QLogTrace()
        }
    }

    private val queue = LinkedBlockingQueue<QLogReport.UploadConfig>()
    private var threadPool: ExecutorService = Executors.newFixedThreadPool(2)
    private lateinit var internalImpl: QLogImpl
    private var appkey: String = ""
    private lateinit var logReport: QLogReport
    fun initLogThread(key: String) {
        this.appkey = key
        internalImpl = QLogImpl()
        internalImpl.upload()
        logReport = QLogReport()
    }


    fun log(content: String) {
        internalImpl.log(content)
    }

    private inner class QLogImpl : IQLogTrace {

        override fun upload() {
            threadPool.execute {
                while (true) {
                    val config = queue.take() as QLogReport.UploadConfig
                    QLog.e("QLogFeatureQueue", "  name:${Thread.currentThread().id}")
                    logReport.report(config, object : QLogReport.IUploadListener {
                        override fun onUploadFinish(result: Boolean, filePath: String) {
                            QLog.e("QLogFeatureQueue", " onUploadFinish")
                        }

                    })
                }
            }
        }

        override fun log(content: String) {
            threadPool.execute {
                if (!QXIMClient.instance.getHttpHost().isNullOrEmpty() && !QXIMClient.instance.getHttpHost().isNullOrEmpty()) {
                    val sb = StringBuilder()
                    sb.append("OS: Android, ")
                    sb.append("OS Version: ${SystemUtil.getSystemVersion()},")
                    sb.append("Model: ${SystemUtil.getSystemModel()},")
                    sb.append("Brand: ${SystemUtil.getDeviceBrand()} | ")
                    sb.append("$appkey | ")
                    sb.append("${QXIMClient.instance.getCurUserId()!!} | ")
                    sb.append("$content")
                    sb.append("\n")
                    val url = "${QXIMClient.instance.getHttpHost()!!}qx-api/app/log/client"
                    val config = QLogReport.UploadConfig(url, "", sb.toString(), "", "", "", "$appkey", QXIMClient.instance.getCurUserId()!!)
                    QLog.e("QLogFeatureQueue", "url:$url,content $sb")
                    queue.put(config)
                }
            }
        }

    }
}
package com.qx.imlib.qlog

import android.util.Log
import com.qx.imlib.utils.NetUtils
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection

class QLogReport {

    private var qLogThreadPool: QLogThreadPool = QLogThreadPool(1)

    private val mUploadCallback: UploadCallback? = null

    fun report(config: UploadConfig, listener: IUploadListener?) {
        this.qLogThreadPool.getExecutorService().submit {
            val result =uploadLog(config)
            listener?.onUploadFinish(result,config.filePath)
        }
    }

    private fun uploadLog(pConfig: UploadConfig) : Boolean {
        QLog.i("QLog", "uploadFile begin.")
        var conn: HttpURLConnection? = null
//        val file = File(pConfig.filePath)
        var outStream: DataOutputStream? = null
        var inStream: InputStream? = null
        var `is`: FileInputStream? = null
        return if (pConfig.content.isNullOrEmpty()) {
            QLog.e("QLog", "content isNullOrEmpty")
            if (this.mUploadCallback != null) {
                this.mUploadCallback.fail(-4, "file not found", null as Throwable?)
            }
            false
        }else if (pConfig.userId != null && pConfig.sdkVer != null && pConfig.appKey != null) {
            var result: Boolean = false
            try {
                conn = NetUtils.createURLConnection(pConfig.uploadUrl)
                conn!!.connectTimeout = 15000
                conn!!.readTimeout = 5000
                conn!!.doInput = true
                conn!!.doOutput = true
                conn!!.useCaches = false
                conn!!.requestMethod = "POST"
                conn.setRequestProperty("Content-Type","application/json;charset=UTF-8")
                outStream = DataOutputStream(conn.outputStream)
                val body = JSONObject()
                body.put("content",pConfig.content)
                outStream.writeBytes(body.toString())
                outStream.flush()
                inStream = conn.inputStream
                val buff = BufferedReader(InputStreamReader(inStream))
                val responseSb = StringBuilder()
                var line: String?
                while (buff.readLine().also { line = it } != null) {
                    responseSb.append(line)
                }
                val responseBody = responseSb.toString()
                Log.d("QLog", "response = $responseBody")
                if (responseBody.contains("\"code\":1000")) {
                    Log.d("QLog", "response = $responseBody")
                    result = true
                    QLog.i("QLog", "upload success ")
                    if (this.mUploadCallback != null) {
                        this.mUploadCallback.success()
                    }
                } else {
                    result = false
                    QLog.e("QLog", "upload error server invalidate")
                    if (this.mUploadCallback != null) {
                        this.mUploadCallback.fail(-2, responseBody, null as Throwable?)
                    }
                }
            } catch (var30: Exception) {
                result = false
                QLog.e("QLog", "http error", var30)
                if (this.mUploadCallback != null) {
                    this.mUploadCallback.fail(-5, "http error", var30)
                }
            } finally {
                conn?.disconnect()
                try {
                    `is`?.close()
                } catch (var29: IOException) {
                    QLog.e("QLog", "uploadFile", var29)
                }
                try {
                    inStream?.close()
                } catch (var28: IOException) {
                    QLog.e("QLog", "uploadFile", var28)
                }
                try {
                    outStream?.close()
                } catch (var27: IOException) {
                    QLog.e("QLog", "uploadFile", var27)
                }
            }
            QLog.i("QLog", "uploadFile end.")
            result
        } else {
            QLog.e("QLog", "params is empty ")
            if (this.mUploadCallback != null) {
                this.mUploadCallback.fail(-1, "params error", null as Throwable?)
            }
            false
        }
    }

    class UploadConfig(var uploadUrl:String, var filePath:String = "",var content:String,
                      var startTime:String,var endTime:String,  var sdkVer:String, var appKey:String, var userId:String) {

    }

    interface IUploadListener {
        fun onUploadFinish(result: Boolean, filePath: String)
    }

    interface UploadCallback {
        fun success()
        fun fail(code: Int, message: String, throwable: Throwable?)

        companion object {
            const val PARAMS_ERROR = -1
            const val SERVER_ERROR = -2
            const val FILE_EMPTY = -3
            const val FILE_NOT_FOUND = -4
            const val HTTP_ERROR = -5
        }
    }

}


package com.qx.imlib.utils.http

import android.os.Build
import android.util.Base64
import com.google.gson.Gson
import com.qx.imlib.utils.encry.CryptUtil
import com.qx.imlib.utils.encry.Key
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.HashMap

object HttpUtil {
    private const val TAG = "HttpUtil"

    var SERVER_URL = ""

    private const val CODE_SUCCESS = 0
    private const val CODE_FAILED = 1

    interface HttpResponseListener {
        fun onProcess()
        fun onSuccess(obj: Any?)
        fun onFailed(code: Int, message: String?)
    }

    fun getHttpPubKey(listener: HttpResponseListener) {
        requestGet(
            SERVER_URL + "qx-api/app/im/application/public",
            BeanPubKey::class.java,
            listener
        )
    }

    fun getSensitiveWord(appKey: String, listener: HttpResponseListener) {
        var appKey = appKey
        val param = HashMap<String, String>()
        appKey = encode(appKey)
        param["appId"] = appKey
        requestPostJson(
            SERVER_URL + "qx-api/app/im/sensitive/findByAppId",
            param,
            BeanGetSensitiveWord::class.java,
            listener
        )
    }

    fun getServerIp(appKey: String, token: String, listener: HttpResponseListener) {
        val param = HashMap<String, String>()
        var appIdKey = "$appKey,$token"
        appIdKey = encode(appIdKey)
        param["appIdKey"] = appIdKey
        requestPostJson(
            SERVER_URL + "qx-api/app/im/application/serverIp",
            param,
            BeanResponse::class.java,
            listener
        )
    }

    fun uploadPushToken(
        appId: String,
        token: String,
        firm: String,
        deviceToken: String,
        listener: HttpResponseListener
    ) {
        val param = HashMap<String, String>()
        var inputPushToken = "$appId,$token,$firm,$deviceToken"
        inputPushToken = encode(inputPushToken)
        param["inputPushToken"] = inputPushToken
        requestPostJson(
            SERVER_URL + "qx-api/app/im/application/inputPushToken",
            param,
            BeanResponse::class.java,
            listener
        )
    }

    private fun requestGet(url: String, clazz: Class<*>, listener: HttpResponseListener) {
        ThreadPoolUtils.run {
            var _is: InputStream? = null
            var conn: HttpURLConnection? = null
            var baos: ByteArrayOutputStream? = null
            try {
                val u = URL(url)
                conn = u.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                _is = conn.inputStream
                baos = ByteArrayOutputStream()
                var len = -1
                val buffer = ByteArray(1024)
                while (_is?.read(buffer).also { len = it!! } != -1) {
                    baos.write(buffer, 0, len)
                }
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val content = String(baos.toByteArray())
                    val obj = Gson().fromJson<Any>(content, clazz)
                    listener.onSuccess(obj)
                } else {
                    listener.onFailed(conn.responseCode, "get fail：$url")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onFailed(-1, "error:" + url + " error:" + e.cause)
            } finally {
                _is?.close()
                baos?.close()
                conn?.disconnect()
            }
        }
    }

    private fun requestPostJson(
        url: String,
        param: HashMap<String, String>,
        clazz: Class<*>,
        listener: HttpResponseListener
    ) {
        ThreadPoolUtils.run {
            var _os: OutputStream? = null
            var conn: HttpURLConnection? = null
            var _in: InputStreamReader? = null
            try {
                val body = JSONObject()
                for (key in param.keys) {
                    body.put(key, param[key])
                }
                val u = URL(url)
                conn = u.openConnection() as HttpURLConnection
                conn!!.setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                conn!!.doOutput = true
                conn!!.doInput = true
                conn!!.requestMethod = "POST"
                val method = conn?.requestMethod
                _os = DataOutputStream(conn?.outputStream)
                val content = body.toString()
                _os?.writeBytes(content)
                _os?.flush()
                if (conn?.responseCode == HttpURLConnection.HTTP_OK) {
                    _in = InputStreamReader(conn?.inputStream)
                    val bf = BufferedReader(_in)
                    var str: String?
                    val response = StringBuilder()
                    while (bf.readLine().also { str = it } != null) {
                        response.append(str)
                    }
                    var jsonStr = response.toString()
                    val obj = Gson().fromJson<Any>(jsonStr, clazz)
                    listener.onSuccess(obj)
                } else {
                    listener.onFailed(conn?.responseCode, "get fail:$url")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onFailed(-1, "error:" + e.message)
            } finally {
                _os?.close()
                _in?.close()
                conn?.disconnect()
            }
        }
    }

    private fun encode(content: String): String {
        var content = content
        try {
            val bytes = CryptUtil.RSAEncrypt(content.toByteArray(), Key.HTTP_SERVER_PUB_KEY)
            val keyBytes: ByteArray
            keyBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getEncoder().encode(bytes)
            } else {
                Base64.encode(bytes, Base64.NO_WRAP)
            }
            content = String(keyBytes)
            content = content.replace("\n", "")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return content
        }
    }


}
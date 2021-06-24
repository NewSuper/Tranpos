package com.qx.imlib.utils.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.qx.imlib.utils.SSLUtils
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object NetUtils {
    private val TAG = NetUtils::class.java.simpleName
    private var isLatestNetWorkAvailable = false

    fun createURLConnection(urlStr: String): HttpURLConnection? {
        val conn: Any
        val url: URL
        if (urlStr.toLowerCase().startsWith("http")) {
            url = URL(urlStr)
            val c = url.openConnection() as HttpsURLConnection
            val sslContext = SSLUtils.getSSLContext()
            if (sslContext != null) {
                c.sslSocketFactory = sslContext.socketFactory
            }
            val hostnameVerifier = SSLUtils.getHostVerifier()
            if (hostnameVerifier != null) {
                c.hostnameVerifier = hostnameVerifier
            }
            conn = c
        } else {
            url = URL(urlStr)
            conn = url.openConnection() as HttpURLConnection
        }
        return conn as HttpURLConnection
    }


    fun isNetWorkAvailable(context: Context): Boolean {
        return if (context == null) {
            false
        } else {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo: NetworkInfo? = null
            try {
                networkInfo = cm.activeNetworkInfo
                Log.d(
                    TAG,
                    "network : " + if (networkInfo != null) networkInfo.isAvailable.toString() + " " + networkInfo.isConnected else "null"
                )
            } catch (var4: Exception) {
                Log.e(TAG, "getActiveNetworkInfo Exception", var4)
            }
            networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        }
    }
}
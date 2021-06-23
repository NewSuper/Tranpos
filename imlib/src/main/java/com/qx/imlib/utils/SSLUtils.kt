package com.qx.imlib.utils

import java.lang.Exception
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext

object SSLUtils {
    private var sHostnameVerifer: HostnameVerifier? = null
    private var sSSLContext: SSLContext? = null

    private fun initSSLContext() {
        try {
            sSSLContext = SSLContext.getInstance("TLS")
            sSSLContext?.init(null, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initHost() {
        sHostnameVerifer = HostnameVerifier { hostname, session ->
            true
        }
    }

    fun getSSLContext(): SSLContext? {
        if (sSSLContext == null) {
            initSSLContext()
        }
        return sSSLContext
    }

    fun setSSLContext(sslContext: SSLContext) {
        sSSLContext = sslContext
    }

    fun setHostnameVerifier(virifier: HostnameVerifier) {
        sHostnameVerifer = virifier
    }

    fun getHostVerifier(): HostnameVerifier? {
        if (sHostnameVerifer == null) {
            initHost()
        }
        return sHostnameVerifer
    }
}
package com.qx.im.model

import android.os.Build
import android.util.Base64
import com.qx.imlib.utils.ContextUtils
import com.qx.imlib.utils.SharePreferencesUtil
import java.nio.charset.Charset

object UserInfoCache {

    private var userId: String = ""
    private var token: String = ""
    private val key = "296C7F07AD27A6DABC8516CC2D87FAA0"
//    private val key = "1234567890123456"

    var appKey = ""

    fun getUserId(): String {
        if (userId.isNullOrEmpty()) {
            var data = SharePreferencesUtil.getInstance(ContextUtils.getInstance().context).getUserIdFromLocal()
            userId = decrypt(data)
        }
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
        SharePreferencesUtil.getInstance(ContextUtils.getInstance().context).updateLocalUserId(encrypt(userId))
    }

    private fun decrypt(data: String): String {
        //先转解密
//        var data = CryptUtil.AESDecrypt(data.toByteArray(), key)
        //再base64解码
        var bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.util.Base64.getDecoder().decode(data)
        } else {
            Base64.decode(data, Base64.NO_WRAP)
        }
        //转为字符串
        var result = String(bytes)

//        LogUtil.debug(this.javaClass, "加密》密文：$data 明文：$result")
        return result
    }

    private fun encrypt(userId: String): String {
        //明文先转base64
        var bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.util.Base64.getEncoder().encode(userId.toByteArray())
        } else {
            Base64.encode(userId.toByteArray(), Base64.NO_WRAP)
        }
        //再加密
//        var data = CryptUtil.AESEncrypt(bytes, key)

        //转成字符串
        var result = String(bytes, Charset.defaultCharset())
//        LogUtil.debug(this.javaClass, "加密》明文：$userId 密文：$result")

        return result
    }


    fun getToken(): String {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }


}
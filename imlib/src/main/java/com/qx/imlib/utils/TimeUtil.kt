package com.qx.imlib.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    @JvmStatic
    fun getTime(time: Long): String {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(Date(time))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
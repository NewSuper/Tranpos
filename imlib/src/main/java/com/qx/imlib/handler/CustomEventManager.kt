package com.qx.imlib.handler

import android.text.TextUtils
import com.qx.message.ICustomEventProvider

object CustomEventManager {
    private var mProviderMap = HashMap<String, ICustomEventProvider>()

    @JvmStatic
    fun registerCustomEventProvider(provider: ICustomEventProvider): Boolean {
        if (TextUtils.isEmpty(provider.customEventTag)) {
            return false
        }
        return if (isExist(provider.customEventTag)) {
            false
        } else {
            mProviderMap[provider.customEventTag] = provider
            true
        }
    }

    @JvmStatic
    fun getCustomEventProvider(tag: String): ICustomEventProvider? {
        return mProviderMap[tag]
    }

    fun isExist(tag: String): Boolean {
        return mProviderMap.keys.contains(tag)
    }
}
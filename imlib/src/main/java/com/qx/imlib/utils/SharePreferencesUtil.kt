package com.qx.imlib.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.qx.im.model.BeanSensitiveWord

class SharePreferencesUtil private constructor() {

    companion object {
        private val NAME_QX_IM_SDK = "qx_im_lib"
        private val TAG_TEXT_FILTER = "im_text_filter"
        private val TAG_CHARACTERS = "im_text_filter_special_character"
        private var KEY_USER_INFO_CACHE = "qx.im.sdk.user_info"
        private var mSp: SharedPreferences? = null

        fun getInstance(context: Context): SharePreferencesUtil {
            mSp = context.getSharedPreferences(NAME_QX_IM_SDK, Context.MODE_PRIVATE)
            return Holder.holder
        }
    }

    object Holder {
        val holder: SharePreferencesUtil = SharePreferencesUtil()
    }

    private fun saveString(key: String, content: String) {
        mSp?.edit()?.putString(key, content)?.apply()
    }

    private fun getString(key: String, defaultValue: String): String {
        return mSp?.getString(key, defaultValue)!!
    }

    fun saveSensitiveWord(json: String) {
        saveString(TAG_TEXT_FILTER, json)
    }

    fun loadSensitiveWord(): Array<BeanSensitiveWord>? {
        val json = getString(TAG_TEXT_FILTER, "")
        return Gson().fromJson(json, Array<BeanSensitiveWord>::class.java)
    }

    fun getUserIdFromLocal(): String {
        return getString(KEY_USER_INFO_CACHE, "")
    }

    fun updateLocalUserId(userId: String) {
        saveString(KEY_USER_INFO_CACHE, userId)
    }

    fun saveSpecialCharacters(chars : String) {
        saveString(TAG_CHARACTERS, chars)
    }

    fun loadSpecialCharacters() : String {
        return getString(TAG_CHARACTERS, "")
    }
}
package com.qx.imlib.utils.event

class ChatNotice {
    /**
     * 是否全局禁言
     */
    var isGlobal = false
    var sendType = ""
    var userId = ""
    var targetId = ""
    var type = ""

    /**
     * 是否整体禁言
     */
    var isAll = false

    /**
     * 是否为封禁
     */
    var isBan = false

    /**
     * set为true，cancel为false
     */
    fun isEnable(): Boolean {
        return type == "set"
    }
}
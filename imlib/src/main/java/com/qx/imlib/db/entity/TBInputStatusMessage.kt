package com.qx.imlib.db.entity

class TBInputStatusMessage() {

    var messageId: String = ""

    var content : String=""

    var extra : String=""

    companion object {
        fun obtain(messageId: String, content: String, extra: String): TBInputStatusMessage {
            var message = TBInputStatusMessage()
            message.messageId = messageId
            message.content = content
            message.extra = extra
            return message
        }
    }
}
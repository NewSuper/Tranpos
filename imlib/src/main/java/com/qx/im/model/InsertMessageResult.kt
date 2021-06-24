package com.qx.im.model

import com.qx.imlib.db.entity.MessageEntity


class InsertMessageResult {
    var newMessageCount = 0
    var messages = ArrayList<MessageEntity>()
}
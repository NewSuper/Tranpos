package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "card_manage")
class TBCardMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "origin_url")
    var originUrl :String=""

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0
}
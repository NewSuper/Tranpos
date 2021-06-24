package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil


@Entity(tableName = "geo_message")
class TBGeoMessage() {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = UUIDUtil.getUUID()

    @ColumnInfo(name = "message_id")
    var messageId: String = ""

    @ColumnInfo(name = "conversation_id")
    var conversationId: String = ""

    @ColumnInfo(name = "title")
    var title: String = "" // 标题

    @ColumnInfo(name = "address")
    var address: String = "" // 地址

    @ColumnInfo(name = "previewUrl")
    var previewUrl: String = "" // 地图缩略地址

    @ColumnInfo(name = "local_path")
    var localPath: String = "" // 地图缩略图本地路径

    @ColumnInfo(name = "lon")
    var lon: Float = 0f // 经度

    @ColumnInfo(name = "lat")
    var lat: Float = 0f// 纬度

    @ColumnInfo(name = "extra")
    var extra: String = "" // 扩展消息

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    companion object {
        fun obtain(conversationId: String, messageId: String, title: String, address: String, preivewUrl: String, localPath: String, lon: Float, lat: Float, extra: String): TBGeoMessage {
            var message = TBGeoMessage()
            message.conversationId = conversationId
            message.messageId = messageId
            message.title = title
            message.address = address
            message.previewUrl = preivewUrl
            message.localPath = localPath
            message.lon = lon
            message.lat = lat
            message.extra = extra
            return message
        }
    }
}
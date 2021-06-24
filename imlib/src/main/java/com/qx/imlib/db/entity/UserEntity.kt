package com.qx.imlib.db.entity


import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "user")
class UserEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: String? = null

    @ColumnInfo(name = "client_id")
    var clientId: String? = null

    @ColumnInfo(name = "identifier")
    var identifier: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "icon")
    var icon: String? = null
}
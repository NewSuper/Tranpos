package com.qx.imlib.db

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.qx.imlib.db.dao.*
import com.qx.imlib.db.entity.*


@Database(entities = [UserEntity::class, MessageEntity::class, TBAtToMessage::class, TBCustomMessage::class,
    TBFileMessage::class, TBGeoMessage::class, TBImageMessage::class, TBNoticeMessage::class, TBImageTextMessage::class,
    TBVideoMessage::class, TBTextMessage::class, TBAudioMessage::class, ConversationEntity::class,
    TBCallMessage::class, TBReplyMessage::class, TBRetransmissionMessage::class, TBUnTrustTime::class],version = 2)
abstract class NewIMDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao?
    abstract fun messageDao(): MessageDao?
    abstract fun conversationDao(): ConversationDao?
    abstract fun textMessageDao(): TextMessageDao?
    abstract fun atToMessageDao(): AtToMessageDao?
    abstract fun noticeMessageDao(): NoticeMessageDao?
    abstract fun imageMessageDao(): ImageMessageDao?
    abstract fun imageTextMessageDao(): ImageTextMessageDao?
    abstract fun fileMessageDao(): FileMessageDao?
    abstract fun geoMessageDao(): GeoMessageDao?
    abstract fun audioMessageDao(): AudioMessageDao?
    abstract fun videoMessageDao(): VideoMessageDao?
    abstract fun customMessageDao(): CustomMessageDao?
    abstract fun callMessageDao(): CallMessageDao?
    abstract fun replyMessageDao(): ReplyMessageDao?
    abstract fun unTrustTimeDao(): UnTrustTimeDao?
    abstract fun retransmissionMessageDao(): RetransmissionMessageDao?

    companion object {
        private var instance: NewIMDatabase? = null
        fun getDatabase(context: Context?): NewIMDatabase? {
            try {
                if (instance == null) {
                    synchronized(NewIMDatabase::class.java) {
                        if (instance == null) {
                            instance = Room.databaseBuilder(context!!, NewIMDatabase::class.java, "new_im_database.db")
                                .addMigrations(MIGRATION_1_2)
                                .build()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("NewIMDatabase", "getDatabase: 数据库异常—》》")
                e.printStackTrace()
            }
            return instance
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // ---------------------------------------
                // 当某个表的字段属性发生变化时需要按以下步骤进行
                // 1：创建一个新的临时表 CREATE TABLE users_new ...
                // 2：将数据从旧表复制到临时表 INSERT INTO users_new ...
                // 3：删了旧表 DROP TABLE users ...
                // 4：将临时表重命名为旧表名字  ALTER TABLE users_new RENAME TO users ...
                // ---------------------------------------
                // 当增加某个表并且其他表没变化时 提供一个空的migration的操作 并添加到addMigrations
                // ---------------------------------------
                // 当对某个表进行增加字段可以使用ALTER TABLE语句
                database.execSQL("ALTER TABLE call_message ADD COLUMN  end_type INTEGER NOT NULL DEFAULT 0 ");
                // ---------------------------------------
            }
        }
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // ---------------------------------------
                // 当某个表的字段属性发生变化时需要按一下步骤进行
                // 1：创建一个新的临时表 CREATE TABLE users_new ...
                // 2：将数据从旧表复制到临时表 INSERT INTO users_new ...
                // 3：删了旧表 DROP TABLE users ...
                // 4：将临时表重命名为旧表名字  ALTER TABLE users_new RENAME TO users ...
                // ---------------------------------------
                // 当增加某个表并且其他表没变化时 只需要提供一个空的migration的操作，并且添加到addMigrations
                // ---------------------------------------
                // 当对某个表进行增加字段可以使用ALTER TABLE语句
                //database.execSQL("ALTER TABLE call_message ADD COLUMN  test2 TEXT ");
                // ---------------------------------------
            }
        }
    }
}
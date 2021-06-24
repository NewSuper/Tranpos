package com.qx.imlib.db.dao;

import com.qx.imlib.db.entity.TBFileMessage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface FileMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFileMessage(TBFileMessage message);

    @Query("SELECT * FROM file_message WHERE message_id = :messageId")
    TBFileMessage getFileMessageByMessageId(String messageId);

    @Query("DELETE FROM file_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM file_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM file_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);

    @Query("UPDATE file_message SET origin_url = :originUrl WHERE message_id = :messageId")
    int updateOriginUrl(String messageId, String originUrl);

    @Query("UPDATE file_message SET local_path = :localPath WHERE message_id = :messageId")
    int updateLocalPath(String messageId,String localPath );
}

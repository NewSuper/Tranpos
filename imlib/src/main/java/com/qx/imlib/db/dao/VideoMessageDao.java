package com.qx.imlib.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.qx.imlib.db.entity.TBVideoMessage;

@Dao
public interface VideoMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertVideoMessage(TBVideoMessage videoMessage);

    @Query("SELECT * FROM video_message WHERE message_id = :messageId")
    TBVideoMessage getVideoMessageByMessageId(String messageId);

    @Query("DELETE FROM video_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM video_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM video_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);

    @Query("UPDATE video_message SET origin_url = :originUrl WHERE message_id = :messageId")
    int updateOriginUrl(String messageId, String originUrl);

    @Query("UPDATE video_message SET head_url = :headUrl WHERE message_id = :messageId")
    int updateHeadUrl(String messageId, String headUrl);

    @Query("UPDATE video_message SET local_path = :localPath WHERE message_id = :messageId")
    int updateLocalPath(String messageId,String localPath );
}

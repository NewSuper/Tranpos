package com.qx.imlib.db.dao;


import com.qx.imlib.db.entity.TBAudioMessage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface AudioMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAudioMessage(TBAudioMessage AudioMessage);

    @Query("SELECT * FROM audio_message WHERE message_id = :messageId")
    TBAudioMessage getAudioMessageByMessageId(String messageId);

    @Query("DELETE FROM at_to_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM at_to_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Query("UPDATE audio_message SET origin_url = :originUrl WHERE message_id = :messageId")
    int updateOriginUrl(String messageId,String originUrl );

    @Query("UPDATE audio_message SET local_path = :localPath WHERE message_id = :messageId")
    int updateLocalPath(String messageId,String localPath );


    @Transaction
    @Query("DELETE FROM at_to_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);
}

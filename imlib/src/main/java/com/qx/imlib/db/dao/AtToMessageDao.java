package com.qx.imlib.db.dao;


import com.qx.imlib.db.entity.TBAtToMessage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface AtToMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAtToMessage(TBAtToMessage... atToMessage);

    @Query("SELECT * FROM at_to_message WHERE message_id = :messageId")
    List<TBAtToMessage> getAtToMessageByMessageId(String messageId);

    @Query("SELECT * FROM at_to_message WHERE conversation_id = :conversationId AND (at_to = :ownerId OR at_to = '-1' ) AND read = 0 ORDER BY timestamp ASC")
    List<TBAtToMessage> getUnReadAtToMessage(String conversationId, String ownerId);

    @Query("UPDATE at_to_message SET read = :read WHERE message_id = :messageId AND conversation_id = :conversationId")
    int updateReadState(String messageId, String conversationId, int read);

    @Query("UPDATE at_to_message SET read = :read WHERE conversation_id = :conversationId")
    int updateReadStateByConversationId(String conversationId, int read);

    @Query("DELETE FROM at_to_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM at_to_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM at_to_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);
}

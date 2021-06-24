package com.qx.imlib.db.dao;

import com.qx.imlib.db.entity.TBCallMessage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface CallMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrReplaceCallMessage(TBCallMessage callMessage);

    @Query("SELECT * FROM call_message WHERE message_id = :messageId")
    TBCallMessage queryCallMsgFromMsgId(String messageId);

    @Query("SELECT * FROM call_message WHERE conversation_id = :conversation_id")
    TBCallMessage queryCallMsgFromConversationId(String conversation_id);

    @Query("DELETE FROM call_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM call_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM call_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId );
}
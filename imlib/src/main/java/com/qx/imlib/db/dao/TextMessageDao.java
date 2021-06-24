package com.qx.imlib.db.dao;

import com.qx.imlib.db.entity.QueryMatchTextCount;
import com.qx.imlib.db.entity.TBTextMessage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface TextMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTextMessage(TBTextMessage TBTextMessage);

    @Query("SELECT * FROM text_message WHERE message_id = :messageId")
    TBTextMessage getTextMessageByMessageId(String messageId);

    @Query("SELECT * FROM text_message WHERE content LIKE :text AND owner_id = :ownerId")
    List<TBTextMessage> searchMessageByText(String ownerId, String text);

    @Query("SELECT * FROM text_message WHERE content LIKE :text AND conversation_id = :conversationId AND owner_id = " +
            ":ownerId")
    List<TBTextMessage> searchMessageByText(String ownerId, String conversationId, String text);

    @Query("SELECT count(conversation_id) as match_count, conversation_id FROM text_message WHERE content" +
            " LIKE :text AND owner_id = :ownerId GROUP BY conversation_id HAVING count(conversation_id)")
    List<QueryMatchTextCount> searchMatchTextCount(String text, String ownerId);

    @Query("DELETE FROM text_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM text_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM text_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);
}
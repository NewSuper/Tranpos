package com.qx.imlib.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.qx.imlib.db.entity.TBReplyMessage;

@Dao
public interface ReplyMessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertMessage(TBReplyMessage replyMessage);

    @Query("SELECT * FROM reply_message WHERE message_id = :messageId AND owner_id = :ownerId")
    TBReplyMessage getByMessageId(String messageId, String ownerId);

    @Query("DELETE FROM reply_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM reply_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

}

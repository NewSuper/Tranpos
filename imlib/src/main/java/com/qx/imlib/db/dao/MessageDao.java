package com.qx.imlib.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.qx.imlib.db.entity.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMessage(MessageEntity messageEntity);

    /**
     * 如果为已阅读：3，则不处理更新
     */
    @Query("UPDATE message SET state = :state, failed_reason = :reason  WHERE  state != '3' AND message_id = :messageId")
    int updateMessageState(String messageId, int state, String reason);

    @Query("UPDATE message SET state = :state WHERE state != '0' AND state != '3' AND state != '4' AND conversation_id = :conversationId AND `from` != :userId")
    int updateMessageStateByConversationId(String conversationId, int state, String userId);

    @Query("UPDATE message SET message_type = :type WHERE message_id = :messageId")
    int updateMessageType(String messageId, String type);

    @Query("UPDATE message SET state = :state WHERE state != 0 AND state != 3 AND state != 4 AND send_type = :sendType AND `from` = :from AND `to` = :to  AND owner_id = :ownerId")
    int updateAllMessageStateToRead(String sendType, String from, String to, int state, String ownerId);

    @Query("UPDATE message SET state = :state, `timestamp` = :timestamp WHERE  state != '3' AND message_id = :messageId")
    int updateMessageStateAndTime(int state, long timestamp, String messageId);

    @Query("SELECT * FROM message WHERE deleted = 0 AND owner_id = :ownerId ")
    List<MessageEntity> getMessage(String ownerId);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId ")
    List<MessageEntity> getMessagesByConversationID(String conversationId);

    /**
     * 拿最新的消息记录
     *
     * @param conversationId 会话id
     */
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND deleted = 0 AND message_type != 'QX:Time' ORDER BY timestamp DESC LIMIT 1")
    MessageEntity getLatestMessaged(String conversationId);

    @Query("SELECT conversation_id FROM message WHERE message_id = :messageId AND owner_id = :ownerId LIMIT 1")
    String getConversationId(String messageId, String ownerId);

    /**
     * 拿timestamp前后duration的记录
     *
     * @param conversationId 会话id
     */
    @Query("SELECT count(message_id) FROM message WHERE message_type != 'QX:Time' AND deleted = 0 AND conversation_id = :conversationId AND timestamp between :startTime and :endTime")
    int getRecentlyMessageCount(String conversationId, long startTime, long endTime);


    /**
     * 拿第一条未读消息（时间最旧的一条）
     */
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND deleted = 0 AND state =:state AND `from` != :receiverId ORDER BY timestamp ASC LIMIT 1")
    MessageEntity getFirstUnReadMessage(int state, String receiverId, String conversationId);

    /**
     * 拿最后（最新）一条消息
     */
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND deleted = 0 AND timestamp <:timestamp ORDER BY timestamp DESC LIMIT 1")
    MessageEntity getLastMessage(long timestamp, String conversationId);

    /**
     * 拿前一条消息
     */
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId  AND deleted = 0 AND timestamp >:timestamp AND state != 0 ORDER BY timestamp ASC LIMIT 1")
    MessageEntity getFrontMessage(long timestamp, String conversationId);

    @Query("SELECT * FROM message WHERE send_type = :sendType AND deleted = 0 AND ((`from` = :from AND `to` = :to ) OR (`from` = :to  AND  `to` = :from )) AND owner_id = :ownerId ORDER BY timestamp DESC LIMIT 1")
    MessageEntity getLatestMessage(String sendType, String from, String to, String ownerId);

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND owner_id = :ownerId ORDER BY timestamp ASC LIMIT 1")
    MessageEntity getOldestMessage(String conversationId, String ownerId);

    @Query("SELECT * FROM message WHERE conversation_id = :conversationId AND owner_id = :ownerId ORDER BY timestamp DESC LIMIT 1")
    MessageEntity getLatestMessaged(String conversationId, String ownerId);

    @Query("SELECT * FROM message WHERE deleted = 0 AND message_id = :messageId LIMIT 1")
    MessageEntity getMessageById(String messageId);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId ORDER BY timestamp DESC LIMIT :pageSize OFFSET :offset")
    List<MessageEntity> getMessageByConversationId(String conversationId, int offset, int pageSize);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId  AND timestamp < :timestamp ORDER BY timestamp DESC LIMIT :pageSize ")
    List<MessageEntity> getMessagesBefore(String conversationId, long timestamp, int pageSize);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId  AND timestamp >= :timestamp ORDER BY timestamp ASC LIMIT :pageSize ")
    List<MessageEntity> getMessagesAfter(String conversationId, long timestamp, int pageSize);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId AND message_type IN (:types) ORDER BY timestamp ASC LIMIT :pageSize OFFSET :offset")
    List<MessageEntity> getMessageByTypeAsc(String conversationId, List<String> types, int offset, int pageSize);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId AND message_type IN (:types) ORDER BY timestamp DESC LIMIT :pageSize OFFSET :offset")
    List<MessageEntity> getMessageByTypeDesc(String conversationId, List<String> types, int offset, int pageSize);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId AND message_type IN (:types) ORDER BY timestamp DESC")
    List<MessageEntity> getAllMessageByTypeDesc(String conversationId, List<String> types);

    @Query("SELECT * FROM message WHERE deleted = 0 AND conversation_id = :conversationId AND message_type IN (:types) ORDER BY timestamp ASC")
    List<MessageEntity> getAllMessageByTypeAsc(String conversationId, List<String> types);

    @Query("SELECT * FROM message WHERE deleted = 0 AND message_id IN (:messageIds)")
    List<MessageEntity> getMessageInIdRegion(List<String> messageIds);

    @Query("SELECT * FROM message WHERE deleted = 0 AND state = :state AND owner_id = :ownerId")
    List<MessageEntity> getMessageByState(int state, String ownerId);

    @Query("SELECT count(message_id) FROM message WHERE deleted = 0 AND message_id = :message_id AND owner_id = :ownerId ")
    int isMessageExist(String message_id, String ownerId);

    @Query("DELETE FROM message WHERE conversation_id = :conversationId")
    int deleteMessageByConversationId(String conversationId);

//    @Query("DELETE FROM message WHERE attach_id = :attachId")
//    int deleteMessageByAttachId(attachIdString);
//
//    @Query("SELECT * FROM message WHERE deleted = 0 AND  attach_id = :attachId")
//    getMessageByAttachId(attachIdString);


    @Transaction
    @Query("DELETE FROM message WHERE owner_id = :ownerId")
    int deleteAllMessage(String ownerId);

    @Delete
    int deleteMessages(MessageEntity messageEntity);

    @Query("DELETE FROM message WHERE message_id = :messageId")
    int deleteMessageById(String messageId);

    @Query("UPDATE  message set deleted = :deleted WHERE owner_id = :ownerId AND message_id IN (:messageIds) ")
    int markMessageDelete(List<String> messageIds, int deleted,String ownerId);

    @Transaction
    @Query("DELETE FROM message WHERE owner_id = :ownerId AND timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteMessageByTimestamp(String ownerId, long timestamp, String conversationId);


}

package com.qx.imlib.db.dao;

import com.qx.imlib.db.entity.ConversationEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface ConversationDao {

    @Query("SELECT conversation_id FROM conversation WHERE conversation_type = :conversationType AND target_id = :targetId AND `owner_id` = :ownerId LIMIT 1")
    String isExistP2PConversation(String conversationType, String targetId, String ownerId);

    @Query("SELECT conversation_id FROM conversation WHERE conversation_type = :conversationType AND target_id = :targetId " +
            "AND `owner_id` = :ownerId LIMIT 1")
    String isExistGroupConversation(String conversationType, String targetId, String ownerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertConversation(ConversationEntity conversationEntity);

    @Query("UPDATE conversation SET `is_top` = :isTop WHERE   conversation_id = :conversationId ")
    int updateP2PTop(int isTop, String conversationId);

    @Query("UPDATE conversation SET `is_top` = :isTop WHERE  conversation_type = :sendType AND target_id = :targetId  AND `owner_id` = :ownerId  ")
    int updateP2PTop(String sendType, String targetId, int isTop, String ownerId);

    @Query("UPDATE conversation SET `is_top` = :isTop WHERE  conversation_type = :sendType AND target_id = :targetId AND `owner_id` = :ownerId  ")
    int updateGroupTop(String sendType, String targetId, int isTop, String ownerId);

    @Query("UPDATE conversation SET `draft` = :draft WHERE  conversation_id = :conversationId ")
    int updateDraft(String draft, String conversationId);

    @Query("UPDATE conversation SET `target_name` = :title WHERE  target_id = :targetId AND conversation_type = :type")
    int updateConversationTitle(String type, String targetId, String title);

    @Query("UPDATE conversation SET `un_read_count` = :count+`un_read_count` WHERE  conversation_type = :sendType AND" +
            " target_id = :targetId AND `owner_id` = :ownerId")
    int addConversationUnReadCount(String sendType, String targetId, int count, String ownerId);

    @Query("UPDATE conversation SET `un_read_count` = :count WHERE  conversation_type = :sendType AND target_id = :targetId " +
            "AND `owner_id` = :ownerId")
    int updateConversationUnReadCount(String sendType, String targetId, int count, String ownerId);

    @Query("UPDATE conversation SET `no_disturbing` = :no_disturbing WHERE conversation_id = :conversation_id")
    int updateNoDisturbing(int no_disturbing, String conversation_id);

    @Query("UPDATE conversation SET `no_disturbing` = :isDisturbing WHERE  conversation_type = :sendType AND target_id = :targetId AND `owner_id` = :ownerId  ")
    int updateP2PNoDisturbing(String sendType, String targetId, int isDisturbing, String ownerId);

    @Query("UPDATE conversation SET `no_disturbing` = :isDisturbing WHERE  conversation_type = :sendType AND target_id = :targetId AND `owner_id` = :ownerId  ")
    int updateGroupNoDisturbing(String sendType, String targetId, int isDisturbing, String ownerId);

    @Query("UPDATE conversation SET `time_indicator` = :timeIndicator, `top_time` = :topTime WHERE  conversation_id =" +
            " :conversationId AND `owner_id` = :ownerId ")
    int updateTimeIndicator(String conversationId, long timeIndicator, long topTime, String ownerId);

    @Query("UPDATE conversation SET `time_indicator` = :timeIndicator WHERE  conversation_id = :conversationId AND " +
            "`owner_id` = :ownerId ")
    int updateTimeIndicator(String conversationId, long timeIndicator, String ownerId);

    @Query("UPDATE conversation SET `time_indicator` = :timeIndicator, `top_time` = :topTime WHERE  conversation_type" +
            " = :sendType AND target_id = :targetId AND `owner_id` = :ownerId")
    int updateTimeIndicator(String sendType,
                            String targetId,
                            long topTime,
                            long timeIndicator,
                            String ownerId);

    @Query("UPDATE conversation SET `time_indicator` = :timeIndicator, `top_time` = :topTime WHERE  conversation_type" +
            " = :sendType AND `owner_id` = :owner_id AND target_id = :targetId")
    int updateGroupTimeIndicator(String sendType, String targetId, String owner_id, long topTime, long timeIndicator);

    @Query("SELECT * FROM conversation WHERE owner_id = :owner_id AND deleted = 0")
    List<ConversationEntity> getAllConversation(String owner_id);

    @Query("SELECT * FROM conversation WHERE owner_id = :owner_id AND deleted = 0 AND conversation_type in (:region)")
    List<ConversationEntity> getConversationInRegion(String owner_id, List<String> region);

    @Query("SELECT * FROM conversation WHERE target_name LIKE :keyWord AND conversation_type IN (:conversationTypes) AND owner_id = :ownerId ORDER BY timestamp DESC")
    List<ConversationEntity> searchConversations(String keyWord, String[] conversationTypes, String ownerId);

    @Query("SELECT * FROM conversation WHERE conversation_id = :conversionId LIMIT 1")
    ConversationEntity getConversationById(String conversionId);

    @Query("SELECT * FROM conversation WHERE target_id = :targetId AND owner_id = :ownerId AND conversation_type = :conversationType LIMIT 1")
    ConversationEntity getConversationByTargetId(String conversationType, String targetId, String ownerId);

    @Query("UPDATE conversation SET deleted = :deleted WHERE conversation_id = :conversationId")
    int markConversationDelete(int deleted, String conversationId);

    @Query("UPDATE conversation SET deleted = :deleted WHERE target_id = :targetId AND conversation_type = :type AND owner_id = :ownerId")
    int markConversationDelete(int deleted, String type, String targetId, String ownerId);

    @Transaction
    @Query("DELETE FROM conversation WHERE owner_id = :ownerId")
    int deleteAllConversation(String ownerId);

    @Query("UPDATE conversation SET `timestamp` = :timestamp  WHERE  conversation_type" +
            " = :sendType AND target_id = :targetId AND `owner_id` = :ownerId")
    int refreshConversationInfo(String sendType, String targetId, long timestamp, String ownerId);

    @Query("UPDATE conversation SET `at_to` = :at_to WHERE  conversation_type = :sendType AND target_id = :targetId AND " +
            "`owner_id` = :ownerId")
    int updateConversationAtTO(String sendType, String targetId, String at_to, String ownerId);

    @Query("UPDATE conversation SET delete_time = :deleteTime WHERE conversation_id = :conversationId")
    int updateDeleteTime(long deleteTime, String conversationId);

    @Query("UPDATE conversation SET background = :url WHERE conversation_id = :conversationId")
    int updateBackground(String url, String conversationId);

    @Query("SELECT SUM(un_read_count) FROM conversation WHERE owner_id = :ownerId  AND no_disturbing = 0 AND conversation_type in (:region)")
    int getAllUnReadCount(String ownerId, List<String> region);

    @Query("SELECT SUM(un_read_count) FROM conversation WHERE conversation_id = :conversationId AND no_disturbing = 0 AND owner_id = :ownerId")
    int getUnReadCount(String conversationId, String ownerId);

    @Query("SELECT SUM(un_read_count) FROM conversation WHERE conversation_id = :conversationId AND owner_id = :ownerId")
    int getUnReadCountIgnoreDisturbing(String conversationId, String ownerId);

    //更新会话图片
    @Query("UPDATE conversation SET `icon` = :icon WHERE  target_id = :targetId AND conversation_type = :type")
    int updateConversationIcon(String type, String targetId, String icon);

    //同时更新会话显示的用户昵称，以及图片
    @Query("UPDATE conversation SET `target_name` = :title, `icon` = :icon WHERE  target_id = :targetId AND conversation_type = :type")
    int updateConversationTitleAndIcon(String type, String targetId, String title, String icon);
}

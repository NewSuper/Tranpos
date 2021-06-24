package com.qx.imlib.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.qx.imlib.db.entity.TBGeoMessage;

@Dao
public interface GeoMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGeoMessage(TBGeoMessage message);

    @Query("SELECT * FROM geo_message WHERE message_id = :messageId")
    TBGeoMessage getGeoMessageByMessageId(String messageId);

    @Query("DELETE FROM geo_message WHERE message_id = :messageId")
    int delete(String messageId);

    @Query("DELETE FROM geo_message WHERE conversation_id = :conversationId")
    int deleteAll(String conversationId);

    @Transaction
    @Query("DELETE FROM geo_message WHERE timestamp <= :timestamp AND conversation_id = :conversationId")
    int deleteByTimestamp(long timestamp, String conversationId);

    @Query("UPDATE geo_message SET local_path = :localPath WHERE message_id = :messageId")
    int updateLocalPath(String messageId,String localPath );

    @Query("UPDATE geo_message SET previewUrl = :previewUrl WHERE message_id = :messageId")
    int updatePreviewUrl(String messageId,String previewUrl );
}

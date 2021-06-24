package com.qx.imlib.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class QueryMatchTextCount {
    @ColumnInfo(name = "conversation_id")
    public String conversationId;

    @ColumnInfo(name = "match_count")
    @NonNull
    public int matchCount;
}

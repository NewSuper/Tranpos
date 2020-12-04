package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.MemberLevel;

import java.util.List;

@Dao
public interface MemberLevelDao extends BaseDao<MemberLevel> {

    @Query("DELETE FROM pos_member_level")
    int deleteAll();

    @Query("SELECT * FROM pos_member_level")
    List<MemberLevel> loadAll();
}

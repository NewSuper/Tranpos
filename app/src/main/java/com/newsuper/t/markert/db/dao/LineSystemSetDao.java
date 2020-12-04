package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.LineSystemSet;

import java.util.List;

@Dao
public interface LineSystemSetDao extends BaseDao<LineSystemSet> {
    @Query("DELETE FROM pos_line_system_set")
    int deleteAll();


    @Query("SELECT * FROM pos_line_system_set")
    List<LineSystemSet> loadAll();
}

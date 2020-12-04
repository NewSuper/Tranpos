package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.ShortCuts;

import java.util.List;

@Dao
public interface ShortCutsDao extends BaseDao<ShortCuts> {
    @Query("DELETE FROM pos_short_cuts")
    int deleteAll();

    @Query("SELECT * FROM pos_short_cuts")
    List<ShortCuts> loadAll();
}

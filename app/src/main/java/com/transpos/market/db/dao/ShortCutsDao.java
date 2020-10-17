package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.ShortCuts;

import java.util.List;

@Dao
public interface ShortCutsDao extends BaseDao<ShortCuts> {
    @Query("DELETE FROM pos_short_cuts")
    int deleteAll();

    @Query("SELECT * FROM pos_short_cuts")
    List<ShortCuts> loadAll();
}

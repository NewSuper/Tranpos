package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.LineSystemSet;

import java.util.List;

@Dao
public interface LineSystemSetDao extends BaseDao<LineSystemSet> {
    @Query("DELETE FROM pos_line_system_set")
    int deleteAll();


    @Query("SELECT * FROM pos_line_system_set")
    List<LineSystemSet> loadAll();
}

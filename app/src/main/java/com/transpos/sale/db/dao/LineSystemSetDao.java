package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.LineSystemSet;

import java.util.List;

@Dao
public interface LineSystemSetDao extends BaseDao<LineSystemSet> {
    @Query("DELETE FROM pos_line_system_set")
    int deleteAll();


    @Query("SELECT * FROM pos_line_system_set")
    List<LineSystemSet> loadAll();
}

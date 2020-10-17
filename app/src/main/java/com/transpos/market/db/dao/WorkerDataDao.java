package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.WorkData;

import java.util.List;

@Dao
public interface WorkerDataDao extends BaseDao<WorkData> {
    @Query("DELETE FROM pos_worker_data")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_data")
    List<WorkData> loadAll();
}

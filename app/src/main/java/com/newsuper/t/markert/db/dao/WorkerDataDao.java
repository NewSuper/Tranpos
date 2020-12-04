package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.WorkData;

import java.util.List;

@Dao
public interface WorkerDataDao extends BaseDao<WorkData> {
    @Query("DELETE FROM pos_worker_data")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_data")
    List<WorkData> loadAll();
}

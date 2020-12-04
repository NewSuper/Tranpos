package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.WorkModule;

import java.util.List;

@Dao
public interface WorkerModuleDao extends BaseDao<WorkModule> {
    @Query("DELETE FROM pos_worker_module")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_module")
    List<WorkModule> loadAll();
}

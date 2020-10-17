package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.WorkModule;

import java.util.List;

@Dao
public interface WorkerModuleDao extends BaseDao<WorkModule> {
    @Query("DELETE FROM pos_worker_module")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_module")
    List<WorkModule> loadAll();
}

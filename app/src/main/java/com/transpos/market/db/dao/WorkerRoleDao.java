package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.WorkRole;

import java.util.List;

@Dao
public interface WorkerRoleDao extends BaseDao<WorkRole> {
    @Query("DELETE FROM pos_worker_role")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_role")
    List<WorkRole> loadAll();
}

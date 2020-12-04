package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.WorkRole;

import java.util.List;

@Dao
public interface WorkerRoleDao extends BaseDao<WorkRole> {
    @Query("DELETE FROM pos_worker_role")
    int deleteAll();

    @Query("SELECT * FROM pos_worker_role")
    List<WorkRole> loadAll();
}

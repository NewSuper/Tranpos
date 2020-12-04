package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.Worker;

import java.util.List;

@Dao
public interface WorkerDao extends BaseDao<Worker> {

    @Query("DELETE FROM pos_worker")
    int deleteAll();

    @Query("SELECT * FROM pos_worker")
    List<Worker> loadAll();

}

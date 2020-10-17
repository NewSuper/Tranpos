package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.Worker;

import java.util.List;

@Dao
public interface WorkerDao extends BaseDao<Worker> {

    @Query("DELETE FROM pos_worker")
    int deleteAll();

    @Query("SELECT * FROM pos_worker")
    List<Worker> loadAll();

}

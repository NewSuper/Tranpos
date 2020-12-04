package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.StoreInfo;

import java.util.List;

@Dao
public interface StoreInfoDao extends BaseDao<StoreInfo> {

    @Query("DELETE FROM pos_store_info")
    int deleteAll();

    @Query("SELECT * FROM pos_store_info")
    List<StoreInfo> loadAll();
}

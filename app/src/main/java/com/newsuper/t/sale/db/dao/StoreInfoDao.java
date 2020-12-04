package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.StoreInfo;

import java.util.List;

@Dao
public interface StoreInfoDao extends BaseDao<StoreInfo> {

    @Query("DELETE FROM pos_store_info")
    int deleteAll();

    @Query("SELECT * FROM pos_store_info")
    List<StoreInfo> loadAll();
}

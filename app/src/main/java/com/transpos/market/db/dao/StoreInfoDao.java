package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.StoreInfo;

import java.util.List;

@Dao
public interface StoreInfoDao extends BaseDao<StoreInfo> {

    @Query("DELETE FROM pos_store_info")
    int deleteAll();

    @Query("SELECT * FROM pos_store_info")
    List<StoreInfo> loadAll();
}

package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.StoreProduct;

import java.util.List;

@Dao
public interface StoreProductDao extends BaseDao<StoreProduct> {

    @Query("DELETE FROM pos_store_product")
    int deleteAll();

    @Query("SELECT * FROM pos_store_product")
    List<StoreProduct> loadAll();
}

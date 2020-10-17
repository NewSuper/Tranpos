package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.StoreProduct;

import java.util.List;

@Dao
public interface StoreProductDao extends BaseDao<StoreProduct> {

    @Query("DELETE FROM pos_store_product")
    int deleteAll();

    @Query("SELECT * FROM pos_store_product")
    List<StoreProduct> loadAll();
}

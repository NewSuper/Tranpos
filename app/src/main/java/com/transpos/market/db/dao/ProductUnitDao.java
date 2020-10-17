package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.ProductUnit;

import java.util.List;

@Dao
public interface ProductUnitDao extends BaseDao<ProductUnit> {
    @Query("DELETE FROM pos_product_unit")
    int deleteAll();

    @Query("SELECT * FROM pos_product_unit")
    List<ProductUnit> loadAll();
}

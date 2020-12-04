package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductUnit;

import java.util.List;

@Dao
public interface ProductUnitDao extends BaseDao<ProductUnit> {
    @Query("DELETE FROM pos_product_unit")
    int deleteAll();

    @Query("SELECT * FROM pos_product_unit")
    List<ProductUnit> loadAll();
}

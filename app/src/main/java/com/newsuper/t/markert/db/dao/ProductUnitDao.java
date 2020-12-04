package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.ProductUnit;

import java.util.List;

@Dao
public interface ProductUnitDao extends BaseDao<ProductUnit> {
    @Query("DELETE FROM pos_product_unit")
    int deleteAll();

    @Query("SELECT * FROM pos_product_unit")
    List<ProductUnit> loadAll();
}

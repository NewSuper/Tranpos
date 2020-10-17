package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductCategory;
import com.transpos.sale.entity.ProductUnit;

import java.util.List;

@Dao
public interface ProductUnitDao extends BaseDao<ProductUnit> {
    @Query("DELETE FROM pos_product_unit")
    int deleteAll();

    @Query("SELECT * FROM pos_product_unit")
    List<ProductUnit> loadAll();
}

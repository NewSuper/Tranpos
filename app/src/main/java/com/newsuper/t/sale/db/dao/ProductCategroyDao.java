package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductCategory;

import java.util.List;

@Dao
public interface ProductCategroyDao extends BaseDao<ProductCategory> {
    @Query("DELETE FROM pos_product_category")
    int deleteAll();

    @Query("SELECT * FROM pos_product_category")
    List<ProductCategory> loadAll();
}

package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductSpec;

import java.util.List;

@Dao
public interface ProductSpecDao extends BaseDao<ProductSpec> {

    @Query("DELETE FROM pos_product_spec")
    int deleteAll();

    @Query("SELECT * FROM pos_product_spec")
    List<ProductSpec> loadAll();
}

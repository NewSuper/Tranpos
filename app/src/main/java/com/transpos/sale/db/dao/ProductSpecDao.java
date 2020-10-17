package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductSpec;

import java.util.List;

@Dao
public interface ProductSpecDao extends BaseDao<ProductSpec> {

    @Query("DELETE FROM pos_product_spec")
    int deleteAll();

    @Query("SELECT * FROM pos_product_spec")
    List<ProductSpec> loadAll();
}

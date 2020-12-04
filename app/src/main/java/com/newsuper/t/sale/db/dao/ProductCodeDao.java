package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductCode;

import java.util.List;

@Dao
public interface ProductCodeDao extends BaseDao<ProductCode> {

    @Query("DELETE FROM pos_product_code")
    int deleteAll();

    @Query("SELECT * FROM pos_product_code")
    List<ProductCode> loadAll();
}

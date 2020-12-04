package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.ProductCode;

import java.util.List;

@Dao
public interface ProductCodeDao extends BaseDao<ProductCode> {

    @Query("DELETE FROM pos_product_code")
    int deleteAll();

    @Query("SELECT * FROM pos_product_code")
    List<ProductCode> loadAll();
}

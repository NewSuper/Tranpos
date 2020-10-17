package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductCode;

import java.util.List;

@Dao
public interface ProductCodeDao extends BaseDao<ProductCode> {

    @Query("DELETE FROM pos_product_code")
    int deleteAll();

    @Query("SELECT * FROM pos_product_code")
    List<ProductCode> loadAll();
}

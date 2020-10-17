package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.ProductCode;

import java.util.List;

@Dao
public interface ProductCodeDao extends BaseDao<ProductCode> {

    @Query("DELETE FROM pos_product_code")
    int deleteAll();

    @Query("SELECT * FROM pos_product_code")
    List<ProductCode> loadAll();
}

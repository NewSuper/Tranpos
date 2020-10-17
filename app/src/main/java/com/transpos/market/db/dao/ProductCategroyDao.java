package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.ProductCategory;

import java.util.List;

@Dao
public interface ProductCategroyDao extends BaseDao<ProductCategory> {
    @Query("DELETE FROM pos_product_category")
    int deleteAll();

    @Query("SELECT * FROM pos_product_category")
    List<ProductCategory> loadAll();
}

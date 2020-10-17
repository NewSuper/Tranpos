package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.ProductBrand;

import java.util.List;

@Dao
public interface ProductBrandDao extends BaseDao<ProductBrand> {

    @Query("DELETE FROM pos_product_brand")
    int deleteAll();

    @Query("SELECT * FROM pos_product_brand")
    List<ProductBrand> loadAll();
}

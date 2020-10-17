package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductBrand;

import java.util.List;

@Dao
public interface ProductBrandDao extends BaseDao<ProductBrand> {

    @Query("DELETE FROM pos_product_brand")
    int deleteAll();

    @Query("SELECT * FROM pos_product_brand")
    List<ProductBrand> loadAll();
}

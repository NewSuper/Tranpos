package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductBrand;

import java.util.List;

@Dao
public interface ProductBrandDao extends BaseDao<ProductBrand> {

    @Query("DELETE FROM pos_product_brand")
    int deleteAll();

    @Query("SELECT * FROM pos_product_brand")
    List<ProductBrand> loadAll();
}

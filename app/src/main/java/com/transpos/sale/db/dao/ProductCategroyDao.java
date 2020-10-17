package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductBrand;
import com.transpos.sale.entity.ProductCategory;

import java.util.List;

@Dao
public interface ProductCategroyDao extends BaseDao<ProductCategory> {
    @Query("DELETE FROM pos_product_category")
    int deleteAll();

    @Query("SELECT * FROM pos_product_category")
    List<ProductCategory> loadAll();
}

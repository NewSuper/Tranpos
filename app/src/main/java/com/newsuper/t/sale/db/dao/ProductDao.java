package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.Product;

import java.util.List;

@Dao
public interface ProductDao extends BaseDao<Product> {

    @Query("DELETE FROM pos_product")
    int deleteAll();

    @Query("SELECT * FROM pos_product")
    List<Product> loadAll();

    @Query("SELECT * FROM pos_product WHERE barCode=:barCode")
    Product findProductByCode(String barCode);
}

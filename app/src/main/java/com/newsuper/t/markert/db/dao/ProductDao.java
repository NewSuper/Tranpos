package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.Product;

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

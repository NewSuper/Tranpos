package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.ProductSpec;

import java.util.List;

@Dao
public interface ProductSpecDao extends BaseDao<ProductSpec> {

    @Query("DELETE FROM pos_product_spec")
    int deleteAll();

    @Query("SELECT * FROM pos_product_spec")
    List<ProductSpec> loadAll();
}

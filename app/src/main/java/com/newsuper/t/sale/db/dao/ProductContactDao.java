package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.ProductContact;

import java.util.List;

@Dao
public interface ProductContactDao extends BaseDao<ProductContact> {

    @Query("DELETE FROM pos_product_contact")
    int deleteAll();

    @Query("SELECT * FROM pos_product_contact")
    List<ProductContact> loadAll();

}

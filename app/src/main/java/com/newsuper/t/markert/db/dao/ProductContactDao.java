package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.ProductContact;

import java.util.List;

@Dao
public interface ProductContactDao extends BaseDao<ProductContact> {

    @Query("DELETE FROM pos_product_contact")
    int deleteAll();

    @Query("SELECT * FROM pos_product_contact")
    List<ProductContact> loadAll();

}

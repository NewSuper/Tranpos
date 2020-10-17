package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.ProductContact;

import java.util.List;

@Dao
public interface ProductContactDao extends BaseDao<ProductContact> {

    @Query("DELETE FROM pos_product_contact")
    int deleteAll();

    @Query("SELECT * FROM pos_product_contact")
    List<ProductContact> loadAll();

}

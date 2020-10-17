package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.Supplier;

import java.util.List;

@Dao
public interface SupplierDao extends BaseDao<Supplier> {

    @Query("DELETE FROM pos_supplier")
    int deleteAll();

    @Query("SELECT * FROM pos_supplier")
    List<Supplier> loadAll();
}

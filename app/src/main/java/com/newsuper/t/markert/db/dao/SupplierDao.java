package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.Supplier;

import java.util.List;

@Dao
public interface SupplierDao extends BaseDao<Supplier> {

    @Query("DELETE FROM pos_supplier")
    int deleteAll();

    @Query("SELECT * FROM pos_supplier")
    List<Supplier> loadAll();
}

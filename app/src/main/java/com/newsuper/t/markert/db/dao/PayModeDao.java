package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.PayMode;

import java.util.List;

@Dao
public interface PayModeDao extends BaseDao<PayMode> {

    @Query("DELETE FROM pos_pay_mode")
    int deleteAll();

    @Query("SELECT * FROM pos_pay_mode")
    List<PayMode> loadAll();
}

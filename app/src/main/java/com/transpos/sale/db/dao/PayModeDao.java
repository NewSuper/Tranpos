package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.PayMode;

import java.util.List;

@Dao
public interface PayModeDao extends BaseDao<PayMode> {

    @Query("DELETE FROM pos_pay_mode")
    int deleteAll();

    @Query("SELECT * FROM pos_pay_mode")
    List<PayMode> loadAll();
}

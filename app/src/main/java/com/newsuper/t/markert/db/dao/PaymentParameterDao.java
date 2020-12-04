package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.PaymentParameter;

import java.util.List;

@Dao
public interface PaymentParameterDao extends BaseDao<PaymentParameter> {

    @Query("DELETE FROM pos_payment_parameter")
    int deleteAll();

    @Query("SELECT * FROM pos_payment_parameter")
    List<PaymentParameter> loadAll();
}

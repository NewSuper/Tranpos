package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.PaymentParameter;

import java.util.List;

@Dao
public interface PaymentParameterDao extends BaseDao<PaymentParameter> {

    @Query("DELETE FROM pos_payment_parameter")
    int deleteAll();

    @Query("SELECT * FROM pos_payment_parameter")
    List<PaymentParameter> loadAll();
}

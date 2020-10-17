package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.PaymentParameter;

import java.util.List;

@Dao
public interface PaymentParameterDao extends BaseDao<PaymentParameter> {

    @Query("DELETE FROM pos_payment_parameter")
    int deleteAll();

    @Query("SELECT * FROM pos_payment_parameter")
    List<PaymentParameter> loadAll();
}

package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.OrderPay;

import java.util.List;

@Dao
public interface OrderPayDao extends BaseDao<OrderPay> {

    @Query("DELETE FROM pos_order_pay")
    int deleteAll();


    @Query("SELECT * FROM pos_order_pay")
    List<OrderPay> loadAll();

    @Query("SELECT * FROM pos_order_pay where tradeNo=:tradeNo")
    List<OrderPay> findOderPayInfos(String tradeNo);
}

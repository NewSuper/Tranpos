package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.OrderPay;

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

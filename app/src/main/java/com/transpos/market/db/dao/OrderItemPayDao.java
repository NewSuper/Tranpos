package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.OrderItemPay;

import java.util.List;

@Dao
public interface OrderItemPayDao extends BaseDao<OrderItemPay> {

    @Query("DELETE FROM pos_order_item_pay")
    int deleteAll();


    @Query("SELECT * FROM pos_order_item_pay")
    List<OrderItemPay> loadAll();
}

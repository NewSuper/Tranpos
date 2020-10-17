package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.OrderItem;

import java.util.List;

@Dao
public interface OrderItemDao extends BaseDao<OrderItem> {

    @Query("DELETE FROM pos_order_item")
    int deleteAll();

    @Query("SELECT * FROM pos_order_item")
    List<OrderItem> loadAll();

    @Query("SELECT * FROM pos_order_item WHERE tradeNo =:tradeNo")
    List<OrderItem> findByTradeNo(String tradeNo);
}

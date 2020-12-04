package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.OrderItem;

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

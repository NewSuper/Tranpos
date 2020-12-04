package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.OrderItemPay;

import java.util.List;

@Dao
public interface OrderItemPayDao extends BaseDao<OrderItemPay> {

    @Query("DELETE FROM pos_order_item_pay")
    int deleteAll();


    @Query("SELECT * FROM pos_order_item_pay")
    List<OrderItemPay> loadAll();
}

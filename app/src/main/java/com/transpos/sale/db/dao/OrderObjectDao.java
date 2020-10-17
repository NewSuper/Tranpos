package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.OrderObject;

import java.util.List;

@Dao
public interface OrderObjectDao extends BaseDao<OrderObject> {
    @Query("DELETE FROM pos_order")
    int deleteAll();

    @Query("SELECT * FROM pos_order")
    List<OrderObject> loadAll();

    @Query("SELECT * FROM pos_order WHERE tradeNo=:tardeNo")
    OrderObject checkOrderByNo(String tardeNo);
}

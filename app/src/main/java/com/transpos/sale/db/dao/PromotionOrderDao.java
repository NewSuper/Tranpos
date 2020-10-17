package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.PromotionOrder;

import java.util.List;

@Dao
public interface PromotionOrderDao extends BaseDao<PromotionOrder> {

    @Query("DELETE FROM pos_order_promotion")
    int deleteAll();

    @Query("SELECT * FROM pos_order_promotion")
    List<PromotionOrder> loadAll();

    @Query("SELECT * FROM pos_order_promotion WHERE tradeNo=:tradeNo")
    List<PromotionOrder> findPromotionOrders(String tradeNo);


}

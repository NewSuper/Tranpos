package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.PromotionOrder;

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

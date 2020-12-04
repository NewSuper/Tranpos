package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.PromotionItem;

import java.util.List;

@Dao
public interface OrderItemPromotionDao extends BaseDao<PromotionItem> {

    @Query("DELETE from pos_order_item_promotion")
    int deleteAll();


    @Query("SELECT * FROM pos_order_item_promotion")
    List<PromotionItem> loadAll();
}

package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.PromotionItem;

import java.util.List;

@Dao
public interface OrderItemPromotionDao extends BaseDao<PromotionItem> {

    @Query("DELETE from pos_order_item_promotion")
    int deleteAll();


    @Query("SELECT * FROM pos_order_item_promotion")
    List<PromotionItem> loadAll();
}

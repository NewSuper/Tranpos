package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.PromotionItem;

import java.util.List;


@Dao
public interface OrderItemPromotionDao extends BaseDao<PromotionItem> {

    @Query("DELETE from pos_order_item_promotion")
    int deleteAll();


    @Query("SELECT * FROM pos_order_item_promotion")
    List<PromotionItem> loadAll();
}

package com.transpos.market.db.manger

import com.transpos.market.base.BaseDBManager
import com.transpos.market.base.BaseDao
import com.transpos.market.db.AppDatabase
import com.transpos.market.db.dao.OrderItemPromotionDao
import com.transpos.market.entity.PromotionItem

object OrderItemPromotionDbManger : BaseDBManager<PromotionItem>() {
    override fun getDataBaseDao(): BaseDao<PromotionItem> {
        return AppDatabase.getDatabase().orderItemPromotionDao
    }


    override fun deleteAll(): Int {
        val dao : OrderItemPromotionDao = baseDao as OrderItemPromotionDao
        return dao.deleteAll()
    }


    override fun loadAll(): MutableList<PromotionItem> {
        val dao : OrderItemPromotionDao = baseDao as OrderItemPromotionDao
        return dao.loadAll()
    }
}
package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.OrderItemPromotionDao
import com.transpos.sale.entity.PromotionItem

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
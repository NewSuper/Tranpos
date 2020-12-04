package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.OrderItemPromotionDao
import com.newsuper.t.sale.entity.PromotionItem

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
package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.OrderItemPromotionDao
import com.newsuper.t.markert.entity.PromotionItem

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
package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.PromotionOrderDao
import com.newsuper.t.markert.entity.PromotionOrder

object PromotionOrderDbManger : BaseDBManager<PromotionOrder>(){

    override fun getDataBaseDao(): BaseDao<PromotionOrder> {
        return AppDatabase.getDatabase().promotionOrderDao
    }

    override fun deleteAll(): Int {
        val dao : PromotionOrderDao = baseDao as PromotionOrderDao
        return dao.deleteAll()
    }

    override fun loadAll(): MutableList<PromotionOrder> {
        val dao : PromotionOrderDao = baseDao as PromotionOrderDao
        return dao.loadAll()
    }

    fun findPromotionOrders(tradeNo: String): MutableList<PromotionOrder> {
        val dao : PromotionOrderDao = baseDao as PromotionOrderDao
        return dao.findPromotionOrders(tradeNo)
    }
}
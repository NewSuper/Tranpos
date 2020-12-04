package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.PromotionOrderDao
import com.newsuper.t.sale.entity.PromotionOrder

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
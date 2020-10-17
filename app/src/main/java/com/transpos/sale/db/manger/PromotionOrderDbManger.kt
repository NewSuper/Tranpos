package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.PromotionOrderDao
import com.transpos.sale.entity.PromotionOrder

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
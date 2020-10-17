package com.transpos.market.db.manger

import com.transpos.market.base.BaseDBManager
import com.transpos.market.base.BaseDao
import com.transpos.market.db.AppDatabase
import com.transpos.market.db.dao.PromotionOrderDao
import com.transpos.market.entity.PromotionOrder

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
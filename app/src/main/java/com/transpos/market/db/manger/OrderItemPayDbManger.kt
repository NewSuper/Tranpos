package com.transpos.market.db.manger

import com.transpos.market.base.BaseDBManager
import com.transpos.market.base.BaseDao
import com.transpos.market.db.AppDatabase
import com.transpos.market.db.dao.OrderItemPayDao
import com.transpos.market.entity.OrderItemPay


object OrderItemPayDbManger : BaseDBManager<OrderItemPay>() {
    override fun getDataBaseDao(): BaseDao<OrderItemPay> {
        return AppDatabase.getDatabase().orderItemPayDao
    }

    override fun deleteAll(): Int {
        val dao : OrderItemPayDao = baseDao as OrderItemPayDao
        return dao.deleteAll()
    }

    override fun loadAll(): MutableList<OrderItemPay> {
        val dao : OrderItemPayDao = baseDao as OrderItemPayDao
        return dao.loadAll()
    }
}